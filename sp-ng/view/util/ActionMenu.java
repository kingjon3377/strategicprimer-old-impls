package view.util;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EnumSet;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import model.unit.UnitAction;

/**
 * A menu that lets the player instruct a unit to take an action.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class ActionMenu extends JMenu implements ActionListener {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = -3635754495270828079L;
	/**
	 * The listener we should inform about tile type changes.
	 */
	private final PropertyChangeListener listener;
	/**
	 * The current action.
	 */
	private UnitAction action = UnitAction.Cancel;

	/**
	 * Constructor.
	 * 
	 * @param list
	 *            the listener we'll inform about terrain object changes.
	 */
	public ActionMenu(final PropertyChangeListener list) {
		super("Action");
		listener = list;
		for (UnitAction act : UnitAction.values()) {
			add(act.toString()).addActionListener(this);
		}
	}

	/**
	 * Handle menu item clicks.
	 * 
	 * @param event
	 *            the event we're handling
	 */
	@Override
	public void actionPerformed(final ActionEvent event) {
		if (listener != null) {
			listener.propertyChange(new PropertyChangeEvent(this, "action",
					action, UnitAction.valueOf(event.getActionCommand())));
			action = UnitAction.valueOf(event.getActionCommand());
		}
	}

	/**
	 * Show only those menu items in a specified set.
	 * @param validItems the set of valid items
	 */
	public void hideInvalidItems(final EnumSet<UnitAction> validItems) {
		for (Component com : getMenuComponents()) {
			if (com instanceof JMenuItem) {
				if ("Cancel".equals(((JMenuItem) com).getText())
						|| validItems.contains(UnitAction
								.valueOf(((JMenuItem) com).getText()))) {
					com.setEnabled(true);
				} else {
					com.setEnabled(false);
				}
			}
		}
	}
}
