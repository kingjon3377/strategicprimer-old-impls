package sp.model.astar;

import java.io.Serializable;

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
/**
 * Representation of a location on the grid.
 * 
 * @author tofubeer+astar@gmail.com
 */
public final class Location implements Serializable{
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = 3706427417887760864L;
	/**
	 * Row index
	 */
	private final int row;
	/**
	 * Column index
	 */
	private final int col;

	/**
	 * Constructor
	 * 
	 * @param _row
	 *            Row index
	 * @param _col
	 *            Column index
	 */
	public Location(final int _row, final int _col) {
		row = _row;
		col = _col;
	}

	/**
	 * @return Row index
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return Column index
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Because we override equals().
	 * 
	 * @return An integer representation of the object, for hashing.
	 */
	@Override
	public int hashCode() {
		return ((row * 1000) + col);
	}

	/**
	 * @return whether another object is identical to this one
	 */
	@Override
	public boolean equals(final Object obj) {
		return this == obj ? true
				: obj instanceof Location ? ((row == ((Location) obj).row) && (col == ((Location) obj).col))
						: false;
	}

	/**
	 * @return A string representation of the location
	 */
	@Override
	public String toString() {
		return "[" + row + ", " + col + "]";
	}
	/**
	 * To get around PMD's "don't instantiate objects inside loops"
	 * @param row The row of the new location
	 * @param col The column of the new location
	 * @return a new Location with those parameters
	 */
	public static Location location(final int row, final int col) {
		return new Location(row,col);
	}
}
