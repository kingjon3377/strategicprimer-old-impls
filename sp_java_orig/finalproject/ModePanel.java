package finalproject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.ComponentListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This panel encapsulates the buttons that are used to change the mode. It has
 * the buttons and references to the Game object and the panel of labels (so the
 * labels can be updated when the mode changes). Clicking one of the buttons
 * simply sets the mode to that mode.
 * 
 * @author Jonathan Lovelace
 * @assignment Final Project
 * @course CS108A
 * @semester FA06
 * 
 */
public class ModePanel extends JPanel implements ActionListener {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = -7145161972961395626L;
	/**
	 * A button for the none-of-the-above mode
	 */
	private final JButton noModeButton; // NOPMD
	/**
	 * A button for attack mode
	 */
	private final JButton attackButton; // NOPMD
	/**
	 * A button for move mode
	 */
	private final JButton moveButton; // NOPMD
	/**
	 * A button for ranged attack mode
	 */
	private final JButton rangedAtkButton; // NOPMD
	/**
	 * A button to end a player's turn
	 */
	private final JButton endTurnButton; // NOPMD
	/**
	 * A button to quit the game
	 */
	private final JButton quitButton; // NOPMD
	/**
	 * A button for info mode.
	 */
	private final JButton infoButton; // NOPMD 
	/**
	 * A button for build mode
	 */
	private final JButton buildButton; // NOPMD

	/**
	 * A straightforward constructor. 
	 */
	public ModePanel() {
		super(new FlowLayout());
		noModeButton = new JButton("No Mode");
		add(noModeButton);
		noModeButton.addActionListener(this);
		noModeButton.setToolTipText("Use to get out of the current mode.");

		attackButton = new JButton("Attack");
		add(attackButton);
		attackButton.addActionListener(this);
		attackButton
				.setToolTipText("In Attack mode, click on a unit or building to select it, then "
						+ "click an adjacent target.");

		moveButton = new JButton("Move");
		add(moveButton);
		moveButton.addActionListener(this);
		moveButton
				.setToolTipText("In Move mode, click on a unit to select it, then click a destination"
						+ " tile within its movement range.");

		rangedAtkButton = new JButton("Ranged Attack");
		add(rangedAtkButton);
		rangedAtkButton.addActionListener(this);
		rangedAtkButton
				.setToolTipText("In Ranged Attack mode, click on a unit or building to select it,"
						+ " then click on a target anywhere on the map.");

		endTurnButton = new JButton("End Turn");
		add(endTurnButton);
		endTurnButton.addActionListener(this);
		endTurnButton.setToolTipText("End your turn.");

		infoButton = new JButton("Info");
		add(infoButton);
		infoButton.addActionListener(this);
		infoButton
				.setToolTipText("In Info mode, click any tile to view information about it and the"
						+ " unit or building, if any, on it.");

		buildButton = new JButton("Build");
		add(buildButton);
		buildButton.addActionListener(this);
		buildButton
				.setToolTipText("In Build mode, click an unoccupied tile, then click a button on"
						+ "the panel of building choices to the right to select what to build.");

		quitButton = new JButton("Quit");
		add(quitButton);
		quitButton.addActionListener(this);
		quitButton.setToolTipText("Quit the game.");
	}
	/**
	 * Set up the mode panel
	 * @param mpan The mode panel
	 * @param panel The panel to add it to.
	 * @param list
	 */
    public static void setupModePanel(final ModePanel mpan, final JPanel panel, final ComponentListener list) {
        panel.add(mpan, BorderLayout.NORTH);
        mpan.addComponentListener(list);
    }

	/**
	 * Handle button presses.
	 * @param event The event we're handling.
	 */
	@Override
	public void actionPerformed(final ActionEvent event) {
		if (event.getActionCommand().equals("Quit")) {
			System.exit(0);
		} else if (event.getActionCommand().equals("No Mode")) {
			Game.getGame().setMode(Game.NO_MODE);
		} else if (event.getActionCommand().equals("Attack")) {
			Game.getGame().setMode(Game.ATTACK_MODE);
		} else if (event.getActionCommand().equals("Move")) {
			Game.getGame().setMode(Game.MOVE_MODE);
		} else if (event.getActionCommand().equals("Ranged Attack")) {
			Game.getGame().setMode(Game.RANGED_ATK_MODE);
		} else if (event.getActionCommand().equals("End Turn")) {
			Game.getGame().endTurn();
		} else if (event.getActionCommand().equals("Info")) {
			Game.getGame().setMode(Game.INFO_MODE);
		} else if (event.getActionCommand().equals("Build")) {
			Game.getGame().setMode(Game.BUILD_MODE);
		}
		LabelPanel.getPanel().repaint();
	}
}
