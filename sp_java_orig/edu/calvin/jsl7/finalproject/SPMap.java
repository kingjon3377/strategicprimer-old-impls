package edu.calvin.jsl7.finalproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * The Map class models a map; it holds an array of tiles and (statically) the
 * constants that define how big that array must be and how many terrain types
 * there are.
 * 
 * @author Jonathan Lovelace
 * @date 27 November 2006
 * @assignment Final Project
 * @course CS108A
 * @semester 06FA
 * 
 */

public class SPMap {
	/**
	 * The size of the map in the X dimension
	 */
	private final int mapSizeX;
	/**
	 * The size of the map in the Y dimension
	 */
	private final int mapSizeY;

	/**
	 * The largest terrain type number
	 */
	public static final int MAX_TERRAIN_TYPE = 17;

	/**
	 * The tiles that make up the map.
	 */
	private final List<List<Tile> > myTiles;

	/**
	 * Constructor. Takes the size of the map and an array of Tiles of that
	 * size.
	 * 
	 * @param xSize
	 *            Number of rows
	 * @param ySize
	 *            Number of columns
	 * @param tiles
	 *            The tiles that make up the map
	 */
	public SPMap(final int xSize, final int ySize, final List<List<Tile>> tiles) {
		mapSizeX = xSize;
		mapSizeY = ySize;
		if (tiles.size() == mapSizeX || tiles.get(1).size() != mapSizeY) {
			throw new IllegalArgumentException("Map must be of height "
					+ mapSizeY + " and width " + mapSizeX);
		}
		myTiles = new ArrayList<List<Tile>>(tiles);
	}

	/**
	 * @return The tiles that make up the map
	 */
	public List<List<Tile> > getTiles() {
		return Collections.unmodifiableList(myTiles);
	}

	/**
	 * @param xCoord
	 *            an X coordinate
	 * @param yCoord
	 *            a Y coordinate
	 * @return the tile at those coordinates
	 */
	public Tile getTile(final int xCoord, final int yCoord) {
		if (xCoord > mapSizeX || xCoord < 0) {
			throw new IllegalArgumentException("x out of bounds");
		} else if (yCoord > mapSizeY || yCoord < 0) {
			throw new IllegalArgumentException("y out of bounds");
		}
		return myTiles.get(xCoord).get(yCoord);
	}

	/**
	 * @return The size of the map in the X dimension
	 */
	public int getSizeX() {
		return mapSizeX;
	}

	/**
	 * @return The size of the map in the Y dimension
	 */
	public int getSizeY() {
		return mapSizeY;
	} 

