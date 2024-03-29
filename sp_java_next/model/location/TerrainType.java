package model.location;

import java.util.EnumMap;
import java.util.HashMap;

/**
 * Possible tile types.
 * 
 * @author Jonathan Lovelace
 * 
 */
public enum TerrainType {
	/**
	 * Tundra.
	 */
	Tundra,
	/**
	 * Desert.
	 */
	Desert,
	/**
	 * Mountain.
	 */
	Mountain,
	/**
	 * Boreal forest.
	 */
	BorealForest,
	/**
	 * Temperate forest.
	 */
	TemperateForest,
	/**
	 * Ocean.
	 */
	Ocean,
	/**
	 * Plains.
	 */
	Plains,
	/**
	 * Jungle.
	 */
	Jungle,
	/**
	 * Not visible.
	 */
	NotVisible;
	/**
	 * The mapping from descriptive strings to tile types. Used to make
	 * multiple-return-points warnings go away.
	 */
	private static final HashMap<String, TerrainType> TILE_TYPE_MAP = new HashMap<String, TerrainType>(); // NOPMD
	/**
	 * A mapping from tile types to descriptive strings.
	 */
	private static final EnumMap<TerrainType, String> DESCRIPTIONS = new EnumMap<TerrainType, String>(
			TerrainType.class);
	static {
		TILE_TYPE_MAP.put("tundra", TerrainType.Tundra);
		TILE_TYPE_MAP.put("temperate_forest", TerrainType.TemperateForest);
		TILE_TYPE_MAP.put("boreal_forest", TerrainType.BorealForest);
		TILE_TYPE_MAP.put("ocean", TerrainType.Ocean);
		TILE_TYPE_MAP.put("desert", TerrainType.Desert);
		TILE_TYPE_MAP.put("plains", TerrainType.Plains);
		TILE_TYPE_MAP.put("jungle", TerrainType.Jungle);
		TILE_TYPE_MAP.put("mountain", TerrainType.Mountain);
		TILE_TYPE_MAP.put("notvisible", TerrainType.NotVisible);
		DESCRIPTIONS.put(TerrainType.Tundra, "tundra");
		DESCRIPTIONS.put(TerrainType.TemperateForest, "temperate_forest");
		DESCRIPTIONS.put(TerrainType.BorealForest, "boreal_forest");
		DESCRIPTIONS.put(TerrainType.Ocean, "ocean");
		DESCRIPTIONS.put(TerrainType.Desert, "desert");
		DESCRIPTIONS.put(TerrainType.Plains, "plains");
		DESCRIPTIONS.put(TerrainType.Jungle, "jungle");
		DESCRIPTIONS.put(TerrainType.Mountain, "mountain");
		DESCRIPTIONS.put(TerrainType.NotVisible, "notvisible");
	}

	/**
	 * Parse a tile terrain type.
	 * 
	 * @param string
	 *            A string describing the terrain
	 * @return the terrain type
	 */
	public static TerrainType getTileType(final String string) {
		if (TILE_TYPE_MAP.containsKey(string)) {
			return TILE_TYPE_MAP.get(string);
		} // else
		throw new IllegalArgumentException("Unrecognized terrain type string");
	}

	/**
	 * @return the short tag for the terrain type
	 */
	@Override
	public String toString() {
		return DESCRIPTIONS.get(this);
	}
}
