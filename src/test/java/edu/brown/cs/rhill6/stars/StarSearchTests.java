package edu.brown.cs.rhill6.stars;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class StarSearchTests {


  private ParseStars parser = new ParseStars();
  private StarSearcher searcher = new StarSearcher();
  private ArrayList<String> args = new ArrayList<>(Arrays.asList("stars", ""));
  private ArrayList<String> args2 = new ArrayList<>(Arrays.asList("naive_neighbors", "1", "0", "0", "0"));
  private ArrayList<String> args3 = new ArrayList<>(Arrays.asList("naive_radius", "0", "\"Sol\""));
  private StarSearchTester tester = new StarSearchTester();

  private ArrayList<Star> found = (ArrayList<Star>) Main.getKdTree().getFound();


  @Test
  public void testTiedStars() {
    try {
      args.set(1, "data/stars/one-star.csv");
      parser.action(args);

      // 1 star found
      searcher.action(args2);
      assertEquals(1, found.size());


      // Same for KdTree
      args2.set(0, "neighbors");
      searcher.action(args2);
      ArrayList<Star> newFound = (ArrayList<Star>) Main.getKdTree().getFound();
      assertEquals(1, newFound.size());


      args.set(1, "data/stars/tie-star.csv");
      parser.action(args);

      // 3 stars tied at 0,0,0, return 2
      args2.set(1, "2");
      searcher.action(args2);
      newFound = (ArrayList<Star>) Main.getKdTree().getFound();
      assertEquals(2, newFound.size());
      assertTrue(tester.validate(found));
      ArrayList<Star> holdFound = new ArrayList<>(found);


      // Same for naive_neighbors
      args2.set(0, "naive_neighbors");
      searcher.action(args2);
      newFound = (ArrayList<Star>) Main.getKdTree().getFound();
      assertEquals(2, newFound.size());
      assertTrue(tester.validate(found));
      tester.validatePair(found, holdFound);

      // just reads all 10
      args2.set(1, "12");
      searcher.action(args2);
      newFound = (ArrayList<Star>) Main.getKdTree().getFound();
      assertEquals(10, newFound.size());
      assertTrue(tester.validate(found));

      // Same for neighbors
      args2.set(0, "neighbors");
      searcher.action(args2);
      newFound = (ArrayList<Star>) Main.getKdTree().getFound();
      assertEquals(10, newFound.size());
      assertTrue(tester.validate(found));

      args.set(1, "data/stars/stardata.csv");
      parser.action(args);

      args2.set(1, "1000");
      searcher.action(args2);
      newFound = (ArrayList<Star>) Main.getKdTree().getFound();
      assertEquals(1000, newFound.size());
      assertTrue(tester.validate(found));

    } catch (Exception e) {
      System.err.println(e);
      System.err.println("ERROR: Error reading file in testNaiveNeighbors1");
      assertTrue(false);
    }
  }

  @Test
  public void testRandom() {
    //10 Random executions
    for (int i = 0; i < 10; i++) {
      tester.generateRandom();
      assertTrue(tester.randomRadiusCompare());
      assertTrue(tester.randomNeighborCompare());
    }
  }

  @Test
  public void testName() {
    args.set(1, "data/stars/ten-star.csv");
    parser.action(args);

    // Search with radius 0, don't find names star
    searcher.action(args3);
    assertEquals(0, found.size());

    args3.set(0, "radius");
    searcher.action(args3);
    assertEquals(0, found.size());

    args3.set(1, "1.5");
    searcher.action(args3);
    ArrayList<Star> newFound = (ArrayList<Star>) Main.getKdTree().getFound();
    assertEquals("70667", newFound.get(0).getId());
    assertEquals("71454", newFound.get(1).getId());

    args3.set(0, "naive_radius");
    searcher.action(args3);
    newFound = (ArrayList<Star>) Main.getKdTree().getFound();
    assertEquals("70667", newFound.get(0).getId());
    assertEquals("71454", newFound.get(1).getId());

  }
}
