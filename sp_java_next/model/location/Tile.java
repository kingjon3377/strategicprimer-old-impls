package model.location;

import java.awt.Point;

import model.module.Module;
import model.module.RootModule;

/**
 * In a discrete 2D world, this is the most basic type of location possible.
 * 
 * @author Jonathan Lovelace
 */
public class Tile extends Point implements Location {

	/**
	 * Version UID.
	 */
	private static final long serialVersionUID = 206116332980974500L;

	/**
	 * The top-level module on the tile.
	 */
	private Module moduleOnTile;

	/**
	 * Constructor, to make warnings go away
	 * 
	 * @param row
	 *            The row the tile is in
	 * @param col
	 *            The column the tile is in
	 */
	public Tile(final int row, final int col) {
		super(row, col);
		moduleOnTile = RootModule.getRootModule();
	}

	/**
	 * The interface-defined way of setting the module on the tile. TODO: Should
	 * a tile be able to hold more than one?
	 * 
	 * @param module
	 *            the module to put on the tile
	 */
	public void add(final Module module) {
		if (moduleOnTile.equals(RootModule.getRootModule())) {
			setModuleOnTile(module);
		} else {
			throw new IllegalStateException(
					"There's already a module on this tile");
		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see model.location.Location#contains(model.location.Location)
	 * @return false -- a Tile cannot contain another location.
	 */
	public boolean contains(final Location location) {
		return false;
	}

	/**
	 * @see model.location.Location#contains(model.module.Module)
	 * 
	 * Since this is the most basic level of location, and Modules are supposed
	 * to set their location property to the lowest level possible, a Module is
	 * contained in this tile only if it (or its parent if it isn't capable of
	 * moving itself -- TODO: make some way of checking "is top-level for our
	 * purposes" that doesn't exclude Buildings) has this tile as its Location.
	 * 
	 * @param module
	 *            the module to search for.
	 * @return whether the tile contains the given module
	 */
	public boolean contains(final Module module) {
		return (module.isMobile() ? module.getLocation().equals(this)
				: contains(module.getParent()));
	}

	/**
	 * @return the "top-level" module on the tile
	 */
	public Module getModuleOnTile() {
		return moduleOnTile;
	}

	/**
	 * If
	 * 
	 * @param module
	 *            the module to remove
	 * 
	 * is the top-level module on the tile, remove it. Otherwise, object.
	 */
	public void remove(final Module module) {
		if (moduleOnTile.equals(module)) {
			moduleOnTile = RootModule.getRootModule();
		} else {
			throw new IllegalArgumentException(
					"That isn't the top-level module on the tile!");
		}
	}

	/**
	 * @param _moduleOnTile
	 *            the "top-level" module on the tile
	 */
	public void setModuleOnTile(final Module _moduleOnTile) {
		this.moduleOnTile = _moduleOnTile;
	}
	private TerrainType terrain;
	
}
