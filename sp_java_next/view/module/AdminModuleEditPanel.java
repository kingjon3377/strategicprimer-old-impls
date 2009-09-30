package view.module;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.module.Module;
import model.module.kinds.RenameableModule;
/**
 * 
 * @author Jonathan Lovelace
 *
 */
public class AdminModuleEditPanel extends JPanel implements ActionListener {

	/**
	 * Version UID for serialization
	 */
	private static final long serialVersionUID = -7684896702232114955L;
	/**
	 * Handle button presses on the panel
	 */
	@Override
	public void actionPerformed(final ActionEvent event) {
		if ("Apply".equals(event.getActionCommand())) {
			if (module instanceof RenameableModule) { // NOPMD
				((RenameableModule) module).setName(nameField.getText());
			}
		}
	}
	/**
	 * Constructor
	 */
	public AdminModuleEditPanel() {
		super(new BorderLayout());
		final JPanel panel = new JPanel(new GridLayout(0,2));
		panel.add(new JLabel("Name:"));
		nameField.setEnabled(false);
		panel.add(nameField);
		add(panel,BorderLayout.CENTER);
		add(button,BorderLayout.SOUTH);
		button.addActionListener(this);
	}
	/**
	 * A text field for the module's name
	 */
	private final transient JTextField nameField = new JTextField(5);
	/**
	 * The module we're editing
	 */
	private transient Module module;
	/**
	 * The "Apply" button
	 */
	private final transient JButton button = new JButton("Apply");
	/**
	 * @param mod the module to edit
	 */
	public void setModule(final Module mod) {
		module = mod;
		nameField.setText(module.getName());
		nameField.setEnabled(module instanceof RenameableModule);
		button.setEnabled(module != null);
	}
}
