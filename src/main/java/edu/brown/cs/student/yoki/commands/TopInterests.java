package edu.brown.cs.student.yoki.commands;

import edu.brown.cs.student.yoki.driver.TriggerAction;
import edu.brown.cs.student.yoki.driver.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Subclasses searches through a list or kdTree of stars. Parses input.
 */
public class TopInterests implements TriggerAction {

  private HashMap<Integer, String> topInterests = new HashMap<Integer, String>();
  private int id;
  private User current;

  @Override
  public void action(ArrayList<String> args) {
    try {
      if (args.size() == 2) {
        id = Integer.parseInt(args.get(1));
        current = DataReader.getUserList().get(id);
        System.out.println(current.getId());
      }
    } catch (Exception e) {
      System.err.println("Your command is incorrect");
    }
  }
}
