package advances;

import model.location.Location;
import model.location.NullLocation;
import model.main.UuidManager;
import model.module.Module;
import model.module.kinds.Renameable;
import model.module.kinds.RootModule;
import model.module.kinds.TransferableModule;
import model.module.kinds.Weapon;
import model.player.IPlayer;

/**
 * A simple building.
 * 
 * @author Jonathan Lovelace.
 * 
 */
public class SimpleBuilding implements Module, Renameable, TransferableModule {
	/**
	 * Constructor.
	 */
	public SimpleBuilding() {
		location = NullLocation.NULL_LOC;
	}

	/**
	 * The module's location.
	 */
	private Location location;
	/**
	 * The module's name.
	 */
	private String name;
	/**
	 * UUID.
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
	 * The moduleID for all instances of this exact class.
	 */
	private static final int MODULE_ID = 4;
	/**
	 * @return the moduleID
	 */
	@Override
	public int getModuleID() {
		return MODULE_ID;
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
	 * Set the building's location.
	 * @param loc the building's new location
	 */
	@Override
	public void setLocation(final Location loc) {
		this.location = loc;
	}

	/**
	 * Take an attack.
	 * @param attacker the attacking module
	 */
	@Override
	public void takeAttack(final Weapon attacker) {
		die();
	}

	/**
	 * @return the SimpleBuilding's name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @param newName
	 *            the SimpleBuilding's new name
	 */
	public void setName(final String newName) {
		name = newName;
	}
	/**
	 * The building's owner.
	 */
	private IPlayer owner;
	/**
	 * @return the building's owner
	 */
	@Override
	public IPlayer getOwner() {
		return owner;
	}
	/**
	 * @param newOwner the building's new owner
	 */
	@Override
	public void setOwner(final IPlayer newOwner) {
		owner = newOwner;
	}
}
