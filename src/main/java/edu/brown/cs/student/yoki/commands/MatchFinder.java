package edu.brown.cs.student.yoki.commands;

import edu.brown.cs.student.yoki.Main;
import edu.brown.cs.student.yoki.driver.TreeFunction;
import edu.brown.cs.student.yoki.driver.TriggerAction;
import edu.brown.cs.student.yoki.driver.User;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Subclasses searches through a list or kdTree of stars. Parses input.
 */
public class MatchFinder implements TriggerAction {

  private int k;
  private double r;
  private User starIgnored = null;
  private int[] coords = new int[DataReader.getInterestCount()];
  private ArrayList<User> userList = new ArrayList<User>();

  @Override
  public void action(ArrayList<String> args) {
    if (Double.parseDouble(args.get(1)) >= 0) {
      if (parse(args)) {
        TreeFunction<User> tree = Main.getKdTree();
        tree.setK(k);
        tree.setR(r);
        tree.setNodeIgnored(starIgnored);
        tree.searchAndPrint();
        userList = tree.getFound();
      }
    }
  }

  public ArrayList<Double> propertyBasedTesting(TreeFunction<User> kdTree, ArrayList<User> listOfUsers, int search, User currentUser) {
    ArrayList<Double> dist = new ArrayList<>();
    if (currentUser != null) {
      kdTree.setK(search);
      kdTree.setR(Double.POSITIVE_INFINITY);
      kdTree.setNodeIgnored(currentUser);
      kdTree.kdNeighbors();
      ArrayList<User> kdUsers = kdTree.getFound();
      for (int i = 0; i < kdUsers.size(); i++) {
        kdUsers.get(i).distance(currentUser);
      }

      kdUsers.sort(new CompareDist());
      for (int i = 0; i < kdUsers.size(); i++) {
        dist.add(kdUsers.get(i).distance(currentUser));
      }
      return dist;
    } else {
      return null;
    }
  }

  private final class CompareDist implements Comparator<User> {
    @Override
    public int compare(User a, User b) {
      return Double.compare(a.getDistance(), b.getDistance());
    }
  }
  public ArrayList<User> getUserList() {
    return userList;
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
    TreeFunction<User> tree = Main.getKdTree();
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
