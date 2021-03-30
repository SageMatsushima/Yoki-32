package edu.brown.cs.rhill6.stars;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

import edu.brown.cs.rhill6.map.MapReader;
import edu.brown.cs.rhill6.map.SearchNearest;
import edu.brown.cs.rhill6.map.MapNode;
import edu.brown.cs.rhill6.map.MapWay;
import edu.brown.cs.rhill6.map.Graph;
import edu.brown.cs.rhill6.map.SearchWays;
import edu.brown.cs.rhill6.map.SearchRoute;

import edu.brown.cs.rhill6.mockaroo.ParseMock;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.QueryParamsMap;
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
  private static KdTree<Star> tree = new KdTree<>();
  private static StarSearcher searcher = new StarSearcher();
  private static ParseStars csvParser = new ParseStars();
  private static SearchNearest nearest = new SearchNearest();
  private static Graph<MapNode, MapWay> graph = new Graph<>();

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
    ParseMock mockParser = new ParseMock();

    repl.addAction("stars", csvParser);
    repl.addAction("naive_neighbors", searcher);
    repl.addAction("naive_radius", searcher);
    repl.addAction("mock", mockParser);
    repl.addAction("neighbors", searcher);
    repl.addAction("radius", searcher);

    repl.addAction("map", new MapReader());
    repl.addAction("ways", new SearchWays());
    repl.addAction("nearest", nearest);
    repl.addAction("route", new SearchRoute());

    repl.run();
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
    Spark.get("/stars", new FrontHandler(), freeMarker);
    Spark.post("/results", new ReturnHandler(), freeMarker);
  }

  /**
   * Accessor for KdTree.
   * @return A KdTree of Stars
   */
  public static KdTree<Star> getKdTree() {
    return tree;
  }

  /**
   * Getter for our graph.
   * @return A graph of MapNodes and MapWays
   */
  public static Graph<MapNode, MapWay> getGraph() {
    return graph;
  }

  /**
   * Handle requests to the front page of our Stars website.
   *
   */
  private static class FrontHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      String quote = "<div id=\"quote\">\n"
          + "<p>There are more stars in the universe than grains of sand on the world's beaches.\n"
          + "More stars in the universe than seconds of time that have passed since Earth formed.\n"
          + "More stars than words and sounds ever uttered by all humans who have ever lived.\n"
          + "</p>\n"
          + "<p>- Neil deGrasse Tyson</p>\n"
          + "</div>";

      ImmutableMap.Builder<String, String> variables = new ImmutableMap.Builder()
          .put("title", "Stars: Query the database")
          .put("k", "0")
          .put("r", "0")
          .put("name", "")
          .put("stars", quote)
          .put("x", "0")
          .put("y", "0")
          .put("z", "0")
          .put("kd", "checked")
          .put("naive", "")
          .put("neighbors", "checked")
          .put("radius", "")
          .put("nameSelected", "")
          .put("coordinates", "checked");

      return new ModelAndView(variables.build(), "query.ftl");
    }
  }

  /**
   * Handle response from the server to our Stars website.
   */
  private static class ReturnHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {

      // load stars data
      if (tree.getNodeList().size() == 0) {
        ArrayList<String> args = new ArrayList<>();
        args.add("stars");
        args.add("data/stars/stardata.csv");
        csvParser.action(args);
      }

      QueryParamsMap qm = req.queryMap();

      String k = qm.value("k");
      String r = qm.value("r");
      String name = qm.value("star-name");
      String x = qm.value("x-coord");
      String y = qm.value("y-coord");
      String z = qm.value("z-coord");

      boolean nameFound = true;

      ImmutableMap.Builder<String, String> variables = new ImmutableMap.Builder()
          .put("title", "Stars: Query the Database")
          .put("k", k)
          .put("r", r)
          .put("name", name)
          .put("x", x)
          .put("y", y)
          .put("z", z);

      if (qm.value("algorithm").equals("neighbors")) {
        variables.put("neighbors", "checked");
        variables.put("radius", "");
      } else {
        variables.put("neighbors", "");
        variables.put("radius", "checked");
      }

      if (qm.value("type").equals("naive_")) {
        variables.put("kd", "");
        variables.put("naive", "checked");
      } else {
        variables.put("kd", "checked");
        variables.put("naive", "");
      }

      if (qm.value("input").equals("star-name")) {
        variables.put("nameSelected", "checked");
        variables.put("coordinates", "");
      } else {
        variables.put("nameSelected", "");
        variables.put("coordinates", "checked");
      }

      try {
        //neighbors or radius
        String command = qm.value("algorithm");
        // "" or naive_
        String prefix = qm.value("type");

        if (command.equals("neighbors")) {
          tree.setK(Integer.parseInt(k));
          tree.setR(Double.POSITIVE_INFINITY);
        } else {
          tree.setK(Integer.MAX_VALUE);
          tree.setR(Double.parseDouble(r));
          System.out.println("here");
        }

        if (qm.value("input").equals("star-name")) {
          nameFound = searcher.setCoordsByName(name);
        } else {
          ArrayList<Double> coords = new ArrayList<>();

          coords.add(Double.parseDouble(x));
          coords.add(Double.parseDouble(y));
          coords.add(Double.parseDouble(z));

          tree.setCoords(coords);
        }

        if (prefix.equals("naive_")) {
          tree.naiveNeighbors();
        } else {
          tree.kdNeighbors();
        }

        String table;
        if (nameFound) {
          ArrayList<Star> found = tree.getFound();
          table = starsToTable(found);
        } else {
          table = "Name not found";
        }

        variables.put("stars", table);

        return new ModelAndView(variables.build(), "query.ftl");
      } catch (Exception e) {
        variables.put("stars", e.toString());

        return new ModelAndView(variables.build(), "query.ftl");
      }
    }

    private String starsToTable(ArrayList<Star> stars) {
      StringBuilder table = new StringBuilder();
      table.append("<table><tr><th>Name</th><th>Id</th><th>Coordinates</th></tr>");

      for (Star star : stars) {
        table.append("<tr><td>" + star.getName() + "</td>"
            + "<td>" + star.getId() + "</td>"
            + "<td>" + star.getCoords() + "</td></tr>");
      }

      table.append("</table>");
      return table.toString();
    }
  }

  /**
   * Display an error page when an exception occurs in the server.
   *
   */
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

