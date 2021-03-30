package edu.brown.cs.rhill6.map;
import edu.brown.cs.rhill6.stars.KdNode;
import edu.brown.cs.rhill6.stars.TriggerAction;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Parses a CSV files of stars into both an array and a kd tree of stars.
 */
public class SearchNearest implements TriggerAction {
  /**
   * Parses a csv file of stars and stores them in Main.stars.
   * @param args A list of arguments. Should be stars,file
   */
  public void action(ArrayList<String> args) {
    if (args.size() == 3) {

      try {
        Double lat = Double.parseDouble(args.get(1));
        Double lon = Double.parseDouble(args.get(2));

        KdNode nearest = nearestNode(lat, lon);
        System.out.println(nearest);
      } catch (Exception e) {
        System.err.println("ERROR: nearest takes two numbers");
      }
    } else {
      System.err.println("ERROR: invalid number of arguments, must be in form (nearest # #)");
    }
  }

  /**
   * Get the nearest node to lat/lon coordinates.
   * @param lat A double
   * @param lon A double
   * @return A MapNode
   */
  public static MapNode nearestNode(Double lat, Double lon) {
    ArrayList<Double> coords = new ArrayList<>();
    coords.add(lat);
    coords.add(lon);

    AlexKDTree<MapNode> tree = MapReader.getTree();
    if (tree == null) {
      return null;
    }

    tree.setNeighbor(1);
    tree.setRadius(Double.POSITIVE_INFINITY);
    PriorityQueue<MapNode> found = tree.search(null, tree.getRoot(), coords);

    if (found.size() == 1) {
      return found.peek();
    } else {
      return null;
    }
  }
}
