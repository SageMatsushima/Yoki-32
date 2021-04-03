package edu.brown.cs.yoki.tree;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * A KdTree of Nodes.
 * @param <N> A node extending KdNode
 */
public class KdTree<N extends KdNode> {
  private N root = null;
  private int dim;
  private ArrayList<N> list = new ArrayList<>();

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
    this.list = nodeList;

    if (nodeList.size() == 0) {
      root = null;
      return;
    }
//    getCoords().length
    dim = list.get(0).getCoords().length;
    alexKDTree = new AlexKDTree<>(nodeList, dim);
    root = alexKDTree.getRoot();
//    for (N node : this.list) {
//      double dist = Math.sqrt(Math.pow(nodeIgnored.getCoords()[0] - node.getCoords()[0], 2)
//          + Math.pow(nodeIgnored.getCoords()[1] - node.getCoords()[1], 2)
//          + Math.pow(nodeIgnored.getCoords()[2] - node.getCoords()[2], 2)
//          + Math.pow(nodeIgnored.getCoords()[3] - node.getCoords()[3], 2)
//          + Math.pow(nodeIgnored.getCoords()[4] - node.getCoords()[4], 2));
//      System.out.println(node + ": Dist = " + dist);
//    }
//    System.out.println("-----------");
  }

  /**
   * Performs a KD Tree search and prints the found nodes in order.
   */
  public void searchAndPrint() {
    for (N node : this.list) {
      double dist = Math.sqrt(Math.pow(nodeIgnored.getCoords()[0] - node.getCoords()[0], 2)
          + Math.pow(nodeIgnored.getCoords()[1] - node.getCoords()[1], 2)
          + Math.pow(nodeIgnored.getCoords()[2] - node.getCoords()[2], 2)
          + Math.pow(nodeIgnored.getCoords()[3] - node.getCoords()[3], 2)
          + Math.pow(nodeIgnored.getCoords()[4] - node.getCoords()[4], 2));
      System.out.println(node + ": Dist = " + dist);
    }
    System.out.println("-----------");
    kdNeighbors();
    printFound();
  }

  private void printFound() {
    for (N node : found) {
      double dist = Math.sqrt(Math.pow(nodeIgnored.getCoords()[0] - node.getCoords()[0], 2)
          + Math.pow(nodeIgnored.getCoords()[1] - node.getCoords()[1], 2)
          + Math.pow(nodeIgnored.getCoords()[2] - node.getCoords()[2], 2)
          + Math.pow(nodeIgnored.getCoords()[3] - node.getCoords()[3], 2)
          + Math.pow(nodeIgnored.getCoords()[4] - node.getCoords()[4], 2));
      System.out.println(node + ": Dist = " + dist);
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
    found.add(maxElt);
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
  public void setCoords(int[] newCoords) {
    coords = newCoords;
  }

  /**
   * Setter for node to be ignored.
   * @param node A KdNode
   */
  public void setNodeIgnored(N node) {
    nodeIgnored = node;
  }
}
