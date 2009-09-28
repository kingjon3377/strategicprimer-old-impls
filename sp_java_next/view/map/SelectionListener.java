package view.map;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.main.ActionsMenu;
import view.main.TerrainTypeMenu;

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
		if ((event.getSource() instanceof GUITile) && event.isPopupTrigger()) {
			ActionsMenu.ACTIONS_MENU.setSelectedTile(map.getSelection());
			ActionsMenu.ACTIONS_MENU.show(event.getComponent(), event.getX(), event
					.getY());
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
		if (event.getSource() instanceof GUITile) {
			if (event.isPopupTrigger()) {
				ActionsMenu.ACTIONS_MENU.setSelectedTile(map.getSelection());
				ActionsMenu.ACTIONS_MENU.show(event.getComponent(), event.getX(), event
						.getY());
			}
			map.select((GUITile) event.getSource());
			TerrainTypeMenu.MENU.setTile((GUITile) event.getSource());
		}
	}
}
