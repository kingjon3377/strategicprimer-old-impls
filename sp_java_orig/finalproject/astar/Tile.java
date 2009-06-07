package finalproject.astar;

import java.text.NumberFormat;

import finalproject.SPMap;
import finalproject.SimpleModule;

/**
 * The Tile class models a tile on the map; it knows its position and what kind
 * of terrain is at that position. It can also know who owns any units on the
 * tile.
 * 
 * @author Jonathan Lovelace
 * @date 27 November 2006
 * @assignment Final Project
 * @course CS108A
 * @semester 06FA
 * 
 * @date Summer 2009 Modified to help pathfinding use A* instead of my (bad)
 *       custom algorithm
 */
public final class Tile {
	/**
	 * The three terrain types in the current icon set that bear noticing. All
	 * the others are plain coastline, with varying orientations and
	 * concentrations of ocean.
	 */
	/**
	 * Forest.
	 */
	public static final int TERRAIN_FOREST = 16;
	/**
	 * Plains
	 */
	public static final int TERRAIN_PLAIN = 12;
	/**
	 * Ocean
	 */
	public static final int TERRAIN_OCEAN = 14;
	/**
	 * The place the tile is in the grid
	 */
	private final Location location;
	/**
	 * The terrain type of the tile
	 */
	private final int myType;
	/**
	 * The module on the tile, if any
	 */
	private SimpleModule moduleOnTile;
	/**
	 * How much resource is on the tile
	 */
	private int myResourceOnTile;

	/**
	 * Constructor, taking coordinates and terrain type.
	 * 
	 * @param _loc
	 *            The tile's location on the map
	 * @param terrainType
	 *            The terrain type of the tile
	 */
	public Tile(final Location _loc, final int terrainType) {
		verifyInput(_loc, terrainType);
		location = _loc;
		myType = terrainType;
	}

	/**
	 * @return the terrain type of the tile
	 */
	public int getTerrainType() {
		return myType;
	}

	/**
	 * @return the owner of the unit on the tile
	 */
	public int getUnitOwner() {
		return moduleOnTile == null ? 0 : moduleOnTile.getOwner();
	}

	/**
	 * Precondition checker, using bounds set as static constants in SPMap.
	 * 
	 * @param loc
	 *            The tile's location on the map
	 * @param type
	 *            The terrain type
	 * @return Whether these are valid
	 * @throws IllegalArgumentException
	 *             on invalid input
	 */
	private static boolean verifyInput(final Location loc, final int type)
			throws IllegalArgumentException {
		if ((loc.getCol() < 0) || (loc.getRow() < 0)) {
			throw new IllegalArgumentException("Tile coordinates out of bounds");
		} else if ((type < 0) || (type > SPMap.MAX_TERRAIN_TYPE)) {
			throw new IllegalArgumentException("Tile terrain type invalid");
		} else {
			return true;
		}
	}

	/**
	 * @return the module on the tile
	 */
	public SimpleModule getModuleOnTile() {
		return moduleOnTile;
	}

	/**
	 * @param modules
	 *            the module on the tile
	 */
	public void setModuleOnTile(final SimpleModule modules) {
		moduleOnTile = modules;
	}

	/**
	 * Because we override equals()
	 * 
	 * @return an integer representation, for hashing
	 */
	@Override
	public int hashCode() {
		return location.hashCode();
	}

	/**
	 * @return whether another object is identical to this one
	 * @param obj
	 *            The object in question
	 */
	@Override
	public boolean equals(final Object obj) {
		return (this == obj ? true : obj instanceof Tile ? location
				.equals(((Tile) obj).location) : false);
	}

	/**
	 * @return A string representation of the tile
	 */
	@Override
	public String toString() {
		return location.toString() + ", type " + myType + ", Defense bonus "
				+ getTerrainDefenseBonus() + ", Movement cost "
				+ getMovementCost() + ", Cover bonus "
				+ NumberFormat.getPercentInstance().format(getCoverBonus())
				+ ", Resource on tile: " + myResourceOnTile;
	}

	/**
	 * This number is added to the defense stat of (i.e., subtracted from any
	 * damage done to) the module on the tile. For most tiles it is zero; for
	 * something that hinders movement it might conceivably be negative, but
	 * that seems unlikely at this point.
	 * 
	 * @return the tile's defense bonus
	 */
	public int getTerrainDefenseBonus() {
		return myType == TERRAIN_FOREST ? 5 : 0;
	}

	/**
	 * This number is the amount of speed required to leave the tile. (Since
	 * equality of starting point and destination is checked before emptiness or
	 * negativity of speed, if this is the second-to-last tile, a unit will be
	 * able to leave it so long as it has a positive speed left.) For most tiles
	 * it will be 1. Under no circumstances should it be zero or negative --
	 * that would cause infinite loops and stack overflows in the path checking
	 * algorithm.
	 * 
	 * @return the movement cost of the tile
	 */
	public int getMovementCost() {
		return (myType == TERRAIN_FOREST ? 2
				: (myType == TERRAIN_OCEAN ? 3 : 1));
	}

	/**
	 * This number, plus 1, is the amount that a module's accuracy stat plus a
	 * random number between 0 and 1 must at least match for its ranged attack
	 * to hit the module on this tile. Should be small.
	 * 
	 * @return the tile's cover bonus.
	 */
	public double getCoverBonus() {
		return myType == TERRAIN_FOREST ? 0.2 : 0.0;
	}

	/**
	 * @return the amount of resource on the tile
	 */
	public int getResourceOnTile() {
		return myResourceOnTile;
	}

	/**
	 * @param resourceOnTile
	 *            the amount of resource on the tile
	 */
	public void setResourceOnTile(final int resourceOnTile) {
		if (resourceOnTile < 0) {
			throw new IllegalArgumentException(
					"Resource on tile cannot be negative");
		}
		myResourceOnTile = resourceOnTile;
	}
	/**
	 * @return the tile's location on the map
	 */
	public Location getLocation() {
		return location;
	}
}
