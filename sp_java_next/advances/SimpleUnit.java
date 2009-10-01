package advances;

import model.location.Location;
import model.location.NullLocation;
import model.location.Tile;
import model.main.UuidManager;
import model.module.Module;
import model.module.UnableToMoveException;
import model.module.kinds.MobileModule;
import model.module.kinds.RenameableModule;
import model.module.kinds.Weapon;

/**
 * A simple unit, for prototype purposes where we don't want the full Unit
 * interface yet.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class SimpleUnit implements Module, MobileModule, Weapon, RenameableModule {
	/**
	 * Constructor
	 */
	public SimpleUnit() {
		super();
		setName("SimpleUnit");
	}
	/**
	 * The module's UUID
	 */
	private final long uuid = UuidManager.UUID_MANAGER.getNewUuid();
	/**
	 * The module's location
	 */
	private Location location = NullLocation.getNullLocation();
	/**
	 * The module's name
	 */
	private String name;
	/**
	 * @return the unit's locationn
	 */
	@Override
	public Location getLocation() {
		return location;
	}

	/**
	 * @return the unit's moduleID.
	 */
	@Override
	public int getModuleID() {
		// TODO Auto-generated method stub
		return 2;
	}

	/**
	 * @return the module's "parent," null because this module can't have a
	 *         parent.
	 */
	@Override
	public Module getParent() {
		return null;
	}

	/**
	 * @return the unit's UUID
	 */
	@Override
	public long getUuid() {
		return uuid;
	}

	/**
	 * Take an attack.
	 * 
	 * @bug FIXME: Implement.
	 */
	@Override
	public void takeAttack(final Weapon attacker) {
		die();
	}

	/**
	 * @param loc
	 *            the module's new location.
	 */
	public void setLocation(final Location loc) {
		location = loc;
	}

	/**
	 * Move to a new location, which (FIXME: this shouldn't last) at present
	 * must be a tile
	 * 
	 * @param loc
	 *            the new location
	 * @throws UnableToMoveException
	 *             when the location isn't a tile
	 */
	@Override
	public void move(final Location loc) throws UnableToMoveException {
		if (!(loc instanceof Tile)) {
			throw new UnableToMoveException(
					"Don't know how to move to anything except a Tile");
		}
		location.remove(this);
		try {
			loc.add(this);
		} catch (IllegalStateException except) {
			location.add(this);
			throw new UnableToMoveException("Tile already occupied"); // NOPMD
		}
		setLocation(loc);
	}

	/**
	 * Die.
	 */
	@Override
	public void die() {
		location.remove(this);
	}

	/**
	 * Attack another module
	 * 
	 * @param defender
	 *            the module beng attacked
	 */
	@Override
	public void attack(final Module defender) {
		defender.takeAttack(this);
	}

	/**
	 * Predict the results of an attack on another module
	 * 
	 * @param defender
	 *            the module beng attacked
	 * @return the expected damage dealt
	 */
	@Override
	public int predictDamage(final Module defender) {
		return 15;
	}
	/**
	 * @return the SimpleUnit's name
	 */
	@Override
	public String getName() {
		return name;
	}
	/**
	 * @param _name the ExampleUnit's new name
	 */
	public final void setName(final String _name) {
		name = _name;
	}
	/**
	 * Check whether a move is possible
	 * @param loc the location to pretend to move to
	 * @return whether this unit can move there
	 */
	@Override
	public boolean checkMove(final Location loc) {
		return loc.checkAdd(this);
	}
	/**
	 * FIXME: Implement!
	 * @param loc a location
	 * @return the cost of visiting that location
	 */
	@Override
	public double getCost(final Location loc) {
		// TODO Auto-generated method stub
		return 1;
	}
}
