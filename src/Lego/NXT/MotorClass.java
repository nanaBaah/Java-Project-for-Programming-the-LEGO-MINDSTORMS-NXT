package Lego.NXT;
// Author:		Laxmi Tamang
// Author:		Suman Gajula
// Date:		10.05.2014

import lejos.nxt.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.util.Delay;
public class MotorClass implements MotorInterface {
	private RegulatedMotor frontMotor;
	private RegulatedMotor rearMotor;
	private RegulatedMotor forkLift;
	public RegulatedMotor getFrontMotor() 
	{
		return frontMotor;
	}
	public void setFrontMotor(RegulatedMotor frontMotor) 
	{
		this.frontMotor = frontMotor;
	}
	public RegulatedMotor getRearMotor() 
	{
		return rearMotor;
	}
	public void setRearMotor(RegulatedMotor rearMotor) 
	{
		this.rearMotor = rearMotor;
	}
	public RegulatedMotor getForkLift() 
	{
		return forkLift;
	}
	public void setForkLift(RegulatedMotor forkLift) 
	{
		this.forkLift = forkLift;
	}
	public MotorClass(RegulatedMotor frontMotor, RegulatedMotor rearMotor,RegulatedMotor forkLift) 
	{
		this.frontMotor = frontMotor;
		this.rearMotor = rearMotor;
		this.forkLift = forkLift;
	}
	public MotorClass() 
	{
		this.frontMotor = Motor.A;//default set up
		this.rearMotor = Motor.B;
		this.forkLift = Motor.C;//default 
	}
	//distance= 18cm * rotate_deg/ 360;
	public void moveforwardby(float dist)
	{
		float rotatevalue=dist*360/18;
		this.rearMotor.setSpeed(80);
		this.rearMotor.rotate(-((int)rotatevalue));;
	}
	public  void movebackwardby(float dist)
	{
		float rotatevalue=dist*360/18;
		this.rearMotor.setSpeed(80);
		this.rearMotor.rotate(((int)rotatevalue));
	}
	//when the robot is at most 5 cm left of the pallet
	public void leftToRight()
	{
		movebackwardby(6);
		this.frontMotor.setSpeed(100);
		this.frontMotor.rotate(-45);
		moveforwardby(1.5f);
		this.frontMotor.setSpeed(100);
		this.frontMotor.rotate(45);
		moveforwardby(4);	
	}
	public void RightToLeft()
	{
		movebackwardby(8);
		this.frontMotor.setSpeed(100);
		this.frontMotor.rotate(45);
		moveforwardby(2.0f);
		this.frontMotor.setSpeed(100);
		this.frontMotor.rotate(-45);
		moveforwardby(4);	
	}
	public  void pickup()
	{
		// to put forklift in front of the robot
		// rotate(+ve 90) is top to down by 90 degree
		System.out.println("picking up...................");

		this.rearMotor.setSpeed(60);
		this.rearMotor.rotate(200);//move the robot back

		this.forkLift.setSpeed(45);//lower the hand
		this.forkLift.rotate(140);

		this.rearMotor.setSpeed(60);// go forward
		this.rearMotor.rotate(-150);

		this.forkLift.rotate(-60);// pick up

		this.frontMotor.setSpeed(30);
		this.frontMotor.rotate(30);// turn the robot's front wheel
		this.rearMotor.rotate(-250);// go forward

		this.frontMotor.setSpeed(30);
		this.frontMotor.rotate(-30);// turn the robot

		this.forkLift.rotate(60);// drop it

		this.rearMotor.setSpeed(60);
		this.rearMotor.rotate(160);//move the robot back

		this.forkLift.setSpeed(45);//high the hand
		this.forkLift.rotate(-140);
		
		this.frontMotor.rotate(30);
		this.rearMotor.rotate(90);
		this.frontMotor.rotate(-30);
	}

