package proj_sp2;
class IndivProjectile extends Projectile implements Instance {
	String name;
	int hitPoints;
	int damageDie;
	int hitDamageDice;
	int blastDamageDice;
	int hitDamageAdd;
	int blastDamageAdd;
	int movement;
	int vision;
	double cost;
	int meleeAC;
	int rangedAC;
	int hitBonus;
	int blastHitBonus;
	int flags;
	int currentHP;
	public boolean takeDamage(final int damage) {
		if (damage >= 0) {
			currentHP -= damage;
			return true;
		} else {
			return false;
		}
	}
	int attackRoll(boolean blast) {
		return Dice.rollDice(20) + (blast ? blastBonus : hitBonus);
	}
	int rollDamage(boolean critical, boolean blast) {
		int accumulator = 0;
		int dice = (blast ? blastDamageDice : hitDamageDice) + 
			(critical ? (blast ? blastDamageDice : hitDamageDice) : 
				0);
		for (int i = 0; i < dice; i++) {
			accumulator += Dice.rollDice(damageDie) + 
				(blast ? blastDamageAdd : hitDamageAdd);
		}
		return accumulator;
}
