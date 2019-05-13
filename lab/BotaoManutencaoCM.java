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

public class BotaoManutencaoCM {

	private InvestigadorGUI gui;
	private JButton insertCulture;
	private JButton deleteCulture;
	private char c;
	private JLabel idCultura;
	private JLabel nomeCultura;
	private JLabel descricaoCultura;
	private JLabel utilizadorEmail;
	private JLabel idVariavel;
	private JLabel numeroMedicao;
	private JLabel dataHoraMedicao;
	private JLabel valorMedicao;

	public BotaoManutencaoCM(InvestigadorGUI gui, char c){
		this.gui = gui;
		this.c = c;
		run();
	}

	public void run() {

		JPanel north = new JPanel();
		north.setLayout(new GridLayout(1,3,60,60));
		north.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		insertButton();
		updateButton();
		deleteButton();

		north.add(insertCulture);
		north.add(gui.getUpdateCulture());
		north.add(deleteCulture);
		gui.getFrame().add(north, BorderLayout.NORTH);
		SwingUtilities.updateComponentTreeUI(gui.getFrame());

	}

	private void deleteButton() {
		// TODO Auto-generated method stub
		
	}

	private void updateButton() {
		// TODO Auto-generated method stub
		
	}

	private void insertButton() {
		// TODO Auto-generated method stub
		
	}

	
}
