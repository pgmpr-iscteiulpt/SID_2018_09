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

	public void itsOkActivated() {
		if (!okActivate){
			JButton ok_inserir = new JButton("Ok");
			ok_inserir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(c == 'V') {
						if (!idVariavelT.getText().isEmpty() && !nomeVariavelT.getText().isEmpty()) {
							modelD.addElement(idVariavelT.getText() + "      " + nomeVariavelT.getText());
							modelU.addElement(idVariavelT.getText() + "      " + nomeVariavelT.getText()); 
							SwingUtilities.updateComponentTreeUI(frame);
							String query = "INSERT INTO variaveis (IDVariavel, NomeVariavel)"
									+ "VALUES ("+idVariavelT.getText()+",'"+nomeVariavelT.getText()+"')";
							try {
								PreparedStatement preparedStmt = log.getConn().prepareStatement(query);
								preparedStmt.execute();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}					
							idVariavelT.setText("");
							nomeVariavelT.setText("");	
						}
					} else {
						if (!emailT.getText().isEmpty() && !nomeUtilizadorT.getText().isEmpty() && !categoriaProfissionalT.getText().isEmpty() && !tipoUtilizadorT.getText().isEmpty()) {
							modelD.addElement(emailT.getText() + "      " + nomeUtilizadorT.getText()+ "      " + categoriaProfissionalT.getText() + "      " + tipoUtilizadorT.getText());
							modelU.addElement(emailT.getText() + "      " + nomeUtilizadorT.getText()+ "      " + categoriaProfissionalT.getText() + "      " + tipoUtilizadorT.getText()); 
							SwingUtilities.updateComponentTreeUI(frame);
							String query = "INSERT INTO utilizador (Email, NomeUtilizador, CategoriaProfissional, TipoUtilizador)"
									+ "VALUES ('"+emailT.getText()+"','"+nomeUtilizadorT.getText()+"','"+categoriaProfissionalT.getText()+"','"+tipoUtilizadorT.getText()+"')";
							try {
								PreparedStatement preparedStmt = log.getConn().prepareStatement(query);
								preparedStmt.execute();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							emailT.setText("");
							nomeUtilizadorT.setText("");
							categoriaProfissionalT.setText("");
							tipoUtilizadorT.setText("");	
						}		
					}

					if ( !listD.isSelectionEmpty() ) {
						String g [] = listD.getSelectedValue().split("      ");
						if(c == 'V') {
							String query = "DELETE FROM variaveis WHERE NomeVariavel = '" + g[1] + "';";
							PreparedStatement preparedStmt;
							try {
								preparedStmt = log.getConn().prepareStatement(query);
								preparedStmt.executeUpdate();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						} else {
							String g1 [] = listD.getSelectedValue().split("             ");
							System.out.println("hey" + g1[1]);
							String query = "DELETE FROM utilizador WHERE Email = '" + g1[0] + "';";
							PreparedStatement preparedStmt;
							try {
								preparedStmt = log.getConn().prepareStatement(query);
								preparedStmt.executeUpdate();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						if (isPress) {
							modelU.remove(listD.getSelectedIndex());
							SwingUtilities.updateComponentTreeUI(frame);
						}
						modelD.remove(listD.getSelectedIndex());	
					}

					if(ButtonUpdatePress){
						if(c == 'V') {
							String query = "UPDATE variaveis SET IDVariavel = '"+ idVariavelTU.getText() +"', NomeVariavel='"+ nomeVariavelTU.getText() +"' WHERE IDVariavel = '"+gU[0]+"' AND NomeVariavel = '"+gU[1]+"'";
							PreparedStatement preparedStmt;
							try {
								preparedStmt = log.getConn().prepareStatement(query);
								preparedStmt.executeUpdate();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						} else {
							System.out.println("hey" + listU.getSelectedValue());
//							gU = listU.getSelectedValue().split("             ");
//							System.out.println(gU.length + gU[0]);
//							gU1 = gU[1].split("           ");
//							gU2 = gU1[1].split("      ");
							String query = "UPDATE utilizador SET Email = '"+ emailTU.getText() +"', NomeUtilizador='"+ nomeUtilizadorTU.getText() +"', CategoriaProfissional = '"+ categoriaProfissionalTU.getText() +"', TipoUtilizador = '" + tipoUtilizadorTU.getText() +"' WHERE Email = '"+gU[0]+"' AND NomeUtilizador = '"+gU1[0]+"' AND CategoriaProfissional='"+gU2[0]+"' AND TipoUtilizador='"+gU2[1]+"'";
							PreparedStatement preparedStmt;
							try {
								preparedStmt = log.getConn().prepareStatement(query);
								preparedStmt.executeUpdate();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						frame.remove(inserir);
						updateVariable.doClick();
						SwingUtilities.updateComponentTreeUI(frame);
						ButtonUpdatePress = false;
					}

					if ( !listU.isSelectionEmpty() ) {


						System.out.println(listU.getSelectedValue());
						frame.remove(scrollPU);
						frame.remove(listU);
						gU = listU.getSelectedValue().split("      ");
						inserir = new JPanel();
						if(c == 'V') {
							inserir.setLayout(new GridLayout(2,2));				
							//						inserir.setPreferredSize(new Dimension (300,20));
							//						inserir.setBorder(BorderFactory.createEmptyBorder(0, 400, 0, 400));

							idVariavel = new JLabel("IDVariavel");
							nomeVariavel = new JLabel("NomeVariavel");
						} else {

							gU = listU.getSelectedValue().split("             ");
							inserir.setLayout(new GridLayout(4,2));				

							email = new JLabel("Email");
							nomeUtilizador = new JLabel("NomeUtilizador");
							categoriaProfissional = new JLabel("CategoriaProfissional");
							tipoUtilizador = new JLabel("TipoUtilizador");
						}
						inserir.setPreferredSize(new Dimension (300,20));
						inserir.setBorder(BorderFactory.createEmptyBorder(0, 400, 0, 400));

						JPanel p1 = new JPanel();
						JPanel p2 = new JPanel();
						JPanel p3 = new JPanel();
						JPanel p4 = new JPanel();

						if(c == 'V') {
							idVariavelTU = new JTextField(gU[0]);
							idVariavelTU.setPreferredSize(new Dimension (130,20));
							p1.add(idVariavelTU);
							p1.setBorder(BorderFactory.createEmptyBorder(98, 0, 0, 0));

							nomeVariavelTU = new JTextField(gU[1]);
							nomeVariavelTU.setPreferredSize(new Dimension (130,20));
							p2.add(nomeVariavelTU);
							nomeVariavel.setBorder(BorderFactory.createEmptyBorder(0, 0, 200, 0));
							p2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

							inserir.add(idVariavel);
							inserir.add(p1);
							inserir.add(nomeVariavel);
							inserir.add(p2);

						} else {
							System.out.println(listU.getSelectedValue());
							gU = listU.getSelectedValue().split("             ");
							gU1 = gU[1].split("           ");
							gU2 = gU1[1].split("      ");
							
							emailTU = new JTextField(gU[0]);
							emailTU.setPreferredSize(new Dimension (130,20));
							p1.add(emailTU);
							p1.setBorder(BorderFactory.createEmptyBorder(42, 0, 0, 0));

							nomeUtilizadorTU = new JTextField(gU1[0]);
							nomeUtilizadorTU.setPreferredSize(new Dimension (130,20));
							p2.add(nomeUtilizadorTU);
							p2.setBorder(BorderFactory.createEmptyBorder(42, 0, 0, 0));

							categoriaProfissionalTU = new JTextField(gU2[0]);
							categoriaProfissionalTU.setPreferredSize(new Dimension (130,20));
							p3.add(categoriaProfissionalTU);
							p3.setBorder(BorderFactory.createEmptyBorder(42, 0, 0, 0));

							tipoUtilizadorTU = new JTextField(gU2[1]);
							tipoUtilizadorTU.setPreferredSize(new Dimension (130,20));
							p4.add(tipoUtilizadorTU);
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
						frame.add(inserir, BorderLayout.CENTER);
						SwingUtilities.updateComponentTreeUI(frame);
						modelU.removeAllElements();
						ButtonUpdatePress = true;
						System.out.println(modelU.size());
						SwingUtilities.updateComponentTreeUI(frame);
					}
				}
			});	
			JPanel ok = new JPanel();					
			ok.add(ok_inserir);
			voltarJ.add(ok, BorderLayout.CENTER);
			okActivate = true;
		}	
	}
	
	public JFrame getFrame() {
		return frame;
	}
	public JTextField getIdVariavelT() {
		return idVariavelT;
	}
	public JTextField getNomeVariavelT() {
		return nomeVariavelT;
	}
	public JButton getUpdateVariable() {
		return updateVariable;
	}
	public JScrollPane getScrollU() {
		return scrollU;
	}
	public JPanel getScrollPU() {
		return scrollPU;
	}
	public JList<String> getListU() {
		return listU;
	}
	public DefaultListModel<String> getModelU() {
		return modelU;
	}
	public DefaultListModel<String> getModelD() {
		return modelD;
	}
	public JScrollPane getScrollD() {
		return scrollD;
	}
	public LoginGUI getLog() {
		return log;
	}
	public JList<String> getListD() {
		return listD;
	}
	public JPanel getVoltarJ() {
		return voltarJ;
	}
	public boolean getIsPress() {
		return isPress;
	}
	public void setPress(boolean isPress) {
		this.isPress = isPress;
	}
	public JTextField getEmailT() {
		return emailT;
	}
	public JTextField getNomeUtilizadorT() {
		return nomeUtilizadorT;
	}
	public JTextField getCategoriaProfissionalT() {
		return categoriaProfissionalT;
	}
	public JTextField getTipoUtilizadorT() {
		return tipoUtilizadorT;
	}
	public String getUtiName() {
		return utiName;
	}


}