package edu.brown.cs.student.yoki.util;

import edu.brown.cs.student.yoki.driver.TriggerAction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;

/**
 * Reads a CSV file line by line.
 */
public class CreateTable implements TriggerAction {
  private final BufferedReader reader;

  /**
   * Constructor for a CSV Parser.
   * @param filename The path to a csv file
   * @throws Exception BufferedReader exception on bad file name
   */
  public CreateTable(String filename) throws Exception {
    reader = new BufferedReader(new FileReader(filename));
  }

  @Override
  public void action(ArrayList<String> args) throws SQLException, ClassNotFoundException {
    Connection conn = null;
    PreparedStatement prep;
    String filename = "data/bigData.sqlite";
    String[] cols = this.readLine();
    try {
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:" + filename;
      conn = DriverManager.getConnection(urlToDB);
      Statement stat = conn.createStatement();
      stat.executeUpdate("PRAGMA foreign_keys=ON;");
      for (int i = 0; i < cols.length; i++) {
        System.out.println("ALTER TABLE user_interests ADD " + cols[i] + ";");
        String toQuest = "ALTER TABLE user_interests ADD " + cols[i] + " INTEGER default 0 not null;";
        prep = conn.prepareStatement(toQuest);
        prep.executeUpdate();
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
  public String[] readLine() {
    try {
      String line = "";
      String[] queryStatement = new String[621];
      int i = 0;
      while ((line = reader.readLine()) != null) {
        String[] output = line.split(",", -1);
        if (output[2].equals("") || output[2] == null) {
          break;
        } else {
          queryStatement[i] = output[2];
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
