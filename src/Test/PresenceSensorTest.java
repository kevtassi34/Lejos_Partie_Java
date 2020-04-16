package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.NoBrickFound;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import portailEV3.PresenceSensor;

public class PresenceSensorTest {

	@Test(expected = NoBrickFound.class)
	public void testPresence() {
		Port port = null;
		PresenceSensor capteurP = new PresenceSensor(port);
		boolean presence = capteurP.presence();
		assertEquals(true,presence);
		fail("Not yet implemented");
	}

}
