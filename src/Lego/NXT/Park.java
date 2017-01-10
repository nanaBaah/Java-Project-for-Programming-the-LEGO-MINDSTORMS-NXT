package Lego.NXT;
//Author Deniz Aptula 20.06.2014

import lejos.nxt.*;	
import lejos.nxt.addon.ColorHTSensor;


public class Park { 
	
    private UltrasonicSensor sonic;
	private ColorHTSensor cmps_d;
	private int right, left, last_check; // Distance variables for the Ultrasonic sensor
	private int color_id = 3; // 0 = RED , 1 = GREEN, Red is on the left, 
	//Green is on the right side of garage entrance
	
	Park( UltrasonicSensor u_sensor, ColorHTSensor c_sensor ){ // Class Constructor	
		this.sonic = u_sensor;
		this.cmps_d = c_sensor;
	}
	
	public void moveforwardby(float dist) // Method taken from motor group
	{// Method moves the robot forward by specified centimeters
		float rotatevalue=dist*360/18;
		Motor.B.setSpeed(100);
		Motor.B.rotate(-((int)rotatevalue));
	}
	public void movebackwardby(float dist) // Method taken from motor group
	{// Method moves the robot backward by specified centimeters
		float rotatevalue=dist*360/18;
		Motor.B.setSpeed(100);
		Motor.B.rotate(((int)rotatevalue));
	}

	public int abs(int a) { // Returns the absolute value of an integer
		return (a <= 0) ? 0 - a : a;
	}
	
	public void distance_greater_6cm() throws Exception
	{// When the distance is greater than 11cm while the robot is trying to park, this means 
	 // its front part hit one of edges of the garage if the distance is less than 11cm and greater than
	// 6 cm this means wheel of the robot got stuck thats why it should go back and adjust itself.
		
		movebackwardby(6);
		Motor.A.setSpeed(20);
		Motor.A.rotateTo(0);
		Motor.A.rotateTo(-50);
		Motor.A.rotateTo(50, true);
		while(Motor.A.isMoving()) // While Motor A is moving the color sensor is detecting color
		{
			color_id = cmps_d.getColorID();
			if (color_id == 1 || color_id == 0){ // 0 RED, 1 GREEN
				break;
			}	
		}
		System.out.println("Color detected "+ color_id);
		Thread.sleep(1000);
		// Do this after detection !!!
		Motor.A.setSpeed(100);
		if(color_id == 1){ // 0 RED, 1 GREEN
			// Turn left orientation
			movebackwardby(4);
			Motor.A.rotateTo(20);
			moveforwardby(13);	
		}
		else{
			// Turn right orientation
			movebackwardby(4);
			Motor.A.rotateTo(-20);
			moveforwardby(13);
		}

	}

	
	
	public void distance_greater_11cm() throws Exception 
	{ // When the distance is greater than 11cm while the robot is trying to park, this means 
	 // its front part hit one of edges of the garage if the distance is less than 11cm and greater than
	// 6 cm this means wheel of the robot got stuck thats why it should go back and adjust itself.
		
		movebackwardby(1);
		Motor.A.setSpeed(20);
		Motor.A.rotateTo(0);
		Motor.A.rotateTo(-30);
		Motor.A.rotateTo(30, true);
		while(Motor.A.isMoving()) // While Motor A is moving the color sensor is detecting color
		{
			color_id = cmps_d.getColorID();
			if (color_id == 1 || color_id == 0){ // 0 RED, 1 GREEN
				break; // Break when the color is detected
			}	
		}
		System.out.println("Color detected "+ color_id);
		Thread.sleep(1000);
		
		// Do this after detection!!!
		Motor.A.setSpeed(100);
		if(color_id == 1){ // 0 RED, 1 GREEN
			// Turn left orientation
			Motor.A.rotateTo(0);
			movebackwardby(3);
			Motor.A.rotateTo(40);
			moveforwardby(4);
			Motor.A.rotateTo(0);
			moveforwardby(13);	
			if(sonic.getDistance() > 6)
			{
				movebackwardby(4);
				Motor.A.rotateTo(20);
				moveforwardby(13);
				
			}
		}
		else{
			// Turn right orientation
			Motor.A.rotateTo(0);
			movebackwardby(3);
			Motor.A.rotateTo(-40);
			moveforwardby(4);
			Motor.A.rotateTo(0);
			moveforwardby(13);
			if(sonic.getDistance() > 6)
			{
				movebackwardby(4);
				Motor.A.rotateTo(-20);
				moveforwardby(13);
				
			}
		}

	}
	

