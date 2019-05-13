package dealWithSensors;

import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MyCallback implements MqttCallback {

	private LinkedBlockingQueue<SensorInfo> dataFromSensors = new LinkedBlockingQueue<SensorInfo>();

	@Override
	public void connectionLost(Throwable arg0) {
		arg0.printStackTrace();
		System.out.println("The connection was lost");
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		System.out.println("The delivery was completed");

	}

	@Override
	public void messageArrived(String topic, MqttMessage MQTTmsg) throws Exception {

		String msg = MQTTmsg.toString();
		double temperature = -800;
		double luminosity = 0;

		if (msg.toString().charAt(0) == '{') {
			msg = msg.toString().replace("{", "");
			msg = msg.toString().replace("}", "");
		}

		String[] fields = msg.toString().split(",");
		try {
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].split("\"")[1].equals("tmp")) {
					temperature = Double.parseDouble(fields[i].split("\"")[3]);
				}

				if (fields[i].split("\"")[1].equals("cell")) {
					luminosity = Double.parseDouble(fields[i].split("\"")[3]);
				}
			}
		} catch (Exception e) {
			return;
		}

		if (!(temperature == -800 && luminosity == 0))
			dataFromSensors.add(new SensorInfo(temperature, luminosity));
	}

	public LinkedBlockingQueue<SensorInfo> getDataFromSensors() {
		return dataFromSensors;
	}
}
