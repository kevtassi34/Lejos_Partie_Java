package portailEV3;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;

public class ContactSensor {

	EV3TouchSensor contactSensor;

	public ContactSensor(Port port) {
		this.contactSensor = new EV3TouchSensor(port);
	}

	boolean contact() {

		float[] sample = new float[contactSensor.sampleSize()];
		contactSensor.fetchSample(sample, 0);

		float etat = sample[0];
		
		// la fonction retourne un booléen. Il renvoie vrai si état vaut 1, faux sinon
		return etat == 1;

	}

}
