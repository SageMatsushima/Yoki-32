package edu.brown.cs.rhill6.stars;

import edu.brown.cs.rhill6.stars.KdTree;
import edu.brown.cs.rhill6.stars.Main;
import edu.brown.cs.rhill6.stars.Star;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class StarSearchTester {

  // generate random array

  // get found for naive, copy it, get found for kd search

  private ArrayList<Double> coords = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0));
  private Random rand = new Random();
  private int k;
  private double r;
  private KdTree tree = Main.getKdTree();
  private ArrayList<Star> nodes = (ArrayList<Star>) tree.getNodeList();
  private ArrayList<Star> found = (ArrayList<Star>) tree.getFound();
  private static double THRESHOLD = 0.0000001;

  public void generateRandom() {

    nodes.clear();
    // IDs need to be distinct, can just increment numbers
    int total = rand.nextInt(100);
    for (int i = 0; i < total; i++) {
      Star newStar = new Star(Integer.toString(i), "Star " + i,
          rand.nextDouble(), rand.nextDouble(), rand.nextDouble());

      nodes.add(newStar);
    }

    coords.set(0, rand.nextDouble());
    coords.set(1, rand.nextDouble());
    coords.set(2, rand.nextDouble());
    tree.setCoords(coords);

    tree.listToTree(nodes);
  }

  public boolean randomNeighborCompare() {
    // Add extra to sometimes have k > elts
    k = rand.nextInt(nodes.size() + 10);

    tree.setK(k);
    tree.setR(Double.POSITIVE_INFINITY);

    if (compare()) {
      return true;
    } else {
      System.err.println("ERROR: Neighbors");
      return false;
    }
  }

  public boolean randomRadiusCompare() {
    // Radius must be > 0
    tree.setR(Math.abs(rand.nextDouble()));
    tree.setK(Integer.MAX_VALUE);

    if (compare()) {
      return true;
    } else {
      System.err.println("ERROR: Radius");
      return false;
    }
  }

  private boolean compare() {
    tree.setCoords(coords);
    found.clear();
    tree.naiveNeighbors();

    ArrayList<Star> holdFound = new ArrayList<>(found);
    tree.kdNeighbors();

    if (!validate(found)) {
      System.err.println("ERROR: Error validating KD");
      return false;
    } else if (!validate(holdFound)) {
      System.err.println("ERROR: Error validating naive");
      return false;
    } else if (!validatePair(holdFound, found)) {
      System.err.println("ERROR: Error validating pair");
      return false;
    } else {
      return true;
    }
  }

  public boolean validate(ArrayList<Star> found) {
    // Check if sorted nodes are valid
    if (found.size() == 0) {
      return true;
    }

    HashSet<String> seen = new HashSet<>();
    double dist = 0;

    for (Star star : found) {
      if (seen.contains(star.getId())) {
        System.err.println("ERROR: Duplicate id");
        return false;
      }

      seen.add(star.getId());

      double new_dist = star.distance(coords);

      if (new_dist < dist) {
        System.err.println("ERROR: Farther star comes before closer star");
        return false;
      } else {
        dist = new_dist;
      }
    }

    return true;
  }

  public boolean validatePair(ArrayList<Star> f1, ArrayList<Star> f2) {
    if (f1.size() != f2.size()) {
      System.err.println("ERROR: Algorithms found different numbers of stars");
      return false;
    }

    for (int i = 0; i < f1.size(); i++) {
      if (Math.abs(f1.get(i).distance(coords) - f2.get(i).distance(coords)) > THRESHOLD) {
        System.err.println("ERROR: Distance mismatch");
        return false;
      }
    }

    return true;
  }

}
