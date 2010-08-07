package view.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import model.module.SPModule;
import model.unit.Harvester;
import view.gui.module.HarvesterEditor;
import view.gui.module.ModuleEditPanel;

/**
 * A window to allow an admin to edit a unit or building.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class ModuleEditFrame extends JFrame {
	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = 3646970429478465694L;
	/**
	 * The cancel button.
	 */
	private final JButton revertButton = new JButton("Revert");
	/**
	 * The apply button.
	 */
	private final JButton applyButton = new JButton("Apply");

	/**
	 * Constructor.
	 * 
	 * @param mod
	 *            the module to be edited.
	 */
	public ModuleEditFrame(final SPModule mod) {
		setLayout(new GridLayout(0, 2));
		selectManager(mod).populate(mod, this, applyButton, revertButton);
		add(revertButton);
		add(applyButton);
	}

	/**
	 * Pick the suitable ModuleEditPanel implementation.
	 * 
	 * @param mod
	 *            the module we want to edit.
	 * @return a suitable ModuleEditPanel.
	 */
	private static final ModuleEditPanel selectManager(final SPModule mod) {
		if (mod instanceof Harvester) {
			return new HarvesterEditor();
		} else {
			throw new IllegalStateException("Don't know how to edit that");
		}
	}
}
