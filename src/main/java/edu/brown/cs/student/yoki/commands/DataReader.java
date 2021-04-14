package edu.brown.cs.student.yoki.commands;

import edu.brown.cs.student.yoki.Main;
import edu.brown.cs.student.yoki.driver.Interest;
import edu.brown.cs.student.yoki.driver.TriggerAction;
import edu.brown.cs.student.yoki.driver.User;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Reads a database containing tables of nodes and ways.
 */
public class DataReader implements TriggerAction {
  //setting up instance variables
  private static Connection conn;
  private static String dataPath = null;
  private static ArrayList<User> userList = new ArrayList<User>();
  private static int interestCount;
  private static HashMap<Integer, Interest> convert = new HashMap<>();
  private static int currentId = 1;
  private static User currentUser;

  /**
   * Action command that executes the MapReader code.
   *
   * @param args List of strings
   */
  @Override
  public void action(ArrayList<String> args) {
    if (args.size() == 2) {
      String path = args.get(1);
      // Check if file exists, otherwise an empty temporary database will be created
      if (new File(path).exists()) {
        try {
          dataPath = path;
          allUserData();
//          printHash();
          System.out.println("Reading data from " + path);
        } catch (SQLException | ClassNotFoundException sqlEx) {
          System.out.println("ERROR: Error reading from " + path);
        }
      } else {
        System.err.println("ERROR: " + path + " is not a valid file.");
      }
    } else {
      System.err.println("ERROR: Maps takes one additional argument");
    }
  }

  public static void printHash() {
    for (int i = 0; i < interestCount; i++) {
      Interest interest = convert.get(i + 8);
      System.out.println(interest.getId() + " " + interest.getTag() + " " + interest.getName());
    }
  }

  private void allUserData() throws SQLException, ClassNotFoundException {
    if (conn != null) {
      conn.close();
    }

    // connect to database
    Class.forName("org.sqlite.JDBC");
    String urlToDB = "jdbc:sqlite:" + dataPath;
    conn = DriverManager.getConnection(urlToDB);
    Statement stat = conn.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys=ON");
    try {
      PreparedStatement prep1 = SQLcommands.getAll();
      ResultSet rs1 = prep1.executeQuery();
      ResultSetMetaData rsmd = rs1.getMetaData();
      interestCount = rsmd.getColumnCount() - 7;

      HashMap<String, Integer> converter = new HashMap<>();
      for (int i = 7; i < interestCount; i++) {
        String interestName = rsmd.getColumnName(i);
        converter.put(interestName, i);
      }

      while (rs1.next()) {
        int id = rs1.getInt("id");
        String firstName = rs1.getString("first_name");
        String lastName = rs1.getString("last_name");
        String email = rs1.getString("email");
        String password = rs1.getString("password");
        int year = rs1.getInt("year");

        int[] interests = new int[interestCount];
        for (int j = 0; j < interests.length; j++) {
          interests[j] = rs1.getInt(j + 8);
          String tag = rsmd.getColumnName(j + 8);
          convert.put(j + 8, new Interest(j + 8, tag));
        }

        User user = new User(id, firstName, lastName, email, password, year, interests);
        if (id == currentId) {
          currentUser = user;
        }
        if (!SQLcommands.isAMatch(currentId, id)) {
          userList.add(user);
          System.out.println(user.toString());
        }
      }
      Main.getKdTree().listToTree(userList);
      prep1.close();
      rs1.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("ERROR: There was an error reading in node data");
    }
  }


  public static Connection getConnection() {
    return conn;
  }

  public static ArrayList<User> getUserList() {
    return userList;
  }

  public static String getdataPath() {
    return dataPath;
  }

  public static int getInterestCount() {
    return interestCount;
  }

  public static HashMap<Integer, Interest> getConvert() {
    return convert;
  }

  public static User getCurrentUser() {
    return currentUser;
  }

}
