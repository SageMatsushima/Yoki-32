package edu.brown.cs.rhill6.mockaroo;

import edu.brown.cs.rhill6.stars.CsvParser;
import edu.brown.cs.rhill6.stars.TriggerAction;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Parses the mock_data file.
 */
public class ParseMock implements TriggerAction {

  /**
   * Parses a file of data and prints all people if all lines conform to a MockPerson.
   * @param args An array of "mock" and a filename
   */
  public void action(ArrayList<String> args) {
    if (args.size() == 2) {
      try {
        CsvParser parser = new CsvParser(args.get(1));

        String[] newLine = parser.readLine();
        String[] firstLine = "first_name,last_name,timestamp,email,gender,street_address"
            .split(",");

        if (!Arrays.equals(newLine, firstLine)) {
          System.err.println("ERROR: Incorrect first line for mock data");
          return;
        }

        String firstName, lastName, dateTime, email, gender, address;
        ArrayList<MockPerson> people = new ArrayList<>();

        while (true) {
          newLine = parser.readLine();
          // EOF
          if (newLine == null) {
            break;
          }

          if (newLine.length != 6) {
            System.err.println("ERROR: Line of csv has the wrong number of entries");
            return;
          }

          firstName = newLine[0];
          lastName = newLine[1];
          dateTime = newLine[2];
          email = newLine[3];
          gender = newLine[4];
          address = newLine[5];

          if (dateTime.length() == 0) {
            System.err.println("ERROR: Invalid datetime");
            return;
          }

          people.add(new MockPerson(firstName, lastName, dateTime,
              email, gender, address));
        }

        for (MockPerson person : people) {
          System.out.println(person);
        }
      } catch (Exception e) {
        System.err.println("ERROR: error reading from file " + args.get(1));
      }
    } else {
      System.err.println("ERROR: mock requires one additional argument");
    }
  }
}
