package edu.brown.cs.student.yoki.commands;

import edu.brown.cs.student.yoki.Main;
import edu.brown.cs.student.yoki.driver.TreeFunction;
import edu.brown.cs.student.yoki.driver.TriggerAction;
import edu.brown.cs.student.yoki.driver.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import static java.lang.System.err;

/**
 * Subclasses searches through a list or kdTree of stars. Parses input.
 */
public class UpdateInterests implements TriggerAction {

  private int k;
  private double r;
  private User starIgnored = null;
  private int[] coords = new int[DataReader.getInterestCount()];
  private ArrayList<User> userList = new ArrayList<User>();

  @Override
  public void action(ArrayList<String> args) {
    if (args.size() == 2) {
      if (DataReader.getdataPath() == null) {
        err.println("ERROR: No database loaded");
      } else {
        try {
          int searchId = Integer.parseInt(args.get(1));
          ArrayList<Integer> idList = new ArrayList<>();
          PreparedStatement prep = SQLcommands.getUserIds();
          // North to south, less than lat 1 and greater than lat2
          prep.setInt(1, searchId);
          ResultSet rs1 = prep.executeQuery();
          while (rs1.next()) {
            idList.add(rs1.getInt("id"));
          }
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
