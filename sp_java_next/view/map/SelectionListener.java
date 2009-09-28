package view.map;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A listener to handle mouse clicks to change the selection.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class SelectionListener implements MouseListener {
	/**
	 * Constructor
	 * 
	 * @param _map
	 *            The GUIMap that should be told about selection changes.
	 */
	public SelectionListener(final GUIMap _map) {
		map = _map;
	}

	/**
	 * The GUIMap that should be told about selection changes
	 */
	private final GUIMap map;

	/**
	 * Change the selected tile when a tile is clicked.
	 */
	@Override
	public void mouseClicked(final MouseEvent event) {
		if (event.getSource() instanceof GUITile) {
			map.select((GUITile) event.getSource());
			TerrainTypeMenu.MENU.setTile((GUITile) event.getSource());
		}
	}

	/**
	 * Required by interface
	 * 
	 * @param event
	 *            ignored
	 */
	@Override
	public void mouseEntered(final MouseEvent event) {
		// Do nothing for now
	}

	/**
	 * Required by interface
	 * 
	 * @param event
	 *            ignored
	 */
	@Override
	public void mouseExited(final MouseEvent event) {
		// Do nothing for now
	}

	/**
	 * Change the selected tile when a tile is clicked.
	 * 
	 * @param event
	 *            ignored
	 */
	@Override
	public void mousePressed(final MouseEvent event) {
		if (event.isPopupTrigger() && map.isAdmin()) {
//			TerrainTypeMenu.MENU.show(event.getComponent(), event.getX(), event
//					.getY());
		}
		if (event.getSource() instanceof GUITile) {
			map.select((GUITile) event.getSource());
			TerrainTypeMenu.MENU.setTile((GUITile) event.getSource());
		}
	}

	/**
	 * Change the selected tile when a tile is clicked.
	 * 
	 * @param event
	 *            ignored
	 */
	@Override
	public void mouseReleased(final MouseEvent event) {
		if (event.isPopupTrigger() && map.isAdmin()) {
//			TerrainTypeMenu.MENU.show(event.getComponent(), event.getX(), event
//					.getY());
		}
		if (event.getSource() instanceof GUITile) {
			map.select((GUITile) event.getSource());
			TerrainTypeMenu.MENU.setTile((GUITile) event.getSource());
		}
	}
}
