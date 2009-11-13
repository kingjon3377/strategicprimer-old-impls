package model.module.features;

import model.location.Location;
import model.location.NullLocation;
import model.main.UuidManager;
import model.module.Module;
import model.module.kinds.Resource;
import model.module.kinds.RootModule;
import model.module.kinds.Weapon;
import model.module.resource.Oil;
import model.player.IPlayer;

/**
 * An oil well, an example Feature that is a tap-able Resource.
 * 
 * @author Jonathan Lovelace
 */
public class OilWell implements Feature, Resource {
	/**
	 * Constructor.
	 */
	public OilWell() {
		setLocation(NullLocation.NULL_LOC);
	}
	/**
	 * @return A description of the feature.
	 */
	@Override
	public String description() {
		return "An oil well";
	}

	/**
	 * Remove the feature.
	 */
	@Override
	public void die() {
		getLocation().remove(this);
	}

	/**
	 * The feature's location.
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
	private static final int MODULE_ID = 8;

	/**
	 * @return the moduleID indicating that this is an OilWell.
	 */
	@Override
	public int getModuleID() {
		return MODULE_ID;
	}

	/**
	 * @return "OilWell"
	 */
	@Override
	public String getName() {
		return "OilWell";
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
	 * @param loc
	 *            the crater's new location
	 */
	@Override
	public final void setLocation(final Location loc) {
		location = loc;
	}

	/**
	 * Attacking a crater does nothing.
	 * 
	 * @param attacker
	 *            ignored
	 */
	@Override
	public void takeAttack(final Weapon attacker) {
		// Do nothing
	}

	/**
	 * How much of the resource there is.
	 */
	private double quantity = -1.0;

	/**
	 * @return how much of the resource there is
	 */
	@Override
	public double getQuantity() {
		return quantity;
	}

	/**
	 * @param newQuantity
	 *            how much of this resource there is to be in this collection of
	 *            it.
	 */
	@Override
	public void setQuantity(final double newQuantity) {
		quantity = newQuantity;
	}

	/**
	 * Tap the well. FIXME: Implement by creating a new class to represent what gets tapped.
	 * @param qty the quantity to tap
	 * @return that much as a Resource
	 */
	@Override
	public Resource tap(final double qty) {
		if (qty > quantity) {
			throw new IllegalArgumentException("Can't tap an oil well for more than it contains");
		} else if (qty < 0) {
			throw new IllegalArgumentException("Can't tap a negative amount of oil");
		}
		quantity -= qty;
		return new Oil(qty);
	}

}
