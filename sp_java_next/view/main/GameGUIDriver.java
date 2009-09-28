package view.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import model.main.Game;
import view.map.GUIMap;
import view.map.MiniMap;
import view.map.TerrainTypeMenu;
import view.module.CurrentModulePanel;
import view.module.InitiativeQueue;

/**
 * @author Jonathan Lovelace
 * 
 */
public class GameGUIDriver extends JFrame {

	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = 7140529521567754564L;
	/**
	 * Logger
	 */
	private static final Logger LOGGER = Logger.getLogger(GameGUIDriver.class.getName());
	/**
	 * The main window
	 */
	private static GameGUIDriver gui;

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		if (args.length > 0) {
			parseArgs(args);
		}
		gui = new GameGUIDriver();
		gui.run();
	}

	/**
	 * The panel showing the initiative queue.
	 */
	private transient InitiativeQueue initQueue;
	/**
	 * The panel showing the current module.
	 */
	private transient CurrentModulePanel modulePanel;

	/**
	 * The mini-map
	 */
	private transient MiniMap miniMap;
	
	/**
	 * Constructor.
	 */
	public GameGUIDriver() {
		super();
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIgnoreRepaint(false);
		setPreferredSize(new Dimension(640,480));
		setMaximumSize(new Dimension(800,600));
		/**
		 * The main map
		 */
		final GUIMap map = new GUIMap(Game.getGame().getMap());
		add(new JScrollPane(map), BorderLayout.CENTER);
		/**
		 * The window's menu. TODO: Should this be made its own class, hiding the
		 * initialization details?
		 */
		final JMenuBar menu = new JMenuBar();
		menu.add(createFileMenu());
		if (map.isAdmin()) {
			menu.add(TerrainTypeMenu.MENU);
		}
		add(menu, BorderLayout.NORTH);
		initQueue = new InitiativeQueue();
		add(initQueue, BorderLayout.WEST);
		modulePanel = new CurrentModulePanel();
		add(modulePanel, BorderLayout.EAST);
		miniMap = new MiniMap();
		add(miniMap, BorderLayout.SOUTH);
		pack();
		repaint();
	}

	/**
	 * The main loop.
	 */
	private void run() {
		// TODO Auto-generated method stub
		setVisible(true);
	}
	/**
	 * Parse CLI arguments
	 * @param args The arguments
	 */
	private static void parseArgs(final String[] args) {
		if (args.length > 1) {
			LOGGER.severe("Can't handle more than one argument yet");
		}
		Game.getGame().createMap(args[0]);
	}
	/**
	 * @return The File menu to add to the menu bar.
	 */
	private JMenu createFileMenu() {
		MenuListener.MENU_LISTENER.setDriver(this);
		final JMenu fileMenu = new JMenu("File");
		final JMenuItem openItem = new JMenuItem("Open");
		openItem.addActionListener(MenuListener.MENU_LISTENER);
		fileMenu.add(openItem);
		final JMenuItem saveItem = new JMenuItem("Save");
		saveItem.addActionListener(MenuListener.MENU_LISTENER);
		fileMenu.add(saveItem);
		final JMenuItem quitItem = new JMenuItem("Quit");
		quitItem.addActionListener(MenuListener.MENU_LISTENER);
		fileMenu.add(quitItem);
		return fileMenu;
	}
	/**
	 * Quits the program.
	 */
	public void quit() {
		dispose();
	}
}
