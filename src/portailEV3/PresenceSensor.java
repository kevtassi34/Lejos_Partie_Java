package portailEV3;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class PresenceSensor {

	EV3UltrasonicSensor presenceSensor;

	PresenceSensor(Port port) {
		this.presenceSensor = new EV3UltrasonicSensor(port);
	}

	boolean presence() {

		float[] sample = new float[presenceSensor.sampleSize()];
		presenceSensor.fetchSample(sample, 0);

		float etat = sample[0];
		
		if (etat < 0.5)
			return true;
		else
			return false;

	}
}
