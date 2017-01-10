/*
 * 	Author	: Nana Baah
 * 	Date	: 30 May 2014
 * 
 */
 

package Lego.NXT;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

import lejos.util.Delay;

public class IdentifyPallet {
	private boolean i;
	private float d_left;
	private float d_right;
	private float minimum_value;
	private   MotorClass myMotor;
	private CalibrateDistance dist1;//= new UltrasonicClass();
	private CalibrateDistance dist2;//= new UltrasonicClass();
	private ColorClass color;

//	public final static  IdentifyPallet pallet = new IdentifyPallet();
	public IdentifyPallet() {
		d_left = 0.0f;
		d_right = 0.0f;
		minimum_value=0.0f;
		i=false;
		myMotor= null;
		dist1= new CalibrateDistance();
		dist2= new CalibrateDistance();
		color= new ColorClass();
	}
	
	public boolean isI() {
		return i;
	}
	public void setI(boolean i) {
		this.i = i;
	}
	public float getD_left() {
		return d_left;
	}
	public void setD_left(float d_left) {
		this.d_left = d_left;
	}
	public float getD_right() {
		return d_right;
	}
	public void setD_right(float d_right) {
		this.d_right = d_right;
	}
	public float getMinimum_value() {
		return minimum_value;
	}
	public void setMinimum_value(float minimum_value) {
		this.minimum_value = minimum_value;
	}
	public MotorClass getMyMotor() {
		return myMotor;
	}
	public void setMyMotor(MotorClass myMotor) {
		this.myMotor = myMotor;
	}
	public CalibrateDistance getDist1() {
		return dist1;
	}
	public void setDist1(CalibrateDistance dist1) {
		this.dist1 = dist1;
	}
	public CalibrateDistance getDist2() {
		return dist2;
	}
	public void setDist2(CalibrateDistance dist2) {
		this.dist2 = dist2;
	}
	public IdentifyPallet(MotorClass myMotor) 
	{
	this.myMotor = myMotor;
	d_left = 0.0f;
	d_right = 0.0f;
	minimum_value=0.0f;
	i=false;
	dist1= new CalibrateDistance();
	dist2= new CalibrateDistance();
}
	//Initial.scanner();
	public void runIT() {	
		adjustMotor();
		ColorDetect_pickUP();
	}
	public void adjustMotor(){
		while (i==false){
			float d1_front= dist1.getTheDistance();
			Delay.msDelay(500);
			float d2_front= dist2.getTheDistance();
			if ((d1_front==d2_front)&& (d2_front <=5)){
				myMotor.moveforwardby(dist1.getTheDistance());
				i=true;
			}
			else if((d1_front==d2_front)&& (d2_front >5))
			{
				//myMotor.getFrontMotor().resetTachoCount();
				 myMotor.resetTachoCount( myMotor.getFrontMotor());
				myMotor.getFrontMotor().setSpeed(20);
				//myMotor.getFrontMotor().setSpeed(20);
				myMotor.getFrontMotor().rotateTo(15);
				//myMotor.getFrontMotor().rotateTo(15);
				Delay.msDelay(100);
				d_left = dist1.getTheDistance();

				//myMotor.getFrontMotor().setSpeed(20);
				myMotor.getFrontMotor().rotateTo(0);
				//myMotor.getFrontMotor().rotateTo(0);
				Delay.msDelay(100);

				//myMotor.getFrontMotor().setSpeed(20);
				myMotor.getFrontMotor().rotateTo(-15);
				//myMotor.getFrontMotor().rotateTo(-15);
				Delay.msDelay(100);
				d_right = dist1.getTheDistance();
				
				//myMotor.getFrontMotor().setSpeed(20);
				myMotor.getFrontMotor().rotateTo(0);
				//myMotor.getFrontMotor().rotateTo(0);
				Delay.msDelay(100);
				/*myMotor.getFrontMotor().setSpeed(20);
				myMotor.getFrontMotor().rotate(15);
				Delay.msDelay(100);*/
					if(d1_front<d_left){
						minimum_value= d1_front;
					}
					else{
						minimum_value= d_left;
						myMotor.RightToLeft();
						
						if (minimum_value > d_right){
							minimum_value = d_right;
							myMotor.leftToRight();
						}
					}
			    System.out.println("minimum value:"+ minimum_value);
			    myMotor.moveforwardby(minimum_value);
				i=true;
			}
		}
	}

