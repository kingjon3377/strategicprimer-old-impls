package finalproject;

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
public class GameGUIDriver extends JFrame implements ActionListener, 
		ComponentListener {
	/**
	 * A logger to replace System.out calls.
	 */
	private static final Logger LOGGER = Logger.getLogger(GameGUIDriver.class
			.getName());
	/**
	 * The amount of resources each player starts with
	 */
	private static final int STARTING_RESOURCE = 6;
	/**
	 * How big each tile is in pixels
	 */
	private static final int TILE_SIZE_PIXELS = 200;
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = -8142817362548925853L;
	/**
	 * The map panel. FIXME: Should be singleton
	 */
	private final MapPanel mapPanel;
	/**
	 * Refresh everything on all user actions.
	 * 
	 * @param event
	 *            The event; ignored.
	 */
    @Override
	public void actionPerformed(final ActionEvent event) {
		LabelPanel.getPanel().repaint();
		mapPanel.repaint();
	}


	/**
	 * GUI constructor. The "throws ..." construct is because functions it calls
	 * throw exceptions, and the compiler objects if I let them stand ...
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
		

		// Creating map
		Game.getGame(SPMapParser.createBoard(getClass().getResource("board.txt"), getClass()
				.getResource("resources.txt")), new ArrayList<SimpleModule>(), players);

		// Setting up mapPanel
		setLayout(new BorderLayout());
		mapPanel = new MapPanel(Game.getGame().getMap().getSizeX(), Game.getGame()
				.getMap().getSizeY(), new TerrainIconFilenames(SPMap.MAX_TERRAIN_TYPE,
					"../../../../Images/final/",".png"));

		mainPanel.add(LabelPanel.getPanel(), BorderLayout.SOUTH);

		// Setting up modePanel
        ModePanel.setupModePanel(new ModePanel(), mainPanel, PackComponentListener.LISTENER);
		// Setting up inter-panel communication to allow updating labelPanel
		mainPanel.add(mapPanel, BorderLayout.CENTER);
		setPreferredSize(new Dimension(Game.getGame().getMap().getSizeX()
				* TILE_SIZE_PIXELS, Game.getGame().getMap().getSizeY() * TILE_SIZE_PIXELS));
		mapPanel.addComponentListener(PackComponentListener.LISTENER);
		add(mainPanel, BorderLayout.WEST);

	/**
	 * The build panel
	 */
		final JPanel buildPanel = new BuildPanel(this); // NOPMD
		add(buildPanel, BorderLayout.EAST);
		buildPanel.addComponentListener(PackComponentListener.LISTENER);

		setTitle("Prototype of Strategic Primer/Yudexen");
		setUpTestUnits();
		mapPanel.repaint();
		pack();
	}
	/**
	 * The entry point for the program.
	 * 
	 * @param args
	 *            Command-line arguments.
	 */
	public static void main(final String[] args) {
		try {
			new GameGUIDriver(2).setVisible(true);
		} catch (FileNotFoundException e) {
			LOGGER.log(Level.SEVERE,"File not found error initializing GUI.",e);
			System.exit(1);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE,"I/O error initializing GUI.",e);
			System.exit(2);
		}
	}

	/**
	 * Function to set some units for the start of the game -- to not clutter up
	 * the constructor, increasing readability and making changing this easier.
	 * 
	 */
	private static void setUpTestUnits() {
		Game.getGame().setPlayerResources(STARTING_RESOURCE, 1);
		BuildPanel.buildUnit("Phalanx");
		BuildPanel.buildUnit("Cavalry");
		Game.getGame().endTurn();
		Game.getGame().setPlayerResources(STARTING_RESOURCE, 2);
		BuildPanel.buildUnit("Phalanx");
		BuildPanel.buildUnit("Cavalry");
		Game.getGame().endTurn();
		Game.getGame().setPlayerResources(0, 1);
		Game.getGame().setPlayerResources(0, 2);
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
