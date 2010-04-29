package model.map;

/**
 * A tile on the map, representing a 10-foot-by-10-foot area.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class Tile {
	/**
	 * The terrain of this tile.
	 */
	private TileType type;
	/**
	 * The elevation of the tile.
	 */
	private int elevation;
	/**
	 * The water table level of the tile. If higher than the elevation, 
	 * this tile is a water tile.
	 */
	private int waterLevel;
	/**
	 * Constructor.
	 */
	public Tile() {
		this(TileType.UNEXPLORED);
	}

	/**
	 * Constructor.
	 * 
	 * @param tileType
	 *            the terrain of this tile
	 */
	public Tile(final TileType tileType) {
		this(tileType, 0);
	}

	/**
	 * Constructor.
	 * 
	 * @param tileType
	 *            the terrain of this tile
	 * @param elev
	 *            the elevation of this tile
	 */
	public Tile(final TileType tileType, final int elev) {
		this(tileType, elev, 0);
	}
	/**
	 * Constructor.
	 * 
	 * @param tileType
	 *            the terrain of this tile
	 * @param elev
	 *            the elevation of this tile
	 * @param waterTable
	 *            the water table level on this tile
	 */
	public Tile(final TileType tileType, final int elev, final int waterTable) {
		type = tileType;
		elevation = elev;
		waterLevel = waterTable;
	}

	/**
	 * @return the terrain of this tile
	 */
	public TileType getType() {
		return type;
	}

	/**
	 * @param tileType
	 *            the new terrain of this tile
	 */
	public void setType(final TileType tileType) {
		type = tileType;
	}

	/**
	 * @return the elevation of this tile
	 */
	public int getElevation() {
		return elevation;
	}

	/**
	 * @param elev
	 *            the elevation of this tile
	 */
	public void setElevation(final int elev) {
		elevation = elev;
	}
	
	/**
	 * @return the water level
	 */
	public int getWaterLevel() {
		return waterLevel;
	}
	
	/**
	 * @param waterTable
	 *            the water table level
	 */
	public void setWaterLevel(final int waterTable) {
		waterLevel = waterTable;
	}
	/**
	 * @param other
	 *            another Tile
	 * @return whether it's identical to this one
	 */
	@Override
	public boolean equals(final Object other) {
		return other instanceof Tile && ((Tile) other).type.equals(type)
				&& ((Tile) other).elevation == elevation;
	}
	/**
	 * Since we override equals().
	 * @return a hash code.
	 */
	@Override
	public int hashCode() {
		return type.hashCode() | elevation;
	}
}
