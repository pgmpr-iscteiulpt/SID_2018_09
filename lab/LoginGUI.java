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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private LoginGUI log;

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
		log = this;

		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					login();
				} catch (SQLException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login.dispose();
			}
		});
	}
	
	public void login() throws SQLException, ClassNotFoundException {
		if ( confirmLog(nameField, passwordField) ) {
			if ( getAccount() == 'I' ) {
				new InvestigadorGUI(log, nameField.getText());
				System.out.println(nameField.getText());
				login.dispose();
			} else if ( getAccount() == 'A') {
				new AdministradorGUI(log, nameField.getText());
				login.dispose();
			} else if( getAccount() == 'N') {
				JOptionPane.showMessageDialog(login, "Utilizador não existente");
			}
		}
	}

	public char getAccount() {
		String query = "SELECT TipoUtilizador, Email FROM utilizador WHERE Email ='"+nameField.getText()+"'";
		PreparedStatement preparedStmt = null;
		char s = 0;
		try {
			preparedStmt = conn.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery(query);
			if(!rs.next()) {
				s = 'N';
			} else {
				String type = rs.getString(1);
				String email = rs.getString(2);
				if( type.equals("Investigador")) {
					s = 'I';
				} else if (type.equals("Administrador")) {
					s = 'A';
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}

	public boolean confirmLog(JTextField nameField, JPasswordField passwordField) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost/bdorigem_php", "root", "");
		return true;
	}

	public Connection getConn() {
		return conn;
	}

	public static void main(String[] args) {
		new LoginGUI();
	}

}