package dealWithSensors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import org.bson.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MigrateToMYSQL {
	
	private MongoClient mongoClient;

	private ArrayList<Document> getFromMongo() {

		mongoClient = new MongoClient(
				new MongoClientURI("mongodb://gestorMongo:pass123@localhost:27017/?authSource=SID_2019_G9"));

		MongoDatabase db = mongoClient.getDatabase("SID_2019_G9");
		MongoCollection<Document> table = db.getCollection("Medicoes");

		ArrayList<Document> result = new ArrayList<Document>();

		BasicDBObject field = new BasicDBObject();
		field.put("Migrated", 0);
		MongoCursor<Document> cursor = table.find(field).iterator();
		while (cursor.hasNext()) {
			Document obj = cursor.next();
			result.add(obj);
		}

		return result;

	}

	private ArrayList<Object> buildFields(String[] fields) {

		ArrayList<Object> builtFields = new ArrayList<Object>();
		Timestamp timestamp = null;

		double temp = Double.parseDouble(fields[1].split("=")[1]);
		double lumin = Double.parseDouble(fields[2].split("=")[1]);
		String date = fields[3].split("=")[1];
		String time = fields[4].split("=")[1];

		timestamp = Timestamp.valueOf(date + " " + time);

		builtFields.add(temp);
		builtFields.add(lumin);
		builtFields.add(timestamp);

		return builtFields;

	}

	public void migrate() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/bdorigem_php", "Gestor", "passgestor");

		ArrayList<Document> result = getFromMongo();
		ArrayList<Object> builtFields = new ArrayList<Object>();
		if (result != null) {
			for (Document d : result) {
				System.out.println(d);
				String[] fields = d.toString().split(",");
				builtFields = buildFields(fields);
				String query = "INSERT INTO medicoesluminosidade (DataHoraMedicao, ValorMedicaoLuminosidade)"
						+ "VALUES (?,?)";
				PreparedStatement preparedStmt = conn.prepareStatement(query);
				preparedStmt.setTimestamp(1, (Timestamp) builtFields.get(2));
				preparedStmt.setDouble(2, (double) builtFields.get(1));

				String query2 = "INSERT INTO medicoestemperatura (DataHoraMedicao, ValorMedicaoTemperatura)"
						+ "VALUES (?,?)";
				PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
				preparedStmt2.setTimestamp(1, (Timestamp) builtFields.get(2));
				preparedStmt2.setDouble(2, (double) builtFields.get(0));

				try {
					preparedStmt.execute();
					preparedStmt2.execute();
				} catch (SQLException e) {
					return;
				}

				// Update do campo Migrated para 1 apenas depois de exportar com sucesso para o
				// MySQL
				MongoCollection<Document> col = mongoClient.getDatabase("SID_2019_G9").getCollection("Medicoes");
				Document doc = (Document) col.find(d).first();

				BasicDBObject updateField = new BasicDBObject();
				updateField.append("Migrated", 1);

				BasicDBObject setQuery = new BasicDBObject();
				setQuery.append("$set", updateField);
				col.updateOne(doc, setQuery);

			}
		}

	}

	public void closeClient() {
		if (mongoClient != null)
			mongoClient.close();
	}

}
