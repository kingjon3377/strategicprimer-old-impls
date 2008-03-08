package model.location;

import java.awt.Point;

import model.module.Module;

/**
 * In a discrete 2D world, this is the most basic type of location possible.
 * 
 * @author Jonathan Lovelace
 */
public class Tile extends Point implements Location {

	/**
	 * Constructor, to make warnings go away
	 * @param row The row the tile is in
	 * @param col The column the tile is in
	 */
	public Tile(final int row, final int col) {
		super(row,col);
	}
	/**
	 * Version UID.
	 */
	private static final long serialVersionUID = 206116332980974500L;

	/**
	 * (non-Javadoc)
	 * 
	 * @see model.location.Location#contains(model.location.Location)
	 * @return false -- a Tile cannot contain another location.
	 */
	public boolean contains(@SuppressWarnings("unused")
	final Location location) {
		return false;
	}

	/**
	 * The top-level module on the tile.
	 */
	private Module moduleOnTile;
	
	/**
	 * @see model.location.Location#contains(model.module.Module)
	 * 
	 * Since this is the most basic level of location, and Modules are supposed
	 * to set their location property to the lowest level possible, a Module is
	 * contained in this tile only if it (or its parent if it isn't capable of
	 * moving itself -- TODO: make some way of checking "is top-level for our
	 * purposes" that doesn't exclude Buildings) has this tile as its Location.
	 * 
	 * @return whether the tile contains the given module
	 */
	public boolean contains(final Module module) {
		return (module.isMobile() ? module.getLocation().equals(this) : contains(module.getParent()));
	}

	/**
	 * @return the "top-level" module on the tile
	 */
	public Module getModuleOnTile() {
		return moduleOnTile;
	}

	/**
	 * @param _moduleOnTile the "top-level" module on the tile
	 */
	public void setModuleOnTile(final Module _moduleOnTile) {
		this.moduleOnTile = _moduleOnTile;
	}
}
