package edu.brown.cs.student.yoki.commands;

import edu.brown.cs.student.yoki.driver.Interest;
import edu.brown.cs.student.yoki.driver.TriggerAction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

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
        System.err.println("ERROR: No database loaded");
      } else {
        try {
          int id = Integer.parseInt(args.get(1));

          PreparedStatement prep = SQLcommands.getUserInterests();
          // North to south, less than lat 1 and greater than lat2
          prep.setInt(1, id);
          ResultSet rs = prep.executeQuery();
          for (int i = 2; i < DataReader.getInterestCount(); i++) {
            try {
              userInterests.add(rs.getInt(i));
              System.out.print(rs.getInt(i) + ", ");
            } catch (Exception e) {
              System.err.println("Something went wrong with reading in data");
            }
          }
          prep.close();
          rs.close();
          getTopInterests();
        } catch (Exception e) {
          e.printStackTrace();
          System.err.println("ERROR: must enter valid numbers");
        }
      }
    } else {
      System.err.println("ERROR: ways takes 4 additional arguments");
    }
  }

  public ArrayList<Integer> getUserInterests() {
    return userInterests;
  }

  public HashMap<Integer, Interest> getTopInterests() {
    HashMap<Integer, Interest> currentInterest = new HashMap<Integer, Interest>();
    HashMap<Integer, Interest> converter = DataReader.getConvert();
    ArrayList<Interest> topInterests = new ArrayList<>();
//    DataReader.printHash();
//    DataReader.getInterestCount()-8
    for (int i = 0; i < DataReader.getInterestCount()-2; i++) {
      if (userInterests.get(i) > 0) {
        currentInterest.put(i, new Interest(i, converter.get(i+8).getTag(), userInterests.get(i)));
        topInterests.add(new Interest(i, converter.get(i+8).getTag(), userInterests.get(i)));
        System.out.println((i+8) + ", " + converter.get(i+8).getTag() + ", " + userInterests.get(i));
      }
    }
    topInterests.sort(new DistComparator());
    return currentInterest;
  }

  private final class DistComparator implements Comparator<Interest> {
    @Override
    public int compare(Interest a, Interest b) {
      return Integer.compare(b.getScore(), a.getScore());
    }
  }

}