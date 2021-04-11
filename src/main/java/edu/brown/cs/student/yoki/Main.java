package edu.brown.cs.student.yoki;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.brown.cs.student.yoki.commands.DataReader;
import edu.brown.cs.student.yoki.commands.InterestsReader;
import edu.brown.cs.student.yoki.commands.MatchFinder;
import edu.brown.cs.student.yoki.commands.UserReader;
import edu.brown.cs.student.yoki.driver.*;

import com.google.gson.Gson;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.common.collect.ImmutableMap;

import freemarker.template.Configuration;

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


  private List<User> users = new ArrayList<>();
  private static final Gson GSON = new Gson();

  private Main(String[] args) {
    this.args = args;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
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
    }


    ArrayList<String> dataReaderArgs = new ArrayList<>();
    dataReaderArgs.add("data");
    dataReaderArgs.add("data/smallData.sqlite");
    dataReader.action(dataReaderArgs);
    ArrayList<String> finderArgs = new ArrayList<>();
    finderArgs.add("match");
    finderArgs.add("10");
    finderArgs.add("1");
    matches.action(finderArgs);
    this.setUsers(matches.getUserList());

    REPL repl = new REPL();
    repl.addAction("data", dataReader);
    repl.addAction("interests", interestsReader);
    repl.addAction("match", matches);
    repl.addAction("user", userReader);

    repl.run();
  }
  public static TreeFunction<User> getKdTree() {
    return tree;
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
    //Spark.get("/stars", new FrontHandler(), freeMarker);
    Spark.get("/yoki", new YokiHandler(), freeMarker);
<<<<<<< HEAD
    Spark.get("/learn", new LearnHandler(), freeMarker);
    Spark.get("/teach", new TeachHandler(), freeMarker);
    Spark.get("/settings", new SettingsHandler(), freeMarker);
    Spark.get("/profileEdit", new ProfileEditHandler(), freeMarker);
    Spark.get("/match", new MatchHandler(), freeMarker);
=======
    Spark.get("/yokimatch", new MatchHandler());
>>>>>>> 08e1f8f9a509f84d92ff3c4e01134b11b39808db
//    Spark.get("/userData", new UserData(), freeMarker);
  }

 /* private static class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {

      ImmutableMap.Builder<String, String> variables = new ImmutableMap.Builder();
      return new ModelAndView(variables.build(), "query.ftl");
    }
  }*/

  //sends to Front-end next match -> pops from our list
  //when program loads, run the program and store a list of matches in Main
  private class YokiHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {

      //User tod = new User(20, "test", "test", "test", "test", 3023, new int[]{0, 1});
      //Map<String, User> variables = ImmutableMap.of("user", tod);
      //ImmutableMap.Builder<String, User> variables = new ImmutableMap.Builder();
      System.out.println(Main.this.getUsers().size());
      if (Main.this.getUsers().size() > 0) {
        User nextMatch = Main.this.getUsers().remove(0);
        Map<String, User> variables = ImmutableMap.of("user", nextMatch);
        return new ModelAndView(variables, "main.ftl");
      }
      return new ModelAndView(null, "main.ftl");
    }
  }

  private class MatchHandler implements Route {
    @Override
    public String handle(Request req, Response res) {

      //User tod = new User(20, "test", "test", "test", "test", 3023, new int[]{0, 1});
      //Map<String, User> variables = ImmutableMap.of("user", tod);
      //ImmutableMap.Builder<String, User> variables = new ImmutableMap.Builder();
      System.out.println(Main.this.getUsers().size());
      if (Main.this.getUsers().size() > 0) {
        User nextMatch = Main.this.getUsers().remove(0);
        Map<String, User> variables = ImmutableMap.of("user", nextMatch);
        return GSON.toJson(variables);
      }
      return "null";
    }
  }

  private static class MatchHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {

      ImmutableMap.Builder<String, String> variables = new ImmutableMap.Builder();
      return new ModelAndView(variables.build(), "main.ftl");
    }
  }

  private static class LearnHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {

      ImmutableMap.Builder<String, String> variables = new ImmutableMap.Builder();
      return new ModelAndView(variables.build(), "LearnSubjects.ftl");
    }
  }

  private static class TeachHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {

      ImmutableMap.Builder<String, String> variables = new ImmutableMap.Builder();
      return new ModelAndView(variables.build(), "TeachSubjects.ftl");
    }
  }

  private static class SettingsHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {

      ImmutableMap.Builder<String, String> variables = new ImmutableMap.Builder();
      return new ModelAndView(variables.build(), "Settings.ftl");
    }
  }

  private static class ProfileEditHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {

      ImmutableMap.Builder<String, String> variables = new ImmutableMap.Builder();
      return new ModelAndView(variables.build(), "ProfileEdit.ftl");
    }
  }

//  private static class UserData implements TemplateViewRoute {
//    @Override
//    public ModelAndView handle(Request req, Response res) {
////      User currentUser = finder.get()
////      Map<String, String> variables = ImmutableMap.of("userData", );
//      return new ModelAndView(variables, "autocorrect.ftl");
//    }
//  }

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

