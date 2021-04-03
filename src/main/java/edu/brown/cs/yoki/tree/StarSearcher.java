package edu.brown.cs.yoki.tree;

import edu.brown.cs.yoki.Main;

import java.util.ArrayList;

/**
 * Subclasses searches through a list or kdTree of stars. Parses input.
 */
public class StarSearcher implements TriggerAction {

  private int k;
  private double r, x, y, z;
  private Users starIgnored = null;
  private final ArrayList<Double> coords = new ArrayList<>();

  @Override
  public void action(ArrayList<String> args) {
    if (Double.parseDouble(args.get(1)) >= 0) {
      if (parse(args)) {
        KdTree<Users> tree = Main.getKdTree();

        tree.setCoords(coords);
        tree.setK(k);
        tree.setR(r);
        tree.setNodeIgnored(starIgnored);

        if (isKdCommand(args.get(0))) {
          tree.searchAndPrint();
        } else {
          tree.naiveSearchAndPrint();
        }
      }
    }
  }


  private boolean parse(ArrayList<String> args) {
    if (args.size() == 3 || args.size() == 5) {
      if (args.get(0).equals("naive_neighbors")
          || args.get(0).equals("neighbors")) {
        k = Integer.parseInt(args.get(1));
        r = Double.POSITIVE_INFINITY;
      } else {
        r = Double.parseDouble(args.get(1));
        k = Integer.MAX_VALUE;
      }

      if (args.size() == 3) {
        // k,name
        String name = args.get(2);

        if (name.length() <= 2 || (name.charAt(0) != '"')
            || (name.charAt(name.length() - 1)) != '"') {
          System.err.println("ERROR: Name must be surrounded by double quotes and nonempty");
          return false;
        }
        // Strip quotes
        name = name.substring(1, name.length() - 1);

//        if (!setCoordsByName(name)) {
//          System.err.println("ERROR: No star found named " + name);
//          return false;
//        }

      } else {
        // k,x,y,z
        starIgnored = null;

        x = Double.parseDouble(args.get(2));
        y = Double.parseDouble(args.get(3));
        z = Double.parseDouble(args.get(4));

        coords.clear();
        coords.add(x);
        coords.add(y);
        coords.add(z);
      }

      return true;
    } else {
      System.err.println("ERROR: " + args + " requires 2 or 4 additional parameters");
      return false;
    }
  }

  private boolean isKdCommand(String s) {
    return s.equals("neighbors") || s.equals("radius");
  }

  /**
   * Use the name of a star to set the search coordinates
   * and node ignored in the KD Tree.
   * @param name A string name of a star
   * @return A boolean saying if a star of the given name was found
   */
//  public boolean setCoordsByName(String name) {
//    KdTree<Users> tree = Main.getKdTree();
//    ArrayList<Users> stars = tree.getNodeList();
//    for (Users star : stars) {
//      if (star.getName().equals(name)) {
//        starIgnored = star;
//
//        x = star.getX();
//        y = star.getY();
//        z = star.getZ();
//
//        coords.clear();
//        coords.add(x);
//        coords.add(y);
//        coords.add(z);
//
//        tree.setCoords(coords);
//        tree.setNodeIgnored(starIgnored);
//
//        return true;
//      }
//    }
//
//    return false;
//  }
}
