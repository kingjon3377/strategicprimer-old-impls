package view.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import model.location.Location;
import model.module.Module;

/**
 * A menu to allow the user to select which module on a tile to apply future
 * commands to.
 * 
 * FIXME: This utterly breaks model-view separation
 * 
 * @author Jonathan Lovelace
 * 
 */
public final class TopModuleMenu extends JMenu {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = 6442417093578550296L;
	/**
	 * Singleton.
	 */
	public static final TopModuleMenu TOP_MODULE_MENU = new TopModuleMenu();

	/**
	 * Singleton constructor.
	 */
	private TopModuleMenu() {
		super("Select top module");
	}

	/**
	 * Change the menu to a new location's modules.
	 * 
	 * @param loc
	 *            the location in question
	 */
	public void initialize(final Location loc) {
		removeAll();
		for (final Module mod : loc.getModules()) {
			add(createMenuItem(loc, mod));
		}
	}

	/**
	 * Create a menu item that, when clicked, selects the module in the
	 * location.
	 * 
	 * @param loc
	 *            a location
	 * @param mod
	 *            a module in that location
	 * @return the menu item
	 */
	private static JMenuItem createMenuItem(final Location loc, final Module mod) {
		final JMenuItem item = new JMenuItem(mod.getName());
		item.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent evt) {
				loc.select(mod);
			}
		});
		return item;
	}
}
