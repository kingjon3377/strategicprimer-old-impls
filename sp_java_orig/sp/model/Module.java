package sp.model;

import finalproject.astar.Tile;

// ESCA-JAVA0136:
// ESCA-JAVA0100:
/**
 * The Module class is the base class from which all units, buildings,
 * and (in future versions) weapons, etc., descend. It knows all of
 * its statistics that are common to those categories.
 * 
 * @author Jonathan Lovelace
 * @date 27 November 2006
 * @assignment Final Project
 * @course CS108A
 * @semester 06FA
 * 
 */
public abstract class Module implements IModule, Weapon, Statistician {
	/**
	 * 
	 */
	private static final long serialVersionUID = -501346868850104985L;

	/**
	 * Unit Unique Identifier; this should be unique for each kind
	 * of module. At first it should be used in top-level modules
	 * (units and buildings) as an index for what image should be
	 * used to represent the module and to determine what terrain
	 * is passable. The implicit value, -1, is not valid; it
	 * should be overridden in a subclass.
	 */
	protected long uuid = -1;

	/**
	 * If mobile, as expected -- how far it can move in a given time. For a
	 * turn-based game, that'll probably be how many tiles it can move in each
	 * turn. If not mobile, rotational speed.
	 */
	protected int speed;

	/**
	 * A conflation of shielding, armor, and damage reduction, since this
	 * project's implementation of combat is lazy, assuming that every melee
	 * attack hits and doing aim checking for ranged attacks only on the
	 * attacker. This number will be subtracted from the damage amount received
	 * from the attacker, with the difference being dealt to the defender.
	 */
	protected int defense;

	/**
	 * Maximum HP. (That's "Hit Points" or "Health Points", for the
	 * uninitiated.) Must be positive.
	 */

	protected int maxHP;

	/**
	 * Current HP.
	 */
	protected int hitPoints;

	/**
	 * ID of the player that owns the module.
	 */
	protected int owner;

	/**
	 * If this module is free-standing, this is its location. (Encapsulates
	 * [x,y], and provides for "null".)
	 */
	protected Tile location;

	/**
	 * If this module is not free-standing, this is the module that holds it.
	 */
	protected Module parent;

	/**
	 * True if the module has moved this turn (it can move at most once per
	 * turn).
	 */
	protected boolean hasMoved;

	/**
	 * True if the module has attacked (melee or ranged) this turn (it can
	 * attack at most once per turn).
	 */
	protected boolean hasAttacked;

	/**
	 * If true, remove me from any collections the next time they
	 * are pruned.  Cannot be set directly.
	 */
	protected boolean deleted; // NOPMD by kingjon on 5/19/08 12:55 AM

	/**
	 * The module's name.
	 */
	protected String name;

	/**
	 * Constructor: Make sure that the module won't be deleted on
	 * the next round. If a subclass needs more features, it can
	 * extend this.
	 */
	protected Module() {
		deleted = false;
	}

	/**
	 * @return the module's defense stat
	 */
	public int getDefense() {
		return defense;
	}

	/**
	 * @return the module's current HP
	 */
	public int getHitPoints() {
		return hitPoints;
	}

	/**
	 * @return the tile the module is on
	 */
	public Tile getLocation() {
		return location;
	}

	/**
	 * @return the module's maximum HP
	 */
	public int getMaxHP() {
		return maxHP;
	}

	/**
	 * This is not an empty method!
	 * 
	 * @return the module's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return which player owns the module
	 */
	public int getOwner() {
		return owner;
	}

	/**
	 * @return the module's parent in the tree
	 */
	public Module getParent() {
		return parent;
	}

	/**
	 * @return the module's speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @return the module's UUID.
	 */
	public long getUuid() {
		return uuid;
	}

	/**
	 * @return whether to remove this module
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @return whether the module has attacked this turn
	 */
	public boolean isHasAttacked() {
		return hasAttacked;
	}

