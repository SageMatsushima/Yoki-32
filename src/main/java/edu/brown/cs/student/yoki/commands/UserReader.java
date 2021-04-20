package edu.brown.cs.student.yoki.commands;

import edu.brown.cs.student.yoki.driver.TriggerAction;
import edu.brown.cs.student.yoki.driver.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static java.lang.System.err;

/**
 * Subclasses searches through a list or kdTree of stars. Parses input.
 */
public class UserReader implements TriggerAction {
  private ArrayList<String> userData = new ArrayList<>();
  private User user;
  @Override
  public void action(ArrayList<String> args) {
    if (args.size() == 2) {
      if (DataReader.getdataPath() == null) {
        err.println("ERROR: No database loaded");
      } else {
        try {
          int searchId = Integer.parseInt(args.get(1));

          PreparedStatement prep = SQLcommands.getUserData();
          // North to south, less than lat 1 and greater than lat2
          prep.setInt(1, searchId);
          ResultSet rs1 = prep.executeQuery();

          ArrayList<String> userInfo = new ArrayList<String>();
          ArrayList<Integer> idYear = new ArrayList<Integer>();

          int id = rs1.getInt("id");
          double year = rs1.getDouble("year");

          userInfo.add(rs1.getString("first_name"));
          userInfo.add(rs1.getString("last_name"));
          userInfo.add(rs1.getString("email"));
          userInfo.add(rs1.getString("password"));
          userInfo.add(rs1.getString("images"));
          userInfo.add(rs1.getString("major"));
          userInfo.add(rs1.getString("bio"));

          int[] interests = new int[DataReader.getInterestCount()];
          for (int j = 0; j < interests.length; j++) {
            interests[j] = rs1.getInt(j + DataReader.getUserDataColumnLen() + 2);
          }

          user = new User(id, year, userInfo, interests);

          getUser();
          prep.close();
          rs1.close();
        } catch (Exception e) {
          e.printStackTrace();
          err.println("ERROR: must enter valid numbers");
        }
      }
    } else {
      err.println("ERROR: ways takes 4 additional arguments");
    }
  }

  public ArrayList<String> getUserData() {
    return userData;
  }

  public User getUser() {
    System.out.println(user.toString());
    return user;
  }

}
