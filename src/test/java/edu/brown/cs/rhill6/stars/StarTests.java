package test.java.edu.brown.cs.rhill6.stars;

import edu.brown.cs.rhill6.stars.Star;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class StarTests {

  @Test
  public void testDistance() {
    Star s1 = new Star("a", "name", 0, 1, 2.5);
    Star s2 = new Star("b", "name", -1, 2, 4);

    assertEquals(s1.distance(s2), s2.distance(s1), 0.00001);
    assertEquals(s1.distance(s2), 2.06155, 0.0001);
    assertEquals(0, s1.distance(s1), 0.0000001);

    ArrayList<Double> coords = new ArrayList<>();
    coords.add(0.1);
    coords.add(2.0);
    coords.add(0.0);

    assertEquals(s1.distance(coords), 2.69444, 0.0001);
  }
}
