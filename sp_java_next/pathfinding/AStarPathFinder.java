package pathfinding;

import java.util.ArrayList;
import java.util.List;

import model.location.IPoint;
import model.location.Point;
import model.location.SPMap;
import model.module.kinds.MobileModule;

/**
 * A path finder implementation that uses the AStar heuristic based algorithm to
 * determine a path.
 * 
 * @author Kevin Glass
 */
public class AStarPathFinder implements PathFinder {
	/** The set of nodes that have been searched through */
	private final List<Node> closed = new ArrayList<Node>();
	/** The set of nodes that we do not yet consider fully searched */
	private final SortedList<Node> open = new SortedList<Node>();

	/** The map being searched */
	private SPMap map;
	/** The maximum depth of search we're willing to accept before giving up */
	private int maxSearchDistance;

	/** The complete set of nodes across the map */
	private Node[][] nodes;
	/** True if we allow diagonal movement */
	private boolean allowDiagMovement;
	/** The heuristic we're applying to determine which nodes to search first */
	private AStarHeuristic heuristic;

	/**
	 * Create a path finder with the default heuristic - closest to target.
	 * 
	 * @param _map
	 *            The map to be searched
	 * @param _maxSearchDist
	 *            The maximum depth we'll search before giving up
	 * @param _allowDiagMove
	 *            True if the search should try diagonal movement
	 */
	public AStarPathFinder(final SPMap _map, final int _maxSearchDist,
			final boolean _allowDiagMove) {
		this(_map, _maxSearchDist, _allowDiagMove, new ClosestHeuristic());
	}

	/**
	 * Create a path finder
	 * 
	 * @param _heuristic
	 *            The heuristic used to determine the search order of the map
	 * @param _map
	 *            The map to be searched
	 * @param maxSearchDist
	 *            The maximum depth we'll search before giving up
	 * @param _allowDiagMove
	 *            True if the search should try diaganol movement
	 */
	public AStarPathFinder(final SPMap _map, final int maxSearchDist,
			final boolean _allowDiagMove, final AStarHeuristic _heuristic) {
		this.heuristic = _heuristic;
		this.map = _map;
		this.maxSearchDistance = maxSearchDist;
		this.allowDiagMovement = _allowDiagMove;

		nodes = new Node[_map.getSizeCols()][_map.getSizeRows()];
		for (int x = 0; x < _map.getSizeCols(); x++) {
			for (int y = 0; y < _map.getSizeRows(); y++) {
				nodes[x][y] = new Node(x, y); // NOPMD
			}
		}
	}

	/**
	 * @see PathFinder#findPath(Mover, int, int, int, int)
	 * @return the shortest path for this mover from the start to the
	 *         destination. Returns an *empty* Path, rather than null, if no
	 *         path exists.
	 */
	@Override
	public Path findPath(final MobileModule mover, final int startX,
			final int startY, final int destX, final int destY) {
		// easy first check, if the destination is blocked, we can't get there
		final Path path = new Path();
		if (map.getTileAt(destX, destY).checkAdd(mover)) {

			// initial state for A*. The closed group is empty. Only the
			// starting

			// tile is in the open list and it'e're already there
			nodes[startX][startY].setCost(0);
			nodes[startX][startY].setDepth(0);
			closed.clear();
			open.clear();
			open.add(nodes[startX][startY]);

			nodes[destX][destY].setParent(null);

			pathSearch(mover, nodes[startX][startY], nodes[destX][destY]);

			// since we'e've run out of search there was no path. Just return
			// null

			if (nodes[destX][destY].getParent() != null) {

				// At this point we've definitely found a path so we can uses
				// the parent references of the nodes to find out way from the
				// target location back to the start recording the nodes on the
				// way.

				Node target = nodes[destX][destY];
				while (target != nodes[startX][startY]) {
					path.prependStep(target.getX(), target.getY());
					target = target.getParent();
				}
				path.prependStep(startX, startY);

				// thats it, we have our path

			}
		}
		System.out.println(path);
		return path;
	}

	/**
	 * Formerly the main logic of findPath()
	 * 
	 * @param mover
	 *            The moving module
	 * @param start
	 *            The coordinates of the starting tile
	 * @param dest
	 *            The coordinates of the destination tile
	 */
	private void pathSearch(final MobileModule mover, final IPoint start,
			final IPoint dest) {
		// while we haven'n't exceeded our max search depth
		int maxDepth = 0;
		while ((maxDepth < maxSearchDistance) && (open.size() != 0)) {
			// pull out the first node in our open list, this is determined
			// to be the most likely to be the next step based on our
			// heuristic

			final Node current = getFirstInOpen();
			if (current == nodes[dest.getX()][dest.getY()]) {
				break;
			}

			removeFromOpen(current);
			addToClosed(current);

			// search through all the neighbours of the current node
			// evaluating them as next steps

			for (int x = -1; x < 2; x++) {
				for (int y = -1; y < 2; y++) {
					if (x + current.getX() < 0 || y + current.getY() < 0) {
						continue;
					}
					final int temp = checkNode(mover, start, dest, current,
							nodes[(x + current.getX())][(y + current.getY())]);
					if (temp != -1) {
						maxDepth = temp;
					}
				}
			}
		}
	}

