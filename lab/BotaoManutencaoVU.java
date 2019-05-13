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

		insertButton();
//		updateButton();
//		deleteButton();

		north.add(insertVariable);
		north.add(gui.getUpdateVariable());
		north.add(deleteVariable);
		gui.getFrame().add(north, BorderLayout.NORTH);
		SwingUtilities.updateComponentTreeUI(gui.getFrame());

	}

	public void insertButton() {
		if (c == 'V') {
			insertVariable = new JButton("Inserir variável");
		} else {
			insertVariable = new JButton("Inserir utilizador");	
		}
		insertVariable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JPanel inserir = new JPanel();
				if (c == 'V') {
					inserir.setLayout(new GridLayout(2,2));	

					idVariavel = new JLabel("IDVariavel");
					nomeVariavel = new JLabel("NomeVariavel");
				} else {
					inserir.setLayout(new GridLayout(4,2));

					email = new JLabel("Email");
					nomeUtilizador = new JLabel("NomeUtilizador");
					categoriaProfissional = new JLabel("CategoriaProfissional");
					tipoUtilizador = new JLabel("TipoUtilizador");
				}

				inserir.setPreferredSize(new Dimension (300,20));
				inserir.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));

				JPanel p1 = new JPanel();
				JPanel p2 = new JPanel();
				JPanel p3 = new JPanel();
				JPanel p4 = new JPanel();

				if (c == 'V') {
					//inserir.setLayout(new GridLayout(4,2));
					System.out.println(gui);
					System.out.println(gui.getIdVariavelT());
					gui.getIdVariavelT().setPreferredSize(new Dimension (130,20));
					p1.add(gui.getIdVariavelT());
					p1.setBorder(BorderFactory.createEmptyBorder(98, 0, 0, 0));

					gui.getNomeVariavelT().setPreferredSize(new Dimension (130,20));
					p2.add(gui.getNomeVariavelT());
					nomeVariavel.setBorder(BorderFactory.createEmptyBorder(0, 0, 200, 0));
					p2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));


					inserir.add(idVariavel);
					inserir.add(p1);
					inserir.add(nomeVariavel);
					inserir.add(p2);

				} else {
					gui.getEmailT().setPreferredSize(new Dimension (130,20));
					p1.add(gui.getEmailT());
					p1.setBorder(BorderFactory.createEmptyBorder(42, 0, 0, 0));

					gui.getNomeUtilizadorT().setPreferredSize(new Dimension (130,20));
					p2.add(gui.getNomeUtilizadorT());
					p2.setBorder(BorderFactory.createEmptyBorder(42, 0, 0, 0));

					gui.getCategoriaProfissionalT().setPreferredSize(new Dimension (130,20));
					p3.add(gui.getCategoriaProfissionalT());
					p3.setBorder(BorderFactory.createEmptyBorder(42, 0, 0, 0));

					gui.getTipoUtilizadorT().setPreferredSize(new Dimension (130,20));
					p4.add(gui.getTipoUtilizadorT());
					p4.setBorder(BorderFactory.createEmptyBorder(42, 0, 0, 0));


					inserir.add(email);
					inserir.add(p1);
					inserir.add(nomeUtilizador);
					inserir.add(p2);
					inserir.add(categoriaProfissional);
					inserir.add(p3);
					inserir.add(tipoUtilizador);
					inserir.add(p4);

				}
				gui.getFrame().add(inserir, BorderLayout.WEST);

				gui.itsOkActivated();
				SwingUtilities.updateComponentTreeUI(gui.getFrame());

			}
		});
	}

	
}
