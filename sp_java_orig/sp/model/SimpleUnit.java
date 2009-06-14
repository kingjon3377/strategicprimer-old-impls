package sp.model;

import java.text.NumberFormat;

import sp.model.astar.Tile;

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
public class SimpleUnit extends Module implements Unit, Weapon {
	/**
	 * Version UID for serialization.
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
	 * Constructor: All stats should be initialized by the caller. (Maybe I'll
	 * write a more full-featured constructor that takes them as arguments
	 * later, but likely not, since this game is a prototype of sorts for the
	 * game I hope to finish someday, and it will likely have most units in
	 * separate classes, each of which would have to implement a constructor
	 * with the same signature.
	 * 
	 */
	public SimpleUnit() {
		super(null);
		parent = null; // NOPMD by kingjon on 5/18/08 8:20 PM
	}

	/**
	 * Attack a specified module (unit or building, thus far). Caller is
	 * responsible for checking whether this attack is valid. This is the
	 * implementation for the simple (scalar-based, non-modular) framework.
	 * 
	 * @param target
	 *            The target module.
	 */
	@Override
	public final void attack(final IModule target) {
		if (target instanceof Module) {
			int dmg = meleeAttack
					- ((Module) target).getDefense()
					- ((target.getLocation() == null ? 0 : target.getLocation()
							.defenseBonus(target)));
			if (dmg < 0) {
				dmg = 0;
			} // FIXME: This is badly designed
			((Module) target).setHitPoints(((Module) target).getHitPoints()
					- dmg);
			target.takeAttack(this);
		}
	}

	/**
	 * Check to see whether it is possible to attack the specified module. This
	 * checks that the unit has not already attacked this turn, that the target
	 * is not null, and that the target or its parent is next to the unit.
	 * 
	 * @param target
	 *            The potential target
	 * @return whether it is possible to attack that module
	 */
	@Override
	public final boolean checkAttack(final IModule target) {
		return (target != null && (target.getLocation() != null || target
				.getParent() != null))
				&& (hasAttacked ? false
						: target.getParent() == null ? (((Tile) target
								.getLocation()).getLocation().getRow() < (((Tile) location)
								.getLocation().getRow() - 1))
								|| (((Tile) target.getLocation()).getLocation()
										.getRow() > (((Tile) location)
										.getLocation().getRow() + 1))
								|| (((Tile) target.getLocation()).getLocation()
										.getCol() < (((Tile) location)
										.getLocation().getCol() - 1))
								|| (((Tile) target.getLocation()).getLocation()
										.getCol() > (((Tile) location)
										.getLocation().getCol() + 1)) ? false
								: true
								: checkAttack(target.getParent()));
	}

	/**
	 * Checks to see whether it is possible to move to the specified tile. This
	 * checks to see that the unit is free-standing, that the pointer is
	 * non-null, that the unit has not moved already, and that there is a route
	 * to the tile within the unit's maximum movement.
	 * 
	 * @param tile
	 *            The tile to which the unit would move
	 * @param map
	 *            the map (TODO: why?)
	 * @return whether that tile is a valid destination
	 */
	@Override
	public final boolean checkMove(final MoveTarget tile, final SPMap map) {
		if (!(tile instanceof Tile)) {
			throw new IllegalStateException(
					"Movement to non-Tile locations is not supported yet");
		}
		return (!(getLocation() == null) && ((Tile)tile).getModuleOnTile() == null
				&& !hasMoved && map.checkPath(getLocation(), tile, speed, this));
	}

	/**
	 * Check to see whether it is possible to make a ranged attack against the
	 * specified module. At the moment this only checks that the unit has not
	 * already attacked this turn, that the target is not null, and that the
	 * target is connected to the map (either is free-standing or has ancestors
	 * who are).
	 * 
	 * @param target
	 *            The potential target
	 * @return Whether it is possible to make a ranged attack against that
	 *         module
	 */
	@Override
	public final boolean checkRangedAttack(final IModule target) {
		return hasAttacked || target == null ? false
				: target.getLocation() == null ? target.getParent() == null ? false
						: checkRangedAttack(target.getParent())
						: true;
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
	 * description of the "ranged" and "accuracy" stats for explanation. Caller
	 * is responsible for checking whether this is valid.
	 * 
	 * @param target
	 *            The target module.
	 */
	@Override
	public final void rangedAttack(final IModule target) {
		if (!(target instanceof Module)) {
			throw new IllegalStateException(
					"Ranged attacks on non-Modules isn't implemented yet");
		}
		if ((Math.random() + accuracy) > (1 + ((target.getLocation() == null ? 0
				: (target.getLocation().coverBonus(target)))))) {
			int dmg = ranged - ((Module) target).getDefense();
			if (dmg < 0) {
				dmg = 0;
			} // FIXME: This is somewhat badly designed
			((Module) target).setHitPoints(((Module) target).getHitPoints()
					- dmg);
		}
		target.takeAttack(this);
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
		return (getName() == null ? "Unit" : name) + ": Speed: " + speed
				+ ", Defense: " + getDefense() + ", HP: " + hitPoints + '/'
				+ maxHP + ", Attack: " + meleeAttack + ", Ranged Attack: "
				+ ranged + ", Accuracy: "
				+ NumberFormat.getPercentInstance().format(accuracy)
				+ ". Owned by player #" + getOwner();
	}

	/**
	 * Mark this unit to be deleted the next time collections are pruned.
	 */
	@Override
	public void delete() {
		deleted = true;
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

	/**
	 * @param module
	 *            A module to perhaps attack
	 * @return How much damage an attack on it would do
	 * @bug FIXME: This should probably return a class similar to Statistics,
	 *      since not all modules are defined by hit points, attacks can have
	 *      other effects, and an attack on a very important, very durable
	 *      module that did only 5 points of damage but destroyed it should
	 *      probably be preferred to an attack on a very fragile but resilient
	 *      not very important module that did over a hundred but left it
	 *      standing.
	 */
	@Override
	public int predictAttackResult(final IModule module) {
		throw new IllegalStateException("Not implemented yet");
	}

	/**
	 * Move to the specified tile. This is checked to prevent impossible
	 * movement (at present by a descendant-supplied function; TODO: make that a
	 * server-side check). If impossible movement is detected, this fails
	 * silently. Checking beforehand (via the descendant-supplied checkMove())
	 * is recommended. TODO: Check for a target too far and move partway.
	 * 
	 * TODO: limiting a module to one attack and one move per turn is a hack.
	 * Implement it properly via a number-of-actions-per-turn stat, where each
	 * tile move decrements it somewhat and each other action decrements it
	 * somewhat.
	 * 
	 * @param tile
	 *            The destination tile.
	 * @param map
	 *            The map
	 */
	public final void move(final MoveTarget tile, final SPMap map) {
		if (checkMove(tile, map)) {
			getLocation().remove(this);
			setLocation(tile);
			tile.add(this);
			hasMoved = true;
		}
	}

	/**
	 * All units are, by definition, mobile. If it also needs rotational speed,
	 * it should have a module containing that information -- which is part of
	 * why the module system was designed. (Not implemented yet, and likely
	 * won't be in this prototype.)
	 * 
	 * @return that a unit is mobile.
	 */
	@Override
	public final boolean mobile() { // NOPMD
		return true;
	}

	/**
	 * Move to a new location FIXME: We need a way of getting the map without
	 * passing it around, so the function this delegates to can move to this
	 * one.
	 * 
	 * @param newLoc
	 *            The new location
	 */
	public void move(final MoveTarget newLoc) {
		move(newLoc, null);
	}
}
