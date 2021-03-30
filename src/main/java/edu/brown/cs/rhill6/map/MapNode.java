package edu.brown.cs.rhill6.map;
import edu.brown.cs.rhill6.stars.KdNode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Class for a node in the map.
 */
public class MapNode extends KdNode implements Node<MapNode, MapWay> {
  private String id;
  private static final double EARTH_RADIUS = 6371.0;
  /**
   * Constructor for a MapNode.
   * @param id A string id for the node
   * @param latitude A double
   * @param longitude A double
   */
  public MapNode(String id, double latitude, double longitude) {
    this.id = id;
    ArrayList<Double> coords = new ArrayList<>();
    coords.add(latitude);
    coords.add(longitude);
    setCoords(coords);
  }

  /**
   * This method gets outgoing edges from a node as we traverse through dijsktras.
   * @return a list of ways going out from a node
   */
  @Override
  public List<MapWay> getEdges() {
    try {
      PreparedStatement prep = SQLcommands.mapNodeSQL();

      prep.setString(1, id);
      ArrayList<MapWay> edges = new ArrayList<>();
      ResultSet rs = prep.executeQuery();
      HashMap<String, MapNode> nodeIdMap = MapReader.getNodeIdMap();
      //Running through the the result set and getting the node data
      while (rs.next()) {
        String nodeid = rs.getString("id");
        String name = rs.getString("name");
        String startId = rs.getString("start");
        String endId = rs.getString("end");

        MapNode start = nodeIdMap.get(startId);
        MapNode end =  nodeIdMap.get(endId);

        MapWay newEdge = new MapWay(nodeid, name, start, end);
        edges.add(newEdge);
      }
      prep.close();
      rs.close();
      return edges;
    } catch (Exception e) {
      System.err.println("ERROR: Error reading from database");
      return new ArrayList<>();
    }
  }

  /**
   * Get the id of the star.
   * @return Star id as an integer
   */
  public String getId() {
    return id;
  }

  /**
   * Get the latitude coordinates.
   * @return double
   */
  public double getLatitude() {
    return getCoords().get(0);
  }

  /**
   * Get the longitude coordinate.
   * @return double
   */
  public double getLongitude() {
    return getCoords().get(1);
  }

  /**
   * This method checks if the latitude and longitude of the inputs are the same.
   * @param o object
   * @return
   */
  @Override
  public boolean equals(Object o) {

    if (!(o instanceof MapNode)) {
      return false;
    }

    MapNode s2 = (MapNode) o;
    boolean equal = true;

    equal = equal && (id.equals(s2.getId()));
    equal = equal && (getLatitude() == s2.getLatitude());
    equal = equal && (getLongitude() == s2.getLongitude());

    return equal;
  }

  /**
   * This method hashes the id, latitude and longitude.
   * @return a hash code
   */
  @Override
  public int hashCode() {
    return Objects.hash(id, getLatitude(), getLongitude());
  }


  /**
   * Get the Euclidean distance between the star and the given object.
   * @param o A star or an ArrayList of 3 doubles.
   * @return The distance between the star and the given object
   */
  public double distance(Object o) {
    if (o instanceof MapNode) {
      MapNode s2 = (MapNode) o;

      double lat1 = getLatitude();
      double lon1 = getLongitude();
      double lat2 = s2.getLatitude();
      double lon2 = s2.getLongitude();

      return 2 * EARTH_RADIUS * Math.asin(Math.sqrt(
          Math.pow(Math.sin((lat2 - lat1) / 2), 2)
          + Math.cos(lat1) * Math.cos(lat2)
          * Math.pow(Math.sin((lon2 - lon1) / 2), 2)
      ));
    } else {
      System.err.println(
          "ERROR: Node distance must take another MapNode");
      return -1;
    }
  }

  /**
   * Gets the id of the Node.
   * @return node id
   */
  @Override
  public String toString() {
    return id;
  }
}