	public  void rotateLeft45()
	{
		this.frontMotor.setSpeed(80);
		this.frontMotor.rotate(60);  //turn left
		moveforwardby(16);
		this.frontMotor.rotate(-60);  //turn(right) back to zero position 
		moveforwardby(7);
		this.frontMotor.rotate(-50); //turn right
		moveforwardby(10);
		this.frontMotor.rotate(50);  //turn to zero
		Delay.msDelay(500);
		this.frontMotor.rotate(50);  //turn left
		movebackwardby(22); 
		this.frontMotor.rotate(-50);	
	}
	 public void rotateInCorner45() {
		this.frontMotor.setSpeed(100);
		this.frontMotor.rotate(60);//turn left by 60°
		movebackwardby(10);
		Delay.msDelay(500);

		this.frontMotor.rotate(-60);//turn back to zero degree
		movebackwardby(20);
		Delay.msDelay(500);

		this.frontMotor.rotate(60);//turn left
		moveforwardby(10);

		this.frontMotor.rotate(-60);//turn back to zero
		Delay.msDelay(500);
		this.frontMotor.rotate(-20);//turn right 
		moveforwardby(10);
		Delay.msDelay(500);

		int degree = this.frontMotor.getTachoCount();
		System.out.println(degree);
		this.frontMotor.rotate(20);
		moveforwardby(10);
	}
	public  void rotateRight45()
	{
		this.frontMotor.setSpeed(80);
		this.frontMotor.rotate(-50);  //turn right
		moveforwardby(14);
		this.frontMotor.rotate(50);  //turn(left) back to zero position 
		moveforwardby(5);	
		this.frontMotor.rotate(60); //turn left
		moveforwardby(10);	
		this.frontMotor.rotate(-60);  //turn to zero
		Delay.msDelay(500);
		this.frontMotor.rotate(-60);  //turn right
		movebackwardby(18); 
		this.frontMotor.rotate(60);		
	}

	public void resetTachoCount(RegulatedMotor rm) 
	{
		rm.resetTachoCount();
	}

	public void resetTachoCount(RegulatedMotor a, RegulatedMotor b,RegulatedMotor c) 
	{
		a.resetTachoCount();
		b.resetTachoCount();
		c.resetTachoCount();
	}

	public void resetTachoCount(RegulatedMotor a, RegulatedMotor b) 
	{

		a.resetTachoCount();
		b.resetTachoCount();
	}
	
	public  void adjustRobot(int angle,int quadrant){
    	this.frontMotor.resetTachoCount();
    	this.frontMotor.setSpeed(80);
    	if (quadrant==1 || quadrant==2)
    	{if (angle < 0 ) {
			//for (int i = angle; i <= 0; i += 5) {
    		for (int i = 0; i<=-angle; i += 5) {
				this.frontMotor.rotateTo(i+2);
				moveforwardby(2f);
			}
			this.frontMotor.rotateTo(-angle);
			movebackwardby(10.0f);
		//	leftToRight();
			leftToRight();
		//	RightToLeft();
		//	RightToLeft();
			//first quadrant
		}
    	
    	else {
			//for (int j = angle; j >= 0; j -= 4) {
    		for (int j = 0; j <= angle; j += 5) {
				this.frontMotor.rotateTo(-j+2);
				moveforwardby(2f);
				System.out.println("inside for loop"+ j);
			}
			this.frontMotor.rotateTo(-angle);
			System.out.println(" outside for loop"+ angle);
			movebackwardby(10.0f);
			//RightToLeft();
			//RightToLeft();
			//second quadrant
		}}else
    	{if (angle < 0 ) {
			//for (int i = angle; i <= 0; i += 5) {
    		for (int i = 0; i<=-angle; i += 5) {
				this.frontMotor.rotateTo(i+2);
				moveforwardby(2f);
			}
			this.frontMotor.rotateTo(-angle);
			movebackwardby(10.0f);
		//	leftToRight();
		//	leftToRight();
			RightToLeft();
			RightToLeft();
			//third quadrant
		}
    	
    	else {
			//for (int j = angle; j >= 0; j -= 4) {
    		for (int j = 0; j <= angle; j += 5) {
				this.frontMotor.rotateTo(-j+2);
				moveforwardby(2f);
				System.out.println("inside for loop"+ j);
			}
			this.frontMotor.rotateTo(-angle);
			System.out.println(" outside for loop"+ angle);
			movebackwardby(10.0f);
		//	RightToLeft();
			RightToLeft();
		//	leftToRight();
		//	leftToRight();
			//fourth quadrant
		}}
    	resetTachoCount(getFrontMotor());
//    	this.frontMotor.rotateTo(-angle);
//    	this.frontMotor.rotate(-(angle/2+5));
//		movebackwardby(14.0f);
//		this.frontMotor.rotateTo(angle/2);
    
	}
	
	public void stopAll() {
		// TODO Auto-generated method stub
		frontMotor.stop();
		rearMotor.stop();
		forkLift.stop();
		
	}

}




			// When at center left = 39, right 42 cm
			// initialize Ultrasonic Sensor port 1
	