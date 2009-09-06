package proj_sp6;

import java.util.List;

import proj_sp6.Globals.Direction;

class User {
	private User() {
		// Do not instantiate
	}

	static void pause() {
		// Pause until the user says to continue.
	}

	static boolean getBooleanFromUser() {
		// Ask the user for a boolean.
		throw new IllegalStateException("Unimplemented");
	}

	static String getStringFromUser() {
		// Ask the user for a string.
		throw new IllegalStateException("Unimplemented");
	}

	static int getIntegerFromUser() {
		// Ask the user for an ingeger
		throw new IllegalStateException("Unimplemented");
	}
	static double getDoubleFromUser() {
		throw new IllegalStateException("Unimplemented");
	}
	static int getUIntFromUser() {
		throw new IllegalStateException("Unimplemented");
	}
	static long getULongFromUser() {
		throw new IllegalStateException("Unimplemented");
	}
	// need the others
	static Action getAction() {
		// Ask the user which action to take; the previous version had
		// the following menu: attack, bombard, move, construct
		// [structure], gather resources, produce new units, research
		// new technology, end turn, quit the game.
		throw new IllegalStateException("Unimplemented");
	}

	static Point getTile() {
		// Ask the user for a pair of coordinates.
		// This belongs in the Point class, as getPointFromUser().
		return null;
	}

	enum Action {
		ATTACK, BOMBARD, MOVE, CONSTRUCT, GATHER_RESOURCES, PRODUCE, RESEARCH, END_TURN, QUIT, DEFAULT
	}

	public static Unit selectUnit(List<Unit> allUnits) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Direction selectDirection() {
		// TODO Auto-generated method stub
		return null;
	}
}
