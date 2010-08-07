package view.gui.module;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.module.SPModule;
import model.unit.Harvester;
import view.gui.ModuleEditFrame;

// ESCA-JAVA0137:
/**
 * A class to manage editing a Harvester.
 * 
 * @author Jonathan Lovelace
 */
public class HarvesterEditor implements ModuleEditPanel {
	/**
	 * A label describing the field this class adds.
	 */
	private static final JLabel BURDEN_LABEL = new JLabel("Burden");
	/**
	 * The field this class adds, holding the Harvester's burden.
	 */
	private final JTextField burdenField = new JTextField();
	/**
	 * The Harvester this editor is handling
	 */
	private Harvester mod;

	/**
	 * Populate the panel with fields to edit the Harvester's members.
	 * 
	 * @param module
	 *            the module we're editing
	 * @param frame
	 *            the frame we're adding fields to
	 * @param applyButton
	 *            the "apply" button
	 * @param revertButton
	 *            the "revert" button
	 */
	@Override
	public void populate(final SPModule module, final ModuleEditFrame frame,
			final JButton applyButton, final JButton revertButton) {
		if (!(module instanceof Harvester)) {
			throw new IllegalStateException(
					"HarvesterEditor can only edit Harvesters.");
		} else {
			applyButton.addActionListener(this);
			revertButton.addActionListener(this);
			mod = (Harvester) module;
			frame.add(BURDEN_LABEL);
			frame.add(burdenField);
			actionPerformed(new ActionEvent(this, 0, "Revert"));
		}
	}

	/**
	 * Handle Apply and Revert button presses
	 * 
	 * @param event
	 *            the event to handle
	 */
	@Override
	public void actionPerformed(final ActionEvent event) {
		if (mod == null) {
			throw new IllegalStateException(
					"Must call populate() with a non-null module argument before triggering an ActionEvent");
		} else if (event != null) {
			if ("Revert".equals(event.getActionCommand())) {
				burdenField.setText(Integer.toString(mod.getBurden()));
			} else if ("Apply".equals(event.getActionCommand())) {
				mod.setBurden(Integer.parseInt(burdenField.getText()));
			}
		}
	}
}
