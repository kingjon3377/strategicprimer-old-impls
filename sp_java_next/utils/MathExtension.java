package utils;

/**
 * Class to hold extensions to Java's math capabilities -- currently only an
 * improved modulo operator.
 * 
 * @author Jonathan Lovelace
 * @calvin.date 16 November 2006
 * 
 */
public final class MathExtension {
	/**
	 * Constructor to prevent instantiation of this class.
	 */
	private MathExtension() {
		// do nothing
	}

	/**
	 * Makes x % y handle negatives in a sane manner.
	 * 
	 * @param first
	 *            The number
	 * @param second
	 *            The base
	 * @return The modulus
	 */
	public static int modulus(final int first, final int second) {
		return (first < 0 ? modulus(second - first, second) : (first > second ? modulus(
				first - second, second) : first));
	}
}
