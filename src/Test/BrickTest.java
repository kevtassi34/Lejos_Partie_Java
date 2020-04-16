package Test;


import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.NoBrickFound;
import lejos.hardware.port.SensorPort;
import portailEV3.Brick;
import portailEV3.ContactSensor;
import portailEV3.Door;
import portailEV3.State;

public class BrickTest {

	
	/*@Test
	public void testInitialisation() {
		
	//Brick initi = new Brick();
		
		ContactSensor doorSensorClosed = new ContactSensor(SensorPort.S3);
		State etat = State.FERME;
		String statutPorte = "FERME";
		
		if (doorSensorClosed.equals(true)) {
			assertEquals(etat, statutPorte);
		} 
		fail("Not yet implemented");
	} */
	
	@Test (expected = NoBrickFound.class)
	public void testTotalOpening() {
		Brick brique = new Brick();
		State stateDoor = State.valueOf("FERME");
		brique.totalOpening();
		assertEquals("OUVERT", stateDoor == State.valueOf("OUVERT"));
	}

	/*@Test
	public void testPartialOpening() {
		fail("Not yet implemented");
	}

	@Test
	public void testLeftOpening() {
		fail("Not yet implemented");
	}

	@Test
	public void testPartialClosing() {
		fail("Not yet implemented");
	}

	@Test
	public void testTotalClosing() {
		fail("Not yet implemented");
	}*/

}
