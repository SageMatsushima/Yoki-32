package edu.brown.cs.rhill6.map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * This class contains the SQL commands we use throughout the maps package.
 */
public final class SQLcommands {

  private SQLcommands() { }

  /**
   * This method is the SQL command fo the SearchWays class.
   * @return PreparedStatement from the SQL query
   */
  public static PreparedStatement searchWaysSQL() {
    try {
      Connection conn = MapReader.getConnection();
      PreparedStatement prep = conn.prepareStatement("SELECT DISTINCT way.id FROM way, node WHERE "
          + "(node.id = way.start OR node.id = way.end) "
          + "AND node.latitude <= ? AND node.latitude >= ? "
          + "AND node.longitude >= ? AND node.longitude <= ?");
      return prep;
    } catch (Exception e) {
      System.err.println("ERROR: Issue reading in SQL");
      return null;
    }
  }

  /**
   * This method is the SQL command fo the SearchRoute class.
   * @return PreparedStatement from the SQL query
   */
  public static PreparedStatement searchRouteSQL() {
    try {
      Connection conn = MapReader.getConnection();
      PreparedStatement prep = conn.prepareStatement("WITH traversable AS ("
          + "  SELECT name, start s, end e FROM way"
          + "  WHERE type != '' AND type != 'unclassified'"
          + "  AND (name = ? OR name = ?)) SELECT CASE"
          + "   WHEN a1.s = a2.s OR a1.s = a2.e THEN a1.s "
          + "   ELSE a1.e END AS shared FROM traversable a1 "
          + "JOIN traversable a2 ON a1.s=a2.s OR a1.s=a2.e OR a1.e=a2.s OR a1.e=a2.e "
          + "WHERE a1.name != a2.name LIMIT 1;"
      );
      return prep;
    } catch (Exception e) {
      System.err.println("ERROR: Issue reading in SQL");
      return null;
    }
  }

  /**
   * This method is the SQL command fo the MapNode class.
   * @return PreparedStatement from the SQL query
   */
  public static PreparedStatement mapNodeSQL() {
    try {
      Connection conn = MapReader.getConnection();
      PreparedStatement prep = conn.prepareStatement(
          "SELECT id, name, type, start, end FROM way "
            + "WHERE type != 'unclassified' AND type != ''"
            + "AND start=? ");
      return prep;
    } catch (Exception e) {
      System.err.println("ERROR: Issue reading in SQL");
      return null;
    }
  }

  /**
   * This method is the SQL command fo the MapReader class.
   * @return PreparedStatement from the SQL query
   */
  public static ResultSet mapReaderSQL() {
    try {
      Connection conn = MapReader.getConnection();
      String sql1 = "WITH traversable_ways AS (SELECT start, end FROM way "
          + "WHERE type != 'unclassified' AND type != '') "
          + "SELECT DISTINCT n.id, n.latitude, n.longitude FROM traversable_ways w "
          + "JOIN node n ON w.start = n.id OR w.end = n.id;";
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql1);
      return rs;
    } catch (Exception e) {
      System.err.println("ERROR: Issue reading in SQL");
      return null;
    }
  }
}
