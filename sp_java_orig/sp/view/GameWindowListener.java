package sp.view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 * A listener for the main window. Refactored out of the main driver class.
 * @author Jonathan Lovelace
 */
public final class GameWindowListener implements WindowListener{
	/**
	 * Constructor
	 * @param frame The window we're listening to
	 */
	public GameWindowListener(final JFrame frame) {
        myFrame = frame;
    }
	/**
	 * The window we're listening to.
	 */
    private final JFrame myFrame;
    	/**
	 * The following are to make ann.gui.CloseableFrame unnecessary while
	 * achieving the same functions.
	 */
	/**
	 * Repaint on activation
	 *
	 * @param arg0
	 *            ignored
	 */
     @Override
    public void windowActivated(final WindowEvent arg0) {
		myFrame.repaint();
	}

	/**
	 * Quit when the window is closed
	 *
	 * @param arg0
	 *            ignored
	 */
     @Override
	public void windowClosed(final WindowEvent arg0) {
		System.exit(0);
	}

	/**
	 * Quit if the window is closing
	 *
	 * @param arg0
	 *            ignored
	 */
     @Override
	public void windowClosing(final WindowEvent arg0) {
		System.exit(0);
	}

	/**
	 * Do nothing when the window is deactivated
	 *
	 * @param arg0
	 *            ignored
	 */
     @Override
	public void windowDeactivated(final WindowEvent arg0) {
		// Do nothing
	}

	/**
	 * Repack the window when deiconified
	 *
	 * @param arg0
	 *            ignored
	 */
     @Override
	public void windowDeiconified(final WindowEvent arg0) {
		myFrame.pack();
	}

	/**
	 * Do nothing when the window is iconified
	 *
	 * @param arg0
	 *            ignored
	 */
     @Override
	public void windowIconified(final WindowEvent arg0) {
		// Do nothing
	}

	/**
	 * Repaint when the window is opened
	 *
	 * @param arg0
	 *            ignored
	 */
     @Override
	public void windowOpened(final WindowEvent arg0) {
		myFrame.repaint();
	}

}
