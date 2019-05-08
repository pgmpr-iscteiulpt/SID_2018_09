import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginGUI {
	
	private JButton okButton = new JButton("Ok");
	private JButton cancelButton = new JButton("Cancel");
	private JLabel nameLabel = new JLabel("Email : ");
	private JTextField nameField = new JTextField();
	private JLabel passwordLabel = new JLabel("Password : ");
	private JPasswordField passwordField = new JPasswordField();
	private JDialog login;
	private Connection conn;
	
	public LoginGUI() {
		addLoginDialogContent();
	}
	
	private void addLoginDialogContent() {
	
	JPanel loginPanel = new JPanel(new GridBagLayout());
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

	buttonPanel.add(okButton);
	buttonPanel.add(cancelButton);

	GridBagConstraints gbc = new GridBagConstraints();

	gbc.insets = new Insets(4, 4, 4, 4);

	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.weightx = 0;
	loginPanel.add(nameLabel, gbc);

	gbc.gridx = 1;
	gbc.gridy = 0;
	gbc.weightx = 1;
	gbc.fill = GridBagConstraints.HORIZONTAL;
	loginPanel.add(nameField, gbc);

	gbc.gridx = 0;
	gbc.gridy = 1;
	gbc.weightx = 0;
	loginPanel.add(passwordLabel, gbc);

	gbc.gridx = 1;
	gbc.gridy = 1;
	gbc.weightx = 1;
	gbc.fill = GridBagConstraints.HORIZONTAL;
	loginPanel.add(passwordField, gbc);

	login = new JDialog();
	login.setPreferredSize(new Dimension(400,300));
	login.add(loginPanel);
	login.add(buttonPanel, BorderLayout.SOUTH);
	login.setVisible(true);
	Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	login.setLocation(dimension.width/2 - 400/2, dimension.height/2 - 300/2);
	login.setTitle("Login");
	login.pack();
	login.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	
	LoginGUI log = this;
	
	okButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

		}
		

		

		
	});
	
	cancelButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			login.dispose();
		}
	});
	
	}
	
	public char getAccount() {
		// TODO Auto-generated method stub
		return 'I';
	}
	

	public static void main(String[] args) {
		new LoginGUI();
	}

}

