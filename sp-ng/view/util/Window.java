package view.util;

import java.awt.Component;

import javax.swing.JFrame;

/**
 * A utility class to make a window out of a panel or other component.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class Window extends JFrame {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = -8702989963712249998L;

	/**
	 * Constructor.
	 * 
	 * @param comp
	 *            the component to wrap
	 */
	public Window(final Component comp) {
		super();
		add(comp);
		setPreferredSize(comp.getPreferredSize());
		setMaximumSize(comp.getMaximumSize());
		setMinimumSize(comp.getMinimumSize());
		setSize(comp.getSize());
	}
}
