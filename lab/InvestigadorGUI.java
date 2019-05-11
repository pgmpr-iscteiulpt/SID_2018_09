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


	
	private void addButtonsContent() {
		// TODO Auto-generated method stub
		
	}



	private void addFrameContent() {
		// TODO Auto-generated method stub
		
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