	public void park_robot() throws Exception {
		
		Motor.A.setSpeed(100);


		moveforwardby(sonic.getDistance()-16); // The robot is going to park thus it should be in front of the garage
		// When the robot comes to center, the distance to the wall is calculated then subtracting 16 and moving forward
		// by the remaining distance takes the robot in front of the garage, 16cm is decided after numerous tests.

		Motor.A.rotateTo( 92); // 90 prev value // turn the head left for checking the right side distance to the walls
		Thread.sleep(2000);
		left = sonic.getDistance();
		System.out.println("Left distance "+ left);
		Thread.sleep(1000); // Wait for 1000ms
		Motor.A.rotateTo( -100); // -90 prev value // turn the head right for checking the right side distance to the walls
		Thread.sleep(2000);
		right = sonic.getDistance();
		System.out.println("Right distance "+ right);
		Thread.sleep(1000); // Wait for 1000ms

		Motor.A.rotateTo(0);// back to origin, reset motor A, 0 actually

		Thread.sleep(1000); // Wait for 1000ms // cool down

		if (abs(left - right) < 3  ){ // If left, right distance is equal or 1-2 cm greater than each other then 
			// it is considered that the robot is in middle and goes forward directly	
			moveforwardby(15);

			last_check = sonic.getDistance();
			while(last_check > 6){ // Repeat parking adjustment until Ultrasonic sensor detects distance 6 or smaller 
				//which means the robot is parked
			if (last_check > 11){ // 11 at corner stuck ////////////////

				distance_greater_11cm();
			}
			else if(last_check > 6){ // Wheel stuck check /////////////
				distance_greater_6cm();
			}
			last_check = sonic.getDistance(); // End of while loop check the distance again.
			
		}
		}
		else if (left < right) // If right distance is greater the robot is shifted to right
		{
			// shifting the robot to the right	
			Motor.A.rotateTo(-45); // turn head of robot to right
			movebackwardby(5);
			Motor.A.rotateTo(45);
			movebackwardby(5);
			Motor.A.rotateTo(-22);
			moveforwardby(10);
			Motor.A.rotateTo(15); 
			moveforwardby(15);
			
			last_check = sonic.getDistance();
			
			while(last_check > 6){ // Repeat parking adjustment until Ultrasonic sensor detects distance 6 or smaller
				if (last_check > 11){ // 11 at corner stuck /////////////////

					distance_greater_11cm();
				}
				else if(last_check > 6){ // Wheel stuck check ////////////////
					distance_greater_6cm();
				}
				last_check = sonic.getDistance(); // End of while loop check the distance again.
				
			}

		}
		else if(left > right) // If left distance is greater the robot is shifted to left
		{
			// shifting the robot to the left	
			Motor.A.rotateTo(45); // turn head to left
			movebackwardby(5);
			Motor.A.rotateTo(-45);
			movebackwardby(5);
			Motor.A.rotateTo(22);
			moveforwardby(10);
			Motor.A.rotateTo(-15); // -15 prev
			moveforwardby(15);
			Thread.sleep(1000);
			
			last_check = sonic.getDistance();
			
			while(last_check > 6){ // Repeat parking adjustment until Ultrasonic sensor detects distance 6 or smaller
				if (last_check > 11){ // 11 at corner stuck /////////////////

					distance_greater_11cm();
				}
				else if(last_check > 6){ // Wheel stuck check /////////////////////
					distance_greater_6cm();
				}
				last_check = sonic.getDistance(); // End of while loop check the distance again.
				
			}
		}

		Motor.A.stop();
		Motor.B.stop();
	}

}


