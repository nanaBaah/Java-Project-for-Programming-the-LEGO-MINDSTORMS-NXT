package Lego.NXT;
/*
 * 	Author	: Nana Baah
 * 	Date	: 27 May 2014
 * 
 * 	This thread continuously checks the value of the battery
 * 	if the value of the battery less than 4000mv then 
 * 	all requests are aborted and the NXT robot goes to the
 * 	middle of the allocated room
 */


import lejos.nxt.Battery;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.ColorHTSensor;

public class BatteryInterruption extends Thread {
	
	private GoingToMiddle goHome;
	private MotorClass myMotor;
	private boolean checker;
	private float _batt;
	private Park parker;
	private UltrasonicSensor uSonic;
	private ColorHTSensor uColor;
	
	public BatteryInterruption() {
		uColor = new ColorHTSensor(SensorPort.S1);
		uSonic = new UltrasonicSensor(SensorPort.S4);
		goHome = new GoingToMiddle();
		myMotor = new MotorClass();
		checker = true;
		_batt = Battery.getVoltageMilliVolt();
		parker = new Park(uSonic, uColor);
		
		
		System.out.println("battery:" + Battery.getVoltageMilliVolt());
	}
	
	public BatteryInterruption(GoingToMiddle goHome, MotorClass myMotor) {
		this.goHome = goHome;
		this.myMotor = myMotor;
		_batt = Battery.getVoltageMilliVolt();
	}

	public boolean isChecker() {
		return checker;
	}

	public void setChecker(boolean checker) {
		this.checker = checker;
	}
	
	@Override
	public void run() {
		
		while (Battery.getVoltageMilliVolt() <= 4000) {
			if (checker) {
				/*
				 * the return to HOME CODE 
				 */
				checker = false;
				
				try {
					System.out.println("Battery value: " + Battery.getVoltageMilliVolt());
					System.out.println("LOW BATTERY");
					System.out.println("ABORT ALL REQUEST AND GO BACK TO CHARGING UNIT");
					goHome.middle();
					parker.park_robot();
					Sound.twoBeeps();
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("The robot cannot go Home");
				}
				
				/*
				 * IF AT HOME THE TURN OFF THE NXT ROBOT.
				 */
				System.exit(0);
			}
			else {
				myMotor.stopAll();
			}	
		}
	}

	public float get_batt() {
		return _batt;
	}

	public void set_batt(float _batt) {
		this._batt = _batt;
	}
}