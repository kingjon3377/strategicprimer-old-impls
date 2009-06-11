package utils;

/**
 * A Pixel object represents a single colored point in an image. It represents
 * color intensity values for RED, GREEN and BLUE.
 * 
 * @author Richard Wicentowski and Tia Newhall (2005)
 * @author modified by Keith Vander Linden (July2006)
 * 
 */
public class Pixel {
	/**
	 * The maximum intensity of any color of a pixel
	 */
	private static final int MAX_INTENSITY = 255;

	/**
	 * Use these enumerated constants as arguments to refer to one of the three
	 * color intensity values.
	 */
	public enum Color {
		/**
		 * Red
		 */
		RED, 
		/**
		 * Green
		 */
		GREEN, 
		/**
		 * Blue
		 */
		BLUE
	}
	/**
	 * The three color components
	 */
	private int[] component;

	/**
	 * Construct an empty (i.e., black) pixel.
	 */
	public Pixel() {
		this(0, 0, 0);
	}
	/**
	 * Construct a gray-scale pixel
	 * @param gray the intensity of the pixel
	 */
	public Pixel(final int gray) {
		component = new int[1];
		component[0] = gray;
	}

	/**
	 * Construct a pixel with explicit RGB intensity values.
	 * 
	 * @param red red component
	 * @param green green component
	 * @param blue blue component
	 */
	public Pixel(final int red, final int green, final int blue) {
		component = new int[3];
		component[0] = red;
		component[1] = green;
		component[2] = blue;
	}

	/**
	 * Construct a Pixel object with the given component values.
	 * 
	 * @param components
	 */
	public Pixel(final int[] components) {
		component = new int[components.length];
		System.arraycopy(components,0, component,0, components.length);
	}

	/**
	 * Set the pixel color to black.
	 */
	public void toBlack() {
		for (int i = 0; i < component.length; i++) {
			component[i] = 0;
		}
	}

	/**
	 * Set the pixel color to white.
	 */
	public void toWhite() {
		for (int i = 0; i < component.length; i++) {
			component[i] = MAX_INTENSITY;
		}
	}

	/**
	 * @param color
	 *            a color contant (RED, GREEN, BLUE)
	 * @return the intensity value for the pixel's c color
	 */
	public int getColorValue(final Color color) {
		switch (color) {
		case RED:
			return getRed(); // NOPMD
		case GREEN:
			return getGreen(); // NOPMD
		case BLUE:
			return getBlue();
		default:
			throw new IllegalArgumentException("Somehow got an illegal Color");
		}
	}

	/**
	 * @return the pixel's red intensity value
	 */
	public int getRed() {
		return component[0];
	}

	/**
	 * @return the pixel's green intensity value
	 */
	public int getGreen() {
		return component[1];
	}

	/**
	 * @return the pixel's blue intensity value
	 */
	public int getBlue() {
		return component[2];
	}

	/**
	 * @return the intensity value of this gray-scale pixel
	 */
	public int getGray() {
		return component[0];
	}

	/**
	 * Ensure the value is a valid color intensity
	 * @param value a proposed color intensity
	 * @return the intensity, or 0 or 255 if it was an invalid value.
	 */
	private static int normalizeColorValue(final int value) {
		if (value < 0) {
			return 0; // NOPMD
		} else if (value > MAX_INTENSITY) {
			return MAX_INTENSITY; // NOPMD
		} else {
			return value;
		}
	}

	/**
	 * Set the red intensity value of the pixel
	 * 
	 * @param red
	 *            an integer from 0-255
	 */
	public void setRed(final int red) {
		component[0] = normalizeColorValue(red);
	}

	/**
	 * Set the green intensity value of the pixel
	 * 
	 * @param green
	 *            an integer from 0-255
	 */
	public void setGreen(final int green) {
		component[1] = normalizeColorValue(green);
	}

	/**
	 * Set the blue intensity value of the pixel
	 * 
	 * @param blue
	 *            an integer from 0-255
	 */
	public void setBlue(final int blue) {
		component[2] = normalizeColorValue(blue);
	}

	/**
	 * Set the red intensity value of the pixel to red
	 * 
	 * @param gray
	 *            an integer from 0-255
	 */
	public void setGray(final int gray) {
		component[0] = gray;
	}

	/**
	 * @return the color components of the pixel
	 */
	public int[] getComponents() {
		return component.clone();
	}
	/**
	 * @return a String representation of the pixel's color values
	 */
	@Override
	public String toString() {
		final StringBuffer sbuffer = new StringBuffer();
		for (int k = 0; k < component.length; k++) {
			if (k != 0) {
				sbuffer.append('\t');
			}
			sbuffer.append(component[k]);
		}
		return sbuffer.toString();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 * 
	 * @return whether the two objects are equal
	 */
	@Override
	public boolean equals(final Object other) {
		if (other instanceof Pixel) {
			final Pixel pix = (Pixel) other; // NOPMD
			if (pix.component.length == component.length) {
				for (int k = 0; k < component.length; k++) {
					if (pix.component[k] != component[k]) {
						return false; // NOPMD
					}
				}
				return true; // NOPMD
			}
			return false; // NOPMD
		}
		return false;
	}
	/**
	 * @return a hash code for the pixel
	 */
	@Override
	public int hashCode() {
		return (MAX_INTENSITY + 1) * (MAX_INTENSITY + 1) * component[0] + (MAX_INTENSITY + 1) * component[1] + component[2];
	}
	
	/**
	 * A black pixel.
	 */
	public static final Pixel BLACK_PIXEL = new Pixel(0, 0, 0);
}
