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

	public void addFrameContent() {
		idVariavelT = new JTextField();
		nomeVariavelT = new JTextField();
		emailT = new JTextField();
		nomeUtilizadorT = new JTextField();
		categoriaProfissionalT = new JTextField();
		tipoUtilizadorT = new JTextField();
		modelU = new DefaultListModel<String>();
		listU = new JList<String>(modelU);
		scrollU = new JScrollPane(listU);	
		modelD = new DefaultListModel<String>();
		listD = new JList<String>(modelD);
		scrollD = new JScrollPane(listD);
		frame = new JFrame("Laboratório do Administrador: " + utiName);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension (1100,600));
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dimension.width/2 - 1250/2, dimension.height/2 - 750/2);
		frame.setLayout(new BorderLayout());
		frame.pack();

	}

	public void addButtonsContent() {
		AdministradorGUI gui = this;

		center = new JLabel();
		center.setLayout(new GridLayout(2,1,60,60));
		center.setBorder(BorderFactory.createEmptyBorder(75, 75, 75, 75));

		JButton variableButton = new JButton("Manutenção de variáveis");
		variableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c = 'V';
				updateVariable = new JButton("Alterar variável");
				backButton();
//				new BotaoManutencaoVU(gui, c);
			}
		});
		JButton userButton = new JButton("Manutenção de utilizadores");
		userButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c = 'U';
				updateVariable = new JButton("Alterar utilizador");
				backButton();
//				new BotaoManutencaoVU(gui, c);	
			}
		});

		center.add(variableButton);
		center.add(userButton);

		frame.add(center, BorderLayout.CENTER);
	}


	public void backButton(){
		JButton voltar = new JButton("Voltar");
		voltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.getContentPane().removeAll();
				addButtonsContent();
				okActivate = false;
				SwingUtilities.updateComponentTreeUI(frame);
			}
		});

		frame.getContentPane().removeAll();
		voltarJ = new JPanel();
		voltarJ.setLayout(new BorderLayout());
		voltarJ.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		voltarJ.add(voltar, BorderLayout.EAST);
		frame.add(voltarJ,  BorderLayout.SOUTH);

		frame.add(voltarJ,  BorderLayout.SOUTH);
		SwingUtilities.updateComponentTreeUI(frame);
		backButtonPress = true;
	}

	

}