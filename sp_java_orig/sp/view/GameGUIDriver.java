package sp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;

import sp.model.Game;
import sp.model.Module;
import sp.model.SPMap;
import sp.model.SPMapParser;

/**
 * Strategic Primer/Yudexen (the first is my name for the game this will
 * hopefully eventually become, which I began designing but had never created a
 * successful even partial implementation of until now; the second is the name
 * of the game into which I put quite a bit of effort in the Spring 2006
 * semester in CSX, and of which I am now the maintainer due to lack of
 * constinued interest from the rest of CSX -- consider it a fork of Strategic
 * Primer's design) is a strategy game (turn-based for now; likely RTS at some
 * time in the future). This project is a prototype of some parts of it.
 * 
 * Since this is a prototype, it has no win condition. (The final game, many
 * years from now, may.) I need to implement some way of saving and loading
 * games sometime soon -- to help with testing if nothing else.
 * 
 * Main driver -- the GUI or view for the game. Most of the view is encapsulated
 * into several separate panels. The model is found in the game object, which
 * needs the terrainIconFilenames (URL objects rather than Strings in an attempt
 * to make them work even in JARs).
 * 
 * @author Jonathan Lovelace
 * @assignment Final Project
 * @course CS108A
 * @semester FA06
 * 
 */
public final class GameGUIDriver extends JFrame implements ActionListener,
		ComponentListener {
	/**
	 * A version UID for serialization
	 */
	private static final long serialVersionUID = -3834560348481170591L;
	/**
	 * The size of a graphical tile
	 */
	private static final int TILE_SIZE = 200;
	/**
	 * How much of the resource each player starts with
	 */
	private static final int STARTING_RSR = 6;
	/**
	 * A logger to replace System.out calls.
	 */
	private static final Logger LOGGER = Logger.getLogger(GameGUIDriver.class
			.getName());
	/**
	 * The panel that displays the map. FIXME: Should be singleton
	 */
	private transient MapPanel mapPanel;

	/**
	 * Maximum window size -- extracted "magic number".
	 */
	private static final int WINDOW_MAX_SIZE_Y = 15;
	/**
	 * Maximum window size -- extracted "magic number".
	 */
	private static final int WINDOW_MAX_SIZE_X = 15;


	/**
	 * Refresh everything on all user actions.
	 * 
	 * @param event
	 *            The event; ignored.
	 */
	@Override
	public void actionPerformed(final ActionEvent event) {
		LabelPanel.getLabelPanel().repaint();
		mapPanel.repaint();
	}

	/**
	 * Start point of the program.
	 * 
	 * @param args
	 *            Command-line arguments.
	 */
	public static void main(final String[] args) {
		try {
			new GameGUIDriver(2).setVisible(true);
		} catch (final FileNotFoundException e) {
			LOGGER
					.log(Level.SEVERE, "File not found error initializing GUI",
							e);
			Game.quit(1);
		} catch (final IOException e) {
			LOGGER.log(Level.SEVERE, "I/O error initializing GUI", e);
			Game.quit(2);
		}
	}

	/**
	 * Function to set some units for the start of the game -- to not clutter up
	 * the constructor, increasing readability and making changing this easier.
	 */
	private static void setUpTestUnits() {
		for (int i = 1; i < 3; i++) {
			Game.getGame().setPlayerResources(STARTING_RSR, i);
			BuildPanel.buildUnit("Phalanx");
			BuildPanel.buildUnit("Cavalry");
			Game.getGame().endTurn();
		}
		Game.getGame().setPlayerResources(0, 1);
		Game.getGame().setPlayerResources(0, 2);
	}

	/**
	 * GUI constructor. The "throws ..." construct is because functions it calls
	 * throw exceptions, and the compiler objects if I let them stand ...
	 * 
	 * TODO: Make this code somewhat more readable. As it stands, I'm not sure
	 * what panels are encapsulated in what other panels.
	 * 
	 * @param players
	 *            The number of players
	 * @throws IOException
	 *             on I/O errors
	 */
	public GameGUIDriver(final int players) throws IOException {
		super();
		/**
		 * The main panel.
		 */
		final JPanel mainPanel = new JPanel(new BorderLayout()); // NOPMD
		final TerrainIconFilenames terrainIconFiles = new TerrainIconFilenames(
				SPMap.MAX_TERRAIN_TYPE, "", ".png");
		// Creating map
		Game.getGame(SPMapParser.createBoard(getClass()
				.getResource("board.txt"), getClass().getResource(
				"resources.txt")), new ArrayList<Module>(), players);

		// Setting up the map panel
		setLayout(new BorderLayout());
		if ((Game.getGame().getMap().getSizeX() > WINDOW_MAX_SIZE_X)
				|| (Game.getGame().getMap().getSizeY() > WINDOW_MAX_SIZE_Y)) {
			mapPanel = new MapPanel(Game.getGame().getMap().getSizeX(), Game
					.getGame().getMap().getSizeY(), terrainIconFiles,
					WINDOW_MAX_SIZE_X, WINDOW_MAX_SIZE_Y);
		} else {
			mapPanel = new MapPanel(Game.getGame().getMap().getSizeX(), Game
					.getGame().getMap().getSizeY(), terrainIconFiles);
		}

		mainPanel.add(LabelPanel.getLabelPanel(), BorderLayout.SOUTH);

		mainPanel.add(ModePanel.getModePanel(), BorderLayout.NORTH);

		mainPanel.add(mapPanel, BorderLayout.CENTER);
		setPreferredSize(new Dimension(Game.getGame().getMap().getSizeX()
				* TILE_SIZE, Game.getGame().getMap().getSizeY()
				* TILE_SIZE));
		mapPanel.addComponentListener(PackComponentListener.LISTENER);
		ModePanel.getModePanel().addComponentListener(this);
		add(mainPanel, BorderLayout.WEST);

		add(BuildPanel.getBuildPanel(this), BorderLayout.EAST);
		BuildPanel.getBuildPanel(this).addComponentListener(PackComponentListener.LISTENER);

		setTitle("Prototype of Strategic Primer/Yudexen");
		setUpTestUnits();
		this.addWindowListener(new GameWindowListener(this));
		mapPanel.repaint();
		pack();
	}

	/**
	 * Repack when a component is hidden
	 * 
	 * @param arg0
	 *            ignored
	 */
	@Override
	public void componentHidden(final ComponentEvent arg0) {
		pack();
	}

	/**
	 * Repaint when a component is moved.
	 * 
	 * @param arg0
	 *            ignored
	 */
	@Override
	public void componentMoved(final ComponentEvent arg0) {
		repaint();
	}

	/**
	 * Repaint when a component is resized
	 * 
	 * @param arg0
	 *            ignored
	 */
	@Override
	public void componentResized(final ComponentEvent arg0) {
		repaint();
	}

	/**
	 * Repack when a component is shown
	 * 
	 * @param arg0
	 *            ignored
	 */
	@Override
	public void componentShown(final ComponentEvent arg0) {
		pack();
	}

}
