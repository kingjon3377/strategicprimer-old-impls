package proj_sp6;
class User {
	private User() {
		// Do not instantiate
	}
	static void pause() {
		// Pause until the user says to continue.
	}
	static boolean getBooleanFromUser() {
		// Ask the user for a boolean.
	}
	static String getStringFromUser() {
		// Ask the user for a string.
	}
	// need the others
	static int getAction() {
		// Ask the user which action to take; the previous version had
		// the following menu: attack, bombard, move, construct
		// [structure], gather resources, produce new units, research
		// new technology, end turn, quit the game.
		return 0;
	}
	static Point getTile() {
		// Ask the user for a pair of coordinates.
		// This belongs in the Point class, as getPointFromUser().
		return null;
	}
}
