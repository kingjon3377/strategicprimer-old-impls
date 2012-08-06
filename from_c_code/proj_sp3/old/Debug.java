package proj_sp3.old;
class Debug {
	enum Level {
		NONE,
		LOW,
		MEDIUM,
		HIGH
	}
	public static final Level DEBUGLEVEL = Level.HIGH;
	public void spAssert(final String desc, final int line, final String file,
			final boolean x) {
		// Note that properly implementing ASSERT(x) is
		// impossible without a C-like preprocessor
		if (DEBUGLEVEL == Level.NONE) {
			return;
		}
		if (!x) {
			System.err.print("ERROR!! Assert ");
			System.err.print(desc);
			System.err.print(" failed on line ");
			System.err.print(line);
			System.err.print(" in file ");
			System.err.println(file);
		}
	}
	// There is no way to implement EVAL(x) without a C-like macro
	// system, so I'm not going to try.
	static void print(final Object x) {
		if (DEBUGLEVEL == Level.HIGH) {
			System.out.println(x);
		}
	}
}
