package edu.brown.cs.student.yoki.commands;

import edu.brown.cs.student.yoki.Main;
import edu.brown.cs.student.yoki.driver.Interest;
import edu.brown.cs.student.yoki.driver.TreeFunction;
import edu.brown.cs.student.yoki.driver.TriggerAction;
import edu.brown.cs.student.yoki.driver.User;
import org.checkerframework.checker.units.qual.A;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static java.lang.System.err;
import static java.lang.System.out;

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
//          for (int i = 2; i < 8; i++) {
//            try {
//              userData.add(rs.getString(i));
//              out.print(rs.getInt(i) + ", ");
//            } catch (Exception e) {
//              err.println("Something went wrong with reading in data");
//            }
//          }
          int id = rs1.getInt("id");
          String firstName = rs1.getString("first_name");
          String lastName = rs1.getString("last_name");
          String email = rs1.getString("email");
          String password = rs1.getString("password");
          int year = rs1.getInt("year");

          int[] interests = new int[DataReader.getInterestCount()];
          for (int j = 0; j < interests.length; j++) {
            interests[j] = rs1.getInt(j + 8);
          }

          user = new User(id, firstName, lastName, email, password, year, interests);
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
