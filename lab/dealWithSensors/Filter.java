package dealWithSensors;

import java.util.ArrayList;
import org.bson.Document;

public class Filter {

	private static final double TEMPERATURE_TOP_LIMIT = 50;
	private static final double TEMPERATURE_BOTTOM_LIMIT = -10;
	private static final double LUMINOSITY_TOP_LIMIT = 4000;
	private static final double LUMINOSITY_BOTTOM_LIMIT = 0;

	private static final double TEMPERATURE_ACCEPTANCE_MARGIN = 0.1;
	private static final double LUMINOSITY_ACCEPTANCE_MARGIN = 0.3;

	private double lastMeasurementTemperature = -1;
	private double lastMeasurementLuminosity = -1;

	public SensorInfo filter(ArrayList<SensorInfo> list) {
		SensorInfo temperature;
		SensorInfo luminosity;

		if (lastMeasurementTemperature == -1)
			temperature = filterWithMean(list, 'T');
		else
			temperature = filterWithMargin(list, 'T');

		if (lastMeasurementLuminosity == -1)
			luminosity = filterWithMean(list, 'L');
		else
			luminosity = filterWithMargin(list, 'L');

		return merge(temperature, luminosity);

	}

	// Filtra com o método da média. O char indica se é para filtrar a temperatura
	// ou a luminosidade
	private SensorInfo filterWithMean(ArrayList<SensorInfo> list, char c) {
		if (!list.isEmpty()) {
			double mean = 0;
			int count = 0;
			double closest = 0;
			SensorInfo info = null;

			for (SensorInfo s : list) {
				if (c == 'T' && s.getTemperatureValue() != -800) {
					mean += s.getTemperatureValue();
					count++;
				} else {
					if (c == 'L') {
						mean += s.getLuminosityValue();
						count++;
					}
				}

			}

			if (count != 0) {

				mean = mean / count;

				if (c == 'T')
					closest = Math.abs(mean - list.get(0).getTemperatureValue());
				else
					closest = Math.abs(mean - list.get(0).getLuminosityValue());

				for (SensorInfo s : list) {
					if (c == 'T') {
						if (Math.abs(mean - s.getTemperatureValue()) <= closest) {
							closest = Math.abs(mean - s.getTemperatureValue());
							info = s;
						}
					} else {
						if (Math.abs(mean - s.getLuminosityValue()) <= closest) {
							closest = Math.abs(mean - s.getLuminosityValue());
							info = s;
						}

					}
				}
				if (c == 'T')
					lastMeasurementTemperature = getLocation(info.getTemperatureValue(), 'T');
				else
					lastMeasurementLuminosity = getLocation(info.getLuminosityValue(), 'L');

				return new SensorInfo(info.getTemperatureValue(), info.getLuminosityValue());

			} else {
				return new SensorInfo(-800, 0);
			}
		}

		return null;

	}

	// Filtra com o método das margens. O char indica se é para filtrar a
	// temperatura
	// ou a luminosidade
	private SensorInfo filterWithMargin(ArrayList<SensorInfo> list, char c) {
		if (!list.isEmpty()) {

			int count = list.size() - 1;
			boolean found = false;
			SensorInfo info = null;

			while (count >= 0 && !found) {
				if (c == 'T') {
					if (Math.abs(getLocation(list.get(count).getTemperatureValue(), 'T')
							- lastMeasurementTemperature) < TEMPERATURE_ACCEPTANCE_MARGIN) {
						found = true;
						info = list.get(count);
					}
				} else {
					if (Math.abs(getLocation(list.get(count).getLuminosityValue(), 'L')
							- lastMeasurementLuminosity) < LUMINOSITY_ACCEPTANCE_MARGIN) {
						found = true;
						info = list.get(count);
					}
				}
				count--;
			}

			if (info != null) {
				if (c == 'T')
					lastMeasurementTemperature = getLocation(info.getTemperatureValue(), 'T');
				else
					lastMeasurementLuminosity = getLocation(info.getLuminosityValue(), 'L');

				return new SensorInfo(info.getTemperatureValue(), info.getLuminosityValue());

			} else {
				if (c == 'T')
					lastMeasurementTemperature = -1;
				else
					lastMeasurementLuminosity = -1;

				return new SensorInfo(-800, 0);
			}
		}
		return null;

	}

	private double getLocation(double value, char c) {
		double finalValue;
		if (c == 'T') {
			finalValue = (value - TEMPERATURE_BOTTOM_LIMIT) / (TEMPERATURE_TOP_LIMIT - TEMPERATURE_BOTTOM_LIMIT);
		} else {
			finalValue = (value - LUMINOSITY_BOTTOM_LIMIT) / (LUMINOSITY_TOP_LIMIT - LUMINOSITY_BOTTOM_LIMIT);
		}
		return finalValue;

	}

	private SensorInfo merge(SensorInfo temperature, SensorInfo luminosity) {
		if (temperature == null && luminosity == null) {
			return null;
		}
		if (temperature == null && luminosity != null) {
			return new SensorInfo(-800, luminosity.getLuminosityValue(), new Document());
		}
		if (temperature != null && luminosity == null) {
			return new SensorInfo(temperature.getTemperatureValue(), 0, new Document());
		}
		return new SensorInfo(temperature.getTemperatureValue(), luminosity.getLuminosityValue(), new Document());

	}

	public boolean getHasMeasurement() {
		return lastMeasurementLuminosity == -1 || lastMeasurementTemperature == -1;
	}

}
