package view.main;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import model.module.MobileModule;
import model.module.UnableToMoveException;
import view.map.GUITile;

/**
 * A popup menu, primarily for actions that require a start and destination tile
 * (or actor and target)
 * 
 * @author Jonathan Lovelace
 * 
 */
public final class ActionsMenu extends JPopupMenu implements ActionListener {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = -6391713952987105391L;
	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(ActionsMenu.class.getName());
	/**
	 * Singleton
	 */
	public static final ActionsMenu ACTIONS_MENU = new ActionsMenu();
	/**
	 * "Move" item
	 */
	private final JMenuItem moveItem;

	/**
	 * The currently selected tile, which shouldn't be the one from which this
	 * popup springs
	 */
	private GUITile selectedTile;
	/**
	 * The destination/target/etc. tile
	 */
	private GUITile secondTile;

	/**
	 * Singleton constructor
	 */
	private ActionsMenu() {
		super();
		moveItem = new JMenuItem("Move");
		add(moveItem);
		moveItem.addActionListener(this);
	}

	/**
	 * Handle menu item selections.
	 */
	@Override
	public void actionPerformed(final ActionEvent event) {
		if ("Move".equals(event.getActionCommand())) {
			try {
				((MobileModule) selectedTile.getTile().getModuleOnTile()).move(secondTile.getTile());
				selectedTile.repaint();
				secondTile.repaint();
			} catch (UnableToMoveException e) {
				LOGGER.info("Movement failed");
			}
		}
	}

	/**
	 * @param tile
	 *            the currently selected tile
	 */
	public void setSelectedTile(final GUITile tile) {
		selectedTile = tile;
	}

	/**
	 * Pop up the menu
	 * 
	 * @param invoker
	 *            the component that triggered this
	 * @param xCoord
	 *            the X coordinate
	 * @param yCoord
	 *            the Y coordinate
	 */
	@Override
	public void show(final Component invoker, final int xCoord, final int yCoord) {
		if (invoker instanceof GUITile) {
			secondTile = (GUITile) invoker;
		}
		moveItem
				.setEnabled(selectedTile.getTile().getModuleOnTile() instanceof MobileModule);
		super.show(invoker, xCoord, yCoord);
	}
}
