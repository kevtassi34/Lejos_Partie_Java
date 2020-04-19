package portailEV3;

import lejos.hardware.port.Port;

public class Door {

	Motor motor;
	Boolean open, close, opening, closing, stopping;

	public Door(Port port) {
		motor = new Motor(port);
		open = close = opening = closing = stopping = false;
	}
	
	public Boolean getOpening() {
		return opening;
	}
	
	public void setOpening(Boolean opening) {
		this.opening = opening;
	}

	public Boolean getClosing() {
		return closing;
	}

	public void setClosing(Boolean closing) {
		this.closing = closing;
	}

	void opened() {
		this.motor.push();
		this.opening = true;
		this.stopping = false;
	}
	
	void stop(boolean open) {
		this.motor.stop();
		this.opening = this.closing = false;
		this.open = open;
		this.stopping = true;
	}

	void closed() {

		this.motor.pull();
		this.closing = true;
		this.stopping = false;

	}

	boolean openPosition() {
		return this.open;
	}

	boolean closePosition() {
		return this.close;
	}
	
}
