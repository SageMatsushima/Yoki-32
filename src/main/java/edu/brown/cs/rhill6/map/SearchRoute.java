package edu.brown.cs.rhill6.map;

import edu.brown.cs.rhill6.stars.Main;
import edu.brown.cs.rhill6.stars.TriggerAction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for the route command, does an A* search.
 */
public class SearchRoute implements TriggerAction {
  /**
   * Executes the action command that looks for the shortest route.
   * @param args List of strings
   */
  @Override
  public void action(ArrayList<String> args) {
    if (args.size() == 5) {
      if (args.get(1).charAt(0) == '"') {
        // If args in quotes, street intersections
        intersectionSearch(args);
      } else {
        // First arg doesn't start with quotes, try long/lat search
        coordinateSearch(args);
      }
    } else {
      System.err.println("ERROR: route takes 4 additional arguments");
    }
  }

  /**
   * Search for the route given coordinates.
   * @param args the input command
   */
  private void coordinateSearch(ArrayList<String> args) {
    try {
      double lat1 = Double.parseDouble(args.get(1));
      double lon1 = Double.parseDouble(args.get(2));
      double lat2 = Double.parseDouble(args.get(3));
      double lon2 = Double.parseDouble(args.get(4));

      MapNode start = SearchNearest.nearestNode(lat1, lon1);
      MapNode end = SearchNearest.nearestNode(lat2, lon2);

      if (start == null || end == null) {
        System.err.println("Error: Start or end node not found");
        return;
      }

      Main.getGraph().dijkstra(start, end);

    } catch (Exception e) {
      System.err.println("ERROR: route must be given numeric coordinates "
          + "or street names in quotes");
    }
  }

  /**
   * Search for the route given a name.
   * @param args the input command
   */
  private void intersectionSearch(ArrayList<String> args) {
    if (parseNames(args)) {
      String s1 = args.get(1);
      String c1 = args.get(2);
      String s2 = args.get(3);
      String c2 = args.get(4);

      if (s1.equals(c1) || s2.equals(c2)) {
        System.err.println("ERROR: Street and cross street must have distinct names");
        return;
      }

      try {
        PreparedStatement prep = SQLcommands.searchRouteSQL();

        prep.setString(1, s1);
        prep.setString(2, c1);

        ResultSet rs = prep.executeQuery();

        String start;
        String end;

        if (rs.next()) {
          start = rs.getString("shared");
        } else {
          System.err.println("ERROR: No such intersection");
          return;
        }

        prep.setString(1, s2);
        prep.setString(2, c2);

        rs = prep.executeQuery();

        if (rs.next()) {
          end = rs.getString("shared");
        } else {
          System.err.println("ERROR: No such intersection");
          return;
        }

        prep.close();
        rs.close();

        HashMap<String, MapNode> nodeIdMap = MapReader.getNodeIdMap();
        Main.getGraph().dijkstra(nodeIdMap.get(start), nodeIdMap.get(end));

      } catch (Exception e) {
        e.printStackTrace();
        System.err.println("ERROR: Error reading from database");
      }

    } else {
      System.err.println("ERROR: route must be given numeric coordinates "
          + "or street names in quotes");
    }
  }

  /**
   * Given the input command, parse the names.
   * @param args a list strings representing the input command
   * @return if the arguments are correct
   */
  private boolean parseNames(ArrayList<String> args) {
    String s1 = args.get(1);
    String c1 = args.get(2);
    String s2 = args.get(3);
    String c2 = args.get(4);

    if (nameQuoted(s1) && nameQuoted(c1) && nameQuoted(s2) && nameQuoted(c2)) {
      args.set(1, s1.substring(1, s1.length() - 1));
      args.set(2, c1.substring(1, c1.length() - 1));
      args.set(3, s2.substring(1, s2.length() - 1));
      args.set(4, c2.substring(1, c2.length() - 1));
      return true;
    } else {
      return false;
    }
  }

  /**
   * Check if the input name is valid.
   * @param s command string
   * @return a boolean if the string is valid
   */
  private boolean nameQuoted(String s) {
    return s.length() > 1 && s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"';
  }

  /**
   * Getter for the output path.
   * @return A string of the path
   */
  public String getOutput() {
    return Main.getGraph().getOutput();
  }
}
