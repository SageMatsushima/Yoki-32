package edu.brown.cs.student.yoki.commands;

import edu.brown.cs.student.yoki.Database;
import edu.brown.cs.student.yoki.commands.DataReader;
import edu.brown.cs.student.yoki.commands.InterestsReader;
import edu.brown.cs.student.yoki.commands.SQLcommands;
import edu.brown.cs.student.yoki.driver.TriggerAction;
import edu.brown.cs.student.yoki.driver.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

public class NaiveMatch implements TriggerAction {

  private ArrayList<User> userList;


  @Override
  public void action(ArrayList<String> args) throws SQLException, ClassNotFoundException {

    if (args.size() == 3) {
      try {
        String userId = args.get(2);
        PreparedStatement prep = SQLcommands.getAll();
        ResultSet rs = prep.executeQuery();
        ArrayList<User> allUserList = new ArrayList<>();

        while (rs.next()) {
          ArrayList<String> userInfo = new ArrayList<>();
          int id = rs.getInt("id");
          double year = rs.getInt("year");

          userInfo.add(rs.getString("first_name"));
          userInfo.add(rs.getString("last_name"));
          userInfo.add(rs.getString("email"));
          userInfo.add(rs.getString("password"));
          userInfo.add(rs.getString("images"));
          userInfo.add(rs.getString("major"));
          userInfo.add(rs.getString("bio"));

          int[] interests = new int[DataReader.getInterestCount()];
          for (int j = 0; j < interests.length; j++) {
            interests[j] = rs.getInt(j + DataReader.getUserDataColumnLen() + 2);
          }

          InterestsReader ir = new InterestsReader();
          ArrayList<String> userInterests = new ArrayList<>();
          userInterests.add("interests");
          userInterests.add(userId);

          User user = new User(id, year, userInfo, interests);
          allUserList.add(user);
        }

        InterestsReader ir = new InterestsReader();
        ArrayList<String> userInterests = new ArrayList<>();
        userInterests.add("interests");
        userInterests.add(userId);

        ir.action(userInterests);
        CompareUsers userCompare = new CompareUsers(ir.getInterestsList());
        CompareDist distCompare = new CompareDist();
        allUserList.sort(userCompare);

        int numbMatches = Integer.parseInt(args.get(1));
        allUserList.remove(0);
        userList = new ArrayList<>();
        for (int i = 0; i < numbMatches; i++) {
          System.out.println(allUserList.get(i).toString());
          userList.add(allUserList.get(i));
        }
      } catch (Exception e) {
        System.err.println("ERROR: the command naive must follow the form <[naive] [# of matches] [id#]>");
      }
    }
  }

  public ArrayList<Double> propertyBasedTesting(ArrayList<User> userlist, User currentUser) {
    if (currentUser != null) {
      ArrayList<Double> dist = new ArrayList<>();
      for (int i = 0; i < userlist.size(); i++) {
        userlist.get(i).distance(currentUser);
      }
      userlist.sort(new CompareDist());
      userlist.remove(0);
      for (int i = 0; i < userlist.size(); i++) {
        dist.add(userlist.get(i).getDistance());
      }
      return dist;
    } else {
      return null;
    }
  }


  public ArrayList<User> getUserList() {
    return userList;
  }
  public ArrayList<Integer> getUserIds() {
    ArrayList<Integer> ids = new ArrayList<>();
    for (User i: userList) {
      ids.add(i.getId());
    }
    return ids;
  }

  private final class CompareUsers implements Comparator<User> {
    private int[] interests;

    private CompareUsers(int[] interests) {
      this.interests = interests;
    }

    public double targetDis(int[] node, int[] targetPoint) {
      double matchScore = 0;
      for (int i = 0; i < DataReader.getInterestCount(); i++) {
        double relevance = node[i] / 10.0 * targetPoint[i] / 10.0;
        int dist = Math.abs(node[i] - targetPoint[i]);
        matchScore += relevance * (10 - dist);
      }
      return 1 / matchScore;
    }

    @Override
    public int compare(User a, User b) {
      return Double.compare(targetDis(a.getInterests(), interests), targetDis(b.getInterests(), interests));
    }
  }

  private final class CompareDist implements Comparator<User> {
    @Override
    public int compare(User a, User b) {
      return Double.compare(a.getDistance(), b.getDistance());
    }
  }
}
