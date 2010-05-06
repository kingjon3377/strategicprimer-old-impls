package view.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JMenu;

import model.map.TileType;

/**
 * A menu to allow an admin to change a tile's type.
 * 
 * @author Jonathan Lovelace
 */
public class TileTypeMenu extends JMenu implements ActionListener {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = 7257588955224964170L;
	/**
	 * The listener we should inform about tile type changes.
	 */
	private final PropertyChangeListener listener;

	/**
	 * Constructor.
	 * 
	 * @param list
	 *            the listener we'll inform about tile type changes.
	 */
	public TileTypeMenu(final PropertyChangeListener list) {
		super("Change Tile Type");
		listener = list;
		for (TileType type : TileType.values()) {
			add(type.toString()).addActionListener(this);
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
			listener.propertyChange(new PropertyChangeEvent(this, "tiletype",
					null, TileType.valueOf(event.getActionCommand())));
		}
	}
}
