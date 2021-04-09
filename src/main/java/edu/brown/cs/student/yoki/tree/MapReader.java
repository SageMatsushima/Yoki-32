package edu.brown.cs.student.yoki.tree;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Reads a database containing tables of nodes and ways.
 */
public class MapReader implements TriggerAction {
  //setting up instance variables
  private static AlexKDTree<MapNode> tree;
  private static ArrayList<MapNode> nodes = new ArrayList<>();
  private static HashMap<String, MapNode> nodeIdMap = new HashMap<>();
  private static Connection conn;
  private static String mapDataPath = null;

  /**
   * Action command that executes the MapReader code.
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

          nodes.clear();
          nodeIdMap.clear();
          node();

          System.out.println("map set to " + path);
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

  /**
   * Reads in all the nodes from the file.
   * @throws SQLException
   * @throws ClassNotFoundException
   */
  private void node() throws SQLException, ClassNotFoundException {
    if (conn != null) {
      conn.close();
    }

    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:" + mapDataPath;
    conn = DriverManager.getConnection(urlToDB);
    Statement stat = conn.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys=ON");

    // Query nodes that are part of a distinct way
    try (ResultSet rs = SQLcommands.mapReaderSQL();) {
      while (rs.next()) {
        String id = rs.getString("id");
        double latitude = rs.getDouble("latitude");
        double longitude = rs.getDouble("longitude");
        MapNode newNode = new MapNode(id, latitude, longitude);

        nodes.add(newNode);
        nodeIdMap.put(id, newNode);
      }
      rs.close();
    } catch (Exception e) {
      System.err.println("ERROR: There was an error reading in node data");
    }
    tree = new AlexKDTree<MapNode>(nodes, 2);
  }

  /**
   * Getter for the KdTree of MapNodes.
   * @return KdTree
   */
  public static AlexKDTree<MapNode> getTree() {
    return tree;
  }

  /**
   * Getter for nodes.
   * @return An arraylist of MapNodes
   */
  public static ArrayList<MapNode> getNodes() {
    return nodes;
  }

  /**
   * Getter for nodeIdMap.
   * @return A hashmap from id to MapNode
   */
  public static HashMap<String, MapNode> getNodeIdMap() {
    return nodeIdMap;
  }

  /**
   * Getter for database connection.
   * @return A database connection
   */
  public static Connection getConnection() {
    return conn;
  }

  /**
   * getter for the mapDataPath.
   * @return The paths out from nodes
   */
  public static String getMapDataPath() {
    return mapDataPath;
  }

}
