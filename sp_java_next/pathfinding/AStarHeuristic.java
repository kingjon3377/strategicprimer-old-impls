package pathfinding;

import model.location.IPoint;
import model.location.SPMap;
import model.module.kinds.MobileModule;

/**
 * The description of a class providing a cost for a given tile based on a
 * target location and entity being moved. This heuristic controls what priority
 * is placed on different tiles during the search for a path
 * 
 * @author Kevin Glass
 */
public interface AStarHeuristic {

	/**
	 * Get the additional heuristic cost of the given tile. This controls the
	 * order in which tiles are searched while attempting to find a path to the
	 * target location. The lower the cost the more likely the tile will be
	 * searched.
	 * 
	 * @param map
	 *            The map on which the path is being found
	 * @param mover
	 *            The entity that is moving along the path
	 * @param start The coordinates of the tile being evaluated
	 * @param dest
	 *            The coordinates of the target location
	 * @return The cost associated with the given tile
	 */
	double getCost(final SPMap map, final MobileModule mover,
			final IPoint start, final IPoint dest);
}