	/**
	 * The main logic of pathSearch()
	 * 
	 * @param mover
	 *            The moving module
	 * @param start
	 *            The starting tile's coordinates
	 * @param dest
	 *            The destination tile's coordinates
	 * @param current
	 *            the current node
	 * @param node
	 *            the node we're considering around the current one
	 * @return The new max depth
	 */
	private int checkNode(final MobileModule mover, final IPoint start,
			final IPoint dest, final Node current, final Node node) {
		// Check whether the tile in question is the current one
		// (ignore) or disallowed if diagonal movement is
		// forbidden. If neither of these cases, determine the
		// location of the neighbor and evaluate it
		int maxDepth = -1;
		if ((!node.equals(current)) && (legalNeighbor(current, node))
				&& isValidLocation(mover, start, node.getX(), node.getY())) {
			// the cost to get to this node is cost the
			// current plus the movement cost to reach this
			// node. Note that the heuristic value is only used
			// in the sorted open list

			if ((current.getCost() + getMovementCost(mover, current, node)) < node
					.getCost()) {
				removeFromLists(node);
			}

			// if the node hasn't already been processed and
			// discarded then reset it's cost to our current
			// cost and add it as a next possible step (i.e. to
			// the open list)

			if (!inOpenList(node) && !(inClosedList(node))) {
				node.setCost(current.getCost()
						+ getMovementCost(mover, current, node));
				node.setHeuristic(getHeuristicCost(mover, node, dest));
				maxDepth = Math.max(maxDepth, node.setParent(current));
				addToOpen(node);
			}
		}
		return maxDepth;
	}

	/**
	 * @param current
	 *            the current node
	 * @param node
	 *            its neighbor under consideration
	 * @return whether moving to that neighbor is allowed under the rule that
	 *         might prohibit diagonals
	 */
	private boolean legalNeighbor(final Node current, final Node node) {
		return allowDiagMovement || (node.getX() - current.getX() == 0)
				|| (node.getY() - current.getY() == 0);
	}

	/**
	 * Remove a node from the open and closed lists, if it's in either or both
	 * of them.
	 * 
	 * @param node
	 *            the node to remove
	 */
	private void removeFromLists(final Node node) {
		if (inOpenList(node)) {
			removeFromOpen(node);
		}
		if (inClosedList(node)) {
			removeFromClosed(node);
		}
	}

	/**
	 * Get the first element from the open list. This is the next one to be
	 * searched.
	 * 
	 * @return The first element in the open list
	 */
	protected Node getFirstInOpen() {
		return open.first();
	}

	/**
	 * Add a node to the open list
	 * 
	 * @param node
	 *            The node to be added to the open list
	 */
	protected void addToOpen(final Node node) {
		open.add(node);
	}

	/**
	 * Check if a node is in the open list
	 * 
	 * @param node
	 *            The node to check for
	 * @return True if the node given is in the open list
	 */
	protected boolean inOpenList(final Node node) {
		return open.contains(node);
	}

	/**
	 * Remove a node from the open list
	 * 
	 * @param node
	 *            The node to remove from the open list
	 */
	protected void removeFromOpen(final Node node) {
		open.remove(node);
	}

	/**
	 * Add a node to the closed list
	 * 
	 * @param node
	 *            The node to add to the closed list
	 */
	protected void addToClosed(final Node node) {
		closed.add(node);
	}

	/**
	 * Check if the node supplied is in the closed list
	 * 
	 * @param node
	 *            The node to search for
	 * @return True if the node specified is in the closed list
	 */
	protected boolean inClosedList(final Node node) {
		return closed.contains(node);
	}

	/**
	 * Remove a node from the closed list
	 * 
	 * @param node
	 *            The node to remove from the closed list
	 */
	protected void removeFromClosed(final Node node) {
		closed.remove(node);
	}

	/**
	 * Check if a given location is valid for the supplied mover
	 * 
	 * @param mover
	 *            The mover that would hold a given location
	 * @param start
	 *            The starting coordinates
	 * @param xCoord
	 *            The x coordinate of the location to check
	 * @param yCoord
	 *            The y coordinate of the location to check
	 * @return True if the location is valid for the given mover
	 */
	protected boolean isValidLocation(final MobileModule mover,
			final IPoint start, final int xCoord, final int yCoord) {
		return !((((xCoord < 0) || (yCoord < 0)
				|| (xCoord >= map.getSizeCols()) || (yCoord >= map
				.getSizeRows())))
				|| ((start.getX() == xCoord) && (start.getY() == yCoord)) ? ((xCoord < 0)
				|| (yCoord < 0) || (xCoord >= map.getSizeCols()) || (yCoord >= map
				.getSizeRows()))
				: !map.getTileAt(xCoord, yCoord).checkAdd(mover));
	}

	// ESCA-JAVA0173:
	/**
	 * Get the cost to move through a given location
	 * 
	 * @param mover
	 *            The entity that is being moved
	 * @param start
	 *            The coordinates of the tile whose cost is being determined
	 * @param dest
	 *            The coordinates of the target location
	 * @return The cost of movement through the given tile
	 */
	public double getMovementCost(final MobileModule mover, final IPoint start,
			@SuppressWarnings("unused") final IPoint dest) {
		return mover.getCost(map.getTileAt(new Point(start)));
	}

	/**
	 * Get the heuristic cost for the given location. This determines in which
	 * order the locations are processed.
	 * 
	 * @param mover
	 *            The entity that is being moved
	 * @param current
	 *            The coordinates of the tile whose cost is being determined
	 * @param dest
	 *            The coordinates of the target location
	 * @return The heuristic cost assigned to the tile
	 */
	public double getHeuristicCost(final MobileModule mover,
			final IPoint current, final IPoint dest) {
		return heuristic.getCost(map, mover, current, dest);
	}
}