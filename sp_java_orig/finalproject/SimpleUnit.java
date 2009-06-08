package finalproject;

import java.text.NumberFormat;

import finalproject.astar.Tile;

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
	// ESCA-JAVA0098:
	/**
	 * Should the unit be removed from collections when they prune?
	 */
	protected boolean myDelete;

	/**
	 * All units are, by definition, mobile. If it also needs rotational speed,
	 * it should have a module containing that information -- which is part of
	 * why the module system was designed. (Not implemented yet, and likely
	 * won't be in this prototype.)
	 * 
	 * @return true
	 */
	@Override
	public final boolean mobile() {
		return true;
	}

	/**
	 * In the simplified scheme of things ... This amount, less the target's
	 * defense stat, is deal as damage to the target in a melee attack.
	 */
	protected int myAttack;

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
	 * @param name
	 *            the unit's name
	 */
	public void setName(final String name) {
		myName = name;
	}

	/**
	 * @return the unit's attack power
	 */
	public int getAttack() {
		return myAttack;
	}

	/**
	 * @return the unit's ranged attack power
	 */
	public int getRanged() {
		return myRanged;
	}

	/**
	 * @return the unit's accuracy
	 */
	public double getAccuracy() {
		return myAccuracy;
	}

	/**
	 * @param attack
	 *            the unit's attack power
	 */
	public void setAttack(final int attack) {
		myAttack = attack;
	}

	/**
	 * @param ranged
	 *            the unit's ranged attack power
	 */
	public void setRanged(final int ranged) {
		myRanged = ranged;
	}

	/**
	 * @param accuracy
	 *            the unit's accuracy
	 */
	public void setAccuracy(final double accuracy) {
		if ((accuracy < 0.0) || (accuracy > 1.0)) {
			throw new IllegalArgumentException(
					"Accuracy must be between 0 and 1");
		}
		myAccuracy = accuracy;
	}

	/**
	 * Move to the specified tile. While this is checked to prevent impossible
	 * movement, if impossible movement is detected, this fails silently;
	 * checking beforehand (via checkMove() function) is recommended).
	 * 
	 * @param tile
	 *            The destination tile.
	 * @param map
	 *            The map the tile is on
	 */
	public void move(final Tile tile, final SPMap map) {
		if (checkMove(tile, map)) {
			tile.setModuleOnTile(this);
			myLocation.setModuleOnTile(null);
			myLocation = tile;
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
	public void attack(final SimpleModule target) {
		int dmg = myAttack
				- target.getDefense()
				- ((target.getLocation() == null ? 0 : target.getLocation()
						.getTerrainDefenseBonus()));
		if (dmg < 0) {
			dmg = 0;
		}
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
	public void rangedAttack(final SimpleModule target) {
		if ((Math.random() + myAccuracy) > (1 + ((target.getLocation() == null ? 0
				: target.getLocation().getCoverBonus())))) {
			int dmg = myRanged - target.getDefense();
			if (dmg < 0) {
				dmg = 0;
			}
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
	 *            The map the tile is in
	 * @return whether that tile is a valid destination
	 */
	public boolean checkMove(final Tile tile, final SPMap map) {
		return myLocation != null && tile.getModuleOnTile() == null
				&& !myHasMoved
				&& map.checkPath(myLocation, tile, mySpeed, this);
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
	public boolean checkAttack(final SimpleModule target) {
		return (!myHasAttacked && target != null && target.getLocation() == null)
				&& (((target.getParent() != null && checkAttack(target
						.getParent()))) || ((target.getLocation().getLocation()
						.getRow() >= (myLocation.getLocation().getRow() - 1))
						&& (target.getLocation().getLocation().getRow() <= (myLocation
								.getLocation().getRow() + 1))
						&& (target.getLocation().getLocation().getCol() >= (myLocation
								.getLocation().getCol() - 1)) && (target
						.getLocation().getLocation().getCol() <= (myLocation
						.getLocation().getCol() + 1))));
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
	public boolean checkRangedAttack(final SimpleModule target) {
		return !myHasAttacked
				&& target != null
				&& (target.getLocation() != null || ((target.getParent() != null) && checkRangedAttack(target
						.getParent())));
	}

	/**
	 * toString: Outputs the unit's essential stats, as tersely as possible
	 * while still remaining understandable. (This is expected to be used in a
	 * Label of limited size, or something like that.)
	 * 
	 * @return A terse String representation of the unit's essential stats.
	 */
	@Override
	public String toString() {
		return (getName() == null ? "Unit" : getName()) + ": Speed: " + mySpeed
				+ ", Defense: " + myDefense + ", HP: " + myHP + "/" + myMaxHP
				+ ", Attack: " + myAttack + ", Ranged Attack: " + myRanged
				+ ", Accuracy: "
				+ NumberFormat.getPercentInstance().format(myAccuracy)
				+ ". Owned by player #" + myOwner;
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
		myDelete = false;
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
	public boolean isDelete() {
		return myDelete;
	}

}
