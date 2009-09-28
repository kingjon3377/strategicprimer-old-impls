package model.module;

/**
 * A piece of equipment in the game.
 * @author Jonathan Lovelace
 *
 */
public interface Implement extends Module {
	/**
	 * @return What "level" am I? (A higher-level one will presumably work
	 *         better in some way.
	 */
	int getLevel();

	/**
	 * Fix damage? (Not sure what this was. But we do need a way of representing
	 * damage to mechanical as well as organic Modules, from neglect, normal
	 * wear, or enemy attacks, and a way to repair the too-expensive-to-replace
	 * ones.)
	 */
	void repair(); // Should this take an argument?
}
