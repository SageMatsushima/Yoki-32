package edu.brown.cs.student.yoki.commands;

import edu.brown.cs.student.yoki.Main;
import edu.brown.cs.student.yoki.driver.Interest;
import edu.brown.cs.student.yoki.driver.TriggerAction;
import edu.brown.cs.student.yoki.driver.User;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DataReader implements TriggerAction {
  //setting up instance variables
  private static Connection conn;
  private static String dataPath = null;
  private static ArrayList<User> userList = new ArrayList<User>();
  private static int interestCount;
  private static HashMap<Integer, Interest> convert = new HashMap<>();
  private static int currentId = 1;
  private static User currentUser;
  private static int userDataColumnLen = 0;

  @Override
  public void action(ArrayList<String> args) {
    if (args.size() == 2) {
      String path = args.get(1);
      if (new File(path).exists()) {
        try {
          dataPath = path;

          if (conn != null) {
            conn.close();
          }

          Class.forName("org.sqlite.JDBC");
          String urlToDB = "jdbc:sqlite:" + dataPath;
          conn = DriverManager.getConnection(urlToDB);
          Statement stat = conn.createStatement();
          stat.executeUpdate("PRAGMA foreign_keys=ON");

          setUserDataColLen();
          allUserData();
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

  private void setUserDataColLen() throws SQLException, ClassNotFoundException {
    Statement stat = conn.createStatement();
    stat.executeUpdate("PRAGMA foreign_keys=ON");
    try {
      PreparedStatement prep = conn.prepareStatement("SELECT * FROM user_data;");
      ResultSet rs = prep.executeQuery();
      ResultSetMetaData rsmd = rs.getMetaData();
      userDataColumnLen = rsmd.getColumnCount();

      prep.close();
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("ERROR: There was an error reading in node data");
    }
  }

  private void allUserData() throws SQLException, ClassNotFoundException {
    userList = new ArrayList<>();
    try {
      PreparedStatement prep1 = SQLcommands.getAll();
      ResultSet rs1 = prep1.executeQuery();
      ResultSetMetaData rsmd = rs1.getMetaData();
      interestCount = rsmd.getColumnCount() - (userDataColumnLen + 1);

      HashMap<String, Integer> converter = new HashMap<>();
      for (int i = userDataColumnLen + 1; i < interestCount; i++) {
        String interestName = rsmd.getColumnName(i);
        converter.put(interestName, i);
      }

      while (rs1.next()) {
        ArrayList<String> userInfo = new ArrayList<String>();
        int id = rs1.getInt("id");
        double year = rs1.getDouble("year");

        userInfo.add(rs1.getString("first_name"));
        userInfo.add(rs1.getString("last_name"));
        userInfo.add(rs1.getString("email"));
        userInfo.add(rs1.getString("password"));
        userInfo.add(rs1.getString("images"));
        userInfo.add(rs1.getString("major"));
        userInfo.add(rs1.getString("bio"));

        int[] interests = new int[interestCount];
        for (int j = 0; j < interests.length; j++) {
          interests[j] = rs1.getInt(j + userDataColumnLen + 2);
          String tag = rsmd.getColumnName(j + userDataColumnLen + 2);
          convert.put(j + userDataColumnLen + 2, new Interest(j + userDataColumnLen + 2, tag));
        }

        User user = new User(id, year, userInfo, interests);
        if (id == Main.getCurrentId()) {
          currentUser = user;
        }

        if (!SQLcommands.isAMatchPass(Main.getCurrentId(), id)) {
          userList.add(user);
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

  public static int getUserDataColumnLen() {
    return userDataColumnLen;
  }
}
