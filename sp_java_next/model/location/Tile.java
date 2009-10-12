package model.location;

import model.module.Module;
import model.module.kinds.Fortress;
import model.module.kinds.RootModule;

/**
 * In a discrete 2D world, this is the most basic type of location possible.
 * 
 * @author Jonathan Lovelace
 */
public class Tile implements Location {

	/**
	 * Version UID.
	 */
	private static final long serialVersionUID = 206116332980974500L;

	/**
	 * The top-level module on the tile.
	 */
	private Module moduleOnTile;
	/**
	 * The location of this tile on the grid
	 */
	private Point location;
	/**
	 * The event, if any, on the tile
	 */
	private LocationEvent event;
	/**
	 * Constructor, to make warnings go away
	 * 
	 * @param row
	 *            The row the tile is in
	 * @param col
	 *            The column the tile is in
	 */
	public Tile(final int row, final int col) {
		this(new Point(row, col));
	}

	/**
	 * Constructor taking a preconstructed Point
	 * 
	 * @param point
	 *            The point on the grid that this tile goes
	 */
	public Tile(final Point point) {
		location = point;
		moduleOnTile = RootModule.getRootModule();
		terrain = TerrainType.NotVisible;
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
		} else if (moduleOnTile instanceof Fortress) {
			((Fortress) moduleOnTile).add(module);
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
	public boolean contains(final Location loc) {
		return (moduleOnTile instanceof Location && moduleOnTile.equals(loc));
	}

	/**
	 * @see model.location.Location#contains(model.module.Module) Since this is
	 *      the most basic level of location, and Modules are supposed to set
	 *      their location property to the lowest level possible, a Module is
	 *      contained in this tile only if it (or its parent if it isn't capable
	 *      of moving itself -- TODO: make some way of checking "is top-level
	 *      for our purposes" that doesn't exclude Buildings) has this tile as
	 *      its Location.
	 * 
	 * @param module
	 *            the module to search for.
	 * @return whether the tile contains the given module
	 */
	public boolean contains(final Module module) {
		return module != null
				&& (module.getLocation().equals(this)
						|| (moduleOnTile instanceof Fortress ? module
								.getLocation().equals(moduleOnTile) : false) || contains(module
						.getParent()));
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
	 *            is the top-level module on the tile, remove it. Otherwise,
	 *            object.
	 */
	public void remove(final Module module) {
		if (moduleOnTile.equals(module)) {
			moduleOnTile = RootModule.getRootModule();
		} else if (moduleOnTile instanceof Fortress) {
			((Fortress) moduleOnTile).remove(module);
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

	/**
	 * The (most common) terrain type in this tile.
	 */
	private TerrainType terrain;

	/**
	 * @return The (most common) terrain type in this tile.
	 */
	public TerrainType getTerrain() {
		return terrain;
	}

	/**
	 * @param terr
	 *            The new terrain type of the tile
	 */
	public void setTerrain(final TerrainType terr) {
		terrain = terr;
	}

	/**
	 * @param module
	 *            a module
	 * @return whether it's possible to add that module to the tile now.
	 */
	@Override
	public boolean checkAdd(final Module module) {
		return moduleOnTile.equals(RootModule.getRootModule())
				|| (moduleOnTile instanceof Fortress && ((Fortress) moduleOnTile)
						.checkAdd(module));
	}

	/**
	 * @return this tile's location on the grid
	 */
	public Point getLocation() {
		return location;
	}
	/**
	 * @return the event, if any, on the tile
	 */
	public LocationEvent getEvent() {
		return event;
	}
	/**
	 * @param _event the event to go on the tile
	 */
	public void setEvent(final LocationEvent _event) {
		event = _event;
	}
}
