package view.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import controller.util.MapWriter;

/**
 * The file menu
 * 
 * @author Jonathan Lovelace
 */
public class FileMenu extends JMenu implements ActionListener {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = -2361256792090288882L;
	/**
	 * The component we give a loaded map and we make the parent of the
	 * file-chooser dialog. FIXME: This should be done by something in the
	 * controller component, with the GUI driver only getting called as the
	 * source for the file selection dialog.
	 */
	final MapPanel map;
	/**
	 * The file-chooser dialog.
	 */
	private static final JFileChooser FILE_CHOOSER = new JFileChooser();

	/**
	 * Constructor.
	 * 
	 * @param mpanel
	 *            what we call on to actually implement saving and loading
	 */
	public FileMenu(final MapPanel mpanel) {
		super("File");
		map = mpanel;
		addMenuItem("Save map");
		addMenuItem("Load map");
		// add("Quit");
	}
	/**
	 * Add a menu item and set this as its ActionListener.
	 * @param text the text of the menu item
	 */
	private void addMenuItem(final String text) {
		final JMenuItem item = new JMenuItem(text);
		item.addActionListener(this);
		add(item);
	}
	/**
	 * Handle menu items.
	 * 
	 * @param evt
	 *            the event we're handling
	 */
	@Override
	public void actionPerformed(final ActionEvent evt) {
		if ("Save map".equals(evt.getActionCommand())) {
			if (FILE_CHOOSER.showSaveDialog(map) == JFileChooser.APPROVE_OPTION) {
				try {
					new MapWriter().writeMap(FILE_CHOOSER.getSelectedFile()
							.getPath(), map.getMap());
				} catch (IOException e) {
					Logger.getLogger(FileMenu.class.getName()).log(
							Level.SEVERE, "I/O error while saving the map", e);
				}
			}
		} else if ("Load map".equals(evt.getActionCommand())) {
			if (FILE_CHOOSER.showOpenDialog(map) == JFileChooser.APPROVE_OPTION) {
				// map.load(FILE_CHOOSER.getSelectedFile().getPath());
			}
		}
	}
}
