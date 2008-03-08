package view.module;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.module.Module;

/**
 * A panel to describe the current module
 * 
 * @author Jonathan Lovelace
 * 
 */
public class CurrentModulePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6550745365212019975L;
	/**
	 * The label describing the current module.
	 */
	private transient JLabel label;

	/**
	 * Constructor
	 */
	public CurrentModulePanel() {
		super();
		label = new JLabel();
		this.add(label);
	}

	/**
	 * Change the label to a new module
	 * 
	 * @param mod
	 *            The module to describe
	 */
	public void changeCurrentModule(final Module mod) {
		label.setText(mod.toString());
	}

	/**
	 * @param inStream The serialized object stream
	 * @throws IOException Required by the spec
	 * @throws ClassNotFoundException Required by the spec
	 */
	private void readObject(final ObjectInputStream inStream)
			throws IOException, ClassNotFoundException {
		inStream.defaultReadObject();
		label = new JLabel();
	}
	/**
	 * @param out
	 *            the stream
	 * @throws IOException
	 */
	private void writeObject(final ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}

}
