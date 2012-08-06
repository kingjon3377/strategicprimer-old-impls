package sp.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A listener for each tile in the MapPanel. 
 * @author Jonathan Lovelace
 */
public class TileListener implements MouseListener {
	/**
	 * The tile this is the listener for.
	 */
	private final GUITile tile;
	/**
	 * The panel to handle what the event represents
	 */
	private final MapPanel panel;
	/**
	 * Constructor
	 * @param _tile The tile this is the listener for
	 * @param _panel The panel to do the actual work
	 */
	public TileListener(final GUITile _tile, final MapPanel _panel){
		tile = _tile;
		panel = _panel;
	}
	/**
	 * On mouse-click, tell the panel which tile was clicked.
	 */
	@Override
	public void mouseClicked(final MouseEvent event) {
		if (MouseEvent.BUTTON1 == event.getButton()) {
			panel.handleClick(tile.getX(), tile.getY());
		}
	}
	/**
	 * On mouse entry, refresh the label panel.
	 */
	@Override
	public void mouseEntered(final MouseEvent event) {
		LabelPanel.getLabelPanel().repaint();
	}
	/**
	 * On mouse exit, refresh the label panel.
	 */
	@Override
	public void mouseExited(final MouseEvent event) {
		LabelPanel.getLabelPanel().repaint();
	}
	/**
	 * On mouse press, do nothing
	 */
	@Override
	public void mousePressed(final MouseEvent event) {
		// do nothing
	}
	/**
	 * On mouse release, do nothing.
	 */
	@Override
	public void mouseReleased(final MouseEvent event) {
		// do nothing
	}
	/**
	 * To avoid "creating new objects in loops" warnings
	 * @param _tile A GUITile
	 * @param _panel The MapPanel
	 * @return A new TileListener with those parameters.
	 */
	public static TileListener createListener(final GUITile _tile, final MapPanel _panel) {
		return new TileListener(_tile,_panel);
	}
}
