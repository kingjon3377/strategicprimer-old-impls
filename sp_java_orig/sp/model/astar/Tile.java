package sp.model.astar;

import java.io.Serializable;
import java.text.NumberFormat;

import sp.model.IModule;
import sp.model.MoveTarget;
import sp.model.SPMap;

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
public final class Tile implements Serializable, MoveTarget {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = 6270271577021821225L;

	/**
	 * The amount of cover bonus a forest tile gives.
	 */
	private static final double WOODS_COVER_BONUS = 0.2;

	/**
	 * The first of three terrain types in the current icon set that bear
	 * noticing. All the others are plain coastline, with varying orientations
	 * and concentrations of ocean. TODO: Make this an enumerated type (look at
	 * the pictures for accurate descriptions of the rest)
	 */
	public static final int TERRAIN_FOREST = 16;

	/**
	 * The second of three terrain types in the current icon set worth noticing.
	 */
	public static final int TERRAIN_PLAIN = 12;
	/**
	 * The third of three terrain types in the current icon set worth noticing.
	 */
	public static final int TERRAIN_OCEAN = 14;

	/**
	 * Precondition checker, using bounds set as static constants in SPMap.
	 * 
	 * @param loc
	 *            The tile's location on the map
	 * @param type
	 *            The terrain type
	 * @return Whether these are valid
	 */
	private static boolean verifyInput(final Location loc, final int type) {
		return loc.getCol() >= 0 && loc.getRow() >= 0 && type >= 0
				&& type <= SPMap.MAX_TERRAIN_TYPE;
	}

	/**
	 * The place the tile is in the grid
	 */
	private final Location location;
	/**
	 * The terrain type of the tile
	 */
	private final int myType;
	/**
	 * The module (unit or building) on the tile, if any
	 */
	private IModule moduleOnTile;
	/**
	 * How much of the (single) resource is on the tile.
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
		if (!verifyInput(_loc, terrainType)) {
			throw new IllegalArgumentException("Invalid input");
		}
		location = _loc;
		myType = terrainType;
	}

	/**
	 * This number, plus 1, is the amount that a module's accuracy stat plus a
	 * random number between 0 and 1 must at least match for its ranged attack
	 * to hit the module on this tile. Should be small.
	 * 
	 * @param module
	 *            The module benefiting -- ignored. (FIXME: Fix this.)
	 * 
	 * @return the tile's cover bonus.
	 */
	public double coverBonus(final IModule module) {
		return coverBonus();
	}

	/**
	 * This number, plus 1, is the amount that a module's accuracy stat plus a
	 * random number between 0 and 1 must at least match for its ranged attack
	 * to hit the module on this tile. Should be small.
	 * 
	 * @return the tile's cover bonus.
	 */
	public double coverBonus() {
		return (myType == TERRAIN_FOREST ? WOODS_COVER_BONUS : 0.0);
	}

	/**
	 * @return the terrain type of the tile
	 */
	public int getTerrainType() {
		return myType;
	}

	/**
	 * @return which player owns the unit on the tile
	 */
	public int getUnitOwner() {
		return moduleOnTile == null ? 0 : moduleOnTile.getOwner();
	}

	/**
	 * @return the module on the tile
	 */
	public IModule getModuleOnTile() {
		return moduleOnTile;
	}

	/**
	 * @param module
	 *            the module on the tile
	 */
	public void setModuleOnTile(final IModule module) {
		moduleOnTile = module;
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
				+ defenseBonus() + ", Movement cost " + getMovementCost()
				+ ", Cover bonus "
				+ NumberFormat.getPercentInstance().format(coverBonus(null))
				+ ", Resource on tile: " + myResourceOnTile;
	}

	/**
	 * This number is added to the defense stat of (i.e., subtracted from any
	 * damage done to) the module on the tile. For most tiles it is zero; for
	 * something that hinders movement it might conceivably be negative, but
	 * that seems unlikely at this point.
	 * 
	 * @return the terrain's bonus to defense
	 */
	public int defenseBonus() {
		return myType == TERRAIN_FOREST ? 5 : 0;
	}

	/**
	 * This number is added to the defense stat of (i.e., subtracted from any
	 * damage done to) the module on the tile. For most tiles it is zero; for
	 * something that hinders movement it might conceivably be negative, but
	 * that seems unlikely at this point.
	 * 
	 * @param module
	 *            The module whose defense this tile may be boosting
	 * 
	 * @return the terrain's bonus to defense
	 */
	public int defenseBonus(final IModule module) {
		return defenseBonus();
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
	 * @return the movement cost to leave this tile
	 */
	public int getMovementCost() {
		return (myType == TERRAIN_FOREST ? 2
				: (myType == TERRAIN_OCEAN ? 3 : 1));
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

	/**
	 * Add a module to this tile (displacing the current one)
	 * 
	 * @param module
	 *            The module to add
	 */
	@Override
	public void add(final IModule module) {
		this.setModuleOnTile(module);
	}

	/**
	 * Remove a module from the tile.
	 * 
	 * @param module
	 *            the module to remove (MUST be on the tile)
	 */
	@Override
	public void remove(final IModule module) {
		if (module == null || !module.equals(moduleOnTile)) {
			throw new IllegalArgumentException("Module not on this tile");
		}
		this.setModuleOnTile(null);
	}
}
