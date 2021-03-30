package edu.brown.cs.rhill6.maps;

import edu.brown.cs.rhill6.map.*;
import edu.brown.cs.rhill6.stars.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MapDijkstraTest {

  private MapReader reader = new MapReader();
  private ArrayList<String> file = new ArrayList<>(Arrays.asList("map", "data/maps/smallMaps.sqlite3"));
  private ArrayList<String> args = new ArrayList<>(Arrays.asList("route", "", "", "", ""));
  private SearchRoute search = new SearchRoute();

  /**
   * Testing route command and checking if dijkstras is reading in the right nodes and ways for small maps
   * given coordinates
   */
  @Test
  public void testRoutePoints() {
    reader.action(file);
    try {
      args.set(1, "41.8");
      args.set(2, "-71.3");
      args.set(3, "42");
      args.set(4, "-72");
      search.action(args);
      assertEquals(search.getOutput(), "/n/0 -> /n/1 : /w/0\n" +
        "/n/1 -> /n/2 : /w/1\n" +
        "/n/2 -> /n/5 : /w/4\n");
      HashMap<MapNode, Graph<MapNode, MapWay>.NodeDistance> nodeDistanceMap = Main.getGraph().getNodeDistanceMap();
      MapNode goal = Main.getGraph().getGoal();
      Graph.NodeDistance goalNode = nodeDistanceMap.get(goal);

      assertTrue(goal.getLatitude() == 41.8206);
      assertTrue(goal.getLongitude() == -71.4003);
      assertEquals(goal.getId(),  "/n/5");

      Graph.NodeDistance one = nodeDistanceMap.get(goalNode.getPrevious());
      MapNode oneNode = (MapNode) goalNode.getPrevious();
      assertTrue(oneNode.getLatitude() == 41.8206);
      assertTrue(oneNode.getLongitude() == -71.4);
      assertEquals(oneNode.getId(),  "/n/2");
      assertEquals(oneNode.getEdges().get(0).getId(),  "/w/4");

      Graph.NodeDistance two = nodeDistanceMap.get(one.getPrevious());
      MapNode twoNode = (MapNode) one.getPrevious();
      assertTrue(twoNode.getLatitude() == 41.8203);
      assertTrue(twoNode.getLongitude() == -71.4);
      assertEquals(twoNode.getEdges().get(0).getId(),  "/w/1");
      assertEquals(twoNode.getEdges().get(1).getId(),  "/w/3");
      assertEquals(twoNode.getId(),  "/n/1");

      Graph.NodeDistance three = nodeDistanceMap.get(two.getPrevious());
      MapNode threeNode = (MapNode) two.getPrevious();
      assertTrue(threeNode.getLatitude() == 41.82);
      assertTrue(threeNode.getLongitude() == -71.4);
      assertEquals(threeNode.getEdges().get(0).getId(),  "/w/0");
      assertEquals(threeNode.getEdges().get(1).getId(),  "/w/2");
      assertEquals(threeNode.getId(),  "/n/0");
      assertTrue(three.getMinDist() == 0.0);

    } catch(Exception e) {
      System.err.println("ERROR: Error parsing file in testNumLines");
    }
  }

  /**
   * Testing route command and checking if dijkstras is reading in the right nodes and ways for small maps
   * given name
   */
  @Test
  public void testRouteName() {
    reader.action(file);
    try {
      args.set(1, "\"Sootball Ln\"");
      args.set(2, "\"Chihiro Ave\"");
      args.set(3, "\"Sootball Ln\"");
      args.set(4, "\"Yubaba St\"");
      search.action(args);
      assertEquals(search.getOutput(), "/n/1 -> /n/4 : /w/3\n");
    } catch(Exception e) {
      System.err.println("ERROR: Error parsing file in testNumLines");
    }
  }

  @Test
  public void bigMap() {
    file.set(1, "data/maps/maps.sqlite3");
    reader.action(file);
    assertEquals(MapReader.getNodes().size(), 392958);
    try {
      args.set(1, "41.827282");
      args.set(2, "-71.400536");
      args.set(3, "41.8246");
      args.set(4, "-71.40271");
      search.action(args);
      assertEquals(search.getOutput(),
        "/n/4182.7140.1955940297 -> /n/4182.7140.1957915158 : /w/4182.7140.90091982.0.1\n" +
          "/n/4182.7140.1957915158 -> /n/4182.7140.1957915190 : /w/4182.7140.90091982.1.1\n" +
          "/n/4182.7140.1957915190 -> /n/4182.7140.1955940299 : /w/4182.7140.90091982.2.1\n" +
          "/n/4182.7140.1955940299 -> /n/4182.7140.1955940310 : /w/4182.7140.90091982.3.1\n" +
          "/n/4182.7140.1955940310 -> /n/4182.7140.1955940289 : /w/4182.7140.90091982.4.1\n" +
          "/n/4182.7140.1955940289 -> /n/4182.7140.1955940311 : /w/4182.7140.90091982.5.1\n" +
          "/n/4182.7140.1955940311 -> /n/4182.7140.2100936247 : /w/4182.7140.19370476.10.2\n" +
          "/n/4182.7140.2100936247 -> /n/4182.7140.1875433526 : /w/4182.7140.19370476.9.2\n" +
          "/n/4182.7140.1875433526 -> /n/4182.7140.1875433360 : /w/4182.7140.185226711.10.2\n" +
          "/n/4182.7140.1875433360 -> /n/4182.7140.1875433464 : /w/4182.7140.185226711.9.2\n" +
          "/n/4182.7140.1875433464 -> /n/4182.7140.1875433557 : /w/4182.7140.185226711.8.2\n" +
          "/n/4182.7140.1875433557 -> /n/4182.7140.1875433427 : /w/4182.7140.185226711.7.2\n" +
          "/n/4182.7140.1875433427 -> /n/4182.7140.1875433466 : /w/4182.7140.185226711.6.2\n" +
          "/n/4182.7140.1875433466 -> /n/4182.7140.1875433545 : /w/4182.7140.185226711.5.2\n" +
          "/n/4182.7140.1875433545 -> /n/4182.7140.1875433353 : /w/4182.7140.185226711.4.2\n" +
          "/n/4182.7140.1875433353 -> /n/4182.7140.1875433399 : /w/4182.7140.185226711.3.2\n" +
          "/n/4182.7140.1875433399 -> /n/4182.7140.1875433458 : /w/4182.7140.185226711.2.2\n" +
          "/n/4182.7140.1875433458 -> /n/4182.7140.1875433505 : /w/4182.7140.185226711.1.2\n" +
          "/n/4182.7140.1875433505 -> /n/4182.7140.1957908716 : /w/4182.7140.185226711.0.2\n" +
          "/n/4182.7140.1957908716 -> /n/4182.7140.1957908718 : /w/4182.7140.185226710.0.2\n" +
          "/n/4182.7140.1957908718 -> /n/4182.7140.201515130 : /w/4182.7140.177075320.0.2\n" +
          "/n/4182.7140.201515130 -> /n/4182.7140.201509106 : /w/4182.7140.19379977.2.2\n");
    } catch(Exception e) {
      System.err.println("ERROR: Error parsing file in testNumLines");
    }
  }
}
