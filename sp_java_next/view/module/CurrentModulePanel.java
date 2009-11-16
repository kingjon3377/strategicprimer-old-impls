package view.module;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.location.Location;
import model.module.Module;
import model.module.features.Feature;
import model.module.kinds.Resource;

/**
 * A panel to describe the current module.
 * 
 * @author Jonathan Lovelace
 * 
 */
public class CurrentModulePanel extends JPanel {
	/**
	 * Version UID for serialization.
	 */
	private static final long serialVersionUID = 6550745365212019975L;
	/**
	 * The label describing the current module.
	 */
	private final transient JLabel label = new JLabel();
	/**
	 * Administrative version of this panel.
	 */
	private final transient AdminModuleEditPanel adminPanel;

	/**
	 * Constructor.
	 * 
	 * @param admin
	 *            Should we show the administrative panel?
	 */
	public CurrentModulePanel(final boolean admin) {
		super(new GridLayout(0, 1));
		add(label);
		if (admin) {
			adminPanel = new AdminModuleEditPanel();
			add(adminPanel);
		} else {
			adminPanel = null;
		}
	}

	/**
	 * Change the label to a new module.
	 * 
	 * @param mod
	 *            The module to describe
	 */
	public void changeCurrentModule(final Module mod) {
		if (adminPanel != null) {
			adminPanel.setModule(mod);
		}
//		if (mod == null) {
//			label.setText("none");
//		} else if (mod instanceof Fortress
//				&& ((Fortress) mod).getSelected() != null) {
//			changeCurrentModule(((Fortress) mod).getSelected());
//		} else {
//			label.setText(mod.getName());
//		}
		label.setText(getDescription(mod));
	}
	/**
	 * @param mod a module to describe
	 * @return a description of it
	 */
	private static String getDescription(final Module mod) {
		if (mod == null) {
			return "none"; // NOPMD
		} else {
			final StringBuffer buf = new StringBuffer(mod.getName());
			if (mod instanceof Location) {
				buf.append(" (Location, selected: ");
				buf.append(getDescription(((Location) mod).getSelected()));
				buf.append(')');
			}
			if (mod instanceof Feature) {
				buf.append(" (Feature: ");
				buf.append(((Feature) mod).description());
				buf.append(')');
			}
			if (mod instanceof Resource) {
				buf.append(" Resource: ");
				buf.append(((Resource) mod).getQuantity());
				buf.append(')');
			}
			return buf.toString();
		}
	}
}
