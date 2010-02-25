package model.location;

import java.util.HashSet;
import java.util.Set;

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
	 * The set of modules on the tile.
	 */
	private final Set<Module> modules = new HashSet<Module>();
	/**
	 * The location of this tile on the grid.
	 */
	private Point location;
	/**
	 * The event, if any, on the tile.
	 */
	private LocationEvent event;

	/**
	 * Constructor, to make warnings go away.
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
	 * Constructor taking a preconstructed Point.
	 * 
	 * @param point
	 *            The point on the grid that this tile goes
	 */
	public Tile(final Point point) {
		location = point;
		moduleOnTile = RootModule.ROOT_MODULE;
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
		if (moduleOnTile.equals(RootModule.ROOT_MODULE)) {
			modules.add(module);
			select(module);
		} else if (moduleOnTile instanceof Fortress) {
			((Fortress) moduleOnTile).add(module);
		} else {
			modules.add(module);
		}
	}

	/**
	 * @param loc
	 *            a location
	 * @return whether the module on the tile is a location and is the given
	 *         location.
	 */
	public boolean contains(final Location loc) {
		boolean retval = loc.equals(this);
		for (Module mod : modules) {
			retval |= (mod instanceof Location && ((Location) mod).contains(loc));
		}
		return retval;
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
		if (module == null || module.equals(RootModule.ROOT_MODULE)) {
			return false; // NOPMD
		} else if (modules.contains(module)) {
			return true; // NOPMD
		} else {
			for (Module mod : modules) {
				if (mod instanceof Location && ((Location) mod).contains(module)) {
					return true; // NOPMD
				}
			}
			return contains(module.getParent());
		}
	}

	/**
	 * @return the "top-level" module on the tile
	 */
	public Module getSelected() {
		return moduleOnTile;
	}

	/**
	 * If the module to remove is the top-level module on the tile, remove it.
	 * Otherwise, object.
	 * 
	 * @param module
	 *            the module to remove
	 * 
	 */
	public void remove(final Module module) {
		if (moduleOnTile.equals(module)) {
			modules.remove(module);
			if (modules.isEmpty()) {
				moduleOnTile = RootModule.ROOT_MODULE;
			} else {
				moduleOnTile = modules.iterator().next();
			}
		} else if (modules.contains(module)) {
			modules.remove(module);
		} else {
			for (Module mod : modules) {
				if (mod instanceof Location && ((Location) mod).contains(module)) {
					((Location) mod).remove(module);
					return;
				}
			}
			throw new IllegalArgumentException("That isn't on the tile.");
		}
	}

	/**
	 * @param selectee
	 *            the "top-level" module on the tile
	 */
	public void select(final Module selectee) {
		if (modules.contains(selectee)) {
			moduleOnTile = selectee;
		} else if (contains(selectee)) {
			for (Module mod : modules) {
				if (mod instanceof Location && ((Location) mod).contains(selectee)) {
					moduleOnTile = mod;
					((Location) mod).select(selectee);
				}
			}
		} else {
//			System.out.println("Breakpoint here");
			throw new IllegalArgumentException(
					"Can only select a module that is on the tile.");
		}
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
	 * @return whether it's possible to add that module to the tile now. FIXME:
	 *         This now basically returns "true"; it should mean something more
	 *         substantial.
	 */
	@Override
	public boolean checkAdd(final Module module) {
		return !modules.contains(module);
		// return moduleOnTile.equals(RootModule.ROOT_MODULE)
		// || (moduleOnTile instanceof Fortress && ((Fortress) moduleOnTile)
		// .checkAdd(module));
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
	 * @param newEvent
	 *            the event to go on the tile
	 */
	public void setEvent(final LocationEvent newEvent) {
		event = newEvent;
	}

	/**
	 * @return an unmodifiable wrapper around the set of modules on the tile
	 */
	public Set<Module> getModules() {
		final Set<Module> temp = new HashSet<Module>(modules);
		for (Module mod : modules) {
			if (mod instanceof Location) {
				temp.addAll(((Location) mod).getModules());
			}
		}
		return temp;
	}
}
