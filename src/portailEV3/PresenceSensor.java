package portailEV3;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class PresenceSensor {

	EV3UltrasonicSensor PresenceSensor;

	PresenceSensor(Port port) {
		this.PresenceSensor = new EV3UltrasonicSensor(port);
	}

	boolean presence() {

		float[] sample = new float[PresenceSensor.sampleSize()];
		PresenceSensor.fetchSample(sample, 0);

		float etat = sample[0];
		
		if (etat < 0.5)
			return true;
		else
			return false;

	}
}
