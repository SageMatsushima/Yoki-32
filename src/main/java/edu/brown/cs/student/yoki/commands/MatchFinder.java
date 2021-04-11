package edu.brown.cs.student.yoki.commands;

import edu.brown.cs.student.yoki.Main;
import edu.brown.cs.student.yoki.driver.TreeFunction;
import edu.brown.cs.student.yoki.driver.TriggerAction;
import edu.brown.cs.student.yoki.driver.User;
import edu.brown.cs.student.yoki.driver.Interest;

import java.util.ArrayList;
import java.util.HashMap;

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
