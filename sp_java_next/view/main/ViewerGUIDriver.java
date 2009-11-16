package view.main;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import model.main.Game;
import view.map.GUIMap;
import view.map.SelectionListener;

/**
 * A hopefully stripped-down driver that allows the user to view the map but not
 * to make changes, but which will I hope be more responsive in case of the full
 * 6000+ tile map.
 * 
 * @author Jonathan Lovelace
 * 
 */
public final class ViewerGUIDriver extends JFrame {

	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = 745190484074492372L;
	/**
	 * The main window.
	 */
	private static final ViewerGUIDriver FRAME = new ViewerGUIDriver();
	/**
	 * @param args Command-line arguments
	 */
	public static void main(final String[] args) {
		if (args.length > 0) {
			parseArgs(args);
		}
		FRAME.pack();
		FRAME.setVisible(true);
		FRAME.repaint();
	}
	/**
	 * Constructor.
	 */
	private ViewerGUIDriver() {
		super();
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIgnoreRepaint(false);
	}
	/**
	 * Parse command-line arguments.
	 * @param args the arguments
	 */
	private static void parseArgs(final String[] args) {
		Game.getGame().createMap(args[0]);
		final GUIMap map = new GUIMap(Game.getGame().getMap());
		map.setSelectionListener(new SelectionListener(map, null));
		FRAME.add(new JScrollPane(map));
	}
}
