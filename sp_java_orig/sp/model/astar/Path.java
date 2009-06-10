package sp.model.astar;

/*
 *  This file is part of an AStar implementation.
 *  Copyright (C) 2009 tofubeer+astar@gmail.com
 *
 *  This library is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published
 *  by the Free Software Foundation; either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,
 *  USA.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A path: a series of Tiles.
 * 
 * @author tofubeer+astar@gmail.com
 */
public final class Path implements Comparable<Path> {
	/**
	 * The parent path
	 */
	private final Path parent;
	/**
	 * The tile that makes this differ from its parent
	 */
	private final Tile tile;
	/**
	 * The cost of the route
	 */
	private final int cost;
	/**
	 * The distance of the route
	 */
	private final int distance;
	/**
	 * The total heuristic
	 */
	private final int heuristic;

	/**
	 * Constructor
	 * 
	 * @param _parent
	 *            The parent path
	 * @param _tile
	 *            The new tile
	 * @param _heuristic
	 *            The parent's heuristic
	 */
	public Path(final Path _parent, final Tile _tile, final int _heuristic) {
		parent = _parent;
		heuristic = _heuristic;

		distance = parent == null ? 0 : parent.distance + 1;
		cost = parent == null ? heuristic : distance + heuristic;
		tile = _tile;
	}

	/**
	 * @return the total cost of this route
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * @return The tile at this point in the path
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * @return the tiles that make up this path
	 */
	public List<Tile> getTiles() {
		final List<Tile> tiles = new ArrayList<Tile>();
		Path temp = parent;

		tiles.add(tile);

		while (temp != null) {
			tiles.add(temp.tile);
			temp = temp.parent;
		}

		Collections.reverse(tiles);

		return tiles;
	}

	/**
	 * @return an integer representation, for hashing
	 */
	@Override
	public int hashCode() {
		return parent == null ? cost * tile.hashCode() : cost
				* (parent.hashCode() + tile.hashCode());
	}

	/**
	 * @return whether this is identical to another object
	 * @param obj
	 *            the object in question
	 */
	@Override
	public boolean equals(final Object obj) {
		return this == obj ? true
				: obj instanceof Path ? cost == ((Path) obj).cost ? tile
						.equals(((Path) obj).tile) ? parent
						.equals(((Path) obj).parent) : false : false : false;
	}

	/**
	 * @return a String representation of the class.
	 */
	@Override
	public String toString() {
		return toString(new StringBuilder()).toString();
	}

	/**
	 * Creates a string representation of the Path including its ancestors
	 * 
	 * @param builder
	 *            An object to build the string.
	 * @return The StringBuilder holding the string representation
	 */
	private StringBuilder toString(final StringBuilder builder) {
		if (parent != null) {
			parent.toString(builder).append(", ");
		}

		return builder.append("distance: " + distance).append(
				" heuristic: " + heuristic).append(" cost: " + cost).append(
				" -> ").append(tile).append("\r\n");
	}

	/**
	 * Compare this path to another.
	 * 
	 * @param other
	 *            the other path
	 * @return which path has the lower cost
	 */
	@Override
	public int compareTo(final Path other) {
		return cost > other.cost ? 1 : cost < other.cost ? -1 : 0;
	}
}
