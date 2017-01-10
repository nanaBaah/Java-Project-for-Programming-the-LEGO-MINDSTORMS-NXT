package Lego.NXT;
// Author:		Laxmi Tamang
// Author:		Suman Gajula
// Date:		10.05.2014
import lejos.robotics.RegulatedMotor;



public interface MotorInterface {
	
	public void adjustRobot(int angle,int quadrant);
	public void moveforwardby(float dist);
	public  void movebackwardby(float dist);
	//when the robot is at most 5 cm left of the pallet
	public void leftToRight();
	public void RightToLeft();
//	public void leftToRight6degress();
	public  void pickup();
	public  void resetTachoCount(RegulatedMotor rm);
	public  void resetTachoCount(RegulatedMotor a,RegulatedMotor b, RegulatedMotor c);
	public  void resetTachoCount(RegulatedMotor a,RegulatedMotor b);
	public  void rotateLeft45();
	public  void rotateInCorner45();
	public  void rotateRight45();
	public void stopAll();


}