	/**
	 * Checks (recursively) that an unobstructed path exists from the start tile
	 * to the destination tile, taking the given amount of speed and no more. We
	 * require the unit (etc.) doing the moving as a parameter so the starting
	 * point doesn't count as occupied.
	 * 
	 * This is probably the single longest function in the project. It is ugly
	 * code, but I couldn't think of any better way to do it.
	 * 
	 * @bug PMD says this is too long, and I agree.
	 * 
	 * @param start
	 *            The starting tile
	 * @param dest
	 *            The destination tile
	 * @param spd
	 *            The maximum distance
	 * @param mover
	 *            The unit (etc.) doing the moving
	 * @return whether a suitable path exists
	 */
	public boolean checkPath(final Tile start, final Tile dest, // NOPMD
			final int spd, final SimpleModule mover) {
		// If the destination is occupied, return false.
		// If the start is occupied by someone other than the moving module,
		// return false. (For recursion.)
		// If the start and destination are the same, we have reached our
		// destination.
		// If we have not reached our destination but have reached our maximum
		// distance, return false.
		// Beyond that, it gets complicated. There are eight possible
		// directions for movement. We choose the simplest one and recurse into
		// it. If it fails, we try one of the less likely ones.


		return (movePreconditions(start, dest, spd, mover) && ((((dest.getX() > start
				.getX()) && (dest.getY() > start.getY())) && (checkPath(getTile(start
				.getX() + 1, start.getY() + 1), dest, spd - start.getMovementCost(),
				mover)
				|| checkPath(getTile(start.getX() + 1, start.getY()), dest, spd
						- start.getMovementCost(), mover)
				|| checkPath(getTile(start.getX(), start.getY() + 1), dest, spd
						- start.getMovementCost(), mover)
				|| checkPath(getTile(start.getX() + 1, start.getY() - 1), dest, spd
						- start.getMovementCost(), mover)
				|| checkPath(getTile(start.getX() - 1, start.getY() + 1), dest, spd
						- start.getMovementCost(), mover)
				|| checkPath(getTile(start.getX() - 1, start.getY()), dest, spd
						- start.getMovementCost(), mover)
				|| checkPath(getTile(start.getX(), start.getY() - 1), dest, spd
						- start.getMovementCost(), mover) || checkPath(getTile(start
				.getX() - 1, start.getY() - 1), dest, spd - start.getMovementCost(),
				mover)))
				|| (((dest.getX() > start.getX()) && (dest.getY() == start.getY())) && (checkPath(
						getTile(start.getX() + 1, start.getY()), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() + 1, start.getY() - 1), dest,
								spd - start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() + 1, start.getY() + 1), dest,
								spd - start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX(), start.getY() - 1), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX(), start.getY() + 1), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() - 1, start.getY() - 1), dest,
								spd - start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() - 1, start.getY() + 1), dest,
								spd - start.getMovementCost(), mover) || checkPath(
						getTile(start.getX() - 1, start.getY()), dest, spd
								- start.getMovementCost(), mover)))
				|| (((dest.getX() > start.getX()) && (dest.getY() < start.getY())) && (checkPath(
						getTile(start.getX() + 1, start.getY() - 1), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() + 1, start.getY()), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX(), start.getY() - 1), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() + 1, start.getY() + 1), dest,
								spd - start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() - 1, start.getY() - 1), dest,
								spd - start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() - 1, start.getY()), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX(), start.getY() + 1), dest, spd
								- start.getMovementCost(), mover) || checkPath(getTile(
						start.getX() - 1, start.getY() + 1), dest, spd
						- start.getMovementCost(), mover)))
				|| (((dest.getX() == start.getX()) && (dest.getY() < start.getY())) && (checkPath(
						getTile(start.getX(), start.getY() - 1), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() - 1, start.getY() - 1), dest,
								spd - start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() + 1, start.getY() - 1), dest,
								spd - start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() - 1, start.getY()), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() + 1, start.getY()), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() - 1, start.getY() + 1), dest,
								spd - start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() + 1, start.getY() + 1), dest,
								spd - start.getMovementCost(), mover) || checkPath(
						getTile(start.getX(), start.getY() + 1), dest, spd
								- start.getMovementCost(), mover)))
				|| (((dest.getX() < start.getX()) && (dest.getY() < start.getY())) && (checkPath(
						getTile(start.getX() - 1, start.getY() - 1), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() - 1, start.getY()), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX(), start.getY() - 1), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() - 1, start.getY() + 1), dest,
								spd - start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() + 1, start.getY() - 1), dest,
								spd - start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() + 1, start.getY()), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX(), start.getY() + 1), dest, spd
								- start.getMovementCost(), mover) || checkPath(getTile(
						start.getX() + 1, start.getY() + 1), dest, spd
						- start.getMovementCost(), mover)))
				|| (((dest.getX() < start.getX()) && (dest.getY() == start.getY())) && (checkPath(
						getTile(start.getX() - 1, start.getY()), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() - 1, start.getY() - 1), dest,
								spd - start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() - 1, start.getY() + 1), dest,
								spd - start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX(), start.getY() - 1), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX(), start.getY() + 1), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() + 1, start.getY() - 1), dest,
								spd - start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() + 1, start.getY() + 1), dest,
								spd - start.getMovementCost(), mover) || checkPath(
						getTile(start.getX() + 1, start.getY()), dest, spd
								- start.getMovementCost(), mover)))
				|| (((dest.getX() < start.getX()) && (dest.getY() > start.getY())) && (checkPath(
						getTile(start.getX() - 1, start.getY() + 1), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() - 1, start.getY()), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX(), start.getY() + 1), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() - 1, start.getY() - 1), dest,
								spd - start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() + 1, start.getY() + 1), dest,
								spd - start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX() + 1, start.getY()), dest, spd
								- start.getMovementCost(), mover)
						|| checkPath(getTile(start.getX(), start.getY() - 1), dest, spd
								- start.getMovementCost(), mover) || checkPath(getTile(
						start.getX() + 1, start.getY() - 1), dest, spd
						- start.getMovementCost(), mover))) || (((dest.getX() == start
				.getX()) && (dest.getY() > start.getY())) && (checkPath(getTile(start
				.getX(), start.getY() + 1), dest, spd - start.getMovementCost(), mover)
				|| checkPath(getTile(start.getX() - 1, start.getY() + 1), dest, spd
						- start.getMovementCost(), mover)
				|| checkPath(getTile(start.getX() + 1, start.getY() + 1), dest, spd
						- start.getMovementCost(), mover)
				|| checkPath(getTile(start.getX() - 1, start.getY()), dest, spd
						- start.getMovementCost(), mover)
				|| checkPath(getTile(start.getX() + 1, start.getY()), dest, spd
						- start.getMovementCost(), mover)
				|| checkPath(getTile(start.getX() - 1, start.getY() - 1), dest, spd
						- start.getMovementCost(), mover)
				|| checkPath(getTile(start.getX() + 1, start.getY() - 1), dest, spd
						- start.getMovementCost(), mover) || checkPath(getTile(start
				.getX(), start.getY() - 1), dest, spd - start.getMovementCost(), mover)))));
	}

	/**
	 * Check preconditions for movement.
	 * 
	 * @param start
	 *            The starting tile
	 * @param dest
	 *            The ending tile
	 * @param speed
	 *            The remaining movement capability
	 * @param mover
	 *            The module moving
	 * @return If preconditions are met
	 */
	private boolean movePreconditions(final Tile start, final Tile dest, final int speed,
			final SimpleModule mover) {
		return (dest.getModuleOnTile() == null && start.getModuleOnTile() != null
				&& start.getModuleOnTile().equals(mover)
				&& (speed > 0 || start.equals(dest)) && start.getX() >= 0
				&& start.getX() < getSizeX() && start.getY() >= 0 && start.getY() < getSizeY());
	}
}
