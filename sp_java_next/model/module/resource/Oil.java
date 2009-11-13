package model.module.resource;

import model.location.Location;
import model.location.NullLocation;
import model.main.UuidManager;
import model.module.Module;
import model.module.kinds.Resource;
import model.module.kinds.RootModule;
import model.module.kinds.Weapon;
import model.player.IPlayer;

/**
 * Oil that has been tapped from an Oil Well.
 * 
 * @author Jonathan Lovelace
 */
public class Oil implements Resource {
	/**
	 * Constructor.
	 */
	public Oil() {
		super();
		qty = 0;
	}

	/**
	 * Constructor.
	 * 
	 * @param quantity
	 *            how much oil is in this collection of it
	 */
	public Oil(final double quantity) {
		super();
		setQuantity(quantity);
	}

	/**
	 * The quantity of oil in this collection of it.
	 */
	private double qty;

	/**
	 * @return the amount of oil in this collection of it.
	 */
	@Override
	public final double getQuantity() {
		return qty;
	}

	/**
	 * @param quantity
	 *            the quantity of oil that is to be in this collection of it
	 */
	@Override
	public final void setQuantity(final double quantity) {
		if (quantity < 0) {
			throw new IllegalArgumentException("Can't have a negative amount of oil");
		}
		qty = quantity;
	}

	/**
	 * Split this collection of oil into two, reducing this by the given
	 * quantity and returning a new Oil object of the given quantity.
	 * 
	 * @param quantity
	 *            a quantity to reduce this by
	 * @return an Oil object of that quantity.
	 */
	@Override
	public Resource tap(final double quantity) {
		if (quantity > qty) {
			throw new IllegalArgumentException(
					"Cannot more of a resource than it contains");
		} else if (quantity < 0) {
			throw new IllegalArgumentException("Can't tap a negative amount of oil");
		} else {
			qty -= quantity;
			return new Oil(quantity);
		}
	}

	/**
	 * This is meaningless for an Oil.
	 */
	@Override
	public void die() {
		// Do nothing.
	}

	/**
	 * @return the Location equivalent of null.
	 */
	@Override
	public Location getLocation() {
		return NullLocation.NULL_LOC;
	}

	/**
	 * The ID for all non-subclassed Oil objects.
	 */
	private static final int MODULE_ID = 9;

	/**
	 * @return that this is an Oil.
	 */
	@Override
	public int getModuleID() {
		return MODULE_ID;
	}

	/**
	 * @return "Oil"
	 */
	@Override
	public String getName() {
		return "Oil";
	}

	/**
	 * @return null
	 */
	@Override
	public IPlayer getOwner() {
		return null;
	}

	/**
	 * @return the module equivalent of null
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
	 * @param location
	 *            ignored
	 */
	@Override
	public void setLocation(final Location location) {
		throw new IllegalStateException(
				"As currently implemented, an Oil object cannot have a Location.");
	}

	/**
	 * @param attacker
	 *            ignored, as are all attacks
	 */
	@Override
	public void takeAttack(final Weapon attacker) {
		// do nothing
	}
}
