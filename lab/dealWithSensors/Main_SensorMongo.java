package dealWithSensors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.bson.Document;

public class Main_SensorMongo {

	private static long MONGO_INTERVAL = 3000;
	private static JTextField records;
	private boolean stop = false;
	private MongoWrite mw;

	public Main_SensorMongo() {
		DataInputStream is = null;
		try {
			is = new DataInputStream(new FileInputStream("Utilities/Intervals.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Scanner s = new Scanner(is);

		s.nextLine();
		s.nextLine();
		try {
			long mysql = Long.parseLong(s.nextLine());
			MONGO_INTERVAL = mysql * 1000;
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			s.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private class Start extends Thread {

		int counter = 0;

		public void run() {
			DataInputStream is = null;
			try {
				is = new DataInputStream(new FileInputStream("Utilities/Counter.dat"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			// Inicializações
			ReadFromSensors rs = new ReadFromSensors();
			mw = new MongoWrite();
			ArrayList<SensorInfo> auxList = new ArrayList<SensorInfo>();
			Filter filter = new Filter();
			ArrayList<Document> infoToMongo = new ArrayList<Document>();
			int lastIndex = 0;

			try {
				lastIndex = is.readInt();
				is.close();
			} catch (IOException e) {
				lastIndex = 0;
			}

			mw.setIndex(lastIndex);
			// Iniciar a leitura dos sensores
			// "tcp://iot.eclipse.org:1883"
			rs.setUpReading("tcp://broker.mqtt-dashboard.com:1883", "/sid_lab_2019_2");

			while (!stop) {
				try {
					if (filter.getHasMeasurement())
						Thread.sleep(10000);
					else
						Thread.sleep(MONGO_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				rs.getData().drainTo(auxList);
				SensorInfo write = filter.filter(auxList);
				if (write != null) {
					infoToMongo.add(write.getDoc());

					try {
						mw.writeToMongoDB(infoToMongo);
						infoToMongo.clear();
						auxList.clear();
						counter++;
						records.setText(counter + " records added to MongoDB");
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Unable to write");
					}

				}
			}

		}

	}

	private void terminate() {
		stop = true;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			DataOutputStream os = new DataOutputStream(new FileOutputStream("IndexCounter/Counter.dat", false));
			if (mw.getIndex() != 0)
				os.writeInt(mw.getIndex());
			os.close();
			mw.closeClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);

	}

	public static void main(String[] args) {

		Main_SensorMongo sm = new Main_SensorMongo();

		JFrame frame = new JFrame("Sensor To Mongo");
		frame.setPreferredSize(new Dimension(500, 200));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - 500 / 2, dim.height / 2 - 200 / 2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		JPanel text = new JPanel(new BorderLayout());
		text.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 0));
		JTextField tf = new JTextField("Currently doing nothing");
		tf.setHorizontalAlignment(JTextField.CENTER);
		tf.setEditable(false);
		tf.setFont(new Font("Courier", Font.ITALIC, 15));
		tf.setBorder(BorderFactory.createEmptyBorder());
		records = new JTextField("0 records added to MongoDB");
		records.setHorizontalAlignment(JTextField.CENTER);
		records.setEditable(false);
		records.setFont(new Font("Courier", Font.PLAIN, 15));
		records.setBorder(BorderFactory.createEmptyBorder());
		text.add(tf, BorderLayout.NORTH);
		text.add(records, BorderLayout.CENTER);
		frame.add(text, BorderLayout.CENTER);

		JButton start = new JButton("Start");
		JButton terminate = new JButton("Terminate");
		start.setPreferredSize(new Dimension(150, 30));
		terminate.setPreferredSize(new Dimension(150, 30));
		start.setBackground(new Color(0, 204, 102));
		terminate.setBackground(new Color(255, 77, 77));
		start.setFont(new Font("Courier", Font.PLAIN, 15));
		terminate.setFont(new Font("Courier", Font.PLAIN, 15));
		terminate.setEnabled(false);
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		buttonsPanel.add(start);
		buttonsPanel.add(terminate);
		frame.add(buttonsPanel, BorderLayout.SOUTH);

		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				start.setEnabled(false);
				terminate.setEnabled(true);
				tf.setText("Retrieving data from sensors to Mongo Database...");
				sm.new Start().start();
			}
		});

		terminate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setText("Terminating...");
				sm.terminate();
			}
		});

		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);

	}
}
