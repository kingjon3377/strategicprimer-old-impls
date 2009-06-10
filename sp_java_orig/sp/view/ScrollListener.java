package sp.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * A listener to make the MapPanel scroll.
 * @author Jonathan Lovelace
 *
 */
public class ScrollListener implements MouseListener {
	/**
	 * The panel we're scrolling
	 */
	private final MapPanel panel;
	/**
	 * Constructor.
	 * @param _panel The panel we're scrolling
	 */
	public ScrollListener(final MapPanel _panel) {
		panel = _panel;
	}
	/**
	 * We figure out what tile got clicked and then handle it in a helper
	 * method.
	 * 
	 * @param arg0
	 *            The event we're handling
	 */
	@Override
	public void mouseClicked(final MouseEvent arg0) {
		if (panel != null) {
			panel.moveMap(arg0.getSource());
			LabelPanel.getLabelPanel().repaint();
		}
	}

	/**
	 * Repaint when the mouse enters the panel.
	 * 
	 * @param arg0
	 *            ignored
	 */
	public void mouseEntered(final MouseEvent arg0) {
		LabelPanel.getLabelPanel().repaint();
	}

	/**
	 * Repaint when the mouse exits the panel.
	 * 
	 * @param arg0
	 *            ignored
	 */
	@Override
	public void mouseExited(final MouseEvent arg0) {
		LabelPanel.getLabelPanel().repaint();
	}

	/**
	 * @param arg0
	 *            ignored
	 */
	@Override
	public void mousePressed(final MouseEvent arg0) {
		// do nothing
	}

	/**
	 * @param arg0
	 *            ignored
	 */
	@Override
	public void mouseReleased(final MouseEvent arg0) {
		// do nothing
	}
}