package edu.brown.cs.rhill6.map;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

/**
 * An edge that has a weight and two nodes.
 * @param <N> Node
 * @param <E> Edge
 */
interface Edge<N extends Node<N, E>, E extends Edge<N, E>> {
  N getStart();
  N getEnd();
  double getWeight();
}

/**
 * An node that has a distance function that takes in other nodes.
 * @param <N> Node
 * @param <E> Edge
 */
interface Node<N extends Node<N, E>, E extends Edge<N, E>> {
  double distance(Object o);
  List<E> getEdges();
}

/**
 * A class that stores information about nodes and edges and runs an A* search.
 * @param <N> Node
 * @param <E> Edge
 */
public class Graph<N extends Node<N, E>, E extends Edge<N, E>> {
  private final HashSet<N> seen = new HashSet<>();
  private final PriorityQueue<NodeDistance> fringe = new PriorityQueue<>(new NodeDistanceCompare());
  private final HashMap<N, NodeDistance> nodeDistanceMap = new HashMap<>();
  private N goal;
  private String output = "";
  private static final int SIZE = 10000;
  private static final int DURATION = 10000;
  //This is the caching variable that gets the edges
  private LoadingCache<N, List<E>> wayCache = CacheBuilder.newBuilder()
      .maximumSize(SIZE)
      .expireAfterWrite(DURATION, TimeUnit.MINUTES)
      .build(new CacheLoader<N, List<E>>() {
        public List<E> load(N key) {
          return key.getEdges();
        }
      });

  /**
   * A comparator for NodeDistance.
   */
  class NodeDistanceCompare implements Comparator<NodeDistance> {
    @Override
    public int compare(NodeDistance n1, NodeDistance n2) {
      return Double.compare(n1.minDist, n2.minDist);
    }
  }

  /**
   * A wrapper class to contain a node, a previous node, and a distance.
   */
  public class NodeDistance {
    private double minDist;
    private N node;
    private N previous;
    private E edge;

    /**
     * Constructor for NodeDistance.
     * @param node Node
     * @param distance Double
     * @param prev Node
     * @param edge Edge
     */
    public NodeDistance(N node, double distance, N prev, E edge) {
      this.node = node;
      this.minDist = distance;
      this.previous = prev;
      this.edge = edge;
    }

    /**
     * A getter method that returns the current node.
     * @return current node
     */
    public N getNode() {
      return this.node;
    }

    /**
     * A getter method that return the minimum distance.
     * @return the minimum distance
     */
    public double getMinDist() {
      return this.minDist;
    }

    /**
     * A getter method that gets the previous node.
     * @return previous node
     */
    public N getPrevious() {
      return this.previous;
    }

    /**
     * A getter method that gets the edges from a node.
     * @return the edges from a node
     */
    public E getEdge() {
      return this.edge;
    }
  }


  /**
   * Use A* to search for a path from one node to another.
   * @param start A node N
   * @param inGoal A node N
   */
  public void dijkstra(N start, N inGoal) {
    if (start.equals(inGoal)) {
      System.out.println("Path starts and ends at node " + start);
      return;
    } else if (start == null) {
      System.err.println("Start node not defined");
      return;
    } else if (inGoal == null) {
      System.err.println("End node not defined");
      return;
    }
    //get the goal node and clear everything
    this.goal = inGoal;
    seen.clear();
    fringe.clear();
    nodeDistanceMap.clear();

    //create a new start node with the input and add it to the fringe and distance map
    NodeDistance startNodeDist = new NodeDistance(start, 0, null, null);
    fringe.add(startNodeDist);
    nodeDistanceMap.put(start, startNodeDist);
    //Run dijstras and see it it passes, then print out the path
    if (dijkstraCheck()) {
      // reached goal
      printPath(nodeDistanceMap.get(goal));
    } else {
      System.out.println(start + " -/- " + goal);
    }
  }

  /**
   * This method runs the dijstra algorithm and adds nodes and paths to nodeDistanceMap.
   * @return a boolean describing if there exists a path to the end
   */
  private boolean dijkstraCheck() {
    while (fringe.size() > 0) {
      NodeDistance current = fringe.poll();
      N node = current.node;
      double dist = current.minDist;

      if (seen.contains(node)) {
        // Already found a shorter path to this node
        continue;
      } else if (node.equals(goal)) {
        return true;
      }

      // Add this node to tree
      seen.add(node);

      // Add or update new nodes in fringe
      List<E> neighbors;

      try {
        neighbors = wayCache.get(node);
      } catch (Exception e) {
        System.err.println("ERROR: Error loading from cache");
        return false;
      }
      //Run through the edges connected to the node and adds it nodeDistanceMap
      for (E edge : neighbors) {
        N endNode = edge.getEnd();
        //This is the implementation of A*
        double edgeWeight = edge.getWeight() + node.distance(goal);
        boolean proceed = !nodeDistanceMap.containsKey(endNode)
            || Double.compare(dist + edgeWeight, node.distance(goal)
            + nodeDistanceMap.get(endNode).minDist) < 0;
        if (proceed) {
          // Not yet seen or shorter path, add to fringe
          NodeDistance nodeDistance =
              new NodeDistance(endNode, dist + edge.getWeight(), node, edge);
          fringe.add(nodeDistance);
          nodeDistanceMap.put(endNode, nodeDistance);
        }
      }
    }
    return false;
  }

  /**
   * This method prints out the path from the start node to the end node.
   * @param n the goal node
   */
  private void printPath(NodeDistance n) {
    if (n.previous != null) {
      printPath(nodeDistanceMap.get(n.previous));
      output += n.previous + " -> " + n.node
        + " : " + n.edge + "\n";
      System.out.println(n.previous + " -> " + n.node
          + " : " + n.edge);
    }
  }

  /**
   * Getter for output string that resets output to the empty string.
   * @return The output string.
   */
  public String getOutput() {
    String temp = output;
    output = "";
    return temp;
  }

  /**
   * This is a getter method to find the end goal node from nodeDistanceMap.
   * @return goal node
   */
  public HashMap<N, NodeDistance> getNodeDistanceMap() {
    return nodeDistanceMap;
  }

  /**
   * This is a getter method to get the goal node to start testing.
   * @return the goal node
   */
  public N getGoal() {
    return goal;
  }
}
