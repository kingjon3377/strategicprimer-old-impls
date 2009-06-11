package model;

/**
 * A module (unit, building, piece of equipment, etc.) in the game.
 * 
 * @author Jonathan Lovelace
 */
public abstract class Module {
	/**
	 * This is mostly to stop the "no abstract methods" warnings.
	 * 
	 * @return the module's name
	 */
	public abstract String getName();

	/**
	 * @return the module's location
	 */
	public MoveTarget getLocation() {
		return location;
	}

	/**
	 * The module's location
	 */
	protected MoveTarget location;

	/**
	 * Constructor
	 * 
	 * @param _location
	 *            The module's initial location
	 */
	protected Module(final MoveTarget _location) {
		location = _location;
	}
	/**
	 * FIXME: Uncomment once we have an idea of what to do for a default
	 * implementation.
	 */
//	/**
//	 * Take an attack from another module
//	 * @param m The attacker
//	 */
//	public abstract void takeAttack(Module m);
}
