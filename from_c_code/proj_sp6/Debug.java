package proj_sp6;
class Debug {
	private Debug() {
		// do not instantiate
	}
	static enum ReturnValues {
		OK,
		ERROR,
		ILLEGAL_DATA,
		ILLEGAL_USE,
		OUT_OF_MP,
		FRIENDLY_OCCUPIED, // occupied means a unit is there
		UNFRIENDLY_OCCUPIED,
		ENEMY_OCCUPIED,
		FRIENDLY_HELD, // held means a structure is there
		UNFRIENDLY_HELD,
		ENEMY_HELD,
		TOO_FAR,
		ILLEGAL_MOVE
	}
	static enum Relations {
		FRIENDLY,
		UNFRIENDLY,
		ENMITY
	}
	static boolean enabled;
}
