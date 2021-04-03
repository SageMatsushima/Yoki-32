package edu.brown.cs.yoki.tree;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Collections;

/**
 * A KdTree of Nodes.
 * @param <N> A node extending KdNode
 */
public class KdTree<N extends KdNode> {
  private N root = null;
  private int dim;
  private final ArrayList<N> list = new ArrayList<>();

  // Fields for search functions
  private int k;
  private double r;
  private int[] coords = new int[5];
  private N nodeIgnored;
  private final ArrayList<N> found = new ArrayList<>();
  private AlexKDTree<N> alexKDTree;

  /**
   * Constructs a KD tree from its list of nodes; nodes remain
   * in the list but are assigned left and right children.
   * @param nodeList List of nodes
   */
  public void listToTree(ArrayList<N> nodeList) {
    // start by getting median along first dim, root
    // recur down tree, alternating dimension
    // pass down range to look for the median in

    if (list.size() == 0) {
      root = null;
      return;
    }

    dim = list.get(0).getCoords().length;
    alexKDTree = new AlexKDTree<>(nodeList, 5);
    root = alexKDTree.getRoot();
  }

  /**
   * Performs a KD Tree search and prints the found nodes in order.
   */
  public void searchAndPrint() {
    kdNeighbors();
    printFound();
  }

  /**
   * Performs a naive list search and prints the found nodes in order.
   */
  public void naiveSearchAndPrint() {
    naiveNeighbors();
    printFound();
  }

  private void printFound() {
    for (N node : found) {
      System.out.println(node);
    }
  }

  /**
   * Performs a KD nearest neighbors search based on fields.
   */
  public void kdNeighbors() {
    found.clear();
    if (root == null || k == 0) {
      return;
    }
    alexKDTree.setNeighbor(k);
    alexKDTree.setRadius(r);
    PriorityQueue<N> foundQ = alexKDTree.search(nodeIgnored, alexKDTree.getRoot(), coords);
    heapToList(foundQ);
  }

  private void heapToList(PriorityQueue<N> maxHeap) {
    if (maxHeap == null || maxHeap.size() == 0) {
      found.clear();
      return;
    }

    N maxElt = maxHeap.poll();
    heapToList(maxHeap);
    if (maxElt.distance(coords) <= r) {
      found.add(maxElt);
    }
  }

  /**
   * Performs a naive list search based on fields.
   */
  public void naiveNeighbors() {
    // Don't add nodes with this distance or less, already added
    double distAdded = -1;
    double currentMin;
    ArrayList<Integer> indices = new ArrayList<>();
    double newDist;

    found.clear();

    while (found.size() < k && found.size() < list.size()) {
      indices.clear();
      currentMin = Double.POSITIVE_INFINITY;

      for (int i = 0; i < list.size(); i++) {
        // If searching from a node, ignore that node
        if (list.get(i).equals(nodeIgnored)) {
          continue;
        }

        newDist = list.get(i).distance(coords);
        if (newDist < currentMin && newDist > distAdded && newDist <= r) {
          // Found better node
          currentMin = newDist;
          indices.clear();
          indices.add(i);
        } else if (newDist == currentMin && newDist > distAdded && newDist <= r) {
          // Two equally close nodes
          indices.add(i);
        }
      }

      distAdded = currentMin;

      if (indices.size() == 0) {
        // None found, finish
        return;
      } else if (found.size() + indices.size() <= k) {
        // Print normally

        for (int idx : indices) {
          found.add(list.get(idx));
        }
      } else {
        // Randomize, print up to k total
        // nodes_found + indices.size() > k
        // indices.size() > k - nodes_found
        Collections.shuffle(indices);
        int holdSize = found.size();
        for (int i = 0; i < k - holdSize; i++) {
          found.add(list.get(indices.get(i)));
        }
      }
    }
  }

  /**
   * Accessor for nodes.
   * @return An ArrayList of nodes
   */
  public ArrayList<N> getNodeList() {
    return list;
  }

  /**
   * Accessor for nodes found in search.
   * @return An ArrayList of nodes
   */
  public ArrayList<N> getFound() {
    return found;
  }

  /**
   * Setter for k.
   * @param newK Nonnegative integer
   */
  public void setK(int newK) {
    k = newK;
  }

  /**
   * Setter for radius.
   * @param newR Nonnegative double
   */
  public void setR(double newR) {
    r = newR;
  }

  /**
   * Setter for coordinates, creates a copy of the given array.
   * @param newCoords An ArrayList of doubles.
   */
  public void setCoords(ArrayList<Double> newCoords) {
    coords = new int[5];
  }

  /**
   * Setter for node to be ignored.
   * @param node A KdNode
   */
  public void setNodeIgnored(N node) {
    nodeIgnored = node;
  }
}
