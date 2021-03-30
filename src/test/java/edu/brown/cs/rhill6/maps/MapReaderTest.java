package edu.brown.cs.rhill6.maps;

import edu.brown.cs.rhill6.map.MapReader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class MapReaderTest {

  private MapReader reader = new MapReader();
  private ArrayList<String> args = new ArrayList<>(Arrays.asList("map", ""));

  @Test
  public void readerSmall() {
    try {
      args.set(1, "data/maps/smallMaps.sqlite3");
      reader.action(args);
      assertEquals(MapReader.getNodes().size(), 6);
    } catch(Exception e) {
      System.err.println("ERROR: Error parsing file in testNumLines");
    }
  }
}
