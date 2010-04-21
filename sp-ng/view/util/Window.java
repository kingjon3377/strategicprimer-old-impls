package view.util;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

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
		JScrollPane jsp = new JScrollPane(comp);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(jsp);
		setPreferredSize(comp.getPreferredSize());
		setMaximumSize(comp.getMaximumSize());
		setMinimumSize(new Dimension(640,480));
		setSize(comp.getSize());
	}
}
