package sp.model;

import java.text.NumberFormat;
import finalproject.astar.Tile;

/**
 * A simplified Unit (i.e., mobile Module). It uses scalar Attack and
 * RangedAttack stats to implement attacking and ranged attacking. Several of
 * its methods perhaps ought to be moved up to Module later, or made into an
 * interface.
 * 
 * @author Jonathan Lovelace
 * @assignment Final Project
 * @course CS108A
 * @semester FA06
 * 
 */
public class SimpleUnit extends Unit {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3602916091364412048L;

	/**
	 * In the simplified scheme of things ... This amount, less the target's
	 * defense stat, is deal as damage to the target in a melee attack.
	 */
	protected int meleeAttack = 0;

	/**
	 * In the simplified scheme of things ... This amount, less the target's
	 * defense stat, is dealt as damage to the target in a ranged attack that
	 * hits (see accuracy stat).
	 */
	protected int ranged;

	/**
	 * In the simplified scheme of things ... If this, plus a random number
	 * between 0 and 1, is greater than 1, a ranged attack hits.
	 */
	protected double accuracy;

	/**
	 * Constructor, to make warnings go away.
	 */
	public SimpleUnit() {
		super();
		parent = null; // NOPMD by kingjon on 5/18/08 8:20 PM
	}

	/**
	 * Module.attack() is a stub; here is the implementation for the simple
	 * (scalar-based, non-modular) framework.
	 * 
	 * @param target
	 *            The target module.
	 */
	@Override
	public final void attack(final Module target) {
		int dmg = meleeAttack
				- target.getDefense()
				- ((target.getLocation() == null ? 0 : target.getLocation()
						.getTerrainDefenseBonus()));
		if (dmg < 0) {
			dmg = 0;
		}
		target.setHitPoints(target.getHitPoints() - dmg);
		target.takeAttack(this);
	}

	/**
	 * Implements checkAttack(). This checks that the unit has not already
	 * attacked this turn, that the target is not null, and that the target or
	 * its parent is next to the unit.
	 * 
	 * @param target
	 *            The potential target
	 * @return whether it is possible to attack that module
	 */
	@Override
	public final boolean checkAttack(final Module target) {
		return (target != null && (target.getLocation() != null || target
				.getParent() != null))
				&& (hasAttacked ? false
						: target.getParent() == null ? (target.getLocation()
								.getLocation().getRow() < (location.getLocation().getRow() - 1))
								|| (target.getLocation().getLocation().getRow() > (location
										.getLocation().getRow() + 1))
								|| (target.getLocation().getLocation().getCol() < (location
										.getLocation().getCol() - 1))
								|| (target.getLocation().getLocation().getCol() > (location
										.getLocation().getCol() + 1)) ? false : true
								: checkAttack(target.getParent()));
	}

	/**
	 * Implements checkMove(). This checks to see that the unit is
	 * free-standing, that the pointer is non-null, that the unit has not moved
	 * already, and that there is a route to the tile within the unit's maximum
	 * movement.
	 * 
	 * @param tile
	 *            The tile to which the unit would move
	 * @param map
	 *            the map (TODO: why?)
	 * @return whether that tile is a valid destination
	 */
	@Override
	public final boolean checkMove(final Tile tile, final SPMap map) {
		return location == null || tile.getModuleOnTile() != null || hasMoved
				|| !map.checkPath(location, tile, speed, this) ? false : true;
	}

	/**
	 * Implement checkRangedAttack(). At the moment this only checks that the
	 * unit has not already attacked this turn, that the target is not null, and
	 * that the target is connected to the map (either is free-standing or has
	 * ancestors who are).
	 * 
	 * @param target
	 *            The potential target
	 * @return Whether it is possible to make a ranged attack against that
	 *         module
	 */
	@Override
	public final boolean checkRangedAttack(final Module target) {
		if (target == null) {
			throw new IllegalArgumentException("Target must be non-null");
		} else if (target.getLocation() == null && target.getParent() == null) {
			throw new IllegalArgumentException(
					"Target must have either location or parent");
		} else {
			return hasAttacked ? false : target.getParent() == null ? true
					: checkRangedAttack(target.getParent());
		}
	}

	/**
	 * @return the unit's ranged accuracy stat
	 */
	public final double getAccuracy() {
		return accuracy;
	}

	/**
	 * @return the unit's attack stat
	 */
	public final int getMeleeAttack() {
		return meleeAttack;
	}

	/**
	 * @return the unit's ranged attack stat
	 */
	public final int getRanged() {
		return ranged;
	}

	/**
	 * More complicated -- make a ranged attack on a specified module. See the
	 * description of the "ranged" and "accuracy" stats for explanation.
	 * 
	 * @param target
	 *            The target module.
	 */
	@Override
	public final void rangedAttack(final Module target) {
		if ((Math.random() + accuracy) > (1 + ((target.getLocation() == null ? 0
				: target.getLocation().getCoverBonus())))) {
			int dmg = ranged - target.getDefense();
			if (dmg < 0) {
				dmg = 0;
			}
			target.setHitPoints(target.getHitPoints() - dmg);
			target.takeAttack(this);
		}
	}

	/**
	 * @param _accuracy
	 *            the unit's accuracy stat
	 */
	public final void setAccuracy(final double _accuracy) {
		if ((_accuracy < 0) || (_accuracy > 1)) {
			throw new IllegalArgumentException(
					"Accuracy must be between 0 and 1");
		}
		accuracy = _accuracy;
	}

	/**
	 * @param attack
	 *            the unit's attack stat
	 */
	public final void setMeleeAttack(final int attack) {
		meleeAttack = attack;
	}

	/**
	 * @param _ranged
	 *            the unit's ranged attack stat
	 */
	public final void setRanged(final int _ranged) {
		ranged = _ranged;
	}

	/**
	 * toString: Outputs the unit's essential stats, as tersely as possible
	 * while still remaining understandable. (This is expected to be used in a
	 * Label of limited size, or something like that.)
	 * 
	 * @return A terse String representation of the unit's essential stats.
	 */
	@Override
	public final String toString() {
		return (name == null ? "Unit" : name) + ": Speed: " + speed
				+ ", Defense: " + defense + ", HP: " + hitPoints + '/' + maxHP
				+ ", Attack: " + meleeAttack + ", Ranged Attack: " + ranged
				+ ", Accuracy: "
				+ NumberFormat.getPercentInstance().format(accuracy)
				+ ". Owned by player #" + owner;
	}

	/**
	 * A Unit is always mobile.
	 * 
	 * @return true
	 */
	@Override
	public boolean mobile() {
		return true;
	}

	/**
	 * Mark this unit to be deleted the next time collections are pruned.
	 */
	@Override
	public void delete() {
		myDelete = true;
	}

	/**
	 * @return Should this unit be deleted the next time collections are pruned?
	 */
	@Override
	public boolean isDeleted() {
		return myDelete;
	}
	/**
	 * Should the unit be removed from collections when they prune?
	 */
	protected boolean myDelete;

	/**
	 * The unit's name
	 */
	private String myName;

	/**
	 * @return the unit's name
	 */
	@Override
	public String getName() {
		return myName;
	}

	/**
	 * @param _name
	 *            the unit's name
	 */
	@Override
	public void setName(final String _name) {
		myName = _name;
	}
}
