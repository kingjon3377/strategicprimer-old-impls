package view.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import model.module.Module;
import model.module.UnableToMoveException;
import model.module.actions.Action;
import model.module.kinds.Fortress;
import model.module.kinds.FunctionalModule;
import model.module.kinds.MobileModule;
import model.module.kinds.RootModule;
import model.module.kinds.Weapon;
import view.map.GUITile;

/**
 * A popup menu, primarily for actions that require a start and destination tile
 * (or actor and target)
 * 
 * @author Jonathan Lovelace
 * 
 */
public final class ActionsMenu extends JMenu implements ActionListener {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = -6391713952987105391L;
	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(ActionsMenu.class
			.getName());
	/**
	 * Singleton
	 */
	public static final ActionsMenu ACTIONS_MENU = new ActionsMenu();
	/**
	 * Has an action been selected?
	 */
	private boolean actionSelected;
	/**
	 * "Move" item
	 */
	private final JMenuItem moveItem;
	/**
	 * "Attack" item
	 */
	private final JMenuItem attackItem;

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
	 * The currently selected action.
	 */
	private long action;
	/**
	 * A mapping from text to action numbers
	 */
	private final Map<String, Long> actionMap = new HashMap<String, Long>();

	/**
	 * Singleton constructor
	 */
	private ActionsMenu() {
		super("Actions");
		moveItem = new JMenuItem("Move");
		add(moveItem);
		moveItem.addActionListener(this);
		attackItem = new JMenuItem("Attack");
		add(attackItem);
		attackItem.addActionListener(this);
		actionSelected = false;
	}

	/**
	 * Refresh the menu.
	 */
	private void refreshMenu() {
		this.removeAll();
		actionMap.clear();
		if (getModuleOnTile(selectedTile) instanceof MobileModule) {
			add(moveItem);
			actionMap.put("Move", -2L);
		}
		if (getModuleOnTile(selectedTile) instanceof Weapon) {
			add(attackItem);
			actionMap.put("Attack", -1L);
		}
		if (getModuleOnTile(selectedTile) instanceof FunctionalModule) {
			for (Action act : ((FunctionalModule) getModuleOnTile(selectedTile))
					.supportedActions()) {
				addMenuItem(new JMenuItem(act.getDescription()));
				actionMap.put(act.getDescription(), act.getNumber());
			}
		}
	}
	/**
	 * Add a menu item and set this as its listener.
	 * @param item the item to add
	 */
	private void addMenuItem(final JMenuItem item) {
		add(item);
		item.addActionListener(this);
	}
	/**
	 * Handle menu item selections.
	 */
	@Override
	public void actionPerformed(final ActionEvent event) {
		if (actionMap.containsKey(event.getActionCommand())) {
			action = actionMap.get(event.getActionCommand());
			actionSelected = true;
		} else {
			System.err.println("Unknown action " + event.getActionCommand());
		}
	}

	/**
	 * @param tile
	 *            the currently selected tile
	 */
	public void setSelectedTile(final GUITile tile) {
		selectedTile = tile;
		refreshMenu();
	}

	/**
	 * @param tile
	 *            the tile the action should be done to
	 */
	public void setSecondTile(final GUITile tile) {
		secondTile = tile;
		if (action == 0) {
			return;
		} else if (action == -2) {
			try {
				if (((MobileModule) getModuleOnTile(selectedTile))
						.checkMove(secondTile.getTile())) {
					((MobileModule) getModuleOnTile(selectedTile))
							.move(secondTile.getTile());
				}
			} catch (UnableToMoveException e) {
				LOGGER.info("Movement failed");
			}
		} else if (action == -1) {
			((Weapon) getModuleOnTile(selectedTile))
					.attack(getModuleOnTile(secondTile));
		} else {
			((FunctionalModule) getModuleOnTile(selectedTile)).action(action,
					getModuleOnTile(secondTile));
		}
		cancelSelectedAction();
		if (selectedTile != null) {
			selectedTile.repaint();
		}
		if (secondTile != null) {
			secondTile.repaint();
		}
	}

	/**
	 * @param tile
	 *            a GUI tile
	 * @return The module on that tile's model, or its module if it is a
	 *         Fortress.
	 */
	private static Module getModuleOnTile(final GUITile tile) {
		return tile == null ? null
				: tile.getTile().getModuleOnTile() instanceof Fortress
						&& (((Fortress) tile.getTile().getModuleOnTile())
								.getModule() != null)
						&& !(((Fortress) tile.getTile().getModuleOnTile())
								.getModule() instanceof RootModule) ? ((Fortress) tile
						.getTile().getModuleOnTile()).getModule()
						: tile.getTile().getModuleOnTile();
	}

	/**
	 * @return whether an action has been selected
	 */
	public boolean isActionSelected() {
		return actionSelected;
	}

	/**
	 * If an action is selected, cancel it.
	 */
	public void cancelSelectedAction() {
		actionSelected = false;
	}
}
