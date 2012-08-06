package proj_sp3;
abstract class Unit_1_0_6 extends Common_1_0_6 {
	Unit_1_0_6() {
		super();
		military = true;
		meleeBonus = 0;
		rangedBonus = 0;
		damageDie = 6;
		meleeDice = 0;
		rangedDice = 0;
		meleeDamageBonus = 0;
		rangedDamageBonus = 0;
		maxMovement = 0;
		currentMovement = 0;
		vision = 0;
	}
	Unit_1_0_6(final Unit_1_0_6 rhs) {
		super(rhs);
		military = rhs.getMilitary();
		meleeBonus = rhs.getMeleeBonus();
		rangedBonus = rhs.getRangedBonus();
		damageDie = rhs.getDamageDie();
		meleeDice = rhs.getMeleeDice();
		rangedDice = rhs.getRangedDice();
		meleeDamageBonus = rhs.getMeleeDamageBonus();
		rangedDamageBonus = rhs.getRangedDamageBonus();
		maxMovement = rhs.getMaxMovement();
		currentMovement = rhs.getCurrentMovement();
		vision = rhs.getVision();
	}
	Unit_1_0_6 copy(final Unit_1_0_6 rhs) {
		super.copy(rhs);
		military = rhs.getMilitary();
		meleeBonus = rhs.getMeleeBonus();
		rangedBonus = rhs.getRangedBonus();
		damageDie = rhs.getDamageDie();
		meleeDice = rhs.getMeleeDice();
		rangedDice = rhs.getRangedDice();
		meleeDamageBonus = rhs.getMeleeDamageBonus();
		rangedDamageBonus = rhs.getRangedDamageBonus();
		maxMovement = rhs.getMaxMovement();
		currentMovement = rhs.getCurrentMovement();
		vision = rhs.getVision();
		return this;
	}
	boolean getMilitary() { return military; }
	boolean setMilitary(final boolean mil) {
		military = mil;
		return true;
	}
	int getMeleeBonus() { return meleeBonus; }
	boolean setMeleeBonus(final int mBonus) {
		meleeBonus = mBonus;
		return true;
	}
	int getRangedBonus() { return rangedBonus; }
	boolean setRangedBonus(final int rBonus) {
		rangedBonus = rBonus;
		return true;
	}
	int getDamageDie() { return damageDie; }
	boolean setDamageDie(final int die) {
		if (die >= 0) {
			damageDie = die;
			return true;
		} else {
			return false;
		}
	}
	int getMeleeDice() { return meleeDice; }
	boolean setMeleeDice(final int mDice) {
		if (mDice >= 0) {
			meleeDice = mDice;
			return true;
		} else {
			return false;
		}
	}
	int getRangedDice() { return rangedDice; }
	boolean setRangedDice(final int rDice) {
		if (rDice >= 0) {
			rangedDice = rDice;
			return true;
		} else {
			return false;
		}
	}
	int getMeleeDamageBonus() { return meleeDamageBonus; }
	boolean setMeleeDamageBonus(final int mDmgBonus) {
		meleeDamageBonus = mDmgBonus;
		return true;
	}
	int getRangedDamageBonus() { return rangedDamageBonus; }
	boolean setRangedDamageBonus(final int rDmgBonus) {
		rangedDamageBonus = rDmgBonus;
		return true;
	}
	int getMaxMovement() { return maxMovement; }
	boolean setMaxMovement(final int maxMv) {
		if (maxMv >= 0) {
			maxMovement = maxMv;
			return true;
		} else {
			return false;
		}
	}
	int getCurrentMovement() { return currentMovement; }
	boolean setCurrentMovement(final int currMv) {
		if (currMv < 0) {
			return false;
		} else if (currMv > maxMovement) {
			currentMovement = maxMovement;
			return true;
		} else {
			currentMovement = currMv;
			return true;
		}
	}
	boolean changeCurrentMovement(final int change) {
		return setCurrentMovement(currentMovement + change);
	}
	int getVision() { return vision; }
	boolean setVision(final int vis) {
		if (vis >= 0) {
			vision = vis;
			return true;
		} else {
			return false;
		}
	}
	private boolean military;
	private int meleeBonus;
	private int rangedBonus;
	private int damageDie;
	private int meleeDice;
	private int rangedDice;
	private int meleeDamageBonus;
	private int rangedDamageBonus;
	private int maxMovement;
	private int currentMovement;
	private int vision;
}
