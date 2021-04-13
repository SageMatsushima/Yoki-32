package edu.brown.cs.student.yoki.commands;


import edu.brown.cs.student.yoki.driver.Interest;
import edu.brown.cs.student.yoki.driver.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class contains the SQL commands we use throughout the maps package.
 */
public final class SQLcommands {

  private SQLcommands() {

  }


  public static PreparedStatement getAllUserData() {
    try {
      Connection conn = DataReader.getConnection();
      PreparedStatement prep = conn.prepareStatement("SELECT * FROM user_data");
      return prep;
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("ERROR: Issue reading in SQL");
      return null;
    }
  }

  public static PreparedStatement getUserIds() {
    try {
      Connection conn = DataReader.getConnection();
      PreparedStatement prep = conn.prepareStatement("SELECT id FROM user_data INNER JOIN user_interests ON user_data.id=user_interests.id;");
      return prep;
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("ERROR: Issue reading in SQL");
      return null;
    }
  }

  public static void insert(ArrayList<String> values) {
    String newDataInsert = "";
    String newInterestInsert = "";

    for (int i = 0; i < 7; i++) {
      newDataInsert += values.get(i) + ", ";
    }

    for (int i = 7; i < values.size(); i++) {
      newInterestInsert += values.get(i) + ", ";
    }

    newDataInsert.substring(0, newDataInsert.length() - 1);
    newInterestInsert.substring(0, newInterestInsert.length() - 1);

    try {
      Connection conn = DataReader.getConnection();
      PreparedStatement prep1 = conn.prepareStatement("INSERT INTO user_interests VALUES (" + newInterestInsert + ");");
      PreparedStatement prep2 = conn.prepareStatement("INSERT INTO user_data VALUES (" + newDataInsert + ");");
      prep1.execute();
      prep2.execute();
      prep1.close();
      prep2.close();

    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("ERROR: Issue reading in SQL");
    }
  }

  public static void update(int userId, HashMap<String, Interest> newInterest) {
    userId = 1;
    try {
      Connection conn = DataReader.getConnection();

      Iterator hmIterator = newInterest.entrySet().iterator();
      while (hmIterator.hasNext()) {
        Map.Entry mapElement = (Map.Entry) hmIterator.next();
        Interest interest = (Interest) mapElement.getValue();
        int value = interest.getScore();
        String key = (String) mapElement.getKey();

        PreparedStatement prep = conn.prepareStatement("UPDATE user_interests SET" + key + "='" + value + "' WHERE id=userId;");
        prep.execute();
        prep.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("ERROR: Issue reading in SQL");
    }
  }

  public static PreparedStatement getUserData() {
    try {
      Connection conn = DataReader.getConnection();
      PreparedStatement prep = conn.prepareStatement("SELECT * FROM user_data INNER JOIN user_interests ON user_data.id=user_interests.id WHERE user_data.id = ?;");
      return prep;
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("ERROR: Issue reading in SQL");
      return null;
    }
  }


  public static PreparedStatement getAll() {
    try {
      Connection conn = DataReader.getConnection();
      PreparedStatement prep = conn.prepareStatement("SELECT * FROM user_data INNER JOIN user_interests ON user_data.id=user_interests.id;");
      return prep;
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("ERROR: Issue reading in SQL");
      return null;
    }
  }

  public static PreparedStatement getUserInterests() {
    try {
      Connection conn = DataReader.getConnection();
      PreparedStatement prep = conn.prepareStatement("SELECT * FROM user_interests WHERE id = ?");
      return prep;
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("ERROR: Issue reading in SQL");
      return null;
    }
  }


  public static PreparedStatement getAllUserInterests() {
    try {
      Connection conn = DataReader.getConnection();
      PreparedStatement prep = conn.prepareStatement("SELECT * FROM user_interests");
      return prep;
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("ERROR: Issue reading in SQL");
      return null;
    }
  }

  public static void addMatch(int userId, int matchId) {
    userId = 1;
    try {
      Connection conn = DataReader.getConnection();
      PreparedStatement prep = conn.prepareStatement("INSERT INTO matches VALUES (?,?,true)");
      prep.setInt(1, userId);
      prep.setInt(2, matchId);
      prep.execute();
      prep.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("ERROR: Issue reading in SQL");
    }
  }

  public static ArrayList<User> getAllMatches(int userId) {
    userId = 1;
    try {
      Connection conn = DataReader.getConnection();
      PreparedStatement prep = conn.prepareStatement("SELECT match_id FROM matches WHERE id=? AND matched=true");
      prep.setInt(1, userId);
      ResultSet rs = prep.executeQuery();
      ArrayList<User> matches = new ArrayList<>();
      while(rs.next()) {
        PreparedStatement prep2 = getUserData();
        prep2.setInt(1, rs.getInt("match_id"));
        ResultSet rs2 = prep.executeQuery();

        int id = rs2.getInt("id");
        String firstName = rs2.getString("first_name");
        String lastName = rs2.getString("last_name");
        String email = rs2.getString("email");
        String password = rs2.getString("password");
        int year = rs2.getInt("year");

        int[] interests = new int[DataReader.getInterestCount()];
        for (int j = 0; j < interests.length; j++) {
          interests[j] = rs2.getInt(j + 8);
        }

        User user = new User(id, firstName, lastName, email, password, year, interests);
        matches.add(user);
      }
      return matches;
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("ERROR: Issue reading in SQL");
      return null;
    }
  }

  public static void addPass(int userId, int matchId) {
    userId = 1;
    try {
      Connection conn = DataReader.getConnection();
      PreparedStatement prep = conn.prepareStatement("INSERT INTO matches VALUES (?,?,false)");
      prep.setInt(1, userId);
      prep.setInt(2, matchId);
      prep.execute();
      prep.close();
      conn.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("ERROR: Issue reading in SQL");
    }
  }
}
