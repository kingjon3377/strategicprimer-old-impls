package view.main;

import java.awt.BorderLayout;

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
	 * The main window
	 */
	private static GameGUIDriver gui;

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
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
		constructor();
	}

	/**
	 * 
	 */
	private void constructor() {
//		mainPanel = new JPanel();
//		mainPanel.setLayout(new BorderLayout());
		setLayout(new BorderLayout());
		map = new GUIMap(Game.getGame().getMap().getSizeRows(), Game.getGame()
				.getMap().getSizeCols());
		add(map, BorderLayout.CENTER);
		menu = new JMenuBar();
		add(menu, BorderLayout.NORTH);
		initQueue = new InitiativeQueue();
		add(initQueue, BorderLayout.WEST);
		modulePanel = new CurrentModulePanel();
		add(modulePanel, BorderLayout.EAST);
		miniMap = new MiniMap();
		add(miniMap, BorderLayout.SOUTH);
	}

//	/**
//	 * @param inStream
//	 *            The stream
//	 * @throws IOException
//	 *             When caught
//	 * @throws ClassNotFoundException
//	 *             When caught
//	 */
//	private void readObject(final ObjectInputStream inStream)
//			throws IOException, ClassNotFoundException {
//		inStream.defaultReadObject();
//		constructor();
//	}

	/**
	 * The main loop.
	 */
	private void run() {
		// TODO Auto-generated method stub
		menu.getAutoscrolls();
		map.getActionMap();
		modulePanel.getActionMap();
		miniMap.getActionMap();
		initQueue.getActionMap();
	}

//	/**
//	 * @param out
//	 *            the stream
//	 * @throws IOException
//	 */
//	private void writeObject(final ObjectOutputStream out) throws IOException {
//		out.defaultWriteObject();
//	}
}
