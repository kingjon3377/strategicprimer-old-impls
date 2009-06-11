package model;
/**
 * A simple unit.
 * @author Jonathan Lovelace
 */
public class SimpleUnit extends SimpleModule implements Unit {
	/**
	 * Move to a new location
	 * @param newLoc The new location
	 */
	public void move(final MoveTarget newLoc) {
		location.remove(this);
		location = newLoc;
		newLoc.add(this);
	}
}
