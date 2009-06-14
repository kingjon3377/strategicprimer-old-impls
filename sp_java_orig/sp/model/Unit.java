package sp.model;


/**
 * A Unit is a module that can be top-level and that, when top-level, can move.
 * 
 * @author Jonathan Lovelace
 */

public interface Unit {

	/**
	 * Checks to see whether it is possible to move to the specified location.
	 * Subclasses are responsible for extending this properly (due to the
	 * various different kinds of movement).
	 * 
	 * @param tile
	 *            The location to which the unit would move
	 * @param map
	 *            The map
	 * @return whether that location is a valid destination
	 */
	boolean checkMove(final MoveTarget tile, final SPMap map);

	/**
	 * A unit is defined as something that can move.
	 * 
	 * @param location
	 *            Whither to move.
	 */
	void move(MoveTarget location);

	/**
	 * TODO: Make a time-based system, like in the original.
	 * 
	 * @return whether the module has moved.
	 */
	boolean isHasMoved();

	/**
	 * @param hasMoved
	 *            whether the module has moved this turn
	 */
	void setHasMoved(boolean hasMoved);

}
