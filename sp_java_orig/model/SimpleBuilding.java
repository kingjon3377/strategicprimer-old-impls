package model;
/**
 * A simple building
 * @author Jonathan Lovelace
 *
 */
public class SimpleBuilding extends SimpleModule implements Building {
	/**
	 * Constructor
	 * @param loc The building's location
	 */
	public SimpleBuilding(final MoveTarget loc) {
		super(loc);
	}
}
