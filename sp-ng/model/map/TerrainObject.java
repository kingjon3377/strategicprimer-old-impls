package model.map;

/**
 * An enumerated type for objects that can be on tiles.
 * 
 * @author Jonathan Lovelace
 */
public enum TerrainObject {
	/**
	 * Nothing. This needs to be enumerated, since we can't use -1 anymore and
	 * don't want to use null.
	 */
	NOTHING,
	/**
	 * A tree.
	 */
	TREE;
}
