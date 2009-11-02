package view.map;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.main.ActionsMenu;
import view.main.TerrainTypeMenu;
import view.module.CurrentModulePanel;

/**
 * A listener to handle mouse clicks to change the selection.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class SelectionListener implements MouseListener {
	/**
	 * Constructor.
	 * 
	 * @param theMap
	 *            The GUIMap that should be told about selection changes.
	 * @param modulePanel
	 *            The module panel that should be told about selection changes
	 */
	public SelectionListener(final GUIMap theMap, final CurrentModulePanel modulePanel) {
		map = theMap;
		panel = modulePanel;
	}

	/**
	 * The GUIMap that should be told about selection changes.
	 */
	private final GUIMap map;
	/**
	 * The ModulePanel that should be told about selection changes.
	 */
	private final CurrentModulePanel panel;

	/**
	 * Ignored.
	 * @param event ignored
	 */
	@Override
	public void mouseClicked(final MouseEvent event) {
		// Do nothing
	}

	/**
	 * When the mouse moves into of a GUITile, update projected movement.
	 * 
	 * @param event
	 *            The GUITile
	 */
	@Override
	public void mouseEntered(final MouseEvent event) {
		if (event.getSource() instanceof GUITile) {
			ActionsMenu.ACTIONS_MENU.drawMovePath((GUITile) event.getSource());
		}
	}

	/**
	 * When the mouse moves out of a GUITile, update projected movement.
	 * 
	 * @param event
	 *            The GUITile
	 */
	@Override
	public void mouseExited(final MouseEvent event) {
		if (event.getSource() instanceof GUITile) {
			ActionsMenu.ACTIONS_MENU.drawMovePath((GUITile) event.getSource());
		}
	}

	/**
	 * Ignored.
	 * 
	 * @param event
	 *            ignored
	 */
	@Override
	public void mousePressed(final MouseEvent event) {
		// Do nothing
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
			if (ActionsMenu.ACTIONS_MENU.isActionSelected()) {
				ActionsMenu.ACTIONS_MENU.applyAction((GUITile) event
						.getComponent());
				panel.changeCurrentModule(((GUITile) event.getSource()).getTile()
						.getModuleOnTile());
				map.select((GUITile) event.getSource());
				TerrainTypeMenu.MENU.setTile((GUITile) event.getSource());
				ActionsMenu.ACTIONS_MENU.setSelectedTile(map.getSelection());
			} else {
				panel.changeCurrentModule(((GUITile) event.getSource()).getTile()
						.getModuleOnTile());
				map.select((GUITile) event.getSource());
				TerrainTypeMenu.MENU.setTile((GUITile) event.getSource());
				ActionsMenu.ACTIONS_MENU.setSelectedTile(map.getSelection());
			}
		}
	}
}
