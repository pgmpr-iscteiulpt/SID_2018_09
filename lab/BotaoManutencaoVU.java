
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

public class BotaoManutencaoVU {

	private AdministradorGUI gui;
	private JButton insertVariable;
	private JButton deleteVariable;
	private char c;
	private JLabel idVariavel;
	private JLabel nomeVariavel;

	private JLabel email;
	private JLabel nomeUtilizador;
	private JLabel categoriaProfissional;
	private JLabel tipoUtilizador;


	public BotaoManutencaoVU(AdministradorGUI gui, char c){
		this.gui = gui;
		this.c = c;
		run();
	}

	public void run() {

		JPanel north = new JPanel();
		north.setLayout(new GridLayout(1,3,60,60));
		north.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

//		insertButton();
//		updateButton();
//		deleteButton();

		north.add(insertVariable);
		north.add(gui.getUpdateVariable());
		north.add(deleteVariable);
		gui.getFrame().add(north, BorderLayout.NORTH);
		SwingUtilities.updateComponentTreeUI(gui.getFrame());

	}

	
}
