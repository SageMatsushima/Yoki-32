package edu.brown.cs.student.yoki;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import edu.brown.cs.student.yoki.commands.*;
import edu.brown.cs.student.yoki.driver.Interest;
import edu.brown.cs.student.yoki.driver.REPL;
import edu.brown.cs.student.yoki.driver.TreeFunction;
import edu.brown.cs.student.yoki.driver.User;
import freemarker.template.Configuration;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.json.JSONException;
import org.json.JSONObject;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import java.io.*;
import java.util.*;

/**
 * The Main class of our project. This is where execution begins.
 *
 */
public final class Main {

  private static final int DEFAULT_PORT = 4567;

  /**
   * The initial method called when execution begins.
   *
   * @param args
   *          An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private final String[] args;
  private static TreeFunction<User> tree = new TreeFunction<>();
  private static MatchFinder matches = new MatchFinder();
  private static DataReader dataReader = new DataReader();
  private static InterestsReader interestsReader = new InterestsReader();
  private static UserReader userReader = new UserReader();
  private static Encrypt encrypt = new Encrypt();
  private static NaiveMatch naiveMatch = new NaiveMatch();


  private List<User> users = new ArrayList<>();
  private Set<User> matchSet = new HashSet();
  private static int currentId = -1;

  private static final Gson GSON = new Gson();

  private Main(String[] args) {
    this.args = args;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> usersInput) {
    users = new ArrayList<>();
    for (User user: usersInput) {
      System.out.println(user.getName());
      users.add(user);
    }
  }

  private void run() {
    // Parse command line arguments
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
    .defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);

    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    } else {
      runSparkServer(DEFAULT_PORT);
    }

    REPL repl = new REPL();
    repl.addAction("data", dataReader);
    repl.addAction("interests", interestsReader);
    repl.addAction("match", matches);
    repl.addAction("user", userReader);
    repl.addAction("encrypt", encrypt);
    repl.addAction("naive", naiveMatch);
    repl.run();
  }

  public static TreeFunction<User> getKdTree() {
    return tree;
  }

  public static void newKdTree() {
    tree = new TreeFunction<>();
  }

  public static int getCurrentId() {return currentId; }

  public static void setCurrentId(int id) {
    currentId = id;
  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");

    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();

    // Setup Spark Routes
    Spark.get("/yoki", new YokiHandler(), freeMarker);
    Spark.get("/main", new MainHandler(), freeMarker);
    Spark.get("/learn", new LearnHandler(), freeMarker);
    Spark.get("/teach", new TeachHandler(), freeMarker);
    Spark.get("/settings", new SettingsHandler(), freeMarker);
    Spark.get("/profileEdit", new ProfileEditHandler(), freeMarker);
    Spark.get("/match", new MatchPageHandler(), freeMarker);
    Spark.get("/profileOverview", new ProfileOverviewHandler(), freeMarker);
    Spark.get("/yokimatch", new MatchHandler());
    Spark.get("/signup", new SignupHandler(), freeMarker);

    Spark.post("/sendmatches", new MatchMapHandler());
    Spark.post("/sendmatch", new MatchMapHandler());
    Spark.post("/listInterests", new ListInterestsHandler());
    Spark.post("/getinterests", new GetInterestHandler());
    Spark.get("/getmatch", new GetMatchesHandler());
    Spark.post("/updateInterests", new UpdateInterests());
    Spark.post("/login", new LoginHandler());
    Spark.post("/profileInfo", new ProfileInfo());
    Spark.post("/addUser", new AddUser());
    Spark.post("/deleteMatch", new DeleteMatch());
    Spark.post("/report", new Report());
    Spark.post("/updateUser", new UpdateUser());

    ArrayList<String> dataReaderArgs = new ArrayList<>();
    dataReaderArgs.add("data");
    dataReaderArgs.add("data/bigData.sqlite");
    dataReader = new DataReader();
    dataReader.action(dataReaderArgs);
//    Spark.get("/userData", new UserData(), freeMarker);
  }

  //sends to Front-end next match -> pops from our list
  //when program loads, run the program and store a list of matches in Main
  private class YokiHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      currentId = -1;
      return new ModelAndView(null, "login.ftl");
    }
  }

  private class MainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      if (currentId == -1) {
        return new ModelAndView(null, "login.ftl");
      }
      ArrayList<String> dataReaderArgs = new ArrayList<>();
      dataReaderArgs.add("data");
      dataReaderArgs.add("data/bigData.sqlite");
      dataReader = new DataReader();
      dataReader.action(dataReaderArgs);
      ArrayList<String> finderArgs = new ArrayList<>();
      finderArgs.add("match");
      finderArgs.add("104");
      finderArgs.add(currentId + "");
      matches = new MatchFinder();
      matches.action(finderArgs);

      Main.this.setUsers(matches.getUserList());
      return new ModelAndView(null, "main.ftl");
    }
  }

  public static String keyreader() {
    try {
      BufferedReader br = new BufferedReader(new FileReader("key.txt"));

      String output = "";
      String st;
      while ((st = br.readLine()) != null) {
        System.out.println(st);
        output += st;
      }
      return output;
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("ERROR: there was an issue reading in the key");
      return null;
    }
  }

  private class LoginHandler implements Route {
    @Override
    public String handle(Request req, Response res) throws JSONException {
      JSONObject loginCreds = new JSONObject(req.body());
      String email = loginCreds.getString("email");
      String password = loginCreds.getString("password");
      System.out.println(password);
      String key = keyreader();
      if (key != null) {
        password = Encrypt.encrypt(password, key);
        System.out.println(password);
        currentId = SQLcommands.getUserId(email, password);
        String status = "false";
        if (currentId > 0) {
          status = "true";
        }
        Map<String, Object> variables = ImmutableMap.of("authenticated", status);
        return GSON.toJson(variables);
      } else {
        System.err.println("ERROR: you do not have the right key");
        return null;
      }
    }
  }

  private class ProfileInfo implements Route {
    @Override
    public String handle(Request req, Response res) throws JSONException {
      User user = SQLcommands.getUserInfo(currentId);
      Map<String, Object> variables = ImmutableMap.of("user", user);
      return GSON.toJson(variables);
    }
  }

  private class UpdateInterests implements Route {
    @Override
    public String handle(Request req, Response res) throws JSONException {
      //update interests
      //update db
      //update matches list
      JSONObject newMatch = new JSONObject(req.body());
      JSONObject interests = newMatch.getJSONObject("interests");
      HashMap<Integer, Interest> interestsMap = new HashMap<>();
      Iterator<String> keys = interests.keys();
      while (keys.hasNext()) {
        String strKey = keys.next();
        Integer key = Integer.parseInt(strKey);
        Integer value = Integer.parseInt(interests.getString(strKey));
        Interest interest = DataReader.getConvert().get(key);
        interest.setScore(value);
        interestsMap.put(key, interest);
      }
      Main.newKdTree();
      SQLcommands.update(currentId, interestsMap);
      SQLcommands.removeAllPasses(currentId);
      ArrayList<String> dataReaderArgs = new ArrayList<>();
      dataReaderArgs.add("data");
      dataReaderArgs.add("data/bigData.sqlite");
      DataReader dr = new DataReader();
      dr.action(dataReaderArgs);


      ArrayList<String> finderArgs = new ArrayList<>();
      finderArgs.add("match");
      finderArgs.add(DataReader.getUserList().size() + "");
      finderArgs.add("" + currentId);
      System.out.println(finderArgs);
      MatchFinder m = new MatchFinder();
      m.action(finderArgs);
      Main.this.setUsers(m.getUserList());

      Map<String, Object> variables = ImmutableMap.of("msg", "done");
      return GSON.toJson(variables);
    }
  }
  private class MatchHandler implements Route {
    @Override
    public String handle(Request req, Response res) {

      System.out.println(Main.this.getUsers().size());
      if (Main.this.getUsers().size() > 0) {
        User nextMatch = Main.this.getUsers().remove(0);
        ArrayList<String> interestsArgs = new ArrayList<>();

        InterestsReader ir = new InterestsReader();
        interestsArgs.add("interests");
        interestsArgs.add(nextMatch.getId() + "");
        ir.action(interestsArgs);
        ArrayList<Interest> topCommonInterests = ir.getTopInterests().get(0);
        ArrayList<Interest> topOtherInterests = ir.getTopInterests().get(1);

        Map<String, Object> variables = ImmutableMap.of("user", nextMatch,
          "topCommonInterests", topCommonInterests, "topOtherInterests", topOtherInterests);
        return GSON.toJson(variables);
      }
      return "null";
    }
  }

  private static class ListInterestsHandler implements Route {
    @Override
    public String handle(Request req, Response res) {
      Map<String, Object> variables = ImmutableMap.of("interestsList", DataReader.getConvert());
      return GSON.toJson(variables);
    }
  }

  private static class MatchPageHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      ImmutableMap.Builder<String, String> variables = new ImmutableMap.Builder();
      if (currentId == -1) {
        return new ModelAndView(variables.build(), "login.ftl");
      }
      return new ModelAndView(variables.build(), "Matches.ftl");
    }
  }

  private static class LearnHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {

      ImmutableMap.Builder<String, String> variables = new ImmutableMap.Builder();
      if (currentId == -1) {
        return new ModelAndView(variables.build(), "login.ftl");
      }
      return new ModelAndView(variables.build(), "LearnSubjects.ftl");
    }
  }

  private static class TeachHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {

      ImmutableMap.Builder<String, String> variables = new ImmutableMap.Builder();
      if (currentId == -1) {
        return new ModelAndView(variables.build(), "yoki.ftl");
      }
      return new ModelAndView(variables.build(), "TeachSubjects.ftl");
    }
  }

  private static class SettingsHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {

      ImmutableMap.Builder<String, String> variables = new ImmutableMap.Builder();
      if (currentId == -1) {
        return new ModelAndView(variables.build(), "login.ftl");
      }
      return new ModelAndView(variables.build(), "Settings.ftl");
    }
  }

  private static class ProfileEditHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {

      ImmutableMap.Builder<String, String> variables = new ImmutableMap.Builder();
      if (currentId == -1) {
        return new ModelAndView(variables.build(), "login.ftl");
      }
      return new ModelAndView(variables.build(), "ProfileEdit.ftl");
    }
  }

  private static class ProfileOverviewHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {

      ImmutableMap.Builder<String, String> variables = new ImmutableMap.Builder();
      if (currentId == -1) {
        return new ModelAndView(variables.build(), "login.ftl");
      }
      return new ModelAndView(variables.build(), "ProfileOverview.ftl");
    }
  }

  private static class SignupHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {

      ImmutableMap.Builder<String, String> variables = new ImmutableMap.Builder();
      return new ModelAndView(variables.build(), "Signup.ftl");
    }
  }

  /**
   * Handler method for post request for matching with a user. Adds the matched user to matchSet.
   */
  private class MatchMapHandler implements Route {
    @Override
    public String handle(Request req, Response res) throws Exception {
      JSONObject newMatch = new JSONObject(req.body());
      int matchId = newMatch.getInt("id");
      boolean isMatch = newMatch.getBoolean("isMatch");
      for (User user: matches.getUserList()) {
        if ((user.getId()) == (matchId)) {
          matchSet.add(user);
          System.out.println("added");
        }
      }
      SQLcommands.addMatch(currentId, matchId, isMatch);
      //add match to db function with matchId
      return "";
    }
  }

