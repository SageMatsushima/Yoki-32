package edu.brown.cs.student.yoki.driver;

import java.util.HashMap;

/**
 * Class for a single star.
 */
public class User extends KdNode {
  private int id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private int year;
  private String images;
  private String major;
  private String bio;

  private int[] interests;

  public User(int id, String firstName, String lastName, String email, String password, int year, int[] interests, String images, String major, String bio) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.interests = interests;
    this.email = email;
    this.password = password;
    this.year = year;
    this.images = images;
    this.major = major;
    this.bio = bio;
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

  public double distance(Object o) {
    return 0; }

  @Override
  public String toString() {
    String str = "id: " + this.id + ", first_name: " + this.firstName + ", last_name: " + this.lastName
        + ", email: " + this.email + ", password: " + this.password + ", year: " + this.year
        + "\nInterest Levels: " + interestsToString() + "\n";
    return str;
  }



  public String interestsToString() {
    String str = "";
    for (int i = 0; i < this.interests.length; i++) {
      str += this.interests[i] + ", ";
    }
    return str.substring(0, str.length() - 2);
  }
}