	//check if Robot is in front of the pallet
	public  boolean ColorDetect_pickUP(){
		
		int [] flr=color.getFlr();
		//Case01
		// it detects RED and sets it position to the middle to pick up in accurate position

		// first case
		if((flr[0]==2 || flr[0]==4) &&(flr[1]==3 || flr[1]==8)&&(flr[2]==2 || flr[2]==4 || flr[2]==7)){
			myMotor.pickup();
			//when left is zero
			/*	
			 * 
			 * if( (flr[1]==3 || flr[1]==8 ) ) {
					Global.myMotor.pickup();
					return true;
				}	
				else if((flr[1]==2 || flr[1]==4 )){
					Global.myMotor.leftToRight();
					Global.myMotor.pickup();
					return true;
			}*/
		}
		// second case
		else if((flr[0]==3 || flr[0]==8) &&(flr[1]==3 || flr[1]==8)&&(flr[2]==2 || flr[2]==4 || flr[2]==7)){
			myMotor.pickup();				
		}
		//third case
		else if((flr[0]==3 || flr[0]==8) &&(flr[1]==3 || flr[1]==8)&&(flr[2]==3 || flr[2]==8)){
			myMotor.pickup();				
		}
		//fourth case
		else if((flr[0]==3 || flr[0]==8) &&(flr[1]==2 || flr[1]==4|| flr[1]==7)&&(flr[2]==3 || flr[2]==8)){
			myMotor.leftToRight();
			ColorDetect_pickUP();

		}
		// fifth case
		else if((flr[0]==2 || flr[0]==4) &&(flr[1]==2 || flr[1]==4|| flr[1]==7)&&(flr[2]==3 || flr[2]==8)){
			myMotor.leftToRight();
			ColorDetect_pickUP();

		}
		// sixth case
		else if((flr[0]==2 || flr[0]==4) &&( flr[1]==7)&&(flr[2]==2 || flr[2]==4||flr[2]==3 || flr[2]==8)){
			myMotor.leftToRight();
			ColorDetect_pickUP();

		}
		// seventh case
		else if((flr[0]==7) &&( flr[1]==7)&&(flr[2]==2 || flr[2]==4||flr[2]==3 || flr[2]==8)){
			myMotor.leftToRight();
			ColorDetect_pickUP();
		}
		// eighth case
		else if((flr[0]==7) &&( flr[1]==2 || flr[1]==4||flr[1]==3 || flr[1]==8)&&(flr[2]==7)){
			myMotor.RightToLeft(); // change from right to left 3cm
			ColorDetect_pickUP();
		}
		// corner case 1
		else if((flr[0]==3||flr[0]==8) &&( flr[1]==2 || flr[1]==4)&&(flr[2]==7)){
			if(GetCornerDist())
			{
				myMotor.rotateRight45();
//				myMotor.rotateRight_right();
				myMotor.pickup();	
			}
			else
			{
				myMotor.rotateLeft45();
				// myMotor.rotateLeft_left();
				myMotor.pickup();	
			}
			ColorDetect_pickUP();
		}
		// corner case 2
		else if((flr[0]==2 || flr[0]==4) &&( flr[1]==2 || flr[1]==4)&&(flr[2]==7)){
			 myMotor.RightToLeft(); // change from right to left 3cm
			ColorDetect_pickUP();
		}
		// corner case 3
				else if((flr[0]==2 || flr[0]==4) &&( flr[1]==7||flr[1]==6)&&(flr[2]==2 || flr[2]==4)){
					 myMotor.leftToRight();// change from left to right 3cm
					ColorDetect_pickUP();
				}
		else
		{	
			ColorDetect_pickUP();
		}
		return false; 	
	}

	public  boolean GetCornerDist(){
		UltrasonicSensor sonic = new UltrasonicSensor(SensorPort.S4);
		int left_distance = 0;
		int right_distance = 0;

		//get +90degree distance (left) and -180 degree distance (right)
		// compare the two distances
		myMotor.getFrontMotor().rotate(90);
		
		left_distance= sonic.getDistance();
		
		myMotor.getFrontMotor().rotate(-180);
		
		right_distance= sonic.getDistance();
		
		myMotor.getFrontMotor().rotate(90);
		// compare if right is more return true
		if (right_distance>left_distance)
			return true;

		//if distance is less from right return false
		return false;
	}
	//Front-Left-Right (FLR) color s


}
