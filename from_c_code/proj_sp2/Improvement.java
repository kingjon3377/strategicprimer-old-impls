package proj_sp2;
public class Improvement {
	String name;
	int hitPoints;
	int meleeBonus; /* Melee attack bonus  */
	int rangedBonus; /* Ranged attack bonus */
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
	double maintenanceMod; /* Maintenance for the entire unit group is multiplied
			 * by this number. */
	double maintenanceAdd; /* This number (usually negative) is added to the
			 * unit group's maintenance. */
	int requiredCrew; /* Minimum number of men in a unit to use this */
	int armorClass; /* Armor Class -- how difficult it is to hit */
	int macAdd; /* This number is added to the unit's Melee AC */
	int racAdd; /* This number is added to the unit's Ranged AC */
	int flags; /* Affects the performance of the improvement */
	Projectile projectileUsed;
}
