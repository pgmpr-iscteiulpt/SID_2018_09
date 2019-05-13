package dealWithSensors;

import java.util.ArrayList;
import org.bson.Document;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoWrite {

	private MongoClient mongoClient;
	private int index;

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void connectMongoDB() {
		mongoClient = new MongoClient(
				new MongoClientURI("mongodb://gestorMongo:pass123@localhost:27017/?authSource=SID_2019_G9"));
	}

	public void closeClient() {
		if (mongoClient != null)
			mongoClient.close();
	}

	public void writeToMongoDB(ArrayList<Document> list) {

		connectMongoDB();
		MongoDatabase db = mongoClient.getDatabase("SID_2019_G9");
		MongoCollection<Document> table = db.getCollection("Medicoes");

		for (Document d : list) {
			table.insertOne(d.append("_id", index));
			index++;
		}

	}

}