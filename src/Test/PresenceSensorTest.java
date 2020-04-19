package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import portailEV3.PresenceSensor;

public class PresenceSensorTest {

	@Test
	public void testPresence() {
		Port port = SensorPort.S4;
		PresenceSensor capteurP = new PresenceSensor(port);
		boolean presence = capteurP.presence();
		assertEquals(true,presence);
	}

}
