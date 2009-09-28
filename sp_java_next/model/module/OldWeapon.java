package model.module;

import java.io.Serializable;

/**
 * A module which can attack is a weapon. A module can have more than one
 * weapon, hence the collection below. Any module that can attack, whether
 * itself or by delegation, should implement this interface.
 * 
 * @author Jonathan Lovelace
 * @deprecated in favor of Weapon
 */
@Deprecated
public interface OldWeapon extends Serializable, Weapon {
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
	 * Predict the outcome of an attack (TODO: Maybe a more nuanced view of the
	 * outcome than simply damage dealt)
	 * 
	 * @param target
	 *            The module being attacked
	 * @return The damage that would likely be dealt. (Should in most cases,
	 *         barring data intentionally hid from the prediction process, be at
	 *         least order-of-magnitude accurate.)
	 */
	int predictDamage(Module target);
}
