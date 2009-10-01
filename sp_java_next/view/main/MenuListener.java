package view.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;

import model.main.Game;
import model.main.MapXMLWriter;
/**
 * A class to handle menu events
 * @author Jonathan Lovelace
 */
public final class MenuListener implements ActionListener, Serializable {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = -3958202858414521914L;
	/**
	 * The singleton object
	 */
	public static final MenuListener MENU_LISTENER = new MenuListener();
	/**
	 * The file-chooser dialog
	 */
	private static final JFileChooser FILE_CHOOSER = new JFileChooser();
	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(MenuListener.class.getName());
	/**
	 * This is singleton
	 */
	private MenuListener() {
		// Nothing as of yet
	}
	/**
	 * Our reference to the main GUI
	 */
	private transient GameGUIDriver driver;
	/**
	 * Sets our reference to the GUI
	 * @param _driver The main GUI
	 */
	public void setDriver(final GameGUIDriver _driver) {
		driver = _driver;
	}
	/**
	 * Handle events.
	 * @param event the event to handle
	 */
	@Override
	public void actionPerformed(final ActionEvent event) {
		if (event.getActionCommand().equals("Open")) {
			if (FILE_CHOOSER.showOpenDialog(driver) == JFileChooser.APPROVE_OPTION) {
				Game.getGame().createMap(FILE_CHOOSER.getSelectedFile().getPath());
				driver.reloadMap();
	        }
		} else if (event.getActionCommand().equals("Save")) {
			if (FILE_CHOOSER.showSaveDialog(driver) == JFileChooser.APPROVE_OPTION) {
				try {
					MapXMLWriter.writeGameToXML(FILE_CHOOSER.getSelectedFile().getPath());
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE,"I/O error when trying to write map to XML file",e);
				}
			}
		} else if (event.getActionCommand().equals("Quit")) {
			driver.quit();
		}
	}

}
