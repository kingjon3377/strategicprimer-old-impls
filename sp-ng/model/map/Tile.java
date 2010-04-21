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
		type = tileType;
		elevation = elev;
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
