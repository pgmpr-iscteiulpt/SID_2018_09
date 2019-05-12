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

public class InvestigadorGUI {

	private JFrame frame;
	private DefaultListModel<String> modelU;
	private JList<String> listU;
	private JScrollPane scrollU;
	private DefaultListModel<String> modelD;
	private JList<String> listD;
	private JScrollPane scrollD;
	private JLabel center;
	private boolean okActivate = false;
	private JPanel voltarJ;
	private LoginGUI log;
	private JTextField idCulturaT;
	private JTextField nomeCulturaT;
	private JTextField descricaoCulturaT;
	private JTextField utilizadorEmailT;
	private boolean isPress = false;
	private JPanel scrollPU = new JPanel();
	private boolean ButtonUpdatePress = false;
	private String gU [];
	private JPanel inserir;
	private JTextField idCulturaTU;
	private JTextField nomeCulturaTU;
	private JTextField descricaoCulturaTU;
	private JTextField utilizadorEmailTU;
	private JButton updateCulture = new JButton("Alterar cultura");
	private String utiName;
	private boolean backButtonPress = false;

	public InvestigadorGUI(LoginGUI log, String utiName) {
		this.log = log;
		this.utiName = utiName;
		addFrameContent();
		addButtonsContent();
		frame.setVisible(true);	
	}

