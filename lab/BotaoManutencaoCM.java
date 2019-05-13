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

	public void insertButton() {
		if (c == 'C') {
			insertCulture = new JButton("Inserir cultura");
		} else {
			insertCulture = new JButton("Inserir medi��o");	
		}
		insertCulture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JPanel inserir = new JPanel();
				if (c == 'C') {
					inserir.setLayout(new GridLayout(4,2));	

					idCultura = new JLabel("IDCultura");
					nomeCultura = new JLabel("NomeCultura");
					descricaoCultura = new JLabel("Descri��oCultura");
					utilizadorEmail = new JLabel("UtilizadorEmail");
				} else {
					inserir.setLayout(new GridLayout(5,2));

					idVariavel = new JLabel("IDVari�vel");
					idCultura = new JLabel("IDCultura");
					numeroMedicao = new JLabel("NumeroMedic�o");
					dataHoraMedicao = new JLabel("DataHoraMedic�o");
					valorMedicao = new JLabel("ValorMedic�o");

				}

				inserir.setPreferredSize(new Dimension (300,20));
				inserir.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));

				JPanel p1 = new JPanel();
				JPanel p2 = new JPanel();
				JPanel p3 = new JPanel();
				JPanel p4 = new JPanel();
				JPanel p5 = new JPanel();

				if (c == 'C') {
					System.out.println(gui);
					System.out.println(gui.getIdCulturaT());
					//inserir.setLayout(new GridLayout(4,2));
					gui.getIdCulturaT().setPreferredSize(new Dimension (130,20));
					p1.add(gui.getIdCulturaT());
					p1.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

					gui.getNomeCulturaT().setPreferredSize(new Dimension (130,20));
					p2.add(gui.getNomeCulturaT());
					p2.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

					gui.getDescricaoCulturaT().setPreferredSize(new Dimension (130,20));
					p3.add(gui.getDescricaoCulturaT());
					p3.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

					gui.getUtilizadorEmailT().setPreferredSize(new Dimension (130,20));
					p4.add(gui.getUtilizadorEmailT());
					p4.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

					inserir.add(idCultura);
					inserir.add(p1);
					inserir.add(nomeCultura);
					inserir.add(p2);
					inserir.add(descricaoCultura);
					inserir.add(p3);
					inserir.add(utilizadorEmail);
					inserir.add(p4);

				} else {
					gui.getIdVariavelT().setPreferredSize(new Dimension (130,20));
					p1.add(gui.getIdVariavelT());
					p1.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

					gui.getIdCulturaT().setPreferredSize(new Dimension (130,20));
					p2.add(gui.getIdCulturaT());
					p2.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

					gui.getNumeroMedicaoT().setPreferredSize(new Dimension (130,20));
					p3.add(gui.getNumeroMedicaoT());
					p3.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

					gui.getDataHoraMedicaoT().setPreferredSize(new Dimension (130,20));
					p4.add(gui.getDataHoraMedicaoT());
					p4.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

					gui.getValorMedicaoT().setPreferredSize(new Dimension (130,20));
					p5.add(gui.getValorMedicaoT());
					p5.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

					inserir.add(idVariavel);
					inserir.add(p1);
					inserir.add(idCultura);
					inserir.add(p2);
					inserir.add(numeroMedicao);
					inserir.add(p3);
					inserir.add(dataHoraMedicao);
					inserir.add(p4);
					inserir.add(valorMedicao);
					inserir.add(p5);
				}
				gui.getFrame().add(inserir, BorderLayout.WEST);

				gui.itsOkActivated();
				SwingUtilities.updateComponentTreeUI(gui.getFrame());

			}
		});
	}


}
