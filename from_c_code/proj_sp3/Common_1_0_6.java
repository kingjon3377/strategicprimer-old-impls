package proj_sp3;
abstract class Common_1_0_6 {
	private String name;
	int maxHitPoints; // unsigned in C++
	int meleeAC; // unsigned in C++
	int rangedAC; // unsigned in C++
	List<Resource_1_0_6> resourcesRequired;
	int currentHitPoints; // unsigned in C++
	int currentLevel; // unsigned in C++
	long currentExperience; // unsigned in C++
	SPPoint position;
	Common_1_0_6() {
		name = "";
		maxHitPoints = 1;
		meleeAC = 10;
		rangedAC = 10;
		resourcesRequired = new ArrayList<Resource_1_0_6>();
		currentHitPoints = 1;
		currentLevel = 1;
		currentExperience = 0;
		position = new SPPoint(0, 0);
	}
	Common_1_0_6(final Common_1_0_6 rhs) {
		name = rhs.getName();
		maxHitPoints = rhs.getMaxHitPoints();
		meleeAC = rhs.getMeleeAC();
		rangedAC = rhs.getRangedAC();
		resourcesRequired = new ArrayList<Resource_1_0_6>(
				rhs.getResourcesRequired());
		currentHitPoints = rhs.getCurrentHitPoints();
		currentLevel = rhs.getCurrentLevel();
		currentExperience = rhs.getCurrentExperience();
		position = new SPPoint(rhs.getPosition());
	}
	Common_1_0_6 copy(final Common_1_0_6 rhs) {
		if (this != rhs) {
			name = rhs.getName();
			resourcesRequired = new ArrayList<Resource_1_0_6>(
					rhs.getResourcesRequired();
			maxHitPoints = rhs.getMaxHitPoints();
			meleeAC = rhs.getMeleeAC();
			rangedAC = rhs.getRangedAC();
			currentHitPoints = rhs.getCurrentHitPoints();
			currentLevel = rhs.getCurrentLevel();
			currentExperience = rhs.getCurrentExperience();
			position = new SPPoint(rhs.getPosition());
		}
		return this;
	}
	String getName() { return name; }
	boolean setName(final String _name) {
		name = _name;
		return true;
	}
	int getMaxHitPoints() { return maxHitPoints; }
	boolean setMaxHitPoints(final int maxHP) {
		if (maxHP >= 0) {
			maxHitPoints = maxHP;
			return true;
		} else {
			return false;
		}
	}
	int getMeleeAC() { return meleeAC; }
	boolean setMeleeAC(final int mac) {
		meleeAC = mac; 
		// The C++ was unsigned, but ACs aren't necessarily > 0.
		return true;
	}
	int getRangedAC() { return rangedAC; }
	boolean setRangedAC(final int rac) {
		rangedAC = rac;
		// The C++ was unsigned, but ACs aren't necessarily > 0.
		return true;
	}
	List<Resource_1_0_6> getResourcesRequired { return resourcesRequired; }
	boolean setResourcesRequired(final List<Resource_1_0_6> rr) {
		resourcesRequired = new ArrayList<Resource_1_0_6>(rr);
	}
	int getCurrentHitPoints() { return currentHitPoints; }
	boolean setCurrentHitPoints(final int currentHP) {
		if (currentHP >= 0) {
			currentHitPoints = currentHP;
			return true;
		} else {
			return false;
		}
	}
	boolean changeCurrentHitPoints(final int change) {
		if (currentHitPoints + change >= 0) {
			currentHitPoints += change;
			return true;
		} else {
			return false; // SP_ILLEGAL_DATA
		}
	}
	int getCurrentLevel() { return currentLevel; }
	boolean setCurrentLevel(final int currLevel) {
		if (currLevel >= 0) {
			currentLevel = currLevel;
			return true;
		} else {
			return false;
		}
	}
	long getCurrentExperience() { return currentExperience; }
	boolean setCurrentExperience(final long currXP) {
		if (currXP >= 0) {
			currentExperience = currXP;
			return true;
		} else {
			return false;
		}
	}
	SPPoint getPosition() { return position; }
	boolean setPosition(final SPPoint pos) {
		position = new SPPoint(pos);
		return true;
	}
	abstract boolean action();
}
