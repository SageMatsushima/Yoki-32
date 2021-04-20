package edu.brown.cs.student.yoki.util;

import edu.brown.cs.student.yoki.driver.TriggerAction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Reads a CSV file line by line.
 */
public class AddData implements TriggerAction {
  private final BufferedReader reader;

  /**
   * Constructor for a CSV Parser.
   * @param filename The path to a csv file
   * @throws Exception BufferedReader exception on bad file name
   */
  public AddData(String filename) throws Exception {
    reader = new BufferedReader(new FileReader(filename));
  }

  @Override
  public void action(ArrayList<String> args) throws SQLException, ClassNotFoundException {
    Connection conn = null;
    PreparedStatement prep;
    String filename = "data/bigData.sqlite";
    int numCols = 621;
    int numRows = 104;
    System.out.println("1");
    try {
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:" + filename;
      conn = DriverManager.getConnection(urlToDB);
      Statement stat = conn.createStatement();
      stat.executeUpdate("PRAGMA foreign_keys=ON;");
      System.out.println("2");
      for (int i = 0; i < numRows; i++) {
        String toQuest = "INSERT INTO user_interests VALUES (" + (i + 1) + ",";
        for (int j = 0; j < numCols-1; j++) {
          int zeroOrNot = new Random().nextInt(100);
          if (zeroOrNot < 5) {
            toQuest += new Random().nextInt(11) + ",";
          } else {
            toQuest += "0,";
          }
        }
        toQuest = toQuest.substring(0, toQuest.length() - 1);
        toQuest += ");";
        System.out.println(toQuest);
        prep = conn.prepareStatement(toQuest);
        prep.execute();
      }
    } catch (ClassNotFoundException e) {
      System.out.println("ERROR: ClassNotFoundException " + e);
    } catch (SQLException e) {
      System.out.println("ERROR: SQLException " + e);
    }
  }
}
