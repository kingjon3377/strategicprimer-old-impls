package pathfinding;

import model.module.kinds.MobileModule;

/**
 * A description of an implementation that can find a path from one 
 * location on a tile map to another based on information provided
 * by that tile map.
 * 
 * @see model.location.SPMap
 * @author Kevin Glass
 */
public interface PathFinder {

	/**
	 * Find a path from the starting location provided (sx,sy) to the target
	 * location (tx,ty) avoiding blockages and attempting to honour costs 
	 * provided by the tile map.
	 * 
	 * @param mover The entity that will be moving along the path. This provides
	 * a place to pass context information about the game entity doing the moving, e.g.
	 * can it fly? can it swim etc.
	 * 
	 * @param startX The x coordinate of the start location
	 * @param startY The y coordinate of the start location
	 * @param destX The x coordinate of the target location
	 * @param destY The y coordinate of the target location
	 * @return The path found from start to end, or null if no path can be found.
	 */
	Path findPath(MobileModule mover, int startX, int startY, int destX, int destY);
}
