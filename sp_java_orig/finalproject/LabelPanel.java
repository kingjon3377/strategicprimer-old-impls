package finalproject;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * An encapsulation of the helpful informational Labels at the bottom of the
 * GUI. It has four labels: one presents information about the current tile,
 * another information about the current unit, another information about the
 * current mode, and another information about the current player. Besides these
 * labels, it also has a reference to the Game object to refer to to get the
 * information it represents.
 * 
 * @author Jonathan Lovelace
 * @assignment Final Project
 * @course CS108A
 * @semester FA06
 * 
 */
public final class LabelPanel extends JPanel {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = 2808827787235226010L;
	/**
	 * A label to describe the terrain of a tile
	 */
	private final transient JLabel terrainLabel;
	/**
	 * A label to describe a unit
	 */
	private final transient JLabel unitLabel;
	/**
	 * A label to describe the current mode
	 */
	private final transient JLabel modeLabel;
	/**
	 * A label to show whose turn it is
	 */
	private final transient JLabel playerLabel;
	/**
	 * The singleton object
	 */
	private static LabelPanel panel;

	/**
	 * @return the LabelPanel
	 */
	public static LabelPanel getPanel() {
		synchronized (LabelPanel.class) {
			if (panel == null) {
				panel = new LabelPanel();
			}
		}
		return panel;
	}

	/**
	 * Constructor: Set up the labels (with values that should never appear) and
	 * initialize my reference to the game.
	 */
	private LabelPanel() {
		super(new GridLayout(0, 1));
		terrainLabel = new JLabel("Terrain information for current tile");
		add(terrainLabel);
		unitLabel = new JLabel("Information about the current unit");
		add(unitLabel);
		modeLabel = new JLabel("Information about the mode");
		add(modeLabel);
		playerLabel = new JLabel(
				"Information about the player whose turn it is");
		add(playerLabel);
	}

	/**
	 * Updates all the labels. This is lengthy mainly because of the length of
	 * the tooltip strings for the Mode label.
	 * 
	 */
	public void updateLabels() {
		switch (Game.getGame().getMode()) {
		case Game.ATTACK_MODE:
			modeLabel.setText("Attack mode");
			modeLabel
					.setToolTipText("Your first click on the map will be interpreted "
							+ "as the attacker, and your second click as the target. "
							+ "A unit can only attack (with this or Ranged Attack) once per turn, "
							+ "and can only attack an adjacent tile with this.");
			break;
		case Game.INFO_MODE:
			modeLabel.setText("Info mode");
			modeLabel
					.setToolTipText("When you click on a tile information about that tile"
							+ " and any units on it will be displayed, and the mode will "
							+ "changed back to no mode on your second click.");
			break;
		case Game.MOVE_MODE:
			modeLabel.setText("Move mode");
			modeLabel
					.setToolTipText("Your first click on the map will be interpreted as the"
							+ " starting tile for movement, and your second click as the"
							+ " destination. A unit can only move once per turn.");
			break;
		case Game.NO_MODE:
			modeLabel.setText("No mode");
			modeLabel
					.setToolTipText("Your clicks on the map will be ignored. Click on one of the "
							+ "buttons in the Mode window to select a mode.");
			break;
		case Game.RANGED_ATK_MODE:
			modeLabel.setText("Ranged Attack mode");
			modeLabel
					.setToolTipText("Your first click on the map will be interpreted as the"
							+ "attacker, and your second click as the target. A unit can only "
							+ "attack (with this or simple Attack) once per turn, but this can hit"
							+ "anywhere on the map. To compensate, it is not a guaranteed hit.");
			break;
		case Game.BUILD_MODE:
			modeLabel.setText("Build Mode");
			modeLabel
					.setToolTipText("Your clicks on the map will be interpreted as the tile on "
							+ "which to build; after selecting a tile, click a button at the right"
							+ " to select something to build.");
			break;
		default:
			throw new IllegalStateException("Unknown mode");
		}
		unitLabel
				.setText((Game.getGame().getTile1() == null)
						|| (Game.getGame().getTile1().getModuleOnTile() == null) ? "No unit selected"
						: Game.getGame().getTile1().getModuleOnTile()
								.toString());
		if (Game.getGame().getTile1() == null) {
			terrainLabel.setText("No tile selected");
		} else {
			terrainLabel.setText(Game.getGame().getTile1().toString());
		}
		playerLabel.setText("Player #" + Game.getGame().getPlayer()
				+ "'s turn, with "
				+ Game.getGame().getPlayerResources(Game.getGame().getPlayer())
				+ " resource units");

	}

	/**
	 * Update the labels on every repaint.
	 */
	@Override
	public void repaint() {
		updateLabels();
		super.repaint();
	}
}
