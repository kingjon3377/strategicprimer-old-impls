package sp.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import sp.model.BuildConstants;
import sp.model.Game;
import sp.model.Game.Statistics;

/**
 * Panel containing building options. This will eventually be rewritten when I
 * add technological advancement to the game, to make the list dynamic;
 * unfortunately, the code is currently only extensible by adding new
 * possibilities directly.
 * 
 * @author Jonathan Lovelace
 * @assignment Final Project
 * @course CS108A
 * @semester FA06
 * 
 */
public final class BuildPanel extends JPanel implements ActionListener {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = -1221529553086160975L;

	/**
	 * The singleton BuildPanel.
	 */
	private static BuildPanel panel;

	/**
	 * @param listener
	 *            An ActionListener for the constructor.
	 * @return The singleton BuildPanel.
	 */
	public static BuildPanel getBuildPanel(final ActionListener listener) {
		synchronized (BuildPanel.class) {
			if (panel == null) {
				panel = new BuildPanel(listener);
			}
		}
		return panel;
	}

	/**
	 * Add a button to the panel (so we don't have to keep references to the
	 * buttons around).
	 * 
	 * @param text
	 *            The text of the button
	 * @param one
	 *            An ActionListener to listen to the button
	 * @param two
	 *            Another ActionListener to listen to the button
	 * @param toolTip
	 *            The button's tooltip
	 */
	private void addButton(final String text, final ActionListener one,
			final ActionListener two, final String toolTip) {
		add(addToolTip(addListener(addListener(new JButton(text), one), two),
				toolTip));
	}

	/**
	 * Add an ActionListener to a button
	 * 
	 * @param button
	 *            The button
	 * @param list
	 *            The ActionListener
	 * @return the button (so we can avoid having final local variables in other
	 *         methods)
	 */
	private static JButton addListener(final JButton button,
			final ActionListener list) {
		button.addActionListener(list);
		return button;
	}

	/**
	 * Add a tooltip to a button
	 * 
	 * @param button
	 *            the button
	 * @param toolTip
	 *            its new tooltip text
	 * @return the button (so we can avoid having final local variables in other
	 *         methods)
	 */
	private static JButton addToolTip(final JButton button, final String toolTip) {
		button.setToolTipText(toolTip);
		return button;
	}

	/**
	 * Constructor. Takes references to the its parent's ActionListener (so the
	 * parent can redraw itself and the status bar after we do something)
	 * 
	 * TODO: Make build possibilities dynamic.
	 * 
	 * @param listener
	 *            A listener to notify of events on this panel.
	 */
	private BuildPanel(final ActionListener listener) {
		super(new GridLayout(0, 1));

		/**
		 * Button to build a phalanx.
		 */
		addButton(BuildConstants.PHALANX_NAME, this, listener,
				"The phalanx was the Greek infantry unit, strong but speedy. Cost: 2.");
		/**
		 * Button to build a cavalry.
		 */
		addButton(BuildConstants.CAVALRY_NAME, this, listener,
				"Cavalry are lightly armed, well-trained warriors on horseback. Cost: 4.");
		/**
		 * Button to build an archer.
		 */
		addButton(BuildConstants.ARCHER_NAME, this, listener,
				"Archers can command the battlefield from a distance but are very"
						+ " weak in melee. Cost: 5.");
		/**
		 * Button to build a ballista.
		 */
		addButton(BuildConstants.BALLISTA_NAME, this, listener,
				"A ballista, a giant crossbow, is a powerful, but expensive and"
						+ " less accurate, siege weapon. Cost: 6.");

	}