	/**
	 * TODO: Make a time-based system, like in the original.
	 * 
	 * @return whether the module has moved.
	 */
	public boolean isHasMoved() {
		return hasMoved;
	}

	/**
	 * Is the module a mobile one? Determines what its speed stat means.
	 * @return whether the module can move
	 */
	public abstract boolean mobile();

	/**
	 * @param _defense
	 *            the module's defense stat
	 */
	public void setDefense(final int _defense) {
		defense = _defense;
	}

	/**
	 * @param _hasAttacked
	 *            whether the module has attacked this turn
	 */
	public void setHasAttacked(final boolean _hasAttacked) {
		hasAttacked = _hasAttacked;
	}

	/**
	 * @param _hasMoved
	 *            whether the module has moved this turn
	 */
	public void setHasMoved(final boolean _hasMoved) {
		hasMoved = _hasMoved;
	}

	/**
	 * @param _hp
	 *            the module's current HP
	 */
	public void setHitPoints(final int _hp) {
		if (_hp > maxHP) {
			hitPoints = maxHP;
		} else {
			hitPoints = _hp;
		}
	}

	/**
	 * @param _location
	 *            the module's new location
	 */
	public void setLocation(final Tile _location) {
		location = _location;
	}

	/**
	 * @param _maxHP
	 *            the module's maximum HP
	 */
	public void setMaxHP(final int _maxHP) {
		if (_maxHP < 0) {
			throw new IllegalArgumentException("Max HP must be nonnegative");
		}
		maxHP = _maxHP;
	}

	/**
	 * @param _mobile
	 *            whether the module can move
	 */
	public abstract void setMobile(final boolean _mobile);

	/**
	 * @param _name
	 *            the module's "name"
	 */
	public void setName(final String _name) {
		name = _name;
	}

	/**
	 * @param _owner
	 *            which player owns the module
	 */
	public void setOwner(final int _owner) {
		owner = _owner;
	}

	/**
	 * @param _parent
	 *            the module's parent in the tree
	 */
	public void setParent(final Module _parent) {
		parent = _parent;
	}

	/**
	 * @param _speed
	 *            the module's speed
	 */
	public void setSpeed(final int _speed) {
		if (_speed < 0) {
			throw new IllegalArgumentException("Speed must be nonnegative");
		}
		speed = _speed;
	}

	/**
	 * @param _uuid
	 *            the module's UUID.
	 */
	public void setUuid(final long _uuid) {
		uuid = _uuid;
	}

	/**
	 * This is called during an attack on the module, as the last
	 * step, immediately after damage has been dealt. It takes
	 * care of removing the module if it has zero HP, so
	 * overriding implementations should call super.takeAttack().
	 * 
	 * @param attacker
	 *            The attacking module (most likely a unit)
	 */
	public void takeAttack(final Module attacker) {
		if (attacker.deleted) {
			throw new IllegalStateException(
					"Attacked by a delete-flagged module!");
		}
		if (hitPoints <= 0) {
			// Remove me from parent -- since no module
			// can have another module yet, this is
			// unimplementable at the present time.
			// Similarly for removing or moving to my
			// parent all my children. We can, however,
			// remove me from my location.
			if (location != null) {
				location.setModuleOnTile(null);
			}
			deleted = true;
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 * @return a string representation of the module
	 */
	@Override
	public String toString() {
		return (name == null ? "Module" : name) + "(mobile=" + mobile()
				+ ",speed=" + speed + ",defense=" + defense + ",maxHP=" + maxHP
				+ ",HP=" + hitPoints + ",owner=" + owner + ",location="
				+ location + ",parent=" + parent + ",hasMoved=" + hasMoved
				+ ",hasAttacked=" + hasAttacked + ')';
	}

	/**
	 * Remove the module from any collections the next time they are pruned.
	 * 
	 * @see Module#isDeleted() getDelete() should return true after this is
	 *      called.
	 */
	public abstract void delete();
}
