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

/**
 * An interface for calculating heuristics
 * @author tofubeer+astar@gmail.com
 */
public interface HeuristicCalculator
{
	/**
	 * Calculate the heuristic between two tiles
	 * @param startTile the starting tile
	 * @param endTile the ending tile
	 * @return the heuristic between them
	 */
    int calculateHeuristic(Tile startTile, Tile endTile);
}