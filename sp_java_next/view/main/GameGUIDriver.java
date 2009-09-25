package view.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import model.main.Game;
import view.map.GUIMap;
import view.map.MiniMap;
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
	 * The window's menu. TODO: Should this be made its own class, hiding the
	 * initialization details?
	 */
	private transient JMenuBar menu;

	/**
	 * The main map
	 */
	private transient GUIMap map;

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
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setIgnoreRepaint(false);
		this.setPreferredSize(new Dimension(640,480));
		this.setMaximumSize(new Dimension(800,600));
		map = new GUIMap(Game.getGame().getMap());
		add(map, BorderLayout.CENTER);
		menu = new JMenuBar();
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
}
