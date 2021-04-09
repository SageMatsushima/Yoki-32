package edu.brown.cs.student.yoki.tree;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

/**
 * Reads a database containing tables of nodes and ways.
 */
public class DataReader implements TriggerAction {
  //setting up instance variables
  private static Connection conn;
  private static String mapDataPath = null;

  /**
   * Action command that executes the MapReader code.
   *
   * @param args List of strings
   */
  @Override
  public void action(ArrayList<String> args) {
    if (args.size() == 2) {
      String path = args.get(1);
      // Check if file exists, otherwise an empty temporary database will be created
      if (new File(path).exists()) {
        try {
          mapDataPath = path;
          allInterests();

          System.out.println("Reading data from " + path);
        } catch (SQLException | ClassNotFoundException sqlEx) {
          System.out.println("ERROR: Error reading from " + path);
        }
      } else {
        System.err.println("ERROR: " + path + " is not a valid file.");
      }
    } else {
      System.err.println("ERROR: Maps takes one additional argument");
    }
  }


  private void allInterests() throws SQLException, ClassNotFoundException {
    if (conn != null) {
      conn.close();
    }

    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:" + mapDataPath;
    conn = DriverManager.getConnection(urlToDB);
    Statement stat = conn.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys=ON");
    try {

      // Query nodes that are part of a distinct way
      PreparedStatement prep = SQLcommands.getUserData();

      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        System.out.println(rs.getInt("id") + ", " +  rs.getString("first_name")
            + ", " + rs.getString("last_name") + ", " + rs.getString("email")
            + ", " + rs.getString("password") + ", " + rs.getInt("year"));
      }
      prep.close();
      rs.close();

    } catch (Exception e) {
      System.err.println("ERROR: There was an error reading in node data");
    }
  }

  public static Connection getConnection() {
    return conn;
  }
}
