package model.map;

/**
 * Tile types.
 * 
 * @author Jonathan Lovelace
 * 
 */
public enum TileType {
	/**
	 * Ocean or river. This will eventually be determined by elevation and water
	 * table.
	 */
	WATER,
	/**
	 * Desert. TODO: How to distinguish between sandy and rocky desert?
	 */
	DESERT,
	/**
	 * Swamp. TODO: Should this be somehow, like water, be just a result of a
	 * certain combination of elevation and water table?
	 */
	SWAMP,
	/**
	 * Plains. Underbrush when near trees.
	 */
	PLAINS,
	/**
	 * Ice or quasi-permanent snow.
	 */
	ICE,
	/**
	 * A tile that the viewer doesn't know about.
	 */
	UNEXPLORED;
	/**
	 * TODO: What about inside buildings?
	 */
}
