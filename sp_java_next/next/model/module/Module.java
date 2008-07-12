package next.model.module;
/**
 * A component in a unit, building, etc. (Need better description.)
 * @author Jonathan Lovelace
 *
 */
public interface Module {
	/**
	 * @return my universally unique ID -- *this* module rather than another of
	 *         its kind.
	 */
	int getUuid();
	/**
	 * @return what kind of module I am
	 */
	int getModuleID();
	/**
	 * Any kind of Module can be attacked.
	 * @param attacker The weapon attacking or being used to attack me.
	 */
	void takeAttack(Weapon attacker);
}
