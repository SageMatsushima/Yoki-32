package edu.brown.cs.rhill6.maps;

import edu.brown.cs.rhill6.map.MapReader;
import edu.brown.cs.rhill6.map.SearchNearest;
import edu.brown.cs.rhill6.stars.KdNode;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class MapNearestTest {


  private MapReader reader = new MapReader();
  private ArrayList<String> file = new ArrayList<>(Arrays.asList("map", "data/maps/smallMaps.sqlite3"));
  private SearchNearest nearest = new SearchNearest();
  private ArrayList<String> args = new ArrayList<>(Arrays.asList("nearest", "", ""));
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

  @Test
  public void testNearest() {
    reader.action(file);
    try {
      args.set(1, "41.82");
      args.set(2, "-71.4");
      nearest.action(args);
      KdNode output = SearchNearest.nearestNode(	41.82,	-71.4);
      assertEquals(output.toString(),"/n/0");

      args.set(1, "41.8206");
      args.set(2, "-71.4003");
      nearest.action(args);
      output = SearchNearest.nearestNode(	41.8206,	-71.4003);
      assertEquals(output.toString(),"/n/5");

      args.set(1, "100");
      args.set(2, "100");
      nearest.action(args);
      output = SearchNearest.nearestNode(100.0,	100.0);
      assertEquals(output.toString(),"/n/2");
    } catch(Exception e) {
      System.err.println("ERROR: Something is wrong");
    }
  }

}
