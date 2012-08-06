package proj_sp5;
class Debug {
	// These should be an enumerated type, and not in Debug.
	public static final int OK = 0;
	public static final int ERROR = 1;
	public static final int ILLEGAL_DATA = 2;
	public static final int ILLEGAL_USE = 3;
	public static final int OUT_OF_MOVEMENT = 4;
	public static final int MEMORY = 5; // out of memory; constant obsolete
	public static final int FRIENDLY_OCCUPIED = 6; // unit in the way
	public static final int UNFRIENDLY_OCCUPIED = 7;
	public static final int ENEMY_OCCUPIED = 8;
	public static final int FRIENDLY_HELD = 9; // structure in the way
	public static final int UNFRIENDLY_HELD = 10;
	public static final int ENEMY_HELD = 11;
	public static final int TOO_FAR = 12;
	public static final int ILLEGAL_MOVE = 13;
	// These should go in their own enum, and not in Debug.
	public static final int FRIENDLY = 0;
	public static final int UNFRIENDLY = 1;
	public static final int ENMITY = 2;
}
