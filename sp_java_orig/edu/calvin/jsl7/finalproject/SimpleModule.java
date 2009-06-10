package edu.calvin.jsl7.finalproject;

import java.io.Serializable;

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
 */
public abstract class SimpleModule implements Serializable {//NOPMD
	/**
	 * UID for serialization.
	 */
	private static final long serialVersionUID = -6594781379900350634L;

	/**
	 * If mobile, as expected -- how far it can move in a given time. For a
	 * turn-based game, that'll probably be how many tiles it can move in each
	 * turn. If not mobile, rotational speed.
	 */
	protected int mySpeed = 0;

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
	 * If this module is not free-standing, this is the module that holds it.
	 */
	protected SimpleModule myParent;

	/**
	 * True if the module has moved this turn (it can move at most once per
	 * turn).
	 */
	protected boolean myHasMoved;

	/**
	 * True if the module has attacked (melee or ranged) this turn (it can
	 * attack at most once per turn).
	 */
	protected boolean myHasAttacked;

	/**
	 * If true, remove me from any collections the next time they are pruned.
	 * Cannot be set directly.
	 */
	protected boolean delete;
	/**
	 * The module's name.
	 */
	protected String myName;

	/**
	 * @return the module's name
	 */
	public abstract String getName();

	/**
	 * @return whether the module should be deleted on the next pass
	 */
	public boolean isDelete() {
		return delete;
	}

	/**
	 * Is the module a mobile one? Determines what its speed stat means.
	 * 
	 * @return whether the module is mobile
	 */
	public abstract boolean isMobile();

	/**
	 * @return the module's speed
	 */
	public int getSpeed() {
		return mySpeed;
	}

	/**
	 * A conflation of shielding, armor, and damage reduction, since this
	 * project's implementation of combat is lazy, assuming that every melee
	 * attack hits and doing aim checking for ranged attacks only on the
	 * attacker. This number will be subtracted from the damage amount received
	 * from the attacker, with the difference being dealt to the defender.
	 * @return the module's defense stat
	 */
	public abstract int getDefense();

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
	 * ID of the player that owns the module.
	 * @return the module's owner
	 */
	public abstract int getOwner();

	/**
	 * If this module is free-standing, this is its location. (Encapsulates
	 * [x,y], and provides for "null".)
	 * @return the tile the module is on
	 */
	public abstract Tile getLocation();

	/**
	 * @return the module's parent module
	 */
	public SimpleModule getParent() {
		return myParent;
	}

	/**
	 * @return whether the module has moved
	 */
	public boolean isHasMoved() {
		return myHasMoved;
	}

	/**
	 * @return whether the module has attacked
	 */
	public boolean isHasAttacked() {
		return myHasAttacked;
	}

	/**
	 * @param speed
	 *            the module's new speed
	 */
	public void setSpeed(final int speed) {
		if (speed < 0) {
			throw new IllegalArgumentException("Speed must be nonnegative");
		}
		mySpeed = speed;
	}

	/**
	 * @param maxHP
	 *            the module's new max HP
	 */
	public void setMaxHP(final int maxHP) {
		if (maxHP < 0) {
			throw new IllegalArgumentException("Max HP must be nonnegative");
		}
		myMaxHP = maxHP;
	}

	/**
	 * @param hitPoints
	 *            the module's new HP
	 */
	public void setHP(final int hitPoints) {
		if (hitPoints > myMaxHP) {
			myHP = myMaxHP;
		} else {
			myHP = hitPoints;
		}
	}

	/**
	 * @param parent
	 *            the module's new parent
	 */
	public void setParent(final SimpleModule parent) {
		myParent = parent;
	}

	/**
	 * @param hasMoved
	 *            whether the module has moved
	 */
	public void setHasMoved(final boolean hasMoved) {
		myHasMoved = hasMoved;
	}

	/**
	 * @param hasAttacked
	 *            whether the module has attacked
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
			if (getLocation() != null) {
				getLocation().setModuleOnTile(null);
			}
			delete = true;
		}
	}

	/**
	 * @return a String representation of the module
	 */
	@Override
	public String toString() {
		return (getName() == null ? "SimpleModule" : getName()) + "(mobile="
				+ isMobile() + ",speed=" + getSpeed() + ",defense=" + getDefense()
				+ ",maxHP=" + getMaxHP() + ",HP=" + getHP() + ",owner=" + getOwner()
				+ ",location=" + getLocation() + ",parent=" + getParent() + ",hasMoved="
				+ isHasMoved() + ",hasAttacked=" + isHasAttacked() + ')';
	}

}
