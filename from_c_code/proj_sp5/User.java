package proj_sp5;

class User {
	private User() {
		// don't instantiate
	}
	static void pause() {
		// Print "Press Enter to continue", then loop until Enter is
		// pressed.
	}
	static boolean getBooleanFromUser() {
		throw new IllegalStateException("Unimplemented");
	}
	static String getStringFromUser() {
		throw new IllegalStateException("Unimplemented");
	}
	// need others for other types ...
	static int getAction() {
		// Have the user select an action from the choices.
		// Choices in C++ CLI implementation were: attack, bombard,
		// move, construct, gather resources, produce new units,
		// research new technology, end the turn, or quit the game.
		// Need to implement this ...
		throw new IllegalStateException("Unimplemented");
	}
	static Point getTile() {
		// Get coordinates from the user.
		// We also need a method getting the actual tile.
		throw new IllegalStateException("Unimplemented");
	}
	static double getDoubleFromUser() {
		throw new IllegalStateException("Unimplemented");
	}
	static int getIntegerFromUser() {
		throw new IllegalStateException("Unimplemented");
	}
	static int getLongIntegerFromUser() {
		throw new IllegalStateException("Unimplemented");
	}
	static int getUIntFromUser() {
		throw new IllegalStateException("Unimplemented");
	}
	static long getULongFromUser() {
		throw new IllegalStateException("Unimplemented");
	}
}
