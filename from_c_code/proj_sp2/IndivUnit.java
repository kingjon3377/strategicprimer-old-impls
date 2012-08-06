package proj_sp2;
import java.util.List;
class IndivUnit implements Instance {
	String name;
	int movement;
	int vision;
	double cost;
	int crew;
	int meleeAC;
	int rangedAC;
	int flags;
	final int[] maxHP = new int[5]; /** Each man on the crew has his own HP score. */
	final int[] currHP = new int[5]; /** This assumes a crew of no more than 5. */
	/** This, as with most things in this version, needs
	 * reworking. */
//	IndivImprovement firstImprovement;
//	boolean hasImprovement;
	List<IndivImprovement> improvements;
	public boolean takeDamage(final int damage) {
		if (damage >= 0) {
			currHP[Dice.rollDice(5) - 1] -= damage;
			return true;
		} else {
			return false;
		}
	}
	public boolean imprTakeDamage(final int damage) {
		return (damage >= 0 && (improvements.size() == 0 
				|| improvements.get(0).takeDamage(damage))); 
	}
	boolean attack(final IndivUnit target, boolean ranged) {
		if (improvements.size() > 0) {
			IndivImprovement weapon = null;
			for (IndivImprovement impr : improvements) {
				if ((ranged && impr.rangedDamageDice > 0) ||
					(!ranged && impr.meleeDamageDice > 0)) {
					weapon = impr;
					break;
				}
			}
			if (weapon != null) {
				int attackRoll = weapon.attackRoll(!ranged, 0);
				int targetAC = (ranged ? target.rangedAC : target.meleeAC);
				if (attackRoll > targetAC) {
					int damage = weapon.damageRoll(attackRoll > targetAC + 10, !ranged);
					return target.takeDamage(damage / 2) &&
						target.imprTakeDamage(damage / 2);
				} else {
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
