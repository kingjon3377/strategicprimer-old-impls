package model.module;

import model.location.Location;
import model.location.NullLocation;
import model.main.UuidManager;

/**
 * A simple unit, for prototype purposes where we don't want the full Unit
 * interface yet.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class SimpleUnit implements Module {
	/**
	 * The module's UUID
	 */
	private final long uuid = UuidManager.UUID_MANAGER.getNewUuid();
	/**
	 * The module's location
	 */
	private Location location = NullLocation.getNullLocation();
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
	 * @bug FIXME: Implement.
	 */
	@Override
	public void takeAttack(final Weapon attacker) {
		// FIXME: Implement
	}
	/**
	 * @param loc the module's new location.
	 */
	public void setLocation(final Location loc) {
		location = loc;
	}
}
