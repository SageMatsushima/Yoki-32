package edu.brown.cs.rhill6.stars;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Parses a CSV files of stars into both an array and a kd tree of stars.
 */
public class ParseStars implements TriggerAction {

  /**
   * Parses a csv file of stars and stores them in Main.stars.
   * @param args A list of arguments. Should be stars,file
   */
  public void action(ArrayList<String> args) {
    // split on median for kd tree to make it balanced
    if (args.size() == 2) {
        // filename
      try {
        CsvParser parser = new CsvParser(args.get(1));
        ArrayList<Star> stars = Main.getKdTree().getNodeList();

        String[] newLine = parser.readLine();
        String[] firstLine = {"StarID", "ProperName", "X", "Y", "Z"};

        if (!Arrays.equals(newLine, firstLine)) {
          System.err.println("ERROR: Incorrect first line for stars data");
          return;
        }

        stars.clear();

        String id, name;
        double x, y, z;

        while (true) {
          newLine = parser.readLine();
          // EOF
          if (newLine == null) {
            break;
          }

          if (newLine.length != 5) {
            System.err.println("ERROR: Line of csv has the wrong number of entries");
            return;
          }

          id = newLine[0];
          name = newLine[1];
          x = Double.parseDouble(newLine[2]);
          y = Double.parseDouble(newLine[3]);
          z = Double.parseDouble(newLine[4]);

          stars.add(new Star(id, name, x, y, z));
        }

        Main.getKdTree().listToTree(stars);

        System.out.println("Read " + stars.size() + " stars from " + args.get(1));

      } catch (Exception e) {
        System.err.println("ERROR: Error reading from file " + args.get(1));
      }

    } else {
      System.err.println("ERROR: stars requires one additional argument");
    }
  }
}
