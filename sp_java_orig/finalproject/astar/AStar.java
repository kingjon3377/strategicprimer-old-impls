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

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import finalproject.SPMap;
/**
 * An implementation of A* 
 * @author tofubeer+astar@gmail.com
 */
public class AStar implements PathFinder {
	/**
	 * The heuristic-calculator we'll use
	 */
	private final HeuristicCalculator calculator;
	/**
	 * Constructor
	 * @param calc the heuristic-calculator we'll use
	 */
	public AStar(final HeuristicCalculator calc) {
		calculator = calc;
	}
	/**
	 * Find the shortest path between two tiles
	 * @param maze the map we're in
	 * @param startLoc The starting location
	 * @param endLoc The ending location
	 * @return The shortest path between the two locations
	 */
	public final Path findPath(final SPMap maze, final Location startLoc,
			final Location endLoc) {
		final PriorityQueue<Path> paths = new PriorityQueue<Path>();
		final Set<Tile> openList = new HashSet<Tile>();
		final Set<Tile> closedList = new HashSet<Tile>();
		final Tile startTile = maze.getTile(startLoc);
		final Tile endTile = maze.getTile(endLoc);
		addToOpenList(null, startTile, endTile, paths, openList);
		Path shortestPath = null;

		while (!openList.isEmpty()) {
			final Path currentPath = paths.remove();
			final Tile currentTile = currentPath.getTile();

			if (currentTile.equals(endTile)) {
				shortestPath = currentPath;
				openList.clear();
			} else {
				openList.remove(currentTile);
				closedList.add(currentTile);
				final Set<Tile> adjacentTiles = maze.getTilesAround(currentPath
						.getTile().getLocation());

				for (final Tile tile : adjacentTiles) {
					if (!(openList.contains(tile))
							&& !(closedList.contains(tile))) {
						addToOpenList(currentPath, tile, endTile, paths,
								openList);
					}
				}
			}
		}

		return shortestPath;
	}
	/**
	 * Add to the list of open tiles.
	 * @param parentPath The parent path (tiles already used)
	 * @param currentTile The current tile
	 * @param endTile The destination tile
	 * @param paths A queue of paths (tried?)
	 * @param openList The set of open tiles
	 */
	private void addToOpenList(final Path parentPath, final Tile currentTile,
			final Tile endTile, final PriorityQueue<Path> paths,
			final Set<Tile> openList) {
		paths.add(new Path(parentPath, currentTile, calculator.calculateHeuristic(currentTile, endTile)));
		openList.add(currentTile);
	}
}
