package edu.brown.cs.rhill6.stars;

import edu.brown.cs.rhill6.stars.Main;
import edu.brown.cs.rhill6.stars.ParseStars;
import edu.brown.cs.rhill6.stars.Star;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class CvsParserTests {

    private ParseStars parser = new ParseStars();
    private ArrayList<String> args = new ArrayList<>(Arrays.asList("stars", ""));


    @Test
    public void testNumLines() {
        try {
            args.set(1, "data/stars/one-star.csv");
            parser.action(args);
            assertTrue(Main.getKdTree().getNodeList().size() == 1);

            args.set(1, "data/stars/ten-star.csv");
            parser.action(args);
            assertTrue(Main.getKdTree().getNodeList().size() == 10);

            args.set(1, "data/stars/stardata.csv");
            parser.action(args);
            assertTrue(Main.getKdTree().getNodeList().size() == 119617);
        } catch(Exception e) {
            System.err.println("ERROR: Error parsing file in testNumLines");
        }
    }


    @Test
    public void testContainsStar() {
        Star star;
        try {
            // Single star
            args.set(1, "data/stars/one-star.csv");
            parser.action(args);
            star = new Star("1", "Lonely Star", 5, -2.24, 10.04);
            assertTrue(Main.getKdTree().getNodeList().get(0).equals(star));

            // Check doesn't have, first, middle, last
            args.set(1, "data/stars/ten-star.csv");
            parser.action(args);
            assertFalse(Main.getKdTree().getNodeList().contains(star));

            star = new Star("0", "Sol", 0, 0, 0);
            assertTrue(Main.getKdTree().getNodeList().contains(star));

            star = new Star("3759", "96 G. Psc", 7.26388,1.55643,0.68697);
            assertTrue(Main.getKdTree().getNodeList().contains(star));

            star = new Star("118721","",-2.28262,0.64697,0.29354);
            assertTrue(Main.getKdTree().getNodeList().contains(star));

            // No parameter can vary
            star = new Star("118722","",-2.28262,0.64697,0.29354);
            assertFalse(Main.getKdTree().getNodeList().contains(star));
            star = new Star("118721"," ",-2.28262,0.64697,0.29354);
            assertFalse(Main.getKdTree().getNodeList().contains(star));
            star = new Star("118721","",-2.28261,0.64697,0.29354);
            assertFalse(Main.getKdTree().getNodeList().contains(star));
            star = new Star("118721","",-2.28262,0.64698,0.29354);
            assertFalse(Main.getKdTree().getNodeList().contains(star));
            star = new Star("118721","",-2.28262,0.64697,0.39354);
            assertFalse(Main.getKdTree().getNodeList().contains(star));

        } catch(Exception e) {
            System.err.println(e);
            assertFalse(true);
        }

    }

}
