package finalproject.astar;

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


import finalproject.SPMap;

/**
 * An interface for finding paths
 * @author tofubeer+astar@gmail.com
 */
public interface PathFinder
{
	/**
	 * Find a path.
	 * @param maze The map to search in
	 * @param start The starting location
	 * @param end The ending location
	 * @return The shortest path between the two locations
	 */
    Path findPath(SPMap maze, Location start, Location end);
}