  /**
   * Handler method for post request for matching with a user. Adds the matched user to matchSet.
   */
  private class GetMatchesHandler implements Route {
    @Override
    public String handle(Request req, Response res) throws Exception {
      Map<String, Object> variables = ImmutableMap.of("matchSet", SQLcommands.getAllMatches(currentId, false));
      return GSON.toJson(variables);
    }
  }

  private class GetInterestHandler implements Route {
    @Override
    public String handle(Request req, Response res) throws JSONException {
      JSONObject newMatch = new JSONObject(req.body());
      int matchId = newMatch.getInt("id");
      User matched = null;
      System.out.println(matchId + "matchID");
      for (User user: SQLcommands.getAllMatches(currentId, false)) {
        System.out.println(user.getId());
        if ((int) (user.getId()) == (int) (matchId)) {
          matched = user;
          System.out.println(user.getName() + " matched in matchset");
        }
      }
      if (matched != null) {
        ArrayList<String> interestsArgs = new ArrayList<>();

        InterestsReader ir = new InterestsReader();
        interestsArgs.add("interests");
        interestsArgs.add(matched.getId() + "");
        ir.action(interestsArgs);
        ArrayList<Interest> topCommonInterests = ir.getTopInterests().get(0);

        Map<String, Object> variables = ImmutableMap.of(
                "topCommonInterests", topCommonInterests);
        return GSON.toJson(variables);
      }
      Map<String, Object> variables = ImmutableMap.of(
              "topCommonInterests", "hi", "topOtherInterests", "hi");
      return GSON.toJson(variables);
    }
  }

