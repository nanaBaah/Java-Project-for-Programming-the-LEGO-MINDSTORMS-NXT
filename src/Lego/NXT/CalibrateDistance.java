/*
 * 	Author	: Nana Baah
 * 	Date	: 30 May 2014
 * 
 */
 
 
package Lego.NXT;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;


public class CalibrateDistance implements CalibrateInterface {

	private float distance;
	private UltrasonicSensor sonic;
	
	public CalibrateDistance() {
		sonic = new UltrasonicSensor(SensorPort.S4);
		distance = 0;
	} 
	
	public float getDistance() {
		return sonic.getDistance();
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}
	
	// get the distance by Ultra Sonic sensor
	public float getTheDistance(){
		
		distance =  sonic.getDistance();
		System.out.println("value:"+ distance);
		//considering the test results we see after 23 cm we see the +1cm precision and below 23cm we see + 5cm range 
		if (distance<=25){
		return (distance-7);
		}
		else{return (distance-3);}
	}

}
