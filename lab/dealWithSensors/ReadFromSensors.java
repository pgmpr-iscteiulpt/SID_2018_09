package dealWithSensors;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class ReadFromSensors {

	private MqttAsyncClient myClient;
	private MqttCallback  myCB;
	
	//Cria o cliente e faz a conexão ao broker (ex: wss://iot.eclipse.org:443) / (tcp://broker.mqtt-dashboard.com:1883)
	private void connectMQTTClient(String broker) {
		MemoryPersistence persistence = new MemoryPersistence();
		try {
			myClient = new MqttAsyncClient(broker, UUID.randomUUID().toString(), persistence);
			IMqttToken token = myClient.connect();
			token.waitForCompletion();
		} catch (MqttException e) {
			e.printStackTrace();
		}

	}
	
	//Define a classe de callback
	private void setCallback (MqttCallback  myCB) {
		this.myCB = myCB;
		myClient.setCallback(myCB);
	}
	
	//Subscreve um tópico
	private void subscribeTopic (String topic) {
		try {
			myClient.subscribe(topic, 0);
		} catch (MqttException e) {
			e.printStackTrace();
		}

	}
	
	
	public void setUpReading (String broker, String topic) {
		connectMQTTClient(broker);
		setCallback(new MyCallback());
		subscribeTopic(topic);
	}


	public LinkedBlockingQueue<SensorInfo> getData() {
		return ((MyCallback)myCB).getDataFromSensors();

	}
	
}