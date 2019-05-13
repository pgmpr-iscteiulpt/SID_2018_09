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

		insertButtons();
		north.add(insertCulture);
		north.add(gui.getUpdateCulture());
		north.add(deleteCulture);
		gui.getFrame().add(north, BorderLayout.NORTH);
		SwingUtilities.updateComponentTreeUI(gui.getFrame());

	}

	public void insertButtons() {
		insertButton();
		updateButton();
		deleteButton();
	}
	
	public void insertButton() {
		if (c == 'C') {
			insertCulture = new JButton("Inserir cultura");
		} else {
			insertCulture = new JButton("Inserir medição");	
		}
		insertCulture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JPanel inserir = new JPanel();
				if (c == 'C') {
					inserir.setLayout(new GridLayout(4,2));	

					idCultura = new JLabel("IDCultura");
					nomeCultura = new JLabel("NomeCultura");
					descricaoCultura = new JLabel("DescriçãoCultura");
					utilizadorEmail = new JLabel("UtilizadorEmail");
				} else {
					inserir.setLayout(new GridLayout(5,2));

					idVariavel = new JLabel("IDVariável");
					idCultura = new JLabel("IDCultura");
					numeroMedicao = new JLabel("NumeroMedicão");
					dataHoraMedicao = new JLabel("DataHoraMedicão");
					valorMedicao = new JLabel("ValorMedicão");

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

	public void updateButton() {
		gui.getUpdateCulture().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String topic = null;
				gui.setPress(true);

				gui.getModelU().removeAllElements();
				//listU = new JList<String>(modelU);
				//scrollU = new JScrollPane(listU);
				if (c == 'C') {
					topic = "ID       Nome       Descrição       Utilizador";
				} else {
					topic = "Cul      Var        ID        Data                                    Valor";
				}
				gui.getScrollU().setViewportBorder(new TitledBorder(topic));						
				gui.getScrollU().setPreferredSize(new Dimension(300, 400));
				gui.getScrollU().setViewportView(gui.getListU());
				//scrollPU = new JPanel();	
				gui.getScrollPU().add(gui.getScrollU());
				//scrollP.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

				gui.getFrame().add(gui.getScrollPU(), BorderLayout.CENTER);

				gui.itsOkActivated();

				if (c == 'C') {
					String query = "SELECT * FROM cultura WHERE UtilizadorEmail = '"+ gui.getUtiName() + "'";
					PreparedStatement preparedStmt = null;
					try { 
						preparedStmt = gui.getLog().getConn().prepareStatement(query);
						ResultSet rs = preparedStmt.executeQuery(query);
						while(rs.next()) {
							int id = rs.getInt(1);
							String name = rs.getString(2);
							String description = rs.getString(3);
							String email = rs.getString(4);
							gui.getModelU().addElement(id + "      " + name + "      " + description + "      " + email);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else {
					String query1 = "SELECT IDCultura FROM cultura WHERE UtilizadorEmail = '"+ gui.getUtiName() + "'";
					PreparedStatement preparedStmt = null;
					ArrayList<Integer> ids = new ArrayList<Integer>();
					try { 
						preparedStmt = gui.getLog().getConn().prepareStatement(query1);
						ResultSet rs = preparedStmt.executeQuery(query1);
						while(rs.next()) {
							ids.add(rs.getInt(1));
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}



					for (Integer i : ids) {


						String query = "SELECT * FROM medicoes WHERE IDCultura = '"+ i + "'";
						//PreparedStatement preparedStmt = null;
						try { 
							preparedStmt = gui.getLog().getConn().prepareStatement(query);
							ResultSet rs = preparedStmt.executeQuery(query);
							while(rs.next()) {
								int idC = rs.getInt(1);
								int idV = rs.getInt(2);
								int id = rs.getInt(3);
								Timestamp data = rs.getTimestamp(4);
								double valor = rs.getDouble(5);
								gui.getModelU().addElement(idC + "             " + idV + "           " + id + "      " + data + "        " + valor);
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
				SwingUtilities.updateComponentTreeUI(gui.getFrame());

			}
		});
	}

	public void deleteButton() {
		if (c == 'C') {
			deleteCulture = new JButton("Apagar cultura");
		} else {
			deleteCulture = new JButton("Apagar medição");
		}
		deleteCulture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				gui.getModelD().removeAllElements();
				//listD = new JList<String>(modelD);
				//scrollD = new JScrollPane(listD);
				String topic = null;
				if (c == 'C') {
					topic = "ID       Nome       Descrição       Utilizador";
				} else {
					topic = "Cul      Var        ID        Data                                    Valor";
				}
				gui.getScrollD().setViewportBorder(new TitledBorder(topic));						
				gui.getScrollD().setPreferredSize(new Dimension(300, 400));
				gui.getScrollD().setViewportView(gui.getListD());
				JPanel scrollP = new JPanel();	
				scrollP.add(gui.getScrollD());
				scrollP.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));


				gui.getFrame().add(scrollP, BorderLayout.EAST);


				gui.itsOkActivated();

				if (c == 'C') {
					String query = "SELECT * FROM cultura WHERE UtilizadorEmail = '"+ gui.getUtiName() + "'";;
					PreparedStatement preparedStmt = null;
					try { 
						preparedStmt = gui.getLog().getConn().prepareStatement(query);
						ResultSet rs = preparedStmt.executeQuery(query);
						while(rs.next()) {
							int id = rs.getInt(1);
							String name = rs.getString(2);
							String description = rs.getString(3);
							String email = rs.getString(4);
							gui.getModelD().addElement(id + "      " + name + "      " + description + "      " + email);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}	
				} else {
					String query1 = "SELECT IDCultura FROM cultura WHERE UtilizadorEmail = '"+ gui.getUtiName() + "'";
					PreparedStatement preparedStmt = null;
					ArrayList<Integer> ids = new ArrayList<Integer>();
					try { 
						preparedStmt = gui.getLog().getConn().prepareStatement(query1);
						ResultSet rs = preparedStmt.executeQuery(query1);
						while(rs.next()) {
							ids.add(rs.getInt(1));
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}



					for (Integer i : ids) {


						String query = "SELECT * FROM medicoes WHERE IDCultura = '"+ i + "'";
						//PreparedStatement preparedStmt = null;
						try { 
							preparedStmt = gui.getLog().getConn().prepareStatement(query);
							ResultSet rs = preparedStmt.executeQuery(query);
							while(rs.next()) {
								int idC = rs.getInt(1);
								int idV = rs.getInt(2);
								int id = rs.getInt(3);
								Timestamp data = rs.getTimestamp(4);
								double valor = rs.getDouble(5);
								gui.getModelD().addElement(idC + "             " + idV + "           " + id + "      " + data + "        " + valor);
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
				SwingUtilities.updateComponentTreeUI(gui.getFrame());

			}


		});
	}
}
