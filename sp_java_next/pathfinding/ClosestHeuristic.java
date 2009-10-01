package pathfinding;

import model.location.IPoint;
import model.location.SPMap;
import model.module.kinds.MobileModule;

/**
 * A heuristic that uses the tile that is closest to the target as the next best
 * tile.
 * 
 * @author Kevin Glass
 */
public class ClosestHeuristic implements AStarHeuristic {
	// ESCA-JAVA0138:
	/**
	 * @param map
	 *            The map
	 * @param mover
	 *            The moving module
	 * @param start The starting coordinates
	 * @param dest The destination coordinates
	 * @return An estimate of the cost of the route
	 * @see AStarHeuristic#getCost(TileBasedMap, Mover, int, int, int, int)
	 */
	@Override
	public double getCost(final SPMap map, final MobileModule mover,
			final IPoint start, final IPoint dest) {
		return Math.sqrt((dest.getX() - start.getX())
				* (dest.getX() - start.getX()) + (dest.getY() - start.getY())
				* (dest.getY() - start.getY()));
	}

}