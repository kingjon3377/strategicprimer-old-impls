package view.gui.module;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.module.SPModule;
import view.gui.ModuleEditFrame;

/**
 * A class to manage the contents of a ModuleEditFrame. There should probably be
 * one of these for each distinct type of unit and building, in fact in a
 * hierarchy mirroring the model hierarchy to allow code reuse by inheritance.
 * 
 * @author Jonathan Lovelace
 * 
 */
public interface ModuleEditPanel extends ActionListener {
	/**
	 * Add controls to edit the fields this "panel" cares about.
	 * 
	 * This method *must* call the frame's addListener() method.
	 * 
	 * @param module
	 *            The module in question
	 * @param frame
	 *            The frame to populate.
	 * @param applyButton
	 *            the "apply" button
	 * @param revertButton
	 *            the "revert" button
	 */
	void populate(final SPModule module, final ModuleEditFrame frame,
			final JButton applyButton, final JButton revertButton);
}
