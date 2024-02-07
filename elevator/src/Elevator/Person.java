package Elevator;

import javax.swing.*;
import java.util.Random;

public class Person
{
	int startF; // Passenger's Origin Information
	int destinationF; // Passenger destination information. Same as destination, but this declares one more thing to use when the elevator stops.
	
	int floor; // Current floor
	int des;  // Destination floor 
	int elevX; // x-coordinate of elevator
	boolean rideElev = false;  // Boarded the elevator
	boolean willRide = true; // If you need to take the elevator, the value assign true
	int x, y; // x/y-coordinate of Passenger
	int des1; //  in front of the elevator
	int rightEnd; // Get off the elevator and go all the way to the right
	boolean isOpen = false; // To open the door and walk out
	Elevator elev;
	
	ImageIcon psn1 = new ImageIcon("img\\monkey&banana1.png");
	ImageIcon psn2 = new ImageIcon("img\\monkey&banana2.png");
	public Person(int startF, int des, Elevator elev)
	{
		this.startF = startF;
		destinationF = des;
		
		floor = startF;
		this.des = des;
		Random random = new Random(); 
		if(elev.serial_num == 'A'){
			des1 = 70 + random.nextInt(35);  
		}
		else if(elev.serial_num == 'B') {
			des1 = 270 + random.nextInt(35);  
		}
		else {
			des1 = 470 + random.nextInt(35);  
		}
		this.elev = elev;
		rightEnd = 700; 
		x = -30;
		y = 600 - (60*floor); 
	}
	public ImageIcon img()
	{
	
		if(0 <= (x % 12) && (x%12) <= 5)
			return psn1;
		else
			return psn2;
	}
	public void addX(int destination)
	{
		if(x < destination)
			x+=1;
	}
	public void addY(int destination)
	{
		if( y < 600 - (60*destination) + 10 ) 
		{
			y++;
			if((y-20)%60 == 0) 
				floor--;
		}
	}
	public void subtractY(int destination) 
	{
		if( y > 600 - (60*destination) + 10 )
		{
			y--;
			if((y-20)%60 == 0) 
				floor++;
		}
	}
}
