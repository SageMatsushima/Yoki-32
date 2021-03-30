package edu.brown.cs.rhill6.map;

/**
 * Class for a way in the map.
 */
public class MapWay implements Edge<MapNode, MapWay> {
  private String id;
  private String name;
  private MapNode start;
  private MapNode end;

  /**
   * Constructor for MapWay.
   * @param id Id string
   * @param name Name string
   * @param start Start node
   * @param end End node
   */
  public MapWay(String id, String name, MapNode start, MapNode end) {
    this.id = id;
    this.name = name;
    this.start = start;
    this.end = end;
  }

  /**
   * Getter for way id.
   * @return Id string
   */
  public String getId() {
    return id;
  }

  /**
   * Getter for way name.
   * @return Name string
   */
  public String getName() {
    return name;
  }

  @Override
  public MapNode getStart() {
    return start;
  }

  @Override
  public MapNode getEnd() {
    return end;
  }

  @Override
  public double getWeight() {
    return end.distance(start);
  }

  @Override
  public String toString() {
    return id;
  }
}
