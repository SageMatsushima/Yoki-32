package edu.brown.cs.student.yoki;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.student.yoki.commands.DataReader;
import edu.brown.cs.student.yoki.commands.InterestsReader;
import edu.brown.cs.student.yoki.commands.MatchFinder;
import edu.brown.cs.student.yoki.commands.TopInterests;
import edu.brown.cs.student.yoki.driver.*;

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
  private static MatchFinder finder = new MatchFinder();
  private static DataReader dataReader = new DataReader();
  private static InterestsReader interestsReader = new InterestsReader();
  private static TopInterests topInterests = new TopInterests();
  private List<User> users = new ArrayList<>();

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

    ArrayList<User> usersList = tree.getFound();
    this.setUsers(usersList);

    REPL repl = new REPL();
    repl.addAction("data", dataReader);
    repl.addAction("interests", interestsReader);
    repl.addAction("match", finder);
    repl.addAction("top", topInterests);

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

      ImmutableMap.Builder<String, String> variables = new ImmutableMap.Builder();
      System.out.println(Main.this.getUsers().size());
      if (Main.this.getUsers().size() > 0) {
        //User nextMatch = Main.this.getUsers().remove(0);
        //variables.put(nextMatch.getName(), nextMatch);
      }
      variables.put("hello", "test");
      return new ModelAndView(variables.build(), "main.ftl");
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

