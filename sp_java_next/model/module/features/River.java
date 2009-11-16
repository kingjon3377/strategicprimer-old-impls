package model.module.features;

import model.location.Location;
import model.location.NullLocation;
import model.main.UuidManager;
import model.module.Module;
import model.module.kinds.Renameable;
import model.module.kinds.RootModule;
import model.module.kinds.Weapon;
import model.player.IPlayer;

/**
 * A river on the map. (One of these per tile, probably. FIXME: That should be
 * improved.)
 * 
 * @author Jonathan Lovelace
 * 
 */
public class River implements Feature, Renameable {
	/**
	 * Constructor.
	 */
	public River() {
		super();
		setLocation(NullLocation.NULL_LOC);
	}
	/**
	 * @return a description of the feature
	 */
	@Override
	public String description() {
		return "A river is here.";
	}

	/**
	 * Remove the river.
	 */
	@Override
	public final void die() {
		getLocation().remove(this);
	}

	/**
	 * The river's location.
	 */
	private Location location;
	/**
	 * @return the feature's location
	 */
	@Override
	public final Location getLocation() {
		return location;
	}
	/**
	 * The moduleID of all non-subclass Rivers.
	 */
	private static final int MODULE_ID = 10;
	/**
	 * @return the moduleID indicating that this is a River.
	 */
	@Override
	public int getModuleID() {
		return MODULE_ID;
	}
	/**
	 * The river's name.
	 */
	private String name = "River";
	/**
	 * @return the river's name
	 */
	@Override
	public final String getName() {
		return name;
	}

	/**
	 * FIXME: There really ought to be a "player" to own landscape features,
	 * etc.
	 * 
	 * @return null
	 */
	@Override
	public IPlayer getOwner() {
		return null;
	}

	/**
	 * @return the root module, since this has no parent in the tree.
	 */
	@Override
	public Module getParent() {
		return RootModule.ROOT_MODULE;
	}

	/**
	 * UUID.
	 */
	private final long uuid = UuidManager.UUID_MANAGER.getNewUuid();
	/**
	 * @return the instance's UUID.
	 */
	@Override
	public final long getUuid() {
		return uuid;
	}

	/**
	 * @param loc the river's new location
	 */
	@Override
	public final void setLocation(final Location loc) {
		location = loc;
	}

	/**
	 * Attacking a river does nothing.
	 * @param attacker ignored
	 */
	@Override
	public void takeAttack(final Weapon attacker) {
		// Do nothing
	}
	/**
	 * @param newName the river's new name
	 */
	@Override
	public final void setName(final String newName) {
		name = newName;
	}
}
