package model.module.kinds;

import model.location.Location;
import model.main.UuidManager;
import model.module.Module;
import model.player.IPlayer;

/**
 * A module that is also a location. At present just acts as a delegate for the
 * Tile that it's on.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class Fortress implements Module, Renameable, Location,
		TransferableModule {
	/**
	 * Constructor.
	 * @param loc The location of the fortress
	 * @param newOwner The owner of the fortress
	 * @param newName The name of the fortress
	 */
	public Fortress(final Location loc, final IPlayer newOwner, final String newName) {
		module = RootModule.ROOT_MODULE;
		location = loc;
		owner = newOwner;
		name = newName;
	}

	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = 804901337979354609L;
	/**
	 * The location of the fortress.
	 */
	private Location location;
	/**
	 * The module in the fortress.
	 */
	private Module module;
	/**
	 * The name of the fortress.
	 */
	private String name;
	/**
	 * The owner of the fortress.
	 */
	private IPlayer owner;
	/**
	 * UUID.
	 */
	private final long uuid = UuidManager.UUID_MANAGER.getNewUuid();

	/**
	 * Destroy the fortress.
	 */
	@Override
	public void die() {
		location.remove(this);
		location.add(module);
	}

	/**
	 * @return the location of the fortress
	 */
	@Override
	public Location getLocation() {
		return location;
	}
	/**
	 * The moduleID of all instances of this exact class.
	 */
	private static final int MODULE_ID = 5;
	/**
	 * @return the moduleid of this class
	 */
	@Override
	public int getModuleID() {
		return MODULE_ID;
	}

	/**
	 * @return the name of the fortress
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * @return the owner of the fortress
	 */
	@Override
	public IPlayer getOwner() {
		return owner;
	}

	/**
	 * @return the fortress's parent in the module tree
	 */
	@Override
	public Module getParent() {
		return RootModule.ROOT_MODULE;
	}

	/**
	 * @return the UUID of this fortress
	 */
	@Override
	public long getUuid() {
		return uuid;
	}

	/**
	 * @param loc
	 *            the new location of the fortress
	 */
	@Override
	public void setLocation(final Location loc) {
		location = loc;
	}

	/**
	 * Pass any attacks on to the module inside, unless the fortress is empty.
	 * 
	 * @param attacker
	 *            the attacker
	 */
	@Override
	public void takeAttack(final Weapon attacker) {
		if (module instanceof RootModule || module == null) {
			die();
		} else {
			module.takeAttack(attacker);
		}
	}

	/**
	 * @param newName
	 *            the new name of the fortress
	 */
	@Override
	public void setName(final String newName) {
		name = newName;
	}

	/**
	 * Add a module to the fortress.
	 * 
	 * @param mod
	 *            the module to add
	 */
	@Override
	public void add(final Module mod) {
		if (mod instanceof Fortress) {
			throw new IllegalStateException(
					"Can't have a fortress inside a fortress");
		} else if (module.equals(RootModule.ROOT_MODULE)) {
			module = mod;
		} else {
			throw new IllegalStateException(
					"There's already a module in this fortress");
		}
	}

	/**
	 * @param mod
	 *            a module that might be added.
	 * @return whether adding it now would succeed
	 */
	@Override
	public boolean checkAdd(final Module mod) {
		return module.equals(RootModule.ROOT_MODULE)
				&& !(mod instanceof Fortress);
	}

	/**
	 * Can't contain other locations.
	 * 
	 * @param loc
	 *            ignored
	 * @return false
	 */
	@Override
	public boolean contains(final Location loc) {
		return false;
	}

	/**
	 * @param mod
	 *            a module
	 * @return whether that module is in this fortress
	 */
	@Override
	public boolean contains(final Module mod) {
		return mod != null
				&& (mod.getLocation().equals(this) || contains(mod.getParent()));
	}

	/**
	 * Remove a module.
	 * 
	 * @param mod
	 *            the module to remove
	 */
	@Override
	public void remove(final Module mod) {
		if (module.equals(mod)) {
			module = RootModule.ROOT_MODULE;
		} else {
			throw new IllegalArgumentException(
					"That isn't the top-level module in the fortress!");
		}
	}

	/**
	 * @param newOwner
	 *            the new owner of the fortress
	 */
	@Override
	public void setOwner(final IPlayer newOwner) {
		owner = newOwner;
	}
	/**
	 * @return the module in the fortress
	 */
	public Module getModule() {
		return module;
	}
}
