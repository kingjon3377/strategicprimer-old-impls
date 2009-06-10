package sp.view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import sp.model.Game;

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
public final class ModePanel extends JPanel implements ActionListener {

	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = 1126429633783095907L;
	/**
	 * The singleton object.
	 */
	private static ModePanel theModePanel;

	// ESCA-JAVA0143:
	/**
	 * ModePanel is singleton.
	 * 
	 * @return the singleton object.
	 */
	public static ModePanel getModePanel() {
		synchronized (ModePanel.class) {
			if (theModePanel == null) {
				theModePanel = new ModePanel();
			}
		}
		return theModePanel;
	}

	/**
	 * Add a button to the panel (so we don't have to give the button a name in
	 * the constructor).
	 * 
	 * @param button
	 *            The button to add
	 * @param toolTip
	 *            Its tooltip
	 */
	private void addButton(final JButton button, final String toolTip) {
		add(button);
		button.addActionListener(this);
		button.setToolTipText(toolTip);
	}

	/**
	 * A straightforward constructor. The ActionListener should be the main
	 * GUIDriver, to allow the map to be updated when the mode changes without
	 * this panel holding a reference to the map panel.
	 */
	private ModePanel() {
		super(new FlowLayout());
		addButton(new JButton("No Mode"), "Use to get out of the current mode.");

		addButton(new JButton("Attack"),
				"In Attack mode, click on a unit or building to select it, then "
						+ "click an adjacent target.");

		addButton(new JButton("Move"),
				"In Move mode, click on a unit to select it, then click a destination"
						+ " tile within its movement range.");

		addButton(new JButton("Ranged Attack"),
				"In Ranged Attack mode, click on a unit or building to select it,"
						+ " then click on a target anywhere on the map.");

		addButton(new JButton("End Turn"), "End your turn.");

		addButton(new JButton("Info"),
				"In Info mode, click any tile to view information about it and the"
						+ " unit or building, if any, on it.");

		addButton(
				new JButton("Build"),
				"In Build mode, click an unoccupied tile, then click a button on"
						+ "the panel of building choices to the right to select what to build.");

		final JButton quitButton = new JButton("Quit"); // NOPMD by kingjon on
		// 5/19/08 12:37 PM
		add(quitButton);
		quitButton.addActionListener(this);
		quitButton.setToolTipText("Quit the game.");
	}

	/**
	 * Handle button presses.
	 * @param event The event we're handling.
	 */
	@Override
	public void actionPerformed(final ActionEvent event) {
		if (event.getActionCommand().equals("Quit")) {
			Game.quit(0); // NOPMD by kingjon on 5/19/08 12:38 PM
		} else if (event.getActionCommand().equals("No Mode")) {
			Game.getGame().setMode(Game.Mode.NO_MODE);
		} else if (event.getActionCommand().equals("Attack")) {
			Game.getGame().setMode(Game.Mode.ATTACK_MODE);
		} else if (event.getActionCommand().equals("Move")) {
			Game.getGame().setMode(Game.Mode.MOVE_MODE);
		} else if (event.getActionCommand().equals("Ranged Attack")) {
			Game.getGame().setMode(Game.Mode.RANGED_ATTACK_MODE);
		} else if (event.getActionCommand().equals("End Turn")) {
			Game.getGame().endTurn();
		} else if (event.getActionCommand().equals("Info")) {
			Game.getGame().setMode(Game.Mode.INFO_MODE);
		} else if (event.getActionCommand().equals("Build")) {
			Game.getGame().setMode(Game.Mode.BUILD_MODE);
		}
		LabelPanel.getLabelPanel().repaint();
	}
}
