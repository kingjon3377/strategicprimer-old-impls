package pathfinding;

import model.location.IPoint;

/**
 * A single node in the search graph.
 * 
 * @author Kevin Glass
 */
public class Node implements Comparable<Node>, IPoint {
	/** The x coordinate of the node. */
	private final int xCoord;
	/** The y coordinate of the node. */
	private final int yCoord;
	/** The path cost for this node. */
	private double cost;
	/** The parent of this node, how we reached it in the search. */
	private Node parent;
	/** The heuristic cost of this node. */
	private double heuristic;
	/** The search depth of this node. */
	private int depth;

	/**
	 * Create a new node.
	 * 
	 * @param xCoordinate
	 *            The x coordinate of the node
	 * @param yCoordinate
	 *            The y coordinate of the node
	 */
	public Node(final int xCoordinate, final int yCoordinate) {
		xCoord = xCoordinate;
		yCoord = yCoordinate;
	}

	/**
	 * Set the parent of this node.
	 * 
	 * @param newParent
	 *            The parent node which lead us to this node
	 * @return The depth we have no reached in searching
	 */
	public int setParent(final Node newParent) {
		depth = newParent == null ? 1 : newParent.depth + 1;
		parent = newParent;
		return depth;
	}

	/**
	 * @param other
	 *            Another Node
	 * @return The result of the comparison with that Node.
	 * @see Comparable#compareTo(Object)
	 */
	@Override
	public int compareTo(final Node other) {
		return (int) Math.round((heuristic + cost)
				- (other.heuristic + other.cost));
	}

	/**
	 * @return the cost associated with the node
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @return the X coordinate of the Node
	 */
	public int getX() {
		return xCoord;
	}

	/**
	 * @return the Y coordinate of the Node
	 */
	public int getY() {
		return yCoord;
	}

	/**
	 * @return the parent Node
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * @param newCost
	 *            the new cost of the Node
	 */
	public void setCost(final double newCost) {
		cost = newCost;
	}

	/**
	 * @param newDepth
	 *            the Node's new depth
	 */
	public void setDepth(final int newDepth) {
		depth = newDepth;
	}

	/**
	 * @param newHeuristic
	 *            the new heuristic
	 */
	public void setHeuristic(final double newHeuristic) {
		heuristic = newHeuristic;
	}
}
