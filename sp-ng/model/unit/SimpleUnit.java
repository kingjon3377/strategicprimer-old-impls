package model.unit;

/**
 * A simple unit class.
 * 
 * @author Jonathan Lovelace
 */
public class SimpleUnit {
	/**
	 * Which player owns this unit.
	 */
	private int owner;
	/**
	 * Constructor.
	 * @param unitOwner which player owns this unit
	 */
	public SimpleUnit(final int unitOwner) {
		owner = unitOwner;
	}
	/**
	 * @return which player owns this unit
	 */
	public int getOwner() {
		return owner;
	}
	/**
	 * @param unitOwner the unit's new owner
	 */
	public void setOwner(final int unitOwner) {
		owner = unitOwner;
	}
}
