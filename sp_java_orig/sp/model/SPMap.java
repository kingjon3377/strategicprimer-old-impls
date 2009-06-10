package sp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import finalproject.astar.AStar;
import finalproject.astar.Location;
import finalproject.astar.SimpleHeuristicCalculator;
import finalproject.astar.Tile;

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

public final class SPMap implements Serializable {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = -8123099288096187883L;
	/**
	 * How big the map is horizontally.
	 */
	private final int mapSizeX;

	/**
	 * How big the map is vertically.
	 */
	private final int mapSizeY;

	/**
	 * The largest terrain type number
	 */
	public static final int MAX_TERRAIN_TYPE = 17;

	/**
	 * The tiles that make up the map.
	 */
	private final List<List<Tile>> myTiles;

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
		if (tiles.size() != mapSizeX || tiles.get(0).size() != mapSizeY) {
			throw new IllegalArgumentException("Map must be of height "
					+ mapSizeY + " and width " + mapSizeX);
		}
		myTiles = new ArrayList<List<Tile>>(tiles);
	}

	/**
	 * @return The tiles that make up the map
	 */
	public List<List<Tile>> getTiles() {
		return Collections.unmodifiableList(myTiles);
	}

	/**
	 * @param location
	 *            A set of coordinates
	 * @return the tile at those coordinates
	 */
	public Tile getTile(final Location location) {
		if (location.getRow() > mapSizeX || location.getRow() < 0) {
			throw new IllegalArgumentException("x out of bounds");
		} else if (location.getCol() > mapSizeY || location.getCol() < 0) {
			throw new IllegalArgumentException("y out of bounds");
		}
		return myTiles.get(location.getRow()).get(location.getCol());
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
			final int spd, final Module mover) {
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

		return (movePreconditions(start, dest, spd, mover) && new AStar(
				new SimpleHeuristicCalculator(mapSizeX * mapSizeY)).findPath(
				this, start.getLocation(), dest.getLocation()).getCost() <= spd);
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
	private boolean movePreconditions(final Tile start, final Tile dest,
			final int speed, final Module mover) {
		return (dest.getModuleOnTile() == null
				&& start.getModuleOnTile() != null
				&& start.getModuleOnTile().equals(mover)
				&& (speed > 0 || start.equals(dest))
				&& start.getLocation().getRow() >= 0
				&& start.getLocation().getRow() < getSizeX()
				&& start.getLocation().getCol() >= 0 && start.getLocation()
				.getCol() < getSizeY());
	}

	/**
	 * @param location
	 *            A location on the map
	 * @return The tiles next to it
	 */
	public Set<Tile> getTilesAround(final Location location) {
		final Set<Tile> retval = new HashSet<Tile>();
		for (int row = location.getRow() - 1; row < location.getRow() + 2; row++) {
			for (int column = location.getCol() - 1; column < location.getCol() + 2; column++) {
				if (row >= 0
						&& row <= mapSizeX
						&& column >= 0
						&& column <= mapSizeY
						&& (row != location.getRow() || column != location
								.getCol())) {
					retval.add(getTiles().get(row).get(column));
				}
			}
		}
		return retval;
	}

	/**
	 * Create an "array" of the specified size, for use in this class's
	 * constructor
	 * 
	 * @param iDim
	 *            One dimension
	 * @param jDim
	 *            The other dimension
	 * @return A filled "array" of Tiles.
	 */
	public static List<List<Tile>> createArray(final int iDim, final int jDim) {
		return createArray(iDim, jDim, 0);
	}

	/**
	 * Create an "array" of the specified size, for use in this class's
	 * constructor
	 * 
	 * @param iDim
	 *            One dimension
	 * @param jDim
	 *            The other dimension
	 * @param terrain
	 *            The terrain type of all the tiles
	 * @return A filled "array" of Tiles.
	 */
	public static List<List<Tile>> createArray(final int iDim, final int jDim,
			final int terrain) {
		final List<List<Tile>> tiles = new ArrayList<List<Tile>>();
		for (int i = 0; i < iDim; i++) {
			tiles.set(i, new ArrayList<Tile>()); // NOPMD
			for (int j = 0; j < jDim; j++) {
				tiles.get(i).set(j, new Tile(new Location(i, j), terrain)); // NOPMD
			}
		}
		return tiles;
	}
}
