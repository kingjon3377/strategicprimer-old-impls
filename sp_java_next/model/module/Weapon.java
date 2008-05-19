package model.module;

import java.io.Serializable;

/**
 * A module which can attack is a weapon. A module can have more than one
 * weapon, hence the collection below. Any module that can attack, whether
 * itself or by delegation, should implement this interface.
 * 
 * @author Jonathan Lovelace
 */
public interface Weapon extends Serializable {
	/**
	 * Actually make the attack
	 * 
	 * @param target
	 *            The module we're attacking
	 */
	void attack(Module target);

	/**
	 * Attack the specified Module. If I can attack directly (or my weapons are
	 * inferior to my direct-attack capability), do so; otherwise pass this on
	 * to my weapons.
	 * 
	 * TODO: Implement better.
	 * 
	 * @param target
	 *            The module to be attacked.
	 */
	/**
	 * public void attack(final Module target) {
	 * 
	 * int dmg = (attackCallback == null ? Integer.MIN_VALUE : attackCallback
	 * .projectedDamage(target));
	 * 
	 * AttackCallback weapon = attackCallback;
	 * 
	 * for (AttackCallback ac : weapons) {
	 * 
	 * if (ac != null && ac.projectedDamage(target) > dmg) {
	 * 
	 * dmg = ac.projectedDamage(target);
	 * 
	 * weapon = ac; } }
	 * 
	 * if (weapon != null) { weapon.attack(target); }
	 *  // TODO: Make and send a result, since this method is void.
	 *  }
	 */

	/**
	 * How much damage is the attack likely to do?
	 * 
	 * @param target
	 *            The module we might be attacking
	 * @return The projected damage
	 */
	int projectedDamage(Module target);
}