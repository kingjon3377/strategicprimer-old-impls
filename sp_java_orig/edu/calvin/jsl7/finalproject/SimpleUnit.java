package edu.calvin.jsl7.finalproject;

import java.text.NumberFormat;

// ESCA-JAVA0136:
/**
 * A simplified Unit (i.e., mobile Module). It uses scalar Attack and
 * RangedAttack stats to implement attacking and ranged attacking. Several of
 * its methods perhaps ought to be moved up to SimpleModule later, or made into
 * an interface.
 * 
 * @author Jonathan Lovelace
 * @assignment Final Project
 * @course CS108A
 * @semester FA06
 * 
 */
public class SimpleUnit extends SimpleModule {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5615054454123743248L;

	/**
	 * All units are, by definition, mobile. If it also needs rotational speed,
	 * it should have a module containing that information -- which is part of
	 * why the module system was designed. (Not implemented yet, and likely
	 * won't be in this prototype.)
	 * 
	 * @return that a unit is mobile.
	 */
	@Override
	public final boolean isMobile() {
		return true;
	}

	/**
	 * In the simplified scheme of things ... This amount, less the target's
	 * defense stat, is deal as damage to the target in a melee attack.
	 */
	protected int myAttack = 0;

	/**
	 * In the simplified scheme of things ... This amount, less the target's
	 * defense stat, is dealt as damage to the target in a ranged attack that
	 * hits (see accuracy stat).
	 */
	protected int myRanged;

	/**
	 * In the simplified scheme of things ... If this, plus a random number
	 * between 0 and 1, is greater than 1, a ranged attack hits.
	 */
	protected double myAccuracy;

	/**
	 * @return the module's attack stat
	 */
	public final int getAttack() {
		return myAttack;
	}

	/**
	 * @return the module's ranged attack stat
	 */
	public final int getRanged() {
		return myRanged;
	}

	/**
	 * @return the module's accuracy stat
	 */
	public final double getAccuracy() {
		return myAccuracy;
	}

	/**
	 * @param attack
	 *            the module's attack stat
	 */
	public final void setAttack(final int attack) {
		myAttack = attack;
	}

	/**
	 * @param ranged
	 *            the module's ranged attack stat
	 */
	public final void setRanged(final int ranged) {
		myRanged = ranged;
	}

	/**
	 * @param accuracy
	 *            the module's accuracy stat
	 */
	public final void setAccuracy(final double accuracy) {
		if ((accuracy < 0) || (accuracy > 1)) {
			throw new IllegalArgumentException(
					"Accuracy must be between 0 and 1");
		}
		myAccuracy = accuracy;
	}

	/**
	 * Basically, damage reduction--the defense stat.
	 */
	protected int defense;

	/**
	 * @return the defense stat
	 */
	@Override
	public final int getDefense() {
		return defense;
	}

	/**
	 * @param def
	 *            the new defense stat
	 */
	public final void setDefense(final int def) {
		defense = def;
	}

	/**
	 * The unit's owner.
	 */
	protected int owner;

	/**
	 * @return the unit's owner
	 */
	@Override
	public final int getOwner() {
		return owner;
	}

	/**
	 * @param _owner
	 *            the new owner
	 */
	public final void setOwner(final int _owner) {
		owner = _owner;
	}

	/**
	 * The unit's name.
	 */
	protected String name;

	/**
	 * @return the unit's name
	 */
	@Override
	public final String getName() {
		return name;
	}

	/**
	 * @param _name
	 *            the unit's new name
	 */
	public final void setName(final String _name) {
		name = _name;
	}

	/**
	 * The tile the unit is on.
	 */
	protected Tile location;

	/**
	 * @return the tile the unit is on
	 */
	@Override
	public final Tile getLocation() {
		return location;
	}

	/**
	 * @param loc
	 *            the new tile the unit is now on
	 */
	public final void setLocation(final Tile loc) {
		location = loc;
	}

	/**
	 * Move to the specified tile. While this is checked to prevent impossible
	 * movement, if impossible movement is detected, this fails silently;
	 * checking beforehand (via checkMove() function) is recommended).
	 * 
	 * @param tile
	 *            The destination tile.
	 * @param map
	 *            the map
	 */
	public final void move(final Tile tile, final SPMap map) {
		if (checkMove(tile, map)) {
			tile.setModuleOnTile(this);
			getLocation().setModuleOnTile(null);
			setLocation(tile);
			myHasMoved = true;
		} 
	}

	/**
	 * Attack a specified module (unit or building, thus far). Caller is
	 * responsible for checking whether this attack is valid.
	 * 
	 * @param target
	 *            The target module.
	 */
	public final void attack(final SimpleModule target) {
		int dmg = myAttack
				- target.getDefense()
				- ((target.getLocation() == null ? 0 : target.getLocation()
						.getTerrainDefenseBonus()));
		if (dmg < 0) {
			dmg = 0;
		} // FIXME: This is badly designed
		target.setHP(target.getHP() - dmg);
		target.takeAttack(this);
	}

	/**
	 * More complicated -- make a ranged attack on a specified module. See the
	 * description of the "ranged" and "accuracy" stats for explanation. Caller
	 * is responsible for checking whether this is valid.
	 * 
	 * @param target
	 *            The target module.
	 */
	public final void rangedAttack(final SimpleModule target) {
		if ((Math.random() + myAccuracy) > (1 + ((target.getLocation() == null ? 0 : target
				.getLocation().getCoverBonus())))) {
			int dmg = myRanged - target.getDefense();
			if (dmg < 0) {
				dmg = 0;
			} // FIXME: This is somewhat badly designed
			target.setHP(target.getHP() - dmg);
		}
		target.takeAttack(this);
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
	 *            the map
	 * @return whether that tile is a valid destination
	 */
	public final boolean checkMove(final Tile tile, final SPMap map) {
		return (!(getLocation() == null) && tile.getModuleOnTile() == null
				&& !myHasMoved && map.checkPath(getLocation(), tile, mySpeed,
				this));
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
	public final boolean checkAttack(final SimpleModule target) {
		return myHasAttacked ? false
				: target == null ? false
						: target.getLocation() == null ? target.getParent() == null ? false
								: checkAttack(target.getParent())
				: (target.getLocation().getX() < (getLocation().getX() - 1))
						|| (target.getLocation().getX() > (getLocation().getX() + 1))
						|| (target.getLocation().getY() < (getLocation().getY() - 1))
						|| (target.getLocation().getY() > (getLocation().getY() + 1)) ? false : true;
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
	public final boolean checkRangedAttack(final SimpleModule target) {
		return myHasAttacked || target == null ? false
				: target.getLocation() == null ? target.getParent() == null ? false
						: checkRangedAttack(target.getParent())
						: true;
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
		return (getName() == null ? "Unit" : myName) + ": Speed: " + mySpeed
				+ ", Defense: " + getDefense() + ", HP: " + myHP + '/'
				+ myMaxHP + ", Attack: " + myAttack + ", Ranged Attack: "
				+ myRanged + ", Accuracy: "
				+ NumberFormat.getPercentInstance().format(myAccuracy)
				+ ". Owned by player #" + getOwner();
	}

	/**
	 * Constructor: All that we need to do is make sure that the unit won't be
	 * deleted on the next round. All other stats should be initialized by the
	 * caller. (Maybe I'll write a more full-featured constructor that takes
	 * them as arguments later, but likely not, since this game is a prototype
	 * of sorts for the game I hope to finish someday, and it will likely have
	 * most units in separate classes, each of which would have to implement a
	 * constructor with the same signature.
	 * 
	 */
	public SimpleUnit() {
		super();
		delete = false;
	}

}
