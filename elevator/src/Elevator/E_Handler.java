package Elevator;

import java.util.Scanner;
import java.awt.*;

import javax.swing.*;
import java.awt.event.*;

public class E_Handler
{
	E_Thread eThread;
	Elevator[] elev = new Elevator[4]; 
	CenterPanel cPanel;
	
	boolean threadOperate = false; 
	
	
	public E_Handler(CenterPanel cp)
	{
		cPanel = cp;
		eThread = new E_Thread(cPanel);
		elev[0] = cPanel.elevA;
		elev[1] = cPanel.elevB;
		elev[2] = cPanel.elevC;
		elev[3] = cPanel.elevD;
	}
	
	/*
	 * Moving elevator by case
	 * */
	public char moving(int curF, int desF, SubFrame sb)
	{
		Scanner keyboard = new Scanner(System.in);

		if(threadOperate == false) 
		{
			eThread.start();
			threadOperate = true;
		}

		
		
		Elevator nearestElev;
		
		sb.startLabel.setText(curF+" ");
		sb.desLabel.setText(" "+desF);
		
		// If the upper / lower elevators are not available
		if( (curF <= 5 && desF >= 6) || (curF >= 6 && desF <= 5) ) 
		{
			// If both A and B each have fewer than the maximum,
			// compare the waiting time and choose the less.
			if(elev[0].num_person < 10 && elev[1].num_person < 10) 
			{
				double wtA = waitingTime(curF, desF, elev[0]);
				double wtB = waitingTime(curF, desF, elev[1]);
			
				sb.aText.setText(String.format("%.1f",wtA)+" sec");
				sb.bText.setText(String.format("%.1f",wtB)+" sec");
				sb.cText.setText("");
		
				if( wtA <= wtB )  
					nearestElev = elev[0];
				else
					nearestElev = elev[1];
			}
			else 
			{
				// A is not full
				if(elev[0].num_person < 10) 				{
					double wtA = waitingTime(curF, desF, elev[0]);
					sb.aText.setText(String.format("%.1f",wtA)+" sec");
					sb.bText.setText("");
					sb.cText.setText("");
					nearestElev = elev[0];
				}
				// B is not full
				else if(elev[1].num_person < 10)
				{
					double wtB = waitingTime(curF, desF, elev[1]);
					sb.bText.setText(String.format("%.1f",wtB)+" sec");
					sb.aText.setText("");
					sb.cText.setText("");
					nearestElev = elev[1];
				}
				// Both A and B are full
				else 
					return 'F'; 
			}
		}
		// If both the current and destination floors are less than 5 floors
		// Only A, B, and D can move
		else if(curF <= 5 && desF <= 5)
		{
			double[] wt = new double[] {9999, 9999, 9999, 9999};
			String[] sec = new String[] {"Full", "Full", "Full", "Full"};
			// If any of A, B, D elevators are not full
			if(elev[3].num_person < 10 || elev[0].num_person < 10 || elev[1].num_person < 10)
			{ 
				for(int k=0; k<4; k++) 
				{   // Get waiting time, except for upper elevator
					if(k != 2 && elev[k].num_person < 10)  
					{
						wt[k] = waitingTime(curF, desF, elev[k]);
						sec[k] = String.format("%.1f", wt[k])+" sec";
					} 
				}
				int minIdx = 0;
				for(int k=1; k<4; k++) 
				{
					if(k != 2 && wt[k] <= wt[minIdx])  
						minIdx = k;
				}
				
				sb.dText.setText(sec[3]);
				sb.aText.setText(sec[0]);
				sb.bText.setText(sec[1]);
				nearestElev = elev[minIdx];
			}
			else // If A, B, D are all full
			{
				sb.dText.setText(sec[3]);
				sb.aText.setText(sec[0]);
				sb.bText.setText(sec[1]);
				return 'F'; 
			}
		}
		// If both the current and destination floors are greater than 6 floors
		// Only A, B, and C can move
		else
		{
			double[] wt = new double[] {9999, 9999, 9999};
			String[] sec = new String[] {"Full", "Full", "Full", "Full"};
			
			// If any of A, B, C elevators are not full
			if(elev[2].num_person < 10 || elev[0].num_person < 10 || elev[1].num_person < 10) //10명이하인 엘리베이터가 하나라도 있다면
			{
				// Get waiting time, except for lower elevator
				for(int k=0; k<3; k++) 
				{
					if(elev[k].num_person < 10)  
					{
						wt[k] = waitingTime(curF, desF, elev[k]);
						sec[k] = String.format("%.1f", wt[k])+" sec";
					}
				}
				int minIdx = 0;
				for(int k=1; k<3; k++) 
				{
					if(wt[k] <= wt[minIdx]) 
						minIdx = k;
				}
						
				sb.cText.setText(sec[2]);
				sb.aText.setText(sec[0]);
				sb.bText.setText(sec[1]);
				nearestElev = elev[minIdx];
			}
			else // If A, B, C are all full
			{
				sb.cText.setText(sec[2]);
				sb.aText.setText(sec[0]);
				sb.bText.setText(sec[1]);
				return 'F'; 
			}				
		}
		
		cPanel.getPerson(curF, desF, nearestElev);
		
		nearestElev.using = true; 
		nearestElev.storeNextDes(curF, desF);
		
		return nearestElev.serial_num; 
	}
	
