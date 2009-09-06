package proj_sp2;
class IndivObject extends SPObject implements Instance {
	String name;
	int meleeBonusAdd;
	int rangedBonusAdd;
	int meleeBonusDice;
	int rangedBonusDice;
	int meleeBonusDamage;
	int rangedBonusDamage;
	int movementBonus;
	int visionBonus;
	double cost;
	double  maintenanceBonus;
	double maintenanceMod;
	int armorClass_Add;
	int flags;
	int maxHP;
	int currHP;
	public boolean takeDamage(final int damage) {
		if (damage >= 0) {
			currHP -= damage;
			return true;
		} else {
			return false;
		}
	}
}
