package portailEV3;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.*;

public class Motor {

	EV3LargeRegulatedMotor motor;

	Motor(Port port) {
		this.motor = new EV3LargeRegulatedMotor(port);
	}

	void push() {
		this.motor.setSpeed(30);
		this.motor.forward();
		//this.motor.rotate(5);
	}

	void pull() {
		this.motor.setSpeed(30);
		this.motor.backward();
		//this.motor.rotate(-5);
	}

	void stop() {
		this.motor.stop();
	}
}

