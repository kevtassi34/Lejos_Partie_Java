package portailEV3;

import exceptions.NoBrickFound;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class PresenceSensor {

	EV3UltrasonicSensor presenceSensor;

	public PresenceSensor(Port port) {
		if (port == null) throw new NoBrickFound("la brick n'est pas connecté à un port");
		this.presenceSensor = new EV3UltrasonicSensor(port);
	}

	public boolean presence() {

		
		float[] sample = new float[presenceSensor.sampleSize()];
		presenceSensor.fetchSample(sample, 0);

		float etat = sample[0];
		
		// la fonction retourne un booléen. Il renvoie vrai si état est < 0.5, faux sinon
		
	return etat < 0.5;

	}
}

