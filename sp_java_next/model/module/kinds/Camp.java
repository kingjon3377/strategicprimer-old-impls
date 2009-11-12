package model.module.kinds;

import model.location.Location;
import model.module.UnableToMoveException;
import model.player.IPlayer;

/**
 * A mobile Fortress.
 * @author Jonathan Lovelace
 */
public class Camp extends Fortress implements MobileModule {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = 740609184492426234L;
	/**
	 * Constructor.
	 * 
	 * @param loc
	 *            The location of the fortress
	 * @param owner
	 *            The owner of the fortress
	 * @param name
	 *            The name of the fortress
	 */
	public Camp(final Location loc, final IPlayer owner, final String name) {
		super(loc, owner, name);
	}
	/**
	 * The moduleID of all instances of this exact class.
	 */
	private static final int MODULE_ID = 7;
	/**
	 * @return the moduleid of this class
	 */
	@Override
	public int getModuleID() {
		return MODULE_ID;
	}
	/**
	 * Check a possible move to a location.
	 * @param loc the location.
	 * @return whether the move is possible
	 */
	@Override
	public boolean checkMove(final Location loc) {
		return loc.checkAdd(this);
	}

	/**
	 * Get the cost of a move to a location.
	 * @param location the location.
	 * @return the cost of the move.
	 */
	@Override
	public double getCost(final Location location) {
		// TODO Auto-generated method stub
		return 1;
	}

	/**
	 * Move to a new location.
	 * 
	 * @param loc
	 *            the new location
	 * @throws UnableToMoveException
	 *             when unable to move
	 */
	@Override
	public void move(final Location loc) throws UnableToMoveException {
		getLocation().remove(this);
		try {
			loc.add(this);
		} catch (IllegalStateException except) {
			getLocation().add(this);
			throw new UnableToMoveException("Location already occupied"); // NOPMD
		}
		setLocation(loc);
	}

}
