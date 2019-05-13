package dealWithSensors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailAlert {

	private class SendEmail {

		String username = "es.grupo9@outloook.com";
		String password = "tenhosono123";
		Properties props = new Properties();
		Session session;

		public void connect() {
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.office365.com");
			props.put("mail.smtp.port", "587");

			session = Session.getInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			session.setDebug(true);
		}

		public void send(String text) {
			try {

				Message message = new MimeMessage(session);
				String to = "mariana.txr@gmail.com";
				message.setFrom(new InternetAddress(("es.grupo9@outlook.com")));
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
				message.setSubject("Alerta Cultura");
				message.setText("text");

				Transport.send(message);

				System.out.println("Email enviado para: " + to);
			} catch (AddressException e) {
				System.out.println("Endereço incorreto ou inexistente!");
			} catch (MessagingException e) {
				System.out.println("Não foi possível enviar email. Verifique o endereço do destinatário");

			}

		}
	}

	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, SQLException {

		int id = 0;

		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/bdorigem_php", "Gestor", "passgestor");

		String query = "SELECT IDAlerta, Descricao FROM alertas ORDER BY IDAlerta DESC LIMIT 1 ";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		ResultSet result = preparedStmt.executeQuery(query);

		if (result.next()) {
			id = result.getInt(1);
		}

		SendEmail se = new MailAlert().new SendEmail();
		se.connect();

		String descricao;

		while (true) {
			Thread.sleep(20000);
			String query2 = "SELECT IDAlerta, Descricao FROM alertas WHERE IDAlerta > " + id;
			PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
			ResultSet result2 = preparedStmt2.executeQuery(query2);

			while (result2.next()) {
				id = result2.getInt(1);
				descricao = result2.getString(2);
				se.send(descricao);
			}

		}
	}

}