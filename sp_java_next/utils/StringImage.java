package utils;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A String-Image pair.
 * @author Jonathan Lovelace
 */
public class StringImage {
	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(StringImage.class.getName()); 
	/**
	 * Constructor.
	 * @param str The filename of the image
	 */
	public StringImage(final String str) {
		string = str;
		final URL url = getClass().getResource(str);
		if (url == null) {
			LOGGER.severe("Couldn't find image " + str);
			return;
		}
		try {
			image = Toolkit.getDefaultToolkit().createImage((ImageProducer) url.getContent());
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,
					"I/O exception while refreshing terrain image", e);
		} 
	}
	/**
	 * The filename of the image.
	 */
	private final String string;
	/**
	 * The Image.
	 */
	private Image image;
	/**
	 * @return the filename
	 */
	public String getString() {
		return string;
	}
	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}
}
