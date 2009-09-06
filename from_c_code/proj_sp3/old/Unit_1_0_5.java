package proj_sp3.old;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
abstract class Unit_1_0_5 {
	public Unit_1_0_5() {
		name = "New Unit_1_0_5";
		maxHitPoints = 1;
		meleeAC = 10;
		rangedAC = 10;
		military = true;
		meleeBonus = 0;
		rangedBonus = 0;
		damageDie = 6;
		meleeDice = 0;
		rangedDice = 0;
		meleeDamageBonus = 0;
		rangedDamageBonus = 0;
		resourcesRequired = new ArrayList<Resource_1_0_5>();
		currentHitPoints = 1;
		currentLevel = 1;
		currentExperience = 0;
		maxMovement = 0;
		currentMovement = 0;
		vision = 0;
		position = new Point(0, 0);
	}
	public Unit_1_0_5(final Unit_1_0_5 rhs) {
		name = rhs.getName();
		maxHitPoints = rhs.getMaxHitPoints();
		meleeAC = rhs.getMeleeAC();
		rangedAC = rhs.getRangedAC();
		military = rhs.isMilitary();
		meleeBonus = rhs.getMeleeBonus();
		rangedBonus = rhs.getRangedBonus();
		damageDie = rhs.getDamageDie();
		meleeDice = rhs.getMeleeDice();
		rangedDice = rhs.getRangedDice();
		meleeDamageBonus = rhs.getMeleeDamageBonus();
		rangedDamageBonus = rhs.getRangedDamageBonus();
		resourcesRequired = new ArrayList<Resource_1_0_5>(
				rhs.getResourcesRequired());
		currentHitPoints = rhs.getCurrentHitPoints();
		currentLevel = rhs.getCurrentLevel();
		currentExperience = rhs.getCurrentExperience();
		maxMovement = rhs.getMaxMovement();
		currentMovement = rhs.getCurrentMovement();
		vision = rhs.getVision();
		position = new Point(rhs.getPosition());
	}
	public Unit_1_0_5 copy(final Unit_1_0_5 rhs) {
		if (rhs != this) {
			name = rhs.getName();
			maxHitPoints = rhs.getMaxHitPoints();
			meleeAC = rhs.getMeleeAC();
			rangedAC = rhs.getRangedAC();
			military = rhs.isMilitary();
			meleeBonus = rhs.getMeleeBonus();
			rangedBonus = rhs.getRangedBonus();
			damageDie = rhs.getDamageDie();
			meleeDice = rhs.getMeleeDice();
			rangedDice = rhs.getRangedDice();
			meleeDamageBonus = rhs.getMeleeDamageBonus();
			rangedDamageBonus = rhs.getRangedDamageBonus();
			resourcesRequired = new ArrayList<Resource_1_0_5>(
					rhs.getResourcesRequired());
			currentHitPoints = rhs.getCurrentHitPoints();
			currentLevel = rhs.getCurrentLevel();
			currentExperience = rhs.getCurrentExperience();
			maxMovement = rhs.getMaxMovement();
			currentMovement = rhs.getCurrentMovement();
			vision = rhs.getVision();
			position = new Point(rhs.getPosition());
		}
		return this;
	}
	public String getName() {
		return name;
	}
	public boolean setName(final String _name) {
		name = _name;
		return true;
	}
	public int getMaxHitPoints() {
		return maxHitPoints;
	}
	public boolean setMaxHitPoints(final int maxHP) {
		if (maxHP >= 0) {
			maxHitPoints = maxHP;
			return true;
		} else {
			return false;
		}
	}
	public int getMeleeAC() {
		return meleeAC;
	}
	public boolean setMeleeAC(final int mac) {
		// AC was originally unsigned, but IMO doesn't need to be
		meleeAC = mac;
		return true;
	}
	public int getRangedAC() {
		return rangedAC;
	}
	public boolean setRangedAC(final int rac) {
		// AC was originally unsigned, but IMO doesn't need to be
		rangedAC = rac;
		return true;
	}
	public boolean isMilitary() {
		return military;
	}
	public boolean setMilitary(final boolean mil) {
		military = mil;
		return true;
	}
	public int getMeleeBonus() {
		return meleeBonus;
	}
	public boolean setMeleeBonus(final int mbonus) {
		meleeBonus = mbonus;
		return true;
	}
	public int getRangedBonus() {
		return rangedBonus;
	}
	public boolean setRangedBonus(final int rbonus) {
		rangedBonus = rbonus;
		return true;
	}
	public int getDamageDie() {
		return damageDie;
	}
	public boolean setDamageDie(final int die) {
		if (die >= 0) {
			damageDie = die;
			return true;
		} else {
			return false;
		}
	}
	public int getMeleeDice() {
		return meleeDice;
	}
	public boolean setMeleeDice(final int mdice) {
		if (mdice >= 0) {
			meleeDice = mdice;
			return true;
		} else {
			return false;
		}
	}
	public int getRangedDice() {
		return rangedDice;
	}
	public boolean setRangedDice(final int rdice) {
		if (rdice >= 0) {
			rangedDice = rdice;
			return true;
		} else {
			return false;
		}
	}
	public int getMeleeDamageBonus() {
		return meleeDamageBonus;
	}
	public boolean setMeleeDamageBonus(final int bonus) {
		meleeDamageBonus = bonus;
		return true;
	}
	public int getRangedDamageBonus() {
		return rangedDamageBonus;
	}
	public boolean setRangedDamageBonus(final int bonus) {
		rangedDamageBonus = bonus;
		return true;
	}
	public List<Resource_1_0_5> getResourcesRequired() {
		return Collections.unmodifiableList(resourcesRequired);
	}
	public boolean setResourcesRequired(final List<Resource_1_0_5> rsr) {
		resourcesRequired = new ArrayList<Resource_1_0_5>(rsr);
		return true;
	}
	public int getCurrentHitPoints() {
		return currentHitPoints;
	}
	public boolean setCurrentHitPoints(final int hp) {
		if (hp >= 0) {
			currentHitPoints = 
					(hp > maxHitPoints ? maxHitPoints : hp);
			return true;
		} else {
			return false;
		}
	}
	public boolean changeCurrentHitPoints(final int change) {
		if (currentHitPoints + change > maxHitPoints) {
			currentHitPoints = maxHitPoints;
		} else if (currentHitPoints + change < 0) {
			currentHitPoints = 0;
		} else {
			currentHitPoints += change;
		}
		return true;
	}
	public int getCurrentLevel() {
		return currentLevel;
	}
	public boolean setCurrentLevel(final int level) {
		if (level >= 0) {
			currentLevel = level;
			return true;
		} else {
			return false;
		}
	}
	public long getCurrentExperience() {
		return currentExperience;
	}
	public boolean setCurrentExperience(final long xp) {
		if (xp >= 0) {
			currentExperience = xp;
			return true;
		} else {
			return false;
		}
	}
	public int getMaxMovement() {
		return maxMovement;
	}
	public boolean setMaxMovement(final int maxmv) {
		if (maxmv >= 0) {
			maxMovement = maxmv;
			return true;
		} else {
			return false;
		}
	}
	public int getCurrentMovement() {
		return currentMovement;
	}
	public boolean setCurrentMovement(final int move) {
		if (move >= 0) {
			currentMovement = 
				(move > maxMovement ? maxMovement : move);
			return true;
		} else {
			return false;
		}
	}
	public boolean changeCurrentMovement(final int change) {
		if (currentMovement + change > maxMovement) {
			currentMovement = maxMovement;
		} else if (currentMovement + change < 0) {
			currentMovement = 0;
		} else {
			currentMovement += change;
		}
		return true;
	}
	public int getVision() {
		return vision;
	}
	public boolean setVision(final int vis) {
		if (vis >= 0) {
			vision = vis;
			return true;
		} else {
			return false;
		}
	}
	public Point getPosition() {
		return position;
	}
	public boolean setPosition(final Point pos) {
		position.copy(pos);
		return true;
	}
	abstract public boolean action();
	private String name;
	private int maxHitPoints; // unsigned
	private int meleeAC; // formerly unsigned
	private int rangedAC; // formerly unsigned
	private boolean military;
	private int meleeBonus;
	private int rangedBonus;
	private int damageDie; // unsigned
	private int meleeDice; // unsigned
	private int rangedDice; // unsigned
	private int meleeDamageBonus;
	private int rangedDamageBonus;
	private List<Resource_1_0_5> resourcesRequired;
	private int currentHitPoints; // unsigned
	private int currentLevel; // unsigned
	private long currentExperience; // unsigned
	private int maxMovement; // unsigned
	private int currentMovement; // unsigned
	private int vision; // unsigned
	private Point position;
}
