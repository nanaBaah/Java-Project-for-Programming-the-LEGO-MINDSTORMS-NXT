/*
 * 	Author	: Nana Baah
 * 	Date	: 30 May 2014
 *
 * 	The 90 x 90 cm square area room/environment where the NXT robot is placed is partitioned into 4 parts namely:
 * 		1st quadrant
 * 		2nd quadrant
 * 		3rd quadrant
 * 		4th quadrant
 *	 					*********************
 *	 					*		  *			*
 *	 					*	2nd	  *	  1st	*
 * 						*		  *			*
 * 						*		  *			*
 * 						*********************
 * 						*		  *			*
 * 						*	3rd	  *	  4th	*
 * 						*		  *			*
 * 						*		  *			*
 * 						*********************
 * 
 * 				Author :	Nana Baah
 * 				Date: 		15/June/2014
 */

package Lego.NXT;
 
import lejos.util.Delay;


public class GoingToMiddle {

	public   MotorClass myMotor; 
	private int minimumValue;
	private int d1Front;
	private int dLeft;
	private int dRight;
	private CalibrateDistance _sonic;
	private PalletDetection myDetect;
	
	public GoingToMiddle() {
		minimumValue = 0;
		d1Front = 0;
		dLeft = 0;
		dRight = 0;
		
	}
	
	public GoingToMiddle(MotorClass myMotor, CalibrateDistance sonic) {
		minimumValue = 0;
		d1Front = 0;
		dLeft = 0;
		dRight = 0;
		this.myMotor = myMotor;
		this._sonic = sonic;
		this.myDetect = new PalletDetection();
	}
	
	public int getMinimumValue() {
		return minimumValue;
	}
	public void setMinimumValue(int minimumValue) {
		this.minimumValue = minimumValue;
	}
	public int getD1Front() {
		return d1Front;
	}
	public void setD1Front(int d1Front) {
		this.d1Front = d1Front;
	}
	public int getdLeft() {
		return dLeft;
	}
	public void setdLeft(int dLeft) {
		this.dLeft = dLeft;
	}
	public int getdRight() {
		return dRight;
	}
	public void setdRight(int dRight) {
		this.dRight = dRight;
	}
	
	
	public void middle() {
		
		 myMotor.resetTachoCount(myMotor.getFrontMotor(),myMotor.getRearMotor());
		 
		switch (myDetect.getQuadrant()) {
		
		case 1:
			 firstQuadrant(_sonic);
			 break;
			
		case 2:
			
			secondQuadrant(_sonic);
			break;
			
		case 3:
			thirdQuadrant(_sonic);
			break;
		
		case 4:
			 fourthQuadrant(_sonic);
			break;

		default:
			System.out.println("Cannot go back home");
			break;
		}	
	
	}

	public void fourthQuadrant(CalibrateDistance _sonic) {
		/*
		 * fourth quadrant of the room
		 * pallet is in first half of the DetectionArray.getDetectarraysecond();
		 * 
		*/
		d1Front = (int) _sonic.getDistance();

		 myMotor.getFrontMotor().setSpeed(20);
		 myMotor.getFrontMotor().rotateTo(15);
		 Delay.msDelay(100);
		 dLeft = (int) _sonic.getDistance();
		
		 myMotor.getFrontMotor().setSpeed(20);
		 myMotor.getFrontMotor().rotateTo(0);
		 Delay.msDelay(100);
		
		 myMotor.getFrontMotor().setSpeed(20);
		 myMotor.getFrontMotor().rotateTo(-15);
		 Delay.msDelay(100);
		 dRight = (int) _sonic.getDistance();
		 
		 myMotor.getFrontMotor().setSpeed(20);
		 myMotor.getFrontMotor().rotateTo(0);
		 Delay.msDelay(100);
		
		 if(d1Front < dLeft) {
			 minimumValue = d1Front;
		 }
		 else {
			 minimumValue = dLeft;
			 
			 if (minimumValue > dRight){
				 minimumValue = dRight;
			 }
		 }
		 
		System.out.println("minimum value:" + minimumValue);
		
//Status...ROTATING
//			myMotor.movebackwardby(minimumValue/2);//we were here before you left
		myMotor.getFrontMotor().rotate(-80 + minimumValue);
		myMotor.movebackwardby(30);					// the room has an area of 90 x 90 cm square 
		myMotor.getFrontMotor().rotateTo(0);
		myMotor.movebackwardby(15);
		System.out.println("This is from " + myDetect.getQuadrant());
	}

