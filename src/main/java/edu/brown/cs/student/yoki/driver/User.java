package edu.brown.cs.student.yoki.driver;

import java.util.ArrayList;
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
  private double year;
  private String images;
  private String major;
  private String bio;
  private double distance;

  private int[] interests;

  public User(int id, double year, ArrayList<String> userInfo, int[] interests) {
    this.id = id;
    this.year = year;

    this.firstName = userInfo.get(0);
    this.lastName = userInfo.get(1);
    this.email = userInfo.get(2);
    this.password = userInfo.get(3);
    this.images = userInfo.get(4);
    this.major = userInfo.get(5);
    this.bio = userInfo.get(6);

    this.interests = interests;
    setCoords(this.interests);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return this.firstName + " " + this.lastName;
  }

  public int[] getInterests() {
    return this.interests;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPassword() {
    return this.password;
  }

  public String getImages() {
    return this.images;
  }

  public String getMajor() {
    return this.major;
  }

  public String getBio() {
    return this.bio;
  }

  public double getDistance() {
    return this.distance;
  }

  public String interestsToString() {
    String str = "";
    for (int i = 0; i < this.interests.length; i++) {
      str += this.interests[i] + ", ";
    }
    return str.substring(0, str.length() - 2);
  }

  @Override
  public double distance(Object user) {
    User compareUser = (User) user;
    double matchScore = 0;
    for (int i = 0; i < interests.length; i++) {
      double relevance = interests[i] / 10.0 * compareUser.interests[i] / 10.0;
      int dist = Math.abs(interests[i] - compareUser.interests[i]);
      matchScore += relevance * (10 - dist);
    }
    distance = 1 / matchScore;
    return distance;
  }

  @Override
  public String toString() {
    String str = "id: " + this.id + ", first_name: " + this.firstName + ", last_name: " + this.lastName
        + ", email: " + this.email + ", password: " + this.password + ", year: " + this.year
        + "\nInterest Levels: " + interestsToString() + "\n";
    return str;
  }
}
