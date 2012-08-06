package sp.model;


/**
 * A simple building
 * @author Jonathan Lovelace
 *
 */
public class SimpleBuilding extends Module implements Building {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = 6256309348097285022L;

	/**
	 * Constructor
	 * @param loc The building's location
	 */
	public SimpleBuilding(final MoveTarget loc) {
		super(loc);
	}
	/**
	 * Mark this building as to be removed in the next cycle.
	 */
	@Override
	public void delete() {
		deleted = true;
	}
}
