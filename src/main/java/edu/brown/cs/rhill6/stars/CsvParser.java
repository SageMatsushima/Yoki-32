package edu.brown.cs.rhill6.stars;

import java.io.FileReader;
import java.io.BufferedReader;

/**
 * Reads a CSV file line by line.
 */
public class CsvParser {
  private final BufferedReader reader;

  /**
   * Constructor for a CSV Parser.
   * @param filename The path to a csv file
   * @throws Exception BufferedReader exception on bad file name
   */
  public CsvParser(String filename) throws Exception {
    reader = new BufferedReader(new FileReader(filename));
  }

  /**
   * Reads the next line of a CSV file.
   * @return an array of Strings, each entry in the line of the CSV file
   */
  public String[] readLine() {
    try {
      String line = reader.readLine();

      if (line == null || line.length() == 0) {
        //eof
        return null;
      } else {
        return line.split(",", -1);
      }
    } catch (Exception e) {
      System.err.println("ERROR: Error reading line");
    }

    return null;
  }
}
