package dealWithSensors;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import org.bson.Document;

public class SensorInfo {

	private double temperatureValue;
	private double luminosityValue;

	private Document doc;

	// Construtor para o recebimento das mensagens do sensor
	public SensorInfo(double temperatureValue, double luminosityValue) {
		this.temperatureValue = temperatureValue;
		this.luminosityValue = luminosityValue;
	}

	// Construtor para as mensagens a enviar para o Mongo
	public SensorInfo(double temperatureValue, double luminosityValue, Document doc) {
		this.temperatureValue = temperatureValue;
		this.luminosityValue = luminosityValue;

		String timestamp = Timestamp
				.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Timestamp.valueOf(LocalDateTime.now())))
				.toString();
		String date = timestamp.split(" ")[0];
		String time = timestamp.split(" ")[1].split("\\.")[0];

		doc.append("Temperature", temperatureValue);
		doc.append("Luminosity", luminosityValue);
		doc.append("Date", date);
		doc.append("Time", time);
		doc.append("Migrated", 0);

		this.doc = doc;
	}

	public double getTemperatureValue() {
		return temperatureValue;
	}

	public double getLuminosityValue() {
		return luminosityValue;
	}

	public Document getDoc() {
		return doc;
	}

}
