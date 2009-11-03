package model.module.features;

import model.location.Location;
import model.location.NullLocation;
import model.main.UuidManager;
import model.module.Module;
import model.module.kinds.RootModule;
import model.module.kinds.Weapon;
import model.player.IPlayer;

/**
 * A sample landscape feature that isn't a Resource.
 * 
 * @author Jonathan Lovelace
 */
public class Crater implements Module, Feature {
	/**
	 * Constructor.
	 */
	public Crater() {
		super();
		setLocation(NullLocation.NULL_LOC);
	}
	/**
	 * Remove the feature.
	 */
	@Override
	public void die() {
		getLocation().remove(this);
	}
	/**
	 * The crater's location.
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
	 * The moduleID of all non-subclass Craters.
	 */
	private static final int MODULE_ID = 6; 
	/**
	 * @return the moduleID indicating that this is a Crater.
	 */
	@Override
	public int getModuleID() {
		return MODULE_ID;
	}

	/**
	 * @return "Crater"
	 */
	@Override
	public String getName() {
		return "Crater";
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
	 * @param loc the crater's new location
	 */
	@Override
	public final void setLocation(final Location loc) {
		location = loc;
	}
	/**
	 * Attacking a crater does nothing.
	 * @param attacker ignored
	 */
	@Override
	public void takeAttack(final Weapon attacker) {
		// Do nothing
	}
	/**
	 * @return a description of the feature
	 */
	@Override
	public String description() {
		return "A crater is here.";
	}
}
