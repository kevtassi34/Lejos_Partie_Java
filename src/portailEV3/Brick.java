package portailEV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;


public class Brick{

	private static int remoteControlCode = 0;
	private static boolean app_alive;
	
	private static ContactSensor leftSensorOpen = new ContactSensor(SensorPort.S1);
	private static ContactSensor doorSensorClosed = new ContactSensor(SensorPort.S3);

	private static PresenceSensor presenceSensor = new PresenceSensor(SensorPort.S4);
	

	private static Door leftDoor = new Door(MotorPort.A);
	private static Door rightDoor = new Door(MotorPort.B);

	private static State stateDoor;

	
	public static void main(String[] args) throws InterruptedException{
		
		stateDoor = State.valueOf("INCONNU");

		
		EcouteBT EBT = new EcouteBT();
		
	
		initialisation();

		EBT.start();

		app_alive = true;
		
		while(app_alive){
			
			remoteControlCode = EBT.byteRecu;
			
			switch(remoteControlCode){
				
				// Ouvrir la porte partiellement
				case 1:
					partialOpening();
					break;

					//Ouvrir la porte
				case 2: 
					totalOpening();
					break;	
					
					//Fermer la porte
				case 3 : 
					totalClosing();
					break;
			}
		}
	}
	
	
	@SuppressWarnings("unlikely-arg-type")
	public static void initialisation() {
		
		leftOpening();
		partialClosing();
	
		if (doorSensorClosed.contact()) {
			stateDoor = State.valueOf("FERME");
		} 
		// Si le capteur de position fermée du portail ne detecte pas de contact alors il y a une erreur
		else {
			LCD.clear();
			LCD.drawString("Erreur lors de l'initialisation", 0, 5);
				Delay.msDelay(5000);
				LCD.clear();
				LCD.refresh();
		}
	
	}
	
	public static void totalOpening() {
	
		if (stateDoor.name().equals("FERME")) {
			
			//capteur de contact ouvert
			while(!leftSensorOpen.contact()) {
				System.out.println("En ouverture totale.");
				rightDoor.opened();
				leftDoor.opened();
				stateDoor = State.valueOf("EnOuvertureTotale");
				
				// capteur de presence 
				while(presenceSensor.presence()) {
					 leftDoor.stop(true);
					 rightDoor.stop(true);    
			        }
			}
			rightDoor.stop(true);
			leftDoor.stop(true);
			stateDoor = State.valueOf("OUVERT");
		}
		else if(stateDoor.name().equals("OUVERT")) {
			System.out.println("En fermeture.");
			totalClosing();
			stateDoor = State.valueOf("FERME");
		}
		else if(stateDoor.name().equals("OUVERT_PARTIELLE")) {
			System.out.println("En fermeture.");
			partialClosing();
			stateDoor = State.valueOf("FERME");
		}
		
		else if(stateDoor.name().equals("EnOuvertureTotale")) {
			System.out.println("En Pause.");
			leftDoor.stop(true);
			rightDoor.stop(true);
			stateDoor = State.valueOf("ARRET");
		}
		else if (stateDoor.name().equals("ARRET")) {
			
			//capteur de contact ouvert
			while(!leftSensorOpen.contact()) {
				System.out.println("En ouverture totale.");
				rightDoor.opened();
				leftDoor.opened();
				stateDoor = State.valueOf("EnOuvertureTotale");
				
				// capteur de presence 
				while(presenceSensor.presence()) {
					 leftDoor.stop(true);
					 rightDoor.stop(true);    
			        }
			}
			rightDoor.stop(true);
			leftDoor.stop(true);
			stateDoor = State.valueOf("OUVERT");
		}
			
	}
	
