package edu.brown.cs.yoki.tree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Reads a CSV file line by line.
 */
public class AddUsers implements TriggerAction{
  private final BufferedReader reader;

  /**
   * Constructor for a CSV Parser.
   * @param filename The path to a csv file
   * @throws Exception BufferedReader exception on bad file name
   */
  public AddUsers(String filename) throws Exception {
    reader = new BufferedReader(new FileReader(filename));
  }

  @Override
  public void action(ArrayList<String> args) throws SQLException, ClassNotFoundException {
    Connection conn = null;
    PreparedStatement prep;
    String filename = "data/bigData.sqlite";
    String[][] data = this.readLine();
    int numRows = 100;
    try {
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:" + filename;
      conn = DriverManager.getConnection(urlToDB);
      Statement stat = conn.createStatement();
      stat.executeUpdate("PRAGMA foreign_keys=ON;");
      for (int i = 0; i < numRows; i++) {
        String toQuest = "INSERT INTO user_data VALUES (" + (i + 5) + ",'" + data[i][0] + "','" + data[i][1] + "','" + data[i][2] + "',123,202";
        toQuest +=  new Random().nextInt(4) + 1;
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

  /**
   * Reads the next line of a CSV file.
   * @return an array of Strings, each entry in the line of the CSV file
   */
  public String[][] readLine() {
    try {
      String line = "";
      String[][] queryStatement = new String[621][3];
      int i = 0;
      while ((line = reader.readLine()) != null) {
        String[] output = line.split(",", -1);
        if (output[0].equals("") || output[0] == null) {
          break;
        } else {
          queryStatement[i][0] = output[0];
          queryStatement[i][1] = output[1];
          queryStatement[i][2] = output[0].toLowerCase() + "_" + output[1].toLowerCase() + "@brown.edu";
          i++;
        }
      }
      return queryStatement;
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("ERROR: Error reading line");
    }

    return null;
  }
}
