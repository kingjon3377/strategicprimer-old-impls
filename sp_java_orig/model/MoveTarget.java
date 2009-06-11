package model;

/**
 * A place to which a unit can move.
 * 
 * @author Jonathan Lovelace
 * 
 */
public interface MoveTarget {
	/**
	 * Add a unit to those in me.
	 * 
	 * @param unit
	 *            The unit.
	 */
	void add(Unit unit);

	/**
	 * Remove a unit from those in me.
	 * 
	 * @param unit
	 *            The unit.
	 */
	void remove(Unit unit);
}
