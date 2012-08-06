package sp.model;

/**
 * An interface to hold all the stat-getters and -setters from IModule.
 * 
 * @author Jonathan Lovelace
 * 
 */
public interface Statistician {
	/**
	 * @return the module's defense stat
	 */
	int getDefense();

	/**
	 * @return the module's current HP
	 */
	int getHitPoints();

	/**
	 * @return the module's maximum HP
	 */
	int getMaxHP();

	/**
	 * @return the module's speed
	 */
	int getSpeed();

	/**
	 * @param defense
	 *            the module's defense stat
	 */
	void setDefense(int defense);

	/**
	 * @param hitPoints
	 *            the module's current HP
	 */
	void setHitPoints(int hitPoints);

	/**
	 * @param maxHP
	 *            the module's maximum HP
	 */
	void setMaxHP(int maxHP);

	/**
	 * @param speed
	 *            the module's speed
	 */
	void setSpeed(int speed);

}
