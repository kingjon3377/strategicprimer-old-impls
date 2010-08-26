package common;

import java.io.Serializable;

import model.map.TileType;

/**
 * A representation of a tile for use in the client and on the wire.
 * @author Jonathan Lovelace
 * @todo add elevation?
 *
 */
public class ClientTile implements Serializable {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = -2701847296281830720L;
	/**
	 * Constructor.
	 * @param location where this tile is
	 * @param ttype the tile-type
	 */
	public ClientTile(final SPPoint location, final TileType ttype) {
		loc = location;
		type = ttype;
	}
	/**
	 * The tile's location.
	 */
	private final SPPoint loc;
	/**
	 * @return the tile's location
	 */
	public SPPoint getLocation() {
		return loc;
	}
	/**
	 * The tile's type
	 */
	private final TileType type;
	/**
	 * @return the tile's type
	 */
	public TileType getType() {
		return type;
	}
}
