package edu.brown.cs.student.yoki.commands;

import edu.brown.cs.student.yoki.driver.Interest;
import edu.brown.cs.student.yoki.driver.TriggerAction;
import edu.brown.cs.student.yoki.driver.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import static java.lang.System.*;

/**
 * Reads a database containing tables of nodes and ways.
 */
public class InterestsReader implements TriggerAction {
  private ArrayList<Integer> userInterests = new ArrayList<Integer>();

  /**
   * Executes the search ways command and allows looks for the ways within a bounded box.
   *
   * @param args List of strings
   */
  @Override
  public void action(ArrayList<String> args) {
    if (args.size() == 2) {
      if (DataReader.getdataPath() == null) {
        err.println("ERROR: No database loaded");
      } else {
        try {
          int id = Integer.parseInt(args.get(1));

          PreparedStatement prep = SQLcommands.getUserInterests();
          // North to south, less than lat 1 and greater than lat2
          prep.setInt(1, id);
          ResultSet rs = prep.executeQuery();
          for (int i = 2; i < DataReader.getInterestCount()+2; i++) {
            try {
              userInterests.add(rs.getInt(i));
              out.print(rs.getInt(i) + ", ");
            } catch (Exception e) {
              err.println("Something went wrong with reading in data");
            }
          }
          prep.close();
          rs.close();
          getTopInterests();
        } catch (Exception e) {
          e.printStackTrace();
          err.println("ERROR: must enter valid numbers");
        }
      }
    } else {
      err.println("ERROR: ways takes 4 additional arguments");
    }
  }

  public ArrayList<Integer> getUserInterests() {
    return userInterests;
  }

  public int[] getInterestsList() {
    int[] output = new int[userInterests.size()];
    for (int i = 0; i < userInterests.size(); i++) {
      output[i] = userInterests.get(i);
    }
    return output;
  }

  public ArrayList<ArrayList<Interest>>  getTopInterests() {
    HashMap<Integer, Interest> converter = DataReader.getConvert();
    ArrayList<Interest> topCommonInterests = new ArrayList<>();
    ArrayList<Interest> topOtherInterests = new ArrayList<>();
    User currentUser = DataReader.getCurrentUser();
    ArrayList<ArrayList<Interest>> topInterests = new ArrayList<>();
    int c = DataReader.getInterestCount();
    for (int i = 0; i < DataReader.getInterestCount(); i++) {
      if (userInterests.get(i) > 0) {
        if (currentUser.getInterests()[i] > 0) {
          topCommonInterests.add(new Interest(i + 1, converter.get(i + DataReader.getUserDataColumnLen() + 2).getTag(), userInterests.get(i)));
        } else {
          topOtherInterests.add(new Interest(i + 1, converter.get(i + DataReader.getUserDataColumnLen() + 2).getTag(), userInterests.get(i)));
        }
      }
    }
    topCommonInterests.sort(new DistComparator());
    topOtherInterests.sort(new DistComparator());
    topInterests.add(topCommonInterests);
    topInterests.add(topOtherInterests);
    return topInterests;
  }

  private final class DistComparator implements Comparator<Interest> {
    @Override
    public int compare(Interest a, Interest b) {
      return Integer.compare(b.getScore(), a.getScore());
    }
  }

}