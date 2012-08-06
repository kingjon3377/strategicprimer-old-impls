package proj_sp3;

class Debug {
	public enum Level {
		NONE, LOW, MEDIUM, HIGH
	}

	public static final Level LEVEL = Level.NONE;

	public static void spAssert(final boolean x) {
		if (LEVEL != Level.NONE && !x) {
			throw new AssertionError(x);
		}
	}

	public static void print(final Object x) {
		if (LEVEL == Level.HIGH) {
			System.out.println(x);
		}
	}
}
