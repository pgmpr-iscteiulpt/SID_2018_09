import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class AdministradorGUI {

	private JFrame frame;
	private DefaultListModel<String> modelU;
	private JList<String> listU;
	private JScrollPane scrollU;
	private DefaultListModel<String> modelD;
	private JList<String> listD;
	private JScrollPane scrollD;
	private LoginGUI log;
	private String utiName;
	private JLabel center;
	private char c;
	private JButton updateVariable;
	private JPanel voltarJ;
	private boolean okActivate = false;
	private boolean backButtonPress = false;
	private boolean isPress = false;
	private boolean ButtonUpdatePress = false;
	private JPanel scrollPU = new JPanel();
	private String gU [];
	private String gU1 [];
	private String gU2 [];
	private JPanel inserir;

	private JTextField idVariavelT;
	private JTextField nomeVariavelT;

	private JTextField idVariavelTU;
	private JTextField nomeVariavelTU;

	private JLabel idVariavel;
	private JLabel nomeVariavel;

	private JTextField emailT;
	private JTextField nomeUtilizadorT;
	private JTextField categoriaProfissionalT;
	private JTextField tipoUtilizadorT;

	private JTextField emailTU;
	private JTextField nomeUtilizadorTU;
	private JTextField categoriaProfissionalTU;
	private JTextField tipoUtilizadorTU;

	private JLabel email;
	private JLabel nomeUtilizador;
	private JLabel categoriaProfissional;
	private JLabel tipoUtilizador;

	public AdministradorGUI(LoginGUI log, String utiName) {
		this.log = log;
		this.utiName = utiName;
		addFrameContent();
		addButtonsContent();
		frame.setVisible(true);
	}

	private void addButtonsContent() {
		// TODO Auto-generated method stub
		
	}

	private void addFrameContent() {
		// TODO Auto-generated method stub
		
	}



}