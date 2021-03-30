package edu.brown.cs.rhill6.maps;

import edu.brown.cs.rhill6.map.MapReader;
import edu.brown.cs.rhill6.map.SearchWays;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class MapWayTest {

  private SearchWays way = new SearchWays();
  private MapReader reader = new MapReader();
  private ArrayList<String> file = new ArrayList<>(Arrays.asList("map", "data/maps/smallMaps.sqlite3"));
  private ArrayList<String> args = new ArrayList<>(Arrays.asList("ways", "", "", "", ""));

  @Test
  public void testSmallWays1() {
    reader.action(file);
    try {
      args.set(1, "42");
      args.set(2, "-72");
      args.set(3, "41.8");
      args.set(4, "-71.3");
      way.action(args);
      assertEquals(way.getOutput(), new ArrayList<>(Arrays.asList("/w/0", "/w/1",
        "/w/2", "/w/3", "/w/4", "/w/5", "/w/6")));
    } catch(Exception e) {
      System.err.println("ERROR: Error parsing file in testNumLines");
    }
  }
}
