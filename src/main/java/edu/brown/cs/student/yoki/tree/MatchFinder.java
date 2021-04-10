package edu.brown.cs.student.yoki.tree;

import edu.brown.cs.student.yoki.Main;

import java.util.ArrayList;

/**
 * Subclasses searches through a list or kdTree of stars. Parses input.
 */
public class MatchFinder implements TriggerAction {

  private int k;
  private double r;
  private User starIgnored = null;
  private int[] coords = new int[DataReader.getInterestCount()];

  @Override
  public void action(ArrayList<String> args) {
    if (Double.parseDouble(args.get(1)) >= 0) {
      if (parse(args)) {
        KdTree<User> tree = Main.getKdTree();
        tree.setK(k);
        tree.setR(r);
        tree.setNodeIgnored(starIgnored);
        tree.searchAndPrint();
      }
    }
  }


  private boolean parse(ArrayList<String> args) {
    if (args.size() == 3) {
      k = Integer.parseInt(args.get(1));
      r = Double.POSITIVE_INFINITY;
      if (!setCoordsByName(args.get(2))) {
        System.err.println("ERROR: No user id found named " + args.get(2));
        return false;
      }
      return true;
    } else {
      System.err.println("ERROR: " + args + " requires the form of <match #amount #id>");
      return false;
    }
  }

  public boolean setCoordsByName(String id) {
    KdTree<User> tree = Main.getKdTree();
    ArrayList<User> users = tree.getNodeList();

    for (User usr : users) {
      if (usr.getId() == Integer.parseInt(id)) {
        starIgnored = usr;
        tree.setCoords(usr.getCoords());
        tree.setNodeIgnored(starIgnored);
        return true;
      }
    }

    return false;
  }
}
