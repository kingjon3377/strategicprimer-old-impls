package edu.calvin.jsl7.finalproject;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
/**
 * A listener for components of the GameGUIDriver
 * @author Jonathan Lovelace
 *
 */
public final class PackComponentListener implements ComponentListener {
	/**
	 * This class is a singleton.
	 */
	private PackComponentListener() {
		// Singleton.
	}
	/**
	 * The singleton object.
	 */
	public static final PackComponentListener LISTENER = new PackComponentListener(); 
	/**
	 * The driver we're managing component events for
	 */
	private static GameGUIDriver driver;
	/**
	 * @param _driver The driver we're managing component events for
	 */
	public static void setDriver(final GameGUIDriver _driver) {
		driver = _driver;
	}
	
	/**
	 * Called when a component is hidden: repack.
	 * 
	 * @param event
	 *            ignored
	 */
	@Override
	public void componentHidden(final ComponentEvent event) {
		driver.pack();
	}
	/**
	 * Called when a component is moved: repaint.
	 * 
	 * @param event
	 *            ignored
	 */
	@Override
	public void componentMoved(final ComponentEvent event) {
		driver.repaint();
	}

	/**
	 * Called when a component is resized: repaint.
	 * 
	 * @param event
	 *            ignored
	 */
	@Override
	public void componentResized(final ComponentEvent event) {
		driver.repaint();
	}

	/**
	 * Called when a component is shown: repack.
	 * 
	 * @param event
	 *            ignored
	 */
	@Override
	public void componentShown(final ComponentEvent event) {
		driver.pack();
	}
}
