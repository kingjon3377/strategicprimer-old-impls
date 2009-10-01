package view.module;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.module.Module;
import model.module.kinds.Fortress;

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
	private final transient JLabel label = new JLabel();
	/**
	 * Administrative version of this panel
	 */
	private final transient AdminModuleEditPanel adminPanel;

	/**
	 * Constructor
	 * 
	 * @param admin
	 *            Should we show the administrative panel?
	 */
	public CurrentModulePanel(final boolean admin) {
		super(new GridLayout(0,1));
		add(label);
		if (admin) {
			adminPanel = new AdminModuleEditPanel();
			add(adminPanel);
		} else {
			adminPanel = null;
		}
	}

	/**
	 * Change the label to a new module
	 * 
	 * @param mod
	 *            The module to describe
	 */
	public void changeCurrentModule(final Module mod) {
		if (adminPanel != null) {
			adminPanel.setModule(mod);
		}
		if (mod == null) {
			label.setText("none");
		} else if (mod instanceof Fortress
				&& ((Fortress) mod).getModule() != null) {
			changeCurrentModule(((Fortress) mod).getModule());
		} else {
			label.setText(mod.getName());
		}
	}

}
