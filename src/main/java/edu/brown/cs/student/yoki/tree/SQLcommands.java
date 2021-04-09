package edu.brown.cs.student.yoki.tree;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * This class contains the SQL commands we use throughout the maps package.
 */
public final class SQLcommands {

  private SQLcommands() {

  }

  /**
   * This method is the SQL command fo the MapReader class.
   *
   * @return PreparedStatement from the SQL query
   */
  public static PreparedStatement getUserData() {
    try {
      Connection conn = DataReader.getConnection();
      PreparedStatement prep = conn.prepareStatement("SELECT * FROM user_data");
      return prep;
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("ERROR: Issue reading in SQL");
      return null;
    }
  }

  public static PreparedStatement getUserInterest(int id) {
    try {
      Connection conn = DataReader.getConnection();
      PreparedStatement prep = conn.prepareStatement("SELECT * FROM user_data WHERE id = ?");
      prep.setInt(1, id);
      return prep;
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("ERROR: Issue reading in SQL");
      return null;
    }
  }
}