  private class AddUser implements Route {
    @Override
    public String handle(Request req, Response res) throws Exception {
      JSONObject newMatch = new JSONObject(req.body());
      String firstName = newMatch.getString("firstName");
      String lastName = newMatch.getString("lastName");
      String email = newMatch.getString("email");
      String password = newMatch.getString("password");
      String key = keyreader();
      password = Encrypt.encrypt(password, key);
      double year = newMatch.getDouble("year");
      String major = newMatch.getString("major");
      String bio = newMatch.getString("bio");

      if (SQLcommands.addUser(firstName, lastName, email, password, year, major, bio)) {
        Map<String, Object> variables = ImmutableMap.of("success", "true");
        currentId = SQLcommands.getUserId(email, password);
        return GSON.toJson(variables);
      }
      Map<String, Object> variables = ImmutableMap.of("success", "false");
      return GSON.toJson(variables);
    }
  }

  private class DeleteMatch implements Route {
    @Override
    public String handle(Request req, Response res) throws Exception {
      JSONObject matchToDelete = new JSONObject(req.body());
      int matchID = matchToDelete.getInt("id");
      System.out.println("deleting " + matchID);
      SQLcommands.deleteMatch(currentId, matchID);

      Map<String, Object> variables = ImmutableMap.of("matchSet", SQLcommands.getAllMatches(currentId, false));
      return GSON.toJson(variables);
    }
  }