	/**
	 * Handles the button-presses from the building choices buttons.
	 * 
	 * Clicking on a button causes us to tell the game to produce a unit with
	 * the stats we think that unit has on the specified tile. TODO: Starting
	 * stats for each kind of unit should be internal to the Game object, or
	 * perhaps handled by another global object. A view object presenting
	 * choices should know stats only in order to inform the player. At present
	 * the model and view are confused here.
	 * 
	 * @param event
	 *            The event that triggered this method
	 */
	@Override
	public void actionPerformed(final ActionEvent event) {
		// If we're not in building mode, or the tile to build
		// on hasn't been selected, do nothing.
		// TODO: Clicking where to build and then what is
		// more internally consistent in this implementation
		// with the rest of the GUI, but is counterintuitive
		// and should be fixed.
		if ((Game.getGame().getMode() != Game.Mode.BUILD_MODE)
				|| (Game.getGame().getTile1() == null)) {
			return;
		} else if (event.getActionCommand().equals(BuildConstants.PHALANX_NAME)) {
			buildUnit(BuildConstants.PHALANX_NAME);
		} else if (event.getActionCommand().equals(BuildConstants.CAVALRY_NAME)) {
			buildUnit(BuildConstants.CAVALRY_NAME);
		} else if (event.getActionCommand().equals(BuildConstants.ARCHER_NAME)) {
			buildUnit(BuildConstants.ARCHER_NAME);
		} else if (event.getActionCommand()
				.equals(BuildConstants.BALLISTA_NAME)) {
			buildUnit(BuildConstants.BALLISTA_NAME);
		}

		// Once the unit has been produced, reset the mode ...
		Game.getGame().setMode(Game.Mode.NO_MODE);

		// ... and refresh the screen.
		repaint();
	}

	/**
	 * Build a unit
	 * 
	 * @param unit
	 *            The name of the unit.
	 */
	public static void buildUnit(final String unit) {
		if (BuildConstants.PHALANX_NAME.equals(unit)) {
			Game.getGame().buildUnit(
					Game.getGame().getTile1(),
					BuildConstants.PHALANX_NAME,
					new Statistics(BuildConstants.PHALANX_HP,
							BuildConstants.PHALANX_SPEED,
							BuildConstants.PHALANX_DEFENSE,
							BuildConstants.PHALANX_ACCURACY,
							BuildConstants.PHALANX_ATTACK,
							BuildConstants.PHALANX_RANGED),
					BuildConstants.PHALANX_COST);
		} else if (BuildConstants.CAVALRY_NAME.equals(unit)) {
			Game.getGame().buildUnit(
					Game.getGame().getTile1(),
					BuildConstants.CAVALRY_NAME,
					new Statistics(BuildConstants.CAVALRY_HP,
							BuildConstants.CAVALRY_SPEED,
							BuildConstants.CAVALRY_DEFENSE,
							BuildConstants.CAVALRY_ACCURACY,
							BuildConstants.CAVALRY_ATTACK,
							BuildConstants.CAVALRY_RANGED),
					BuildConstants.CAVALRY_COST);
		} else if (BuildConstants.ARCHER_NAME.equals(unit)) {
			Game.getGame().buildUnit(
					Game.getGame().getTile1(),
					BuildConstants.ARCHER_NAME,
					new Statistics(BuildConstants.ARCHER_HP,
							BuildConstants.ARCHER_SPEED,
							BuildConstants.ARCHER_DEFENSE,
							BuildConstants.ARCHER_ACCURACY,
							BuildConstants.ARCHER_ATTACK,
							BuildConstants.ARCHER_RANGED),
					BuildConstants.ARCHER_COST);
		} else if (BuildConstants.BALLISTA_NAME.equals(unit)) {
			Game.getGame().buildUnit(
					Game.getGame().getTile1(),
					BuildConstants.BALLISTA_NAME,
					new Statistics(BuildConstants.BALLISTA_HP,
							BuildConstants.BALLISTA_SPEED,
							BuildConstants.BALLISTA_DEFENSE,
							BuildConstants.BALLISTA_ACCURACY,
							BuildConstants.BALLISTA_ATTACK,
							BuildConstants.BALLISTA_RANGED),
					BuildConstants.BALLISTA_COST);
		}
	}
}
