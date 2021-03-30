package edu.brown.cs.rhill6.stars;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class for a single star.
 */
public class Star extends KdNode {
  private String name;
  private String id;

  /**
   * Constructor that allows the user to specify all fields of the star.
   * @param id String Id
   * @param name String that gives the name of the star
   * @param x X coordinate, double
   * @param y Y coordinate, double
   * @param z Z coordinate, double
   */
  public Star(String id, String name, double x, double y, double z) {
    this.id = id;
    this.name = name;
    ArrayList<Double> coords = new ArrayList<>();
    coords.add(x);
    coords.add(y);
    coords.add(z);
    setCoords(coords);
  }

  /**
   * Get the id of the star.
   * @return Star id as an integer
   */
  public String getId() {
    return id;
  }

  /**
   * Get the name of the star.
   * @return Star name as a String
   */
  public String getName() {
    return name;
  }

  /**
   * Get the x coordinate of the star.
   * @return double
   */
  public double getX() {
    return getCoords().get(0);
  }

  /**
   * Get the y coordinate of the star.
   * @return double
   */
  public double getY() {
    return getCoords().get(1);
  }

  /**
   * Get the z coordinate of the star.
   * @return double
   */
  public double getZ() {
    return getCoords().get(2);
  }

  @Override
  public boolean equals(Object o) {

    if (!(o instanceof Star)) {
      return false;
    }

    Star s2 = (Star) o;
    boolean equal = true;

    equal = equal && (id.equals(s2.getId()));
    equal = equal && name.equals(s2.getName());
    equal = equal && (getX() == s2.getX());
    equal = equal && (getY() == s2.getY());
    equal = equal && (getZ() == s2.getZ());

    return equal;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, getX(), getY(), getZ());
  }


  /**
   * Get the Euclidean distance between the star and the given object.
   * @param o A star or an ArrayList of 3 doubles.
   * @return The distance between the star and the given object
   */
  public double distance(Object o) {

    if (o instanceof Star) {
      Star s2 = (Star) o;

      return Math.sqrt(Math.pow(getX() - s2.getX(), 2)
          + Math.pow(getY() - s2.getY(), 2)
          + Math.pow(getZ() - s2.getZ(), 2));
    } else if (o instanceof ArrayList) {
      ArrayList<Double> c = (ArrayList) o;
      if (c.size() != 3) {
        System.err.println("ERROR: distance method given wrong number of coordinates");
        return -1;
      }

      return Math.sqrt(Math.pow(getX() - c.get(0), 2)
          + Math.pow(getY() - c.get(1), 2)
          + Math.pow(getZ() - c.get(2), 2));
    } else {
      System.err.println(
          "ERROR: Star distance must take another star or an ArrayList of coordinates");
      return -1;
    }
  }

  @Override
  public String toString() {
    return id;
  }
}