  private class Report implements Route {
    @Override
    public String handle(Request req, Response res) throws Exception {
      JSONObject matchToDelete = new JSONObject(req.body());
      String email = matchToDelete.getString("email");
      String report = matchToDelete.getString("report");
      int reportedId = SQLcommands.getIdByEmail(email);
      if (reportedId == -1) {
        Map<String, Object> variables = ImmutableMap.of("reported", "false");
        return GSON.toJson(variables);
      }
      SQLcommands.addReport(currentId, reportedId, report);

      Map<String, Object> variables = ImmutableMap.of("reported", "true");
      return GSON.toJson(variables);
    }
  }

  private class UpdateUser implements Route {
    @Override
    public String handle(Request req, Response res) throws Exception {
      JSONObject updateUser = new JSONObject(req.body());
      String firstName = updateUser.getString("firstName");
      String lastName = updateUser.getString("lastName");
      String major = updateUser.getString("major");
      Double year = updateUser.getDouble("year");
      String bio = updateUser.getString("bio");
      String email = updateUser.getString("email");
      String image = updateUser.getString("image");

      System.out.println("updating " + currentId);

      if (SQLcommands.editProfile(currentId, firstName, lastName, major, year, bio, email, image)) {
        Map<String, Object> variables = ImmutableMap.of("success", "true");
        return GSON.toJson(variables);
      }
      Map<String, Object> variables = ImmutableMap.of("success", "false");
      return GSON.toJson(variables);
    }
  }

  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }
}


