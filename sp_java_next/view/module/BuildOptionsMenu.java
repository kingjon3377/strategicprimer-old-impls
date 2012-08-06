package view.module;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import model.main.Game;
import model.module.Advance;
import model.player.Player;

/**
 * A menu to let the player build new modules.
 * 
 * @author Jonathan Lovelace
 */
public final class BuildOptionsMenu extends JMenu implements ActionListener {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = -3727145582043237559L;
	/**
	 * Singleton. FIXME: Should this really be singleton? Or should it be
	 * per-player? Or what?
	 */
	public static final BuildOptionsMenu MENU = new BuildOptionsMenu();

	/**
	 * Constructor.
	 */
	private BuildOptionsMenu() {
		super("Build ...");
	}

	/**
	 * A mapping from advance strings to advances.
	 */
	private final Map<String, Advance> advMap = new HashMap<String, Advance>();

	/**
	 * Refresh the menu.
	 */
	public void refreshMenu() {
		removeAll();
		advMap.clear();
		for (Advance adv : ((Player) Game.getGame().getPlayer(0)).getAdvances()) {
			addMenuItem(new JMenuItem(adv.toString())); // NOPMD
			advMap.put(adv.toString(), adv);
		}
	}

	/**
	 * Add a menu item and set this as its listener.
	 * 
	 * @param item
	 *            the item to add
	 */
	private void addMenuItem(final JMenuItem item) {
		add(item);
		item.addActionListener(this);
	}

	@Override
	public void actionPerformed(final ActionEvent event) {
		if (advMap.containsKey(event.getActionCommand())) {
			// build the selected item
		}
	}

}
