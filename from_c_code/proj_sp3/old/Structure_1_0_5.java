package proj_sp3.old;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
abstract class Structure_1_0_5 {
	public Structure_1_0_5() {
		name = "New Structure";
		maxHitPoints = 1;
		meleeAC = 10;
		rangedAC = 10;
		resourcesRequired = new ArrayList<Resource_1_0_5>();
		currentHitPoints = 1;
		currentLevel = 1;
		currentExperience = 0;
	}
	public Structure_1_0_5(final Structure_1_0_5 rhs) {
		name = rhs.getName();
		maxHitPoints = rhs.getMaxHitPoints();
		meleeAC = rhs.getMeleeAC();
		rangedAC = rhs.getRangedAC();
		resourcesRequired = new ArrayList<Resource_1_0_5>(
				rhs.getResourcesRequired());
		currentHitPoints = rhs.getCurrentHitPoints();
		currentLevel = rhs.getCurrentLevel();
		currentExperience = rhs.getCurrentExperience();
	}
	public Structure_1_0_5 copy(final Structure_1_0_5 rhs) {
		if (this != rhs) {
			name = rhs.getName();
			maxHitPoints = rhs.getMaxHitPoints();
			meleeAC = rhs.getMeleeAC();
			rangedAC = rhs.getRangedAC();
			resourcesRequired = new ArrayList<Resource_1_0_5>(
					rhs.getResourcesRequired());
			currentHitPoints = rhs.getCurrentHitPoints();
			currentLevel = rhs.getCurrentLevel();
			currentExperience = rhs.getCurrentExperience();
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
		// The original was unsigned, but I don't think that's
		// necessary.
		meleeAC = mac;
		return true;
	}
	public int getRangedAC() {
		return rangedAC;
	}
	public boolean setRangedAC(final int rac) {
		// The original was unsigned, but I don't think that's
		// necessary.
		rangedAC = rac;
		return true;
	}
	public List<Resource_1_0_5> getResourcesRequired() {
		return Collections.unmodifiableList(resourcesRequired);
	}
	public boolean setResourcesRequired(final List<Resource_1_0_5> res) {
		resourcesRequired = new ArrayList<Resource_1_0_5>(res);
		return true;
	}
	public int getCurrentHitPoints() {
		return currentHitPoints;
	}
	public boolean setCurrentHitPoints(final int currHP) {
		if (currHP > maxHitPoints) {
			currentHitPoints = maxHitPoints;
			return true;
		} else if (currHP >= 0) {
			currentHitPoints = currHP;
			return true;
		} else {
			return false;
		}
	}
	boolean changeCurrentHitPoints(final int change) {
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
	public abstract boolean action();
	private String name;
	private int maxHitPoints; // unsigned
	private int meleeAC; // formerly unsigned
	private int rangedAC; // formerly unsigned
	private List<Resource_1_0_5> resourcesRequired;
	private int currentHitPoints; // unsigned
	private int currentLevel; // unsigned
	private long currentExperience; // unsigned
}
