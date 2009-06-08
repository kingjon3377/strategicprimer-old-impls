package finalproject;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

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
public class BuildPanel extends JPanel implements ActionListener {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = 1987612092544868685L;
	/**
	 * Add a button with the specified properties.
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
	 * Constructor.
	 * 
	 * @param listener
	 *            A listener to notify of events on this panel.
	 */
	public BuildPanel(final ActionListener listener) {
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
	 * Handle events on the panel: In this case, build units as requested.
	 * 
	 * @param event
	 *            The event we're handling
	 */
	@Override
	public void actionPerformed(final ActionEvent event) {
		if ((Game.getGame().getMode() != Game.BUILD_MODE)
				|| (Game.getGame().getTile1() == null)) {
			return;
		} else if (event.getActionCommand().equals(BuildConstants.PHALANX_NAME)) {
			buildUnit(BuildConstants.PHALANX_NAME);
		} else if (event.getActionCommand().equals(BuildConstants.CAVALRY_NAME)) {
			buildUnit(BuildConstants.CAVALRY_NAME);
		} else if (event.getActionCommand().equals(BuildConstants.ARCHER_NAME)) {
			buildUnit(BuildConstants.ARCHER_NAME);
		} else if (event.getActionCommand().equals(BuildConstants.BALLISTA_NAME)) {
			buildUnit(BuildConstants.BALLISTA_NAME);
		}
		Game.getGame().setMode(Game.NO_MODE);
		repaint();
	}
	/**
	 * Build a unit
	 * @param unit The name of the unit.
	 */
	public static void buildUnit(final String unit) {
		if (BuildConstants.PHALANX_NAME.equals(unit)) {
			Game.getGame().buildUnit(Game.getGame().getPlayer(),
					Game.getGame().getTile1(), BuildConstants.PHALANX_NAME,
					BuildConstants.PHALANX_HP, BuildConstants.PHALANX_HP,
					BuildConstants.PHALANX_SPEED,
					BuildConstants.PHALANX_DEFENSE,
					BuildConstants.PHALANX_ACCURACY,
					BuildConstants.PHALANX_ATTACK,
					BuildConstants.PHALANX_RANGED, BuildConstants.PHALANX_COST);
		} else if (BuildConstants.CAVALRY_NAME.equals(unit)) {
			Game.getGame().buildUnit(Game.getGame().getPlayer(),
					Game.getGame().getTile1(), BuildConstants.CAVALRY_NAME,
					BuildConstants.CAVALRY_HP, BuildConstants.CAVALRY_HP,
					BuildConstants.CAVALRY_SPEED,
					BuildConstants.CAVALRY_DEFENSE,
					BuildConstants.CAVALRY_ACCURACY,
					BuildConstants.CAVALRY_ATTACK,
					BuildConstants.CAVALRY_RANGED, BuildConstants.CAVALRY_COST);
		} else if (BuildConstants.ARCHER_NAME.equals(unit)) {
			Game.getGame().buildUnit(Game.getGame().getPlayer(),
					Game.getGame().getTile1(), BuildConstants.ARCHER_NAME,
					BuildConstants.ARCHER_HP, BuildConstants.ARCHER_HP,
					BuildConstants.ARCHER_SPEED, BuildConstants.ARCHER_DEFENSE,
					BuildConstants.ARCHER_ACCURACY,
					BuildConstants.ARCHER_ATTACK, BuildConstants.ARCHER_RANGED,
					BuildConstants.ARCHER_COST);
		} else if (BuildConstants.BALLISTA_NAME.equals(unit)) {
			Game.getGame().buildUnit(Game.getGame().getPlayer(),
					Game.getGame().getTile1(), BuildConstants.BALLISTA_NAME,
					BuildConstants.BALLISTA_HP, BuildConstants.BALLISTA_HP,
					BuildConstants.BALLISTA_SPEED,
					BuildConstants.BALLISTA_DEFENSE,
					BuildConstants.BALLISTA_ACCURACY,
					BuildConstants.BALLISTA_ATTACK,
					BuildConstants.BALLISTA_RANGED,
					BuildConstants.BALLISTA_COST);
		}	}
}