	public void thirdQuadrant(CalibrateDistance _sonic2) {
		/*
		 * third quadrant of the room
		 * pallet is in first half of the DetectionArray.getDetectarraysecond();
		 * 
		*/
		d1Front = (int) _sonic2.getDistance();

		 myMotor.getFrontMotor().setSpeed(20);
		 myMotor.getFrontMotor().rotateTo(15);
		 Delay.msDelay(100);
		 dLeft = (int) _sonic2.getDistance();
		
		 myMotor.getFrontMotor().setSpeed(20);
		 myMotor.getFrontMotor().rotateTo(0);
		 Delay.msDelay(100);
		
		 myMotor.getFrontMotor().setSpeed(20);
		 myMotor.getFrontMotor().rotateTo(-15);
		 Delay.msDelay(100);
		 dRight = (int) _sonic2.getDistance();
		 
		 myMotor.getFrontMotor().setSpeed(20);
		 myMotor.getFrontMotor().rotateTo(0);
		 Delay.msDelay(100);
		
		 if(d1Front < dLeft) {
			 minimumValue = d1Front;
		 }
		 else {
			 minimumValue = dLeft;
			 
			 if (minimumValue > dRight){
				 minimumValue = dRight;
			 }
		 }
		 
		System.out.println("minimum value:" + minimumValue);
		//Status...ROTATING
		myMotor.moveforwardby(minimumValue/2);
		myMotor.getFrontMotor().rotate(90 - minimumValue);
		myMotor.movebackwardby(30);					// the room has an area of 90 x 90 cm square 
		myMotor.getFrontMotor().rotateTo(0);
		System.out.println("This is from " + myDetect.getQuadrant());
	}

	public void secondQuadrant(CalibrateDistance _sonic2) {
		/*
		 * second quadrant of the room
		 * pallet is in second half of the DetectionArray.getDetectarrayfirst();
		*/
		d1Front = (int) _sonic2.getDistance();

		 myMotor.getFrontMotor().setSpeed(20);
		 myMotor.getFrontMotor().rotateTo(15);
		 Delay.msDelay(100);
		 dLeft = (int) _sonic2.getDistance();
		
		 myMotor.getFrontMotor().setSpeed(20);
		 myMotor.getFrontMotor().rotateTo(0);
		 Delay.msDelay(100);
		
		 myMotor.getFrontMotor().setSpeed(20);
		 myMotor.getFrontMotor().rotateTo(-15);
		 Delay.msDelay(100);
		 dRight = (int) _sonic2.getDistance();
		 
		 myMotor.getFrontMotor().setSpeed(20);
		 myMotor.getFrontMotor().rotateTo(0);
		 Delay.msDelay(100);
		 
		 if(d1Front < dLeft) {
			 minimumValue = d1Front;
		 }
		 else {
			 minimumValue = dLeft;
			 
			 if (minimumValue > dRight){
				 minimumValue = dRight;
			 }
		 }
		 
		 System.out.println("minimum value:" + minimumValue);
		 myMotor.movebackwardby(25 - minimumValue);						// the room has an area of 90 x 90 cm square 
		 
		 myMotor.getFrontMotor().rotateTo(45);
		 myMotor.movebackwardby(20);
		 myMotor.getFrontMotor().rotateTo(0);
		 System.out.println("This is from " + myDetect.getQuadrant());
	}

	public void firstQuadrant(CalibrateDistance _sonic2) {
		/*
		 * first quadrant of the room
		 * pallet is in first half of the DetectionArray.getDetectarrayfirst();
		*/
		
		d1Front = (int) _sonic2.getDistance();
		
		 myMotor.getFrontMotor().setSpeed(20);
		 myMotor.getFrontMotor().rotateTo(15);
		 Delay.msDelay(100);
		 dLeft = (int) _sonic2.getDistance();
		
		 myMotor.getFrontMotor().setSpeed(20);
		 myMotor.getFrontMotor().rotateTo(0);
		 Delay.msDelay(100);
		
		 myMotor.getFrontMotor().setSpeed(20);
		 myMotor.getFrontMotor().rotateTo(-15);
		 Delay.msDelay(100);
		 dRight = (int) _sonic2.getDistance();
		 
		 myMotor.getFrontMotor().setSpeed(20);
		 myMotor.getFrontMotor().rotateTo(0);
		 Delay.msDelay(100);
		 
		 if(d1Front < dLeft) {
			 minimumValue = d1Front;
		 }
		 else {
			 minimumValue = dLeft;
			 
			 if (minimumValue > dRight){
				 minimumValue = dRight;
			 }
		 }
		 
		 System.out.println("minimum value:" + minimumValue);
		 myMotor.movebackwardby(40 - minimumValue);						// the room has an area of 90 x 90 cm square 
		 
		 System.out.println("This is from " + myDetect.getQuadrant());
		 myMotor.getFrontMotor().rotateTo(-25);
		 myMotor.movebackwardby(20);
		 myMotor.getFrontMotor().rotateTo(0);
	}

}
