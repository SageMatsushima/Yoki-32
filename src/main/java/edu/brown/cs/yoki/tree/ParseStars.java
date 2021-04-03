package edu.brown.cs.yoki.tree;
import edu.brown.cs.yoki.Main;

import java.util.ArrayList;

public class ParseStars implements TriggerAction {

  public void action(ArrayList<String> args) {
    // split on median for kd tree to make it balanced
    if (args.size() == 2) {
        // filename
      try {
        CsvParser parser = new CsvParser(args.get(1));
        ArrayList<Users> users = Main.getKdTree().getNodeList();

        String[] newLine = parser.readLine();

        String[] firstLine = {"userID", "firstName", "lastName", "Biking", "Running", "Swimming",
            "Skating", "Skiing"};

        users.clear();

        String id, firstName, lastName;
        int[] interests = new int[5];

        while (true) {
          newLine = parser.readLine();
          // EOF
          if (newLine == null) {
            break;
          }
          if (newLine.length != 8) {
            System.err.println("ERROR: Line of csv has the wrong number of entries");
            return;
          }

          id = newLine[0];
          firstName = newLine[1];
          lastName = newLine[2];
          for (int i = 3; i < newLine.length; i++) {
            interests[i - 3] = Integer.parseInt(newLine[i]);
          }
          users.add(new Users(id, firstName, lastName, interests));
        }

        for (int i = 0; i < users.size(); i++) {
          System.out.println(users.get(i).getId() + ", " + users.get(i).getName());
        }
        Main.getKdTree().listToTree(users);

        System.out.println("Read " + users.size() + " users from " + args.get(1));

      } catch (Exception e) {
        e.printStackTrace();
        System.err.println("ERROR: Error reading from file " + args.get(1));
      }

    } else {
      System.err.println("ERROR: stars requires one additional argument");
    }
  }
}
