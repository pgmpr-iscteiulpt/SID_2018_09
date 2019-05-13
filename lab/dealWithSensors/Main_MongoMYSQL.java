package dealWithSensors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main_MongoMYSQL {

	private static long MYSQL_INTERVAL = 3000;
	private MigrateToMYSQL m;
	private boolean stop = false;
	private static JTextField migrations;

	public Main_MongoMYSQL() {
		DataInputStream is = null;
		try {
			is = new DataInputStream(new FileInputStream("Utilities/Intervals.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Scanner s = new Scanner(is);

		s.nextLine();
		s.nextLine();
		s.nextLine();
		s.nextLine();
		try {
			long mysql = Long.parseLong(s.nextLine());
			MYSQL_INTERVAL = mysql * 1000;
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

	private class Migrate extends Thread {

		int counter = 0;

		@Override
		public void run() {
			m = new MigrateToMYSQL();

			while (!stop) {
				try {
					Thread.sleep(MYSQL_INTERVAL);
					m.migrate();
					counter++;
					migrations.setText("Data has been migrated to MySQL " + counter + " times");
				} catch (ClassNotFoundException | SQLException | InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	private void terminate() {
		stop = true;
		m.closeClient();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	public static void main(String[] args) throws InterruptedException {

		Main_MongoMYSQL mm = new Main_MongoMYSQL();

		JFrame frame = new JFrame("Mongo to MySQL");
		frame.setPreferredSize(new Dimension(600, 200));
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
		migrations = new JTextField("Data has been migrated to MySQL 0 times");
		migrations.setHorizontalAlignment(JTextField.CENTER);
		migrations.setEditable(false);
		migrations.setFont(new Font("Courier", Font.PLAIN, 15));
		migrations.setBorder(BorderFactory.createEmptyBorder());
		text.add(tf, BorderLayout.NORTH);
		text.add(migrations, BorderLayout.CENTER);
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
				tf.setText(
						"Retrieving data from Mongo to MySQL Database every " + MYSQL_INTERVAL / 1000 + " seconds...");
				mm.new Migrate().start();
			}
		});

		terminate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setText("Terminating...");
				mm.terminate();
			}
		});

		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);

	}
}
