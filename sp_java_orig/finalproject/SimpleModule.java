package finalproject;

import finalproject.astar.Tile;

// ESCA-JAVA0100:
/**
 * The SimpleModule class is the base class from which all units, buildings, and
 * (in future versions) weapons, etc., descend. It knows all of its statistics
 * that are common to those categories.
 * 
 * @author Jonathan Lovelace
 * @date 27 November 2006
 * @assignment Final Project
 * @course CS108A
 * @semester 06FA
 * 
 * @bug FIXME: Enerjy says this has too many non-final members, and I agree.
 */
public abstract class SimpleModule {
	// ESCA-JAVA0098:
	/**
	 * If mobile, as expected -- how far it can move in a given time. For a
	 * turn-based game, that'll probably be how many tiles it can move in each
	 * turn. If not mobile, rotational speed.
	 */
	protected int mySpeed;

	/**
	 * A conflation of shielding, armor, and damage reduction, since this
	 * project's implementation of combat is lazy, assuming that every melee
	 * attack hits and doing aim checking for ranged attacks only on the
	 * attacker. This number will be subtracted from the damage amount received
	 * from the attacker, with the difference being dealt to the defender.
	 */
	protected int myDefense;

	/**
	 * Maximum HP. (That's "Hit Points" or "Health Points", for the
	 * uninitiated.) Must be positive.
	 */

	protected int myMaxHP;

	/**
	 * Current HP.
	 */
	protected int myHP;

	/**
	 * ID of the player that owns the module.
	 */
	protected int myOwner;

	/**
	 * If this module is free-standing, this is its location. (Encapsulates
	 * [x,y], and provides for "null".)
	 */
	protected Tile myLocation;

	/**
	 * If this module is not free-standing, this is the module that holds it.
	 */
	protected SimpleModule myParent;

	/**
	 * True if the module has moved this turn (it can move at most once per
	 * turn)
	 */
	protected boolean myHasMoved;

	/**
	 * True if the module has attacked (melee or ranged) this turn (it can
	 * attack at most once per turn).
	 */
	protected boolean myHasAttacked;

	/**
	 * @return the module's name
	 */
	public abstract String getName();

	/**
	 * If true, remove me from any collections the next time they are pruned.
	 * @return whether the module should be pruned.
	 */
	public abstract boolean isDelete();
	/**
	 * Remove the module from any collections the next time they are pruned.
	 * @see SimpleModule#isDelete()
	 * getDelete() should return true after this is called. 
	 */
	public abstract void delete();
	
	/**
	 * @return Is the module a mobile one? Determines what its speed stat means.
	 */
	public abstract boolean mobile();
	/**
	 * @return the module's speed
	 */
	public int getSpeed() {
		return mySpeed;
	}
	/**
	 * @return the module's defense stat
	 */
	public int getDefense() {
		return myDefense;
	}
	/**
	 * @return the module's max HP
	 */
	public int getMaxHP() {
		return myMaxHP;
	}
	/**
	 * @return the module's current HP
	 */
	public int getHP() {
		return myHP;
	}
	/**
	 * @return the module's owner
	 */
	public int getOwner() {
		return myOwner;
	}
	/**
	 * @return the module's location
	 */
	public Tile getLocation() {
		return myLocation;
	}
	/**
	 * @return the module's parent
	 */
	public SimpleModule getParent() {
		return myParent;
	}
	/**
	 * @return whether the module has moved this turn
	 */
	public boolean isHasMoved() {
		return myHasMoved;
	}
	/**
	 * @return whether the module has attacked this turn
	 */
	public boolean isHasAttacked() {
		return myHasAttacked;
	}
	/**
	 * @param speed the module's speed
	 */
	public void setSpeed(final int speed) {
		if (speed < 0) {
			throw new IllegalArgumentException("Speed must be nonnegative");
		}
		mySpeed = speed;
	}
	/**
	 * @param defense the module's defense statistic
	 */
	public void setDefense(final int defense) {
		myDefense = defense;
	}
	/**
	 * @param maxHP the module's max HP
	 */
	public void setMaxHP(final int maxHP) {
		if (maxHP < 0) {
			throw new IllegalArgumentException("Max HP must be nonnegative");
		}
		myMaxHP = maxHP;
	}
	/**
	 * @param hitPoints the module's HP
	 */
	public void setHP(final int hitPoints) {
		if (hitPoints > myMaxHP) {
			myHP = myMaxHP;
		} else {
			myHP = hitPoints;
		}
	}
	/**
	 * @param owner the module's owner
	 */
	public void setOwner(final int owner) {
		myOwner = owner;
	}
	/**
	 * @param location the module's location
	 */
	public void setLocation(final Tile location) {
		myLocation = location;
	}
	/**
	 * @param parent the module's parent
	 */
	public void setParent(final SimpleModule parent) {
		myParent = parent;
	}
	/**
	 * @param hasMoved Has the module moved this turn?
	 */
	public void setHasMoved(final boolean hasMoved) {
		myHasMoved = hasMoved;
	}
	/**
	 * @param hasAttacked Has the module attacked this turn?
	 */
	public void setHasAttacked(final boolean hasAttacked) {
		myHasAttacked = hasAttacked;
	}
	// ESCA-JAVA0173:
	/**
	 * This is called during an attack on the module, as the last step,
	 * immediately after damage has been dealt. It takes care of removing the
	 * module if it has zero HP, so overriding implementations should call
	 * super.takeAttack().
	 * 
	 * @param attacker
	 *            The attacking module (most likely a unit)
	 */
	public void takeAttack(@SuppressWarnings("unused") final SimpleModule attacker) {
		if (myHP <= 0) {
			// Remove me from myParent -- since no module can have another
			// module yet, this is unimplementable at the present time.
			// Similarly for removing or moving to my parent all my children.
			// We can, however, remove me from my location.
			if (myLocation != null) {
				myLocation.setModuleOnTile(null);
			}
			delete();
		}
	}
	/**
	 * @return a String representation of the Module.
	 */
	@Override
	public String toString() {
		return (getName() == null ? "SimpleModule" : getName() ) + "(mobile=" + mobile() + ",speed=" + mySpeed
				+ ",defense=" + myDefense + ",maxHP=" + myMaxHP + ",HP=" + myHP
				+ ",owner=" + myOwner + ",location=" + myLocation + ",parent="
				+ myParent + ",hasMoved=" + myHasMoved + ",hasAttacked="
				+ myHasAttacked + ")";
	}
	
}
