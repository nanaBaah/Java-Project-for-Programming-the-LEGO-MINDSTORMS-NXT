/*
 * 	Author	: Nana Baah
 * 	Date	: 30 May 2014
 * 
 */
 
package Lego.NXT;

import lejos.nxt.addon.ColorHTSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;


public class Controller {

	private    MotorClass myMotor;
	private   IdentifyPallet pallet;
	private  BatteryInterruption _battery;
	private GoingToMiddle myMiddle;
	private PalletDetection myDetect;
	private CalibrateDistance _sonic;
	private Park myPark;
	private UltrasonicSensor uSonic;
	private ColorHTSensor cmps_d_sensor;
	
	public Controller() {
		uSonic = new UltrasonicSensor(SensorPort.S4);
		cmps_d_sensor = new ColorHTSensor(SensorPort.S1);
		myMotor= new MotorClass();
		_sonic = new CalibrateDistance();
		pallet = new IdentifyPallet(myMotor);
		_battery= new BatteryInterruption();
		myMiddle= new GoingToMiddle();
		myDetect = new PalletDetection(_sonic, myMotor );
		myPark = new Park(uSonic, cmps_d_sensor);		
	}
	
	public Controller(MotorClass myMotor, IdentifyPallet pallet, BatteryInterruption _battery, GoingToMiddle myMiddle) {
		this.myMotor = myMotor;
		this.pallet = pallet;
		this._battery = _battery;
		this.myMiddle = myMiddle;
	}
	
	public void start()   {
		
		_battery.start();

		myMotor.resetTachoCount(myMotor.getFrontMotor(), myMotor.getRearMotor());
	
		myDetect.detect();
		
		if (myDetect.isFound()){
			System.out.println("quadrant is "+ myDetect.getQuadrant());
			myMotor.adjustRobot(myDetect.getGetMotorTacho()+5,myDetect.getQuadrant());
			pallet.runIT(); 
		}
		myMiddle.middle();				// Go to middle of room
		
		try {
			myPark.park_robot();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}