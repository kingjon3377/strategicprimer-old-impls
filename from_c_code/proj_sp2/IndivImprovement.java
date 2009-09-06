package proj_sp2;
public class IndivImprovement extends Improvement implements Instance {
	String name;
	int hitPoints;
	int meleeBonus;
	int rangedBonus;
	int damageDie; /* Die used for damage */
	int meleeDamageDice; /* Number of damage dice used in melee */
	int rangedDamageDice; /* Number of damage dice used in ranged combat */
	int meleeDamageAdd; /* Number added to each melee damage die */
	int rangedDamageAdd; /* Number added to each ranged damage die */
	double movementMod; /* Number the unit's Movement score is multiplied by */
	int movementAdd; /* Number added to the unit's Movement score */
	double visionMod; /* Number the unit's Vision score is multiplied by */
	int visionAdd; /* Number added to the unit's Vision score */
	double cost; /* Cost of the improvement */
	double maintenanceCost; /* Base maintenance is this number / 4 */
	double maintenanceMod; /* Maintenance for the entire unit group 
				* is multiplied by this number. */
	double maintenanceAdd; /* This number (usually negative) is added to the
			 	* unit group's maintenance. */
	int minimumCrew; /* Minimum number of men in a unit to use this */
	/** We should have a maximum crew too -- in fact, the idea of
	 * "crew" (like most things from this version) needs
	 * substantial revision. */
	int armorClass; /* Armor Class -- how difficult it is to hit */
	int meleeAC_Add; /* This number is added to the unit's Melee AC */
	int rangedAC_Add; /* This number is added to the unit's Ranged AC */
	int flags; /* Affects the performance of the improvement */
	Projectile projectileUsed;
	int currentHP;
	IndivImprovement next; 
	boolean hasNext;
	/** Those last two should probably be removed in light of
	 * built-in ADTs. */
	public boolean takeDamage(final int damage) {
		if (damage >= 0) {
			currentHP -= damage;
			return true;
		} else {
			return false;
		}
	}
	int attackRoll(boolean melee, int unitBonus) {
		return Dice.rollDice(20) + (melee ? meleeBonus :
				rangedBonus) + unitBonus;
	}
	int attackRoll(boolean melee) {
		return attackRoll(melee, 0);
	}
	int damageRoll(boolean critical, boolean melee) {
		int dice = (melee ? meleeDamageDice : rangedDamageDice) 
				+ (critical ? (melee ?  meleeDamageDice : 
					rangedDamageDice) : 0);
		int accumulator = 0;
		for (int i = 0; i <= dice; i++) {
			accumulator += Dice.rollDice(damageDie);
			accumulator += (melee ? meleeDamageAdd : 
					rangedDamageAdd);
		}
		return accumulator;
	}
}
