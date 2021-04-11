package edu.brown.cs.student.yoki.commands;

import edu.brown.cs.student.yoki.driver.TriggerAction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Reads a database containing tables of nodes and ways.
 */
public class InterestsReader implements TriggerAction {

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
          int i = 2;
          boolean hasNextInterest = true;
          while (hasNextInterest) {
            try {
              System.out.print(rs.getInt(i) + ", ");
              i++;
            } catch (Exception e) {
              hasNextInterest = false;
            }
          }
          System.out.println();
          prep.close();
          rs.close();
        } catch (Exception e) {
          e.printStackTrace();
          System.err.println("ERROR: must enter valid numbers");
        }
      }
    } else {
      System.err.println("ERROR: ways takes 4 additional arguments");
    }
  }
}