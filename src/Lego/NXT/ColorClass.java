/*
 * 	Author	: Nana Baah
 * 	Date	: 30 May 2014
 * 
 * 
 */
 
package Lego.NXT;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorHTSensor;
import lejos.util.Delay;


public class ColorClass {

	private MotorClass myMotor;
	private int []flr;//front , left, right
	private ColorHTSensor color;
	
	public ColorClass(MotorClass myMotor, ColorHTSensor color) {
	
		this.myMotor = myMotor;
		this.flr = new int[3];
		this.color = color;
	}
	public ColorClass() {
		this.flr= new int[3];
		this.myMotor= new MotorClass();
		this.color= new ColorHTSensor(SensorPort.S1);
	}
	
	private int [] FLR_color()
	{

		//ColorHTSensor color = new ColorHTSensor(SensorPort.S1);  
		//int[] flr= new int[3];

		Delay.msDelay(100);
		flr[0]=color.getColorID(); //front
		System.out.println("Front Color: " + flr[0]);
		myMotor.getFrontMotor().resetTachoCount();
		
		myMotor.getFrontMotor().setSpeed(20);
		myMotor.getFrontMotor().rotateTo(10);
		Delay.msDelay(100);
		flr[1]=color.getColorID(); //left
		System.out.println("Left Color: " + flr[1]);

		myMotor.getFrontMotor().setSpeed(20);
		myMotor.getFrontMotor().rotateTo(0);
		Delay.msDelay(100);

		myMotor.getFrontMotor().setSpeed(20);
		myMotor.getFrontMotor().rotateTo(-10);
		Delay.msDelay(100);
		flr[2]=color.getColorID(); //right
		System.out.println("Right Color: " + flr[2]);

		myMotor.getFrontMotor().setSpeed(20);
		myMotor.getFrontMotor().rotateTo(0);
		Delay.msDelay(100);

		return flr; 
	}
	public int[] getFlr() {
		return FLR_color();
		
	}
	public void setFlr(int[] flr) {
		this.flr = flr;
	}
}
