package edu.brown.cs.rhill6.stars;

import java.util.ArrayList;

/**
 * Interface used for adding commands to REPL.
 */
public interface TriggerAction {
  /**
   * Action to execute.
   * @param args List of strings
   */
  void action(ArrayList<String> args);
}