	public static void partialOpening() {

		if ( stateDoor.name().equals("FERME")) {
			
			// capteur de contact en ouverture partielle
			while(!leftSensorOpen.contact()) {
				System.out.println("En ouverture partielle.");
				leftDoor.opened();
				stateDoor = State.valueOf("EnOuverturePartielle");
				
				// capteur de presence 
				while(presenceSensor.presence()) {
					 leftDoor.stop(true);   
			        }
			}
			leftDoor.stop(true);
			stateDoor = State.valueOf("OUVERT_PARTIELLE");
		}
		else if (stateDoor.name().equals("OUVERT_PARTIELLE")) {
			System.out.println("En fermeture.");
			partialClosing();
			stateDoor = State.valueOf("FERME");
		}
		else if (stateDoor.name().equals("OUVERT")) {
			System.out.println("En fermeture.");
			totalClosing();
			stateDoor = State.valueOf("FERME");
		}
		
		else if(stateDoor.name().equals("EnOuverturePartielle")) {
			System.out.println("En Pause.");
			leftDoor.stop(true);
			stateDoor = State.valueOf("ARRET");
		}
		else if (stateDoor.name().equals("ARRET")) {
			
			// capteur de contact en ouverture partielle
			while(!leftSensorOpen.contact()) {
				System.out.println("En ouverture partielle.");
				leftDoor.opened();
				stateDoor = State.valueOf("EnOuverturePartielle");
				
				// capteur de presence 
				while(presenceSensor.presence()) {
					 leftDoor.stop(true);   
			        }
			}
			leftDoor.stop(true);
			stateDoor = State.valueOf("OUVERT_PARTIELLE");
		}
		
	}
	
	public static void leftOpening() {
		
		// capteur de contact en ouverture gauche
		while(!leftSensorOpen.contact()) {
			leftDoor.opened();
			
			// capteur de presence 
			while(presenceSensor.presence()) {
				leftDoor.stop(true);    
			    }
		}
		leftDoor.stop(true);
			
	}
	
	public static void partialClosing() {
		
		// capteur de contact en fermeture partielle
		while (!doorSensorClosed.contact()) {
			leftDoor.closed();
			
			// capteur de presence 
			while(presenceSensor.presence()) {
				 leftDoor.stop(true);    
		        }
		}
		leftDoor.stop(false);
	}
	
	
	public static void totalClosing() {
		
	if (stateDoor.name().equals("OUVERT")) {
		
		//capteur de contact ouvert
		while(!doorSensorClosed.contact()) {
			System.out.println("En fermeture totale.");
			leftDoor.closed();
			rightDoor.closed();
			stateDoor = State.valueOf("EnFermetureTotale");
			
			// capteur de presence 
			while(presenceSensor.presence()) {
				 leftDoor.stop(true);
				 rightDoor.stop(true);    
		        }
		}
		rightDoor.stop(true);
		leftDoor.stop(true);
		stateDoor = State.valueOf("FERME");
	}
	else if (stateDoor.name().equals("FERME")) {
		System.out.println("En Ouverture.");
		totalOpening();
		stateDoor = State.valueOf("OUVERT");
	}
	else if(stateDoor.name().equals("FERME_PARTIELLE")) {
		System.out.println("En Ouverture.");
		partialOpening();
		stateDoor = State.valueOf("OUVERT");
	}
	else if(stateDoor.name().equals("EnFermetureTotale")) {
		System.out.println("En Pause.");
		leftDoor.stop(true);
		rightDoor.stop(true);
		stateDoor = State.valueOf("ARRET");
	}
	else if (stateDoor.name().equals("ARRET")) {
		
		//capteur de contact ouvert
		while(!doorSensorClosed.contact()) {
			System.out.println("En fermeture totale.");
			rightDoor.opened();
			leftDoor.opened();
			stateDoor = State.valueOf("EnFermetureTotale");
			
			// capteur de presence 
			while(presenceSensor.presence()) {
				 leftDoor.stop(true);
				 rightDoor.stop(true);    
		        }
		}
		rightDoor.stop(true);
		leftDoor.stop(true);
		stateDoor = State.valueOf("FERME");
	}
  }

}