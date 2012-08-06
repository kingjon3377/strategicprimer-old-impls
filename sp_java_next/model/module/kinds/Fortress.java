package model.module.kinds;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
public class Fortress implements Module, Renameable, Location, TransferableModule {
	/**
	 * Constructor.
	 * 
	 * @param loc
	 *            The location of the fortress
	 * @param newOwner
	 *            The owner of the fortress
	 * @param newName
	 *            The name of the fortress
	 */
	public Fortress(final Location loc, final IPlayer newOwner, final String newName) {
		selected = RootModule.ROOT_MODULE;
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
	 * The selected module in the fortress.
	 */
	private Module selected;
	/**
	 * The set of modules in the fortress.
	 */
	private final Set<Module> modules = new HashSet<Module>();
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
		for (Module mod : modules) {
			location.add(mod);
		}
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
	 * Pass any attacks on to the selected module inside, unless the fortress is
	 * empty.
	 * 
	 * @param attacker
	 *            the attacker
	 */
	@Override
	public void takeAttack(final Weapon attacker) {
		if (selected instanceof RootModule || selected == null) {
			die();
		} else {
			selected.takeAttack(attacker);
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
			throw new IllegalStateException("Can't have a fortress inside a fortress");
		} else {
			modules.add(mod);
			if (modules.size() == 1) {
				select(mod);
			}
		}
	}

	/**
	 * @param mod
	 *            a module that might be added.
	 * @return whether adding it now would succeed
	 */
	@Override
	public boolean checkAdd(final Module mod) {
		return !modules.contains(mod) && !(mod instanceof Fortress);
	}

	/**
	 * Can't contain other locations. FIXME: This should be more along the lines
	 * of Tile#contains().
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
	 * FIXME: This should be more along the lines of Tile#contains().
	 * 
	 * @param mod
	 *            a module
	 * @return whether that module is in this fortress
	 */
	@Override
	public boolean contains(final Module mod) {
		return mod != null
				&& (modules.contains(mod) || contains(mod.getParent()));
	}

	/**
	 * Remove a module.
	 * 
	 * @param mod
	 *            the module to remove
	 */
	@Override
	public void remove(final Module mod) {
		if (modules.contains(mod)) {
			modules.remove(mod);
			if (selected.equals(mod)) {
				if (modules.isEmpty()) {
					selected = RootModule.ROOT_MODULE;
				} else {
					selected = modules.iterator().next();
				}
			}
		} else {
			throw new IllegalArgumentException(
					"That module isn't directly in the fortress");
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
	@Override
	public Module getSelected() {
		return selected;
	}

	/**
	 * @return a set of the modules in the fortress
	 */
	@Override
	public Set<Module> getModules() {
		return Collections.unmodifiableSet(modules);
	}

	/**
	 * @param module
	 *            the new selected module
	 */
	@Override
	public void select(final Module module) {
		if (modules.contains(module)) {
			selected = module;
		} else {
			throw new IllegalArgumentException("module not directly in the fortress");
		}
	}
}
