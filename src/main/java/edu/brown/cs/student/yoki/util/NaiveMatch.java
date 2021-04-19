package edu.brown.cs.student.yoki.util;

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

  @Override
  public void action(ArrayList<String> args) throws SQLException, ClassNotFoundException {
//need to read into data reader first

    if (args.size() == 2) {
      String userId = args.get(1);

      PreparedStatement prep = SQLcommands.getAll();
      ResultSet rs = prep.executeQuery();
      ArrayList<User> userList = new ArrayList<>();
      while (rs.next()) {
        ArrayList<String> userInfo = new ArrayList<>();
        ArrayList<Integer> idYear = new ArrayList<>();

        idYear.add(rs.getInt("id"));
        idYear.add(rs.getInt("year"));

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

        User user = new User(idYear, userInfo, interests);
        userList.add(user);
      }

      InterestsReader ir = new InterestsReader();
      ArrayList<String> userInterests = new ArrayList<>();
      userInterests.add("interests");
      userInterests.add(userId);
      ir.action(userInterests);
      Compare compare = new Compare(ir.getInterestsList());
      userList.sort(compare);
      System.out.println(userList);
    }
  }
  private final class Compare implements Comparator<User> {
    private int[] interests;

    private Compare(int[] interests) {
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
}
