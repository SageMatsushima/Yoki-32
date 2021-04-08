package edu.brown.cs.yoki;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import edu.brown.cs.yoki.tree.*;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.ExceptionHandler;
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
  private static KdTree<Users> tree = new KdTree<>();
  private static MatchFinder finder = new MatchFinder();
  private static ParseStars csvParser = new ParseStars();
  private static CreateTable createTable;
  private static AddData addRows;
  private static AddUsers addUsers;

  static {
    try {
      addRows = new AddData("data/bigData.sqlite");
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      createTable = new CreateTable("data/Interests.csv");
    } catch (Exception e) {
      e.printStackTrace();
    }
    try {
      addUsers = new AddUsers("data/bigTest.csv");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private Main(String[] args) {
    this.args = args;
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

    REPL repl = new REPL();

    repl.addAction("data", csvParser);
    repl.addAction("match", finder);
    repl.addAction("createTable", createTable);
    repl.addAction("addRows", addRows);
    repl.addAction("addUsers", addUsers);
    repl.run();
  }
  public static KdTree<Users> getKdTree() {
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
  }

 /* private static class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {

      ImmutableMap.Builder<String, String> variables = new ImmutableMap.Builder();
      return new ModelAndView(variables.build(), "query.ftl");
    }
  }*/

  private static class YokiHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {

      ImmutableMap.Builder<String, String> variables = new ImmutableMap.Builder();
      return new ModelAndView(variables.build(), "main.ftl");
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

