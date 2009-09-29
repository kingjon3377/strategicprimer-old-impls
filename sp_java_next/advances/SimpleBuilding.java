package advances;

import model.location.Location;
import model.location.NullLocation;
import model.main.UuidManager;
import model.module.Module;
import model.module.RootModule;
import model.module.Weapon;
/**
 * A simple building
 * @author Jonathan Lovelace.
 *
 */
public class SimpleBuilding implements Module {
	/**
	 * Constructor
	 */
	public SimpleBuilding() {
		location = NullLocation.getNullLocation();
	}
	/**
	 * The module's location
	 */
	private Location location;
	/**
	 * UUID
	 */
	private final long uuid = UuidManager.UUID_MANAGER.getNewUuid();
	/**
	 * Die.
	 */
	@Override
	public void die() {
		location.remove(this);
	}
	/**
	 * @return the module's location
	 */
	@Override
	public Location getLocation() {
		return location;
	}
	/**
	 * @return the moduleID
	 */
	@Override
	public int getModuleID() {
		return 4;
	}
	/**
	 * @return the module's parent in the tree
	 */
	@Override
	public Module getParent() {
		return RootModule.getRootModule();
	}
	/**
	 * @return the module's UUID
	 */
	@Override
	public long getUuid() {
		return uuid;
	}
	/**
	 * Set the building's location
	 */
	@Override
	public void setLocation(final Location loc) {
		this.location = loc;
	}
	/**
	 * Take an attack
	 */
	@Override
	public void takeAttack(final Weapon attacker) {
		die();
	}
}
