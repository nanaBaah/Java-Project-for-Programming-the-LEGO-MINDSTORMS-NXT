package Lego.NXT;

/*
 * 	Author	: Nana Baah
 * 	Date	: 06 June 2014
 * 
 * 	Scans the whole room of 90 x 90 square cm area and detects the pallet
 * 	if any is found.
 * 
 * 	global variable quadrant defines which quadrant the pallet was detected.
 * 		1 = first
 * 		2 = second 
 * 		3 = third
 * 		4 = fourth
 */

import lejos.nxt.Sound;
import lejos.util.Delay;

public class PalletDetection {

	private CalibrateDistance _sonic; 
	private int getMotorTacho;
	private MotorClass myMotor;
	private boolean isFound;
	private int quadrant;
	
	public PalletDetection() {
		setGetMotorTacho(0);
		isFound = false;
		quadrant = 0;
	}

	public boolean isFound() {
		return isFound;
	}

	public void setFound(boolean isFound) {
		this.isFound = isFound;
	}

	public PalletDetection(CalibrateDistance _sonic, MotorClass myMotor) {
		this._sonic = _sonic;
		this.myMotor = myMotor;
	}

	public PalletDetection(MotorClass myMotor) {
		this.myMotor = myMotor;
	}

	public PalletDetection(CalibrateDistance _sonic) {
		this._sonic = _sonic;
	}

	public void detect() {
		myMotor.movebackwardby(55);
		myMotor.getFrontMotor().setSpeed(30);
		myMotor.getFrontMotor().resetTachoCount();
		myMotor.getFrontMotor().rotateTo(-75);
		
		while(true) {
			boolean isBreak = false;
			int tCount = myMotor.getFrontMotor().getTachoCount();
			
			//scanning started
			int count = 0;
			for (int i = 0; i < 150; i += 5) {
				Delay.msDelay(100);
				int dist= (int) _sonic.getDistance();
				myMotor.getFrontMotor().rotateTo(tCount + i);			
				
				System.out.println("array 1 : " + DetectionArrayConstant.detectArrayFirst[count]);
				System.out.println("sonic   : " + dist);
				
				if (dist < (DetectionArrayConstant.detectArrayFirst[count++] - 15)) {
					Delay.msDelay(100);
					Sound.twoBeeps();
					myMotor.getRearMotor().stop();
					
					if (i < 150/2) {
						quadrant = 1;			// pallet is found in the 1st quadrant 
					}
					else {
						quadrant = 2;			// pallet is found in the 2nd quadrant 
					}
					
					setGetMotorTacho(myMotor.getFrontMotor().getTachoCount());
					System.out.println("motor A tacho:" + myMotor.getFrontMotor().getTachoCount());
					
					isFound = true;
					isBreak = true;
					break;
				}
				
			}
			if (isBreak) {
				System.out.println("The pallet was found in area " + quadrant);
				System.out.println("motor A tacho:" + myMotor.getFrontMotor().getTachoCount());
				break;
			}
	
			//scanning complete , proceed to rotate robot by 180
			Delay.msDelay(500);
			
			myMotor.getFrontMotor().rotateTo(0);			// reset back to 0 ---> straight _sonic
			myMotor.getFrontMotor().resetTachoCount();
			myMotor.moveforwardby(40);
			
			//rotating the motor
			Delay.msDelay(500);
			myMotor.getFrontMotor().setSpeed(30);
			myMotor.getRearMotor().setSpeed(30);
			//myMotor.getFrontMotor().rotateTo(-80);
			myMotor.getFrontMotor().rotateTo(-75);
			
			//Status...ROTATING
			myMotor.movebackwardby(27);
			myMotor.getFrontMotor().rotateTo(-75);
/*			
			System.out.println(distListFirst.toString());
			System.out.println("Size: " + distListFirst.size());
*/			
			count = 0;
	
			for (int j = 0; j < 150; j += 5) {
				Delay.msDelay(100);
				myMotor.getFrontMotor().rotateTo(tCount + j);
				int dist = (int) _sonic.getDistance();
				
				System.out.println("array 2 : " + DetectionArrayConstant.detectArraySecond[count]);
				System.out.println("sonic   : " + dist);
				
				if (dist < (DetectionArrayConstant.detectArraySecond[count++] - 5)) {
					Delay.msDelay(100);
					Sound.twoBeeps();
					myMotor.getRearMotor().stop();
				
					if (j < 150/2) {
						quadrant = 3;			// pallet is found in the 3rd quadrant 
					}
					else {
						quadrant = 4;			// pallet is found in the 4th quadrant 
					}
					
					setGetMotorTacho(myMotor.getFrontMotor().getTachoCount());
					System.out.println("motor A tacho:" + myMotor.getFrontMotor().getTachoCount());
					
					isFound = true;
					isBreak = true;
					break;
				}
			}
			
			if (isBreak) {
				break;
			}
			
			Delay.msDelay(100);
			
			myMotor.getFrontMotor().rotateTo(0);
			myMotor.getFrontMotor().resetTachoCount();
			System.out.println("No Pallet was detected"); 						// if pallet is not detected then break from while loop
			Sound.buzz();
			isFound = false;
			Delay.msDelay(100);
			break;
		}
	
	}
	public int getGetMotorTacho() {
		return getMotorTacho;
	}
	public void setGetMotorTacho(int getMotorTacho) {
		this.getMotorTacho = getMotorTacho;
	}

	public int getQuadrant() {
		return quadrant;
	}

	public void setQuadrant(int quadrant) {
		this.quadrant = quadrant;
	}
}
