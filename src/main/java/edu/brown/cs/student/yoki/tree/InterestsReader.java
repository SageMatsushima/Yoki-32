package edu.brown.cs.student.yoki.tree;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

/**
 * Reads a database containing tables of nodes and ways.
 */
public class InterestsReader implements TriggerAction {

  private final ArrayList<String> output = new ArrayList<>();

  /**
   * Executes the search ways command and allows looks for the ways within a bounded box.
   * @param args List of strings
   */
  @Override
//  public void action(ArrayList<String> args) {
//    if (args.size() == 5) {
//      if (DataReader.getdataPath() == null) {
//        System.err.println("ERROR: No database loaded");
//      } else {
//        try {
//          double lat1 = Double.parseDouble(args.get(1));
//          double lon1 = Double.parseDouble(args.get(2));
//          double lat2 = Double.parseDouble(args.get(3));
//          double lon2 = Double.parseDouble(args.get(4));
//
//          PreparedStatement prep = SQLcommands.searchWaysSQL();
//          // North to south, less than lat 1 and greater than lat2
//          prep.setDouble(1, lat1);
//          prep.setDouble(2, lat2);
//
//          // West to east, greater than lon1 and less than lon2
//          prep.setDouble(3, lon1);
//          prep.setDouble(4, lon2);
//
//          ResultSet rs = prep.executeQuery();
//
//          while (rs.next()) {
//            System.out.println(rs.getString("id"));
//            output.add(rs.getString("id"));
//          }
//
//          prep.close();
//          rs.close();
//        } catch (Exception e) {
//          e.printStackTrace();
//          System.err.println("ERROR: must enter valid numbers");
//        }
//      }
//    } else {
//      System.err.println("ERROR: ways takes 4 additional arguments");
//    }
//  }

  /**
   * Getter for the output.
   * @return A list of strings representing the found ways
   */
  public ArrayList<String> getOutput() {
    return output;
  }
}