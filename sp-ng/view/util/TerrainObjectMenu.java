package view.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JMenu;

import model.map.TerrainObject;
/**
 * A menu to allow an admin to change what terrain object is on a tile.
 * @author Jonathan Lovelace.
 *
 */
public class TerrainObjectMenu extends JMenu implements ActionListener {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = -3987751243896123414L;
	/**
	 * The listener we should inform about tile type changes.
	 */
	private final PropertyChangeListener listener;

	/**
	 * Constructor.
	 * 
	 * @param list
	 *            the listener we'll inform about terrain object changes.
	 */
	public TerrainObjectMenu(final PropertyChangeListener list) {
		super("Change Terrain Object");
		listener = list;
		for (TerrainObject type : TerrainObject.values()) {
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
			listener.propertyChange(new PropertyChangeEvent(this, "terr_obj",
					null, TerrainObject.valueOf(event.getActionCommand())));
		}
	}
}
