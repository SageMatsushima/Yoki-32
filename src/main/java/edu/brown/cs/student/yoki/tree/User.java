package edu.brown.cs.student.yoki.tree;


import java.util.HashMap;

/**
 * Class for a single star.
 */
public class User extends KdNode {
  private String firstName;
  private String lastName;
  private int id;
  private String email;
  private String password;
  private int year;

  private int[] interests;

  public User(int id, String firstName, String lastName, String email, String password, int year, int[] interests) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.interests = interests;
    this.email = email;
    this.password = password;
    this.year = year;
    setCoords(this.interests);
  }

  /**
   * Get the id of the star.
   * @return Star id as an integer
   */
  public int getId() {
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

    if (o instanceof User) {
      User s2 = (User) o;
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
    String str = "id: " + this.id + ", first_name: " + this.firstName + ", last_name: " + this.lastName
      + ", email: " + this.email + ", password: " + this.password + ", year: " + this.year
      + "\n" + interestsToString();
    return str;
  }

  public String interestsToString() {
    String str = "";
    for (int i = 0; i < this.interests.length; i++) {
      str += this.interests[i] + ", ";
    }
    return str.substring(0, str.length()-2);
  }
}
