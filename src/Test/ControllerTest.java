package Test;


import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.NoBrickFound;
import lejos.hardware.port.SensorPort;
import portailEV3.ContactSensor;
import portailEV3.Controller;
import portailEV3.Door;
import portailEV3.State;

public class ControllerTest {

	
	/*@Test (expected = NoBrickFound.class)
	public void testInitialisation() {
		
	//Controller initi = new Controller();
		
		ContactSensor doorSensorClosed = new ContactSensor(SensorPort.S3);
		State etat = State.FERME;
		String statutPorte = "FERME";
		
		if (doorSensorClosed.equals(true)) {
			assertEquals(etat, statutPorte);
		} 
		fail("Not yet implemented");
	} */
	
	@Test 
	public void testTotalOpening() {
		Controller brique = new Controller();
		State stateDoor = State.FERME;
		State stateTest = State.OUVERT;
		
		brique.totalOpening();
		assertEquals(stateTest, stateDoor );
		
		State stateDoor1 = State.OUVERT;
		State stateTest1 = State.FERME;
		brique.totalOpening();
		assertEquals(stateTest1, stateDoor1 );
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
