class User {
	private User() {
		// don't instantiate
	}
	void pause() {
		// Print "Press Enter to continue", then loop until Enter is
		// pressed.
	}
	boolean getBooleanFromUser() {
		// implement ...
	}
	String getStringFromUser() {
		// implement ...
	}
	// need others for other types ...
	int getAction() {
		// Have the user select an action from the choices.
		// Choices in C++ CLI implementation were: attack, bombard,
		// move, construct, gather resources, produce new units,
		// research new technology, end the turn, or quit the game.
		// Need to implement this ...
	}
	Point getTile() {
		// Get coordinates from the user.
		// We also need a method getting the actual tile.
	}
}
