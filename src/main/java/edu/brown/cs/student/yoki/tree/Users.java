package edu.brown.cs.student.yoki.tree;


/**
 * Class for a single star.
 */
public class Users extends KdNode {
  private String firstName;
  private String lastName;
  private String id;
  private int[] interests;

  public Users(String id, String firstName, String lastName, int[] interests) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.interests = interests;
    setCoords(this.interests);
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
    return this.firstName + " " + this.lastName;
  }

  public int[] getInterests() {
    return this.interests;
  }

  /**
   * Get the Euclidean distance between the star and the given object.
   * @param o A star or an ArrayList of 3 doubles.
   * @return The distance between the star and the given object
   */
  public double distance(Object o) {

    if (o instanceof Users) {
      Users s2 = (Users) o;
      int sqDist = 0;
      for (int i = 0; i < interests.length; i++) {
        sqDist += Math.pow(getInterests()[i] - s2.getInterests()[i], 2);
      }
      return Math.sqrt(sqDist);
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