	public void addFrameContent() {
		idCulturaT = new JTextField();
		nomeCulturaT = new JTextField();
		descricaoCulturaT = new JTextField();
		utilizadorEmailT = new JTextField();
		modelU = new DefaultListModel<String>();
		listU = new JList<String>(modelU);
		scrollU = new JScrollPane(listU);	
		modelD = new DefaultListModel<String>();
		listD = new JList<String>(modelD);
		scrollD = new JScrollPane(listD);
		frame = new JFrame("Laboratório do Investigador: " + utiName);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension (1100,600));
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dimension.width/2 - 1250/2, dimension.height/2 - 750/2);
		frame.setLayout(new BorderLayout());	
		frame.pack();	
	}

	public void addButtonsContent() {
		InvestigadorGUI gui = this;
		
		center = new JLabel();
		center.setLayout(new GridLayout(2,1,60,60));
		center.setBorder(BorderFactory.createEmptyBorder(75, 75, 75, 75));
		JButton cultureButton = new JButton("Manutenção de culturas");
		cultureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					backButton();
//				new BotaoCultura(gui);
			}
		});
		JButton measuresButton = new JButton("Manutenção de medições");
		measuresButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if (!backButtonPress) {
					backButton();
				//}
//				new BotaoMedicoes();	
			}
		});	
		center.add(cultureButton);
		center.add(measuresButton);
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
					if (!idCulturaT.getText().isEmpty() && !nomeCulturaT.getText().isEmpty() && !descricaoCulturaT.getText().isEmpty() && !utilizadorEmailT.getText().isEmpty()) {
						modelD.addElement(idCulturaT.getText() + "      " + nomeCulturaT.getText()+ "      " + descricaoCulturaT.getText() + "      " + utilizadorEmailT.getText());
						modelU.addElement(idCulturaT.getText() + "      " + nomeCulturaT.getText()+ "      " + descricaoCulturaT.getText() + "      " + utilizadorEmailT.getText()); 
						SwingUtilities.updateComponentTreeUI(frame);
						String query = "INSERT INTO cultura (IDCultura, NomeCultura, DescricaoCultura, UtilizadorEmail)"
								+ "VALUES ("+idCulturaT.getText()+",'"+nomeCulturaT.getText()+"','"+descricaoCulturaT.getText()+"','"+utilizadorEmailT.getText()+"')";
						try {
							PreparedStatement preparedStmt = log.getConn().prepareStatement(query);
							preparedStmt.execute();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}					
						idCulturaT.setText("");
						nomeCulturaT.setText("");
						descricaoCulturaT.setText("");
						utilizadorEmailT.setText("");	
					}

					if ( !listD.isSelectionEmpty() ) {					
						String g [] = listD.getSelectedValue().split("      ");
						String query = "DELETE FROM cultura WHERE NomeCultura = '" + g[1] + "';";
						PreparedStatement preparedStmt;
						try {
							preparedStmt = log.getConn().prepareStatement(query);
							preparedStmt.executeUpdate();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						if (isPress) {
							modelU.remove(listD.getSelectedIndex());
							SwingUtilities.updateComponentTreeUI(frame);
						}
						modelD.remove(listD.getSelectedIndex());	
					}

					if(ButtonUpdatePress){
						String query = "UPDATE cultura SET IDCultura = '"+ idCulturaTU.getText() +"', NomeCultura='"+ nomeCulturaTU.getText() +"', DescricaoCultura = '"+ descricaoCulturaTU.getText() +"', UtilizadorEmail = '" + utilizadorEmailTU.getText() +"' WHERE IDCultura = '"+gU[0]+"' AND NomeCultura = '"+gU[1]+"' AND DescricaoCultura='"+gU[2]+"' AND UtilizadorEmail='"+gU[3]+"'";
						PreparedStatement preparedStmt;
						try {
							preparedStmt = log.getConn().prepareStatement(query);
							preparedStmt.executeUpdate();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						frame.remove(inserir);
						updateCulture.doClick();
						SwingUtilities.updateComponentTreeUI(frame);
						ButtonUpdatePress = false;
					}

					if ( !listU.isSelectionEmpty() ) {
						System.out.println(listU.getSelectedValue());
						frame.remove(scrollPU);
						frame.remove(listU);
						gU = listU.getSelectedValue().split("      ");
						inserir = new JPanel();
						inserir.setLayout(new GridLayout(4,2));				
						inserir.setPreferredSize(new Dimension (300,20));
						inserir.setBorder(BorderFactory.createEmptyBorder(0, 400, 0, 400));

						JLabel idCultura = new JLabel("IDCultura");
						JLabel nomeCultura = new JLabel("NomeCultura");
						JLabel descricaoCultura = new JLabel("DescriçãoCultura");
						JLabel utilizadorEmail = new JLabel("UtilizadorEmail");

						JPanel p1 = new JPanel();
						JPanel p2 = new JPanel();
						JPanel p3 = new JPanel();
						JPanel p4 = new JPanel();

						idCulturaTU = new JTextField(gU[0]);
						idCulturaTU.setPreferredSize(new Dimension (130,20));
						p1.add(idCulturaTU);
						p1.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

						nomeCulturaTU = new JTextField(gU[1]);
						nomeCulturaTU.setPreferredSize(new Dimension (130,20));
						p2.add(nomeCulturaTU);
						p2.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

						descricaoCulturaTU = new JTextField(gU[2]);
						descricaoCulturaTU.setPreferredSize(new Dimension (130,20));
						p3.add(descricaoCulturaTU);
						p3.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

						utilizadorEmailTU = new JTextField(gU[3]);
						utilizadorEmailTU.setPreferredSize(new Dimension (130,20));
						p4.add(utilizadorEmailTU);
						p4.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

						inserir.add(idCultura);
						inserir.add(p1);
						inserir.add(nomeCultura);
						inserir.add(p2);
						inserir.add(descricaoCultura);
						inserir.add(p3);
						inserir.add(utilizadorEmail);
						inserir.add(p4);

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
	public JTextField getIdCulturaT() {
		return idCulturaT;
	}
	public JTextField getNomeCulturaT() {
		return nomeCulturaT;
	}
	public JTextField getDescricaoCulturaT() {
		return descricaoCulturaT;
	}
	public JTextField getUtilizadorEmailT() {
		return utilizadorEmailT;
	}
	public JButton getUpdateCulture() {
		return updateCulture;
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

}