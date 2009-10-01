package pathfinding;

import model.location.IPoint;

/**
 * A single node in the search graph
 * 
 * @author Kevin Glass
 */
public class Node implements Comparable<Node>, IPoint {
	/** The x coordinate of the node */
	private final int xCoord;
	/** The y coordinate of the node */
	private final int yCoord;
	/** The path cost for this node */
	private double cost;
	/** The parent of this node, how we reached it in the search */
	private Node parent;
	/** The heuristic cost of this node */
	private double heuristic;
	/** The search depth of this node */
	private int depth;

	/**
	 * Create a new node
	 * 
	 * @param _xCoord
	 *            The x coordinate of the node
	 * @param _yCoord
	 *            The y coordinate of the node
	 */
	public Node(final int _xCoord, final int _yCoord) {
		this.xCoord = _xCoord;
		this.yCoord = _yCoord;
	}

	/**
	 * Set the parent of this node
	 * 
	 * @param _parent
	 *            The parent node which lead us to this node
	 * @return The depth we have no reached in searching
	 */
	public int setParent(final Node _parent) {
		depth = _parent == null ? 1 : _parent.depth + 1;
		parent = _parent;
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
	 * @param _cost
	 *            the new cost of the Node
	 */
	public void setCost(final double _cost) {
		cost = _cost;
	}

	/**
	 * @param _depth
	 *            the Node's new depth
	 */
	public void setDepth(final int _depth) {
		depth = _depth;
	}

	/**
	 * @param _heuristic
	 *            the new heuristic
	 */
	public void setHeuristic(final double _heuristic) {
		heuristic = _heuristic;
	}
}