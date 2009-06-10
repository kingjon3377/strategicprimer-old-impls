package sp.model;

//import java.text.NumberFormat;
import finalproject.astar.Tile;

/**
 * A Unit is a module that can be top-level and that, when top-level, can move.
 * 
 * @author Jonathan Lovelace
 */

public abstract class Unit extends Module {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6361349785576722257L;
	/**
	 * Checks to see whether it is possible to move to the specified tile.
	 * Subclasses are responsible for extending this properly (due to the
	 * various different kinds of movement); in the base class it returns false.
	 * 
	 * @param tile
	 *            The tile to which the unit would move
	 * @param map
	 *            The map
	 * @return whether that tile is a valid destination
	 */
	public abstract boolean checkMove(Tile tile, SPMap map);

	/**
	 * Move to the specified tile. This is checked to prevent impossible
	 * movement (at present by a descendant-supplied function; TODO: make that a
	 * server-side check). If impossible movement is detected, this fails
	 * silently. Checking beforehand (via the descendant-supplied checkMove())
	 * is recommended. TODO: Check for a target too far and move partway.
	 * 
	 * TODO: limiting a module to one attack and one move per turn is a hack.
	 * Implement it properly via a number-of-actions-per-turn stat, where each
	 * tile move decrements it somewhat and each other action decrements it
	 * somewhat.
	 * 
	 * @param tile
	 *            The destination tile.
	 * @param map
	 *            The map
	 */
	public final void move(final Tile tile, final SPMap map) {
		if (checkMove(tile, map)) {
			tile.setModuleOnTile(this);
			getLocation().setModuleOnTile(null);
			setLocation(tile);
			hasMoved = true;
		}
	}

	/**
	 * mobile is a constant set to true for all units, so prevent that being
	 * changed.
	 * 
	 * @param newMobile
	 *            ignored
	 */
	@Override
	public final void setMobile(final boolean newMobile) {
		if (!newMobile) {
			throw new IllegalArgumentException("A unit cannot be nonmobile");
		}
	}
	/**
	 * All units are, by definition, mobile. If it also needs rotational speed,
	 * it should have a module containing that information -- which is part of
	 * why the module system was designed. (Not implemented yet, and likely
	 * won't be in this prototype.)
	 * 
	 * @return that a unit is mobile.
	 */
	@Override
	public final boolean mobile() { // NOPMD
		return true;
	}

}