	/*
	 * Calculate waiting time
	 * */
	public double waitingTime(int curF, int desF, Elevator elev)
	{
		Queue<Integer> floorQ = new Queue<Integer>();
		
		if(elev.des.qPeek() != null)
			getFloorQueue(curF, desF, elev, floorQ);
		
		// If queue is null
		if(floorQ.qIsEmpty() == true)
		{
			int floor_num = 0;
			floor_num = Math.abs(curF - elev.floor) + Math.abs(desF - curF); //Total moving distance of elevator
			return floor_num * 1.2 + 2.5;
		}
		// If queue is not null
		else
		{
			int size = floorQ.getSize();
			int floor_num = 0; 
			int stopfloor = 0; 
			
			floor_num += Math.abs( floorQ.getValue(0) - elev.floor);  
			for(int k=0; k<floorQ.lastIndexOf(desF); k++)
			{
				floor_num += Math.abs( floorQ.getValue(k+1) - floorQ.getValue(k) ); 
				stopfloor++;
			}
	
			return floor_num * 1.2 + stopfloor * 2.5;
		}
	}
	
	/*
	 * Get elevator queue value
	 * */
	public void getFloorQueue(int curF, int desF, Elevator elev, Queue<Integer> floorQ)
	{
		
		for(int k=0; k<elev.des.getSize(); k++)
			floorQ.insertQ(elev.des.getValue(k));
		
		int size = floorQ.getSize();
		int i=1, j;
		int istart = 1;
		
		// If the elevator has to move up and down
		if(elev.updown == true) 
		{
			boolean inserted = false;
			
			// First stored value in the current queue is not the current floor
			if(curF != floorQ.qPeek()) 
			{
				if( (elev.floor < curF && curF < elev.des.qPeek()) || (elev.floor > curF && curF > elev.des.qPeek()) ) 
					istart = 0; 								
				
				if( (curF < elev.des.qPeek() && curF < elev.floor) || (curF > elev.des.qPeek() && curF > elev.floor) )   
					istart = 0;
				
				for( i=istart; i<size; i++)  
				{
					if(600-(curF*60) > elev.y && elev.isMoving == true && elev.updownRT == true) 
					{
						continue;
					}
					if( curF < floorQ.getValue(i) )
					{
						floorQ.insertNormal(i, curF);
						inserted = true;
						i++;
						break;
					}
					else if( curF == floorQ.getValue(i)) 
					{
						i++;
						inserted = true;
						break;
					}
					else
						continue;
				}
				if(inserted == false)
				{
					boolean repeated = false;
					for(int k=1; k<size; k++)
					{
						if(curF == floorQ.getValue(k)) 
						{
							i = k;
							repeated = true;
							break;
						}
					}
					if(repeated ==  false) 
					{
						floorQ.insertQ(curF);
						i = floorQ.getSize()-1;
					}
				}
			}
			// If the current floor is higher than the destination floor
			if(curF > desF) 
			{
				if(floorQ.rearValue() != desF)
					floorQ.insertQ(desF);
				return;
			}
			inserted = false;
			
			size = floorQ.getSize();  
			for(j = i; j <size; j++)
			{
				if( desF < floorQ.getValue(j) )
				{
					floorQ.insertNormal(j, desF);
					inserted = true;
					j++;
					break;
				}
				else if( desF == floorQ.getValue(j)) 
				{
					j++;
					inserted = true;
					break;
				}
				else
					continue;
			}
			if(inserted == false) 
			{
				floorQ.insertQ(desF);
			}
		}
		else // If the elevator has not to move up and down
		{
			boolean inserted = false;
			
			
			if(curF != floorQ.qPeek()) 
			{
				if( (elev.floor < curF && curF < elev.des.qPeek()) || (elev.floor > curF && curF > elev.des.qPeek()) ) 
					istart = 0; 								

				if( (curF < elev.des.qPeek() && curF < elev.floor) || (curF > elev.des.qPeek() && curF > elev.floor) )   
					istart = 0;
				
				for( i=istart; i<size; i++)  
				{
					if(600-(curF*60) < elev.y && elev.isMoving == true && elev.updownRT == false) 
						continue; 
					if( curF > floorQ.getValue(i) )
					{
						floorQ.insertNormal(i, curF);
						inserted = true;
						i++;
						break;
					}
					else if( curF == floorQ.getValue(i))  
					{
						i++;
						inserted = true;
						break;
					}
					else
						continue;
				}
				if(inserted == false) 
				{
					boolean repeated = false;
					for(int k=1; k<size; k++)
					{
						if(curF == floorQ.getValue(k)) 
						{
							i = k;
							repeated = true;
							break;
						}
					}
					if(repeated ==  false) 
					{
						floorQ.insertQ(curF);
						i = floorQ.getSize()-1;
					}
				}
			}
			// If the current floor is lower than the destination floor
			if(curF < desF) 
			{
				if(floorQ.rearValue() != desF)
					floorQ.insertQ(desF);
				return;
			}
			inserted = false;
		
			size = floorQ.getSize();  
			for(j = i; j <size; j++)
			{
				if( desF > floorQ.getValue(j) )
				{
					floorQ.insertNormal(j, desF);
					inserted = true;
					j++;
					break;
				}
				else if( desF == floorQ.getValue(j)) 
				{
					j++;
					inserted = true;
					break;
				}
				else
					continue;
			}
			if(inserted == false) 
				floorQ.insertQ(desF); 
		}
	}

}