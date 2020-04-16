package portailEV3;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
	private static ArrayList<State> stateList ;

	
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
					
				 default:
					 TimeUnit.SECONDS.sleep(20);
					 totalClosing();
					 while(!doorSensorClosed.contact()) {
						 
					 }
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
			saveState(stateDoor);
		}
		else if(stateDoor.name().equals("OUVERT")) {
			System.out.println("En fermeture.");
			totalClosing();
			stateDoor = State.valueOf("FERME");
			saveState(stateDoor);
		}
		else if(stateDoor.name().equals("OUVERT_PARTIELLE")) {
			System.out.println("En fermeture.");
			partialClosing();
			stateDoor = State.valueOf("FERME");
			saveState(stateDoor);
		}
		
		else if(stateDoor.name().equals("EnOuvertureTotale")) {
			System.out.println("En Pause.");
			leftDoor.stop(true);
			rightDoor.stop(true);
			stateDoor = State.valueOf("ARRET");
			saveState(stateDoor);
		}
		else if (stateDoor.name().equals("ARRET")) {
			
			//capteur de contact ouvert
			while(!leftSensorOpen.contact()) {
				System.out.println("En ouverture totale.");
				rightDoor.opened();
				leftDoor.opened();
				stateDoor = State.valueOf("EnOuvertureTotale");
				saveState(stateDoor);
				
				// capteur de presence 
				while(presenceSensor.presence()) {
					 leftDoor.stop(true);
					 rightDoor.stop(true);    
			        }
			}
			rightDoor.stop(true);
			leftDoor.stop(true);
			stateDoor = State.valueOf("OUVERT");
			saveState(stateDoor);
		}
			
	}
	
	public static void partialOpening() {

		if ( stateDoor.name().equals("FERME")) {
			
			// capteur de contact en ouverture partielle
			while(!leftSensorOpen.contact()) {
				System.out.println("En ouverture partielle.");
				leftDoor.opened();
				stateDoor = State.valueOf("EnOuverturePartielle");
				saveState(stateDoor);
				
				// capteur de presence 
				while(presenceSensor.presence()) {
					 leftDoor.stop(true);   
			        }
			}
			leftDoor.stop(true);
			stateDoor = State.valueOf("OUVERT_PARTIELLE");
			saveState(stateDoor);
		}
		else if (stateDoor.name().equals("OUVERT_PARTIELLE")) {
			System.out.println("En fermeture.");
			partialClosing();
			stateDoor = State.valueOf("FERME");
			saveState(stateDoor);
		}
		else if (stateDoor.name().equals("OUVERT")) {
			System.out.println("En fermeture.");
			totalClosing();
			stateDoor = State.valueOf("FERME");
			saveState(stateDoor);
		}
		
		else if(stateDoor.name().equals("EnOuverturePartielle")) {
			System.out.println("En Pause.");
			leftDoor.stop(true);
			stateDoor = State.valueOf("ARRET");
			saveState(stateDoor);
		}
		else if (stateDoor.name().equals("ARRET")) {
			
			// capteur de contact en ouverture partielle
			while(!leftSensorOpen.contact()) {
				System.out.println("En ouverture partielle.");
				leftDoor.opened();
				stateDoor = State.valueOf("EnOuverturePartielle");
				saveState(stateDoor);
				
				// capteur de presence 
				while(presenceSensor.presence()) {
					 leftDoor.stop(true);   
			        }
			}
			leftDoor.stop(true);
			stateDoor = State.valueOf("OUVERT_PARTIELLE");
			saveState(stateDoor);
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
			saveState(stateDoor);
			
			// capteur de presence 
			while(presenceSensor.presence()) {
				 leftDoor.stop(true);
				 rightDoor.stop(true);    
		        }
		}
		rightDoor.stop(true);	
		leftDoor.stop(true);
		stateDoor = State.valueOf("FERME");
		saveState(stateDoor);
	}
	else if (stateDoor.name().equals("FERME")) {
		System.out.println("En Ouverture.");
		totalOpening();
		stateDoor = State.valueOf("OUVERT");
		saveState(stateDoor);
	}
	else if(stateDoor.name().equals("FERME_PARTIELLE")) {
		System.out.println("En Ouverture.");
		partialOpening();
		stateDoor = State.valueOf("OUVERT");
		saveState(stateDoor);
	}
	else if(stateDoor.name().equals("EnFermetureTotale")) {
		System.out.println("En Pause.");
		leftDoor.stop(true);
		rightDoor.stop(true);
		stateDoor = State.valueOf("ARRET");
		saveState(stateDoor);
	}
	else if (stateDoor.name().equals("ARRET")) {
		
		//capteur de contact ouvert
		while(!doorSensorClosed.contact()) {
			System.out.println("En fermeture totale.");
			rightDoor.opened();
			leftDoor.opened();
			stateDoor = State.valueOf("EnFermetureTotale");
			saveState(stateDoor);
			
			// capteur de presence 
			while(presenceSensor.presence()) {
				 leftDoor.stop(true);
				 rightDoor.stop(true);    
		        }
		}
		rightDoor.stop(true);
		leftDoor.stop(true);
		stateDoor = State.valueOf("FERME");
		saveState(stateDoor);
	}
  }
	
	
	public static void saveState(State st) {
		stateList.add(st);
	}

	public void displayStateList() {
		for (int i =0 ; i<stateList.size();i++) {
		
			switch(stateList.get(i)){
			
			case EnOuvertureTotale:
				System.out.println("porte en Ouverture Totale");
				break;

			case EnOuverturePartielle: 
				System.out.println("porte en Ouverture Partielle");
				break;	 
				
			case EnFermetureTotale : 
				System.out.println("porte en Fermeture Totale");
				break;
				
			case EnFermeturePartielle : 
				System.out.println("porte en Fermeture Partielle");
				break;
				
			case FERME : 
				System.out.println("porte Fermée");
				break;
				
			case OUVERT : 
				System.out.println("porte Ouverte");
				break;
				
			case OUVERT_PARTIELLE : 
				System.out.println("porte Ouvert Partielle");
				break;
				
			case FERME_PARTIELLE : 
				System.out.println("porte en Fermé Partielle");
				break;
				
			case ARRET : 
				System.out.println("porte en Arrêt");
				break;
				
			case INCONNU : 
				System.out.println("Porte en Etat Inconnu");
				break;
				
			 default:
				   break;
		  }
			
		}
	}
}