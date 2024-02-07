package Elevator;

import javax.swing.ImageIcon;

public class E_Thread extends Thread
{
	CenterPanel cPanel;
	Elevator[] elev = new Elevator[4];
	Person[] psn = new Person[100];
	
	public E_Thread(CenterPanel cPanel)
	{
		this.cPanel = cPanel;
		elev[0] = cPanel.elevA;
		elev[1] = cPanel.elevB;
		elev[2] = cPanel.elevC;
		elev[3] = cPanel.elevD;
		psn = cPanel.psn;
	}
	
	/*
	 * Elevator and passenger moving
	 * */
	public void run()
	{
		int[] time = new int[4]; 
		int[] notusingTime = new int[4]; 
		while(true)
		{
			
			cPanel.repaint(); 
			
			for(int i=0; i<100; i++)
			{
				if(psn[i] != null) 
				{
					if(psn[i].rideElev == false) 
						psn[i].addX(psn[i].des1); 
					else 
					{
						if(psn[i].floor == psn[i].des && psn[i].isOpen == true) 
						{				
							psn[i].addX(psn[i].rightEnd); 
						}
						else 
						{
							if(psn[i].elev.using == true)
							{
								if(psn[i].elev.floor < psn[i].elev.des.qPeek()) 
								{
									psn[i].subtractY(psn[i].elev.des.qPeek());	
								}
								else if(psn[i].elev.floor > psn[i].elev.des.qPeek())
								{
									psn[i].addY(psn[i].elev.des.qPeek());
								}
							}
						}
					}
				}
			}
			
			// Elevator A, B, C and D move
			for(int i=0; i<4; i++)
			{
				if(elev[i].using == true) 
				{
					notusingTime[i] = 0;
					
					if(elev[i].des.getSize() >= 2)
					{
						if(elev[i].des.getValue(0) == elev[i].des.getValue(1)) 
							elev[i].des.deleteQ();
					}
					if(elev[i].y > 600 - ( 60*elev[i].des.qPeek() ) ) 
					{											
						elev[i].subtractY(elev[i].des.qPeek());	
					}
					else if(elev[i].y < 600 - ( 60*elev[i].des.qPeek() ) )
					{
						elev[i].addY(elev[i].des.qPeek());
					}
					
					boolean doContinue = false;
					char ch;
					for(int k=0; k<30; k++)
					{
						if(elev[i].psn[k] != null)
						{
							
							if(elev[i].psn[k].startF == elev[i].floor && elev[i].exactFloor == true)  
							{
								if(elev[i].psn[k].startF != elev[i].des.qPeek())
								{
									
									elev[i].des.insertNormal(0, elev[i].psn[k].startF);  
								}
								ch = 's';
								deleteSameFloor(elev[i], k, ch); 
								int indicator = elev_door(i, k, time, elev);
							
								if(indicator == 0) 
								{
									doContinue = true;
									break;
								}
								closeDoor(i, k, time, elev, ch);
							}
							else if(elev[i].psn[k].destinationF == elev[i].floor && elev[i].psn[k].startF == -1 && elev[i].exactFloor == true)
							{
								if(elev[i].psn[k].destinationF != elev[i].des.qPeek())
								{
									
									elev[i].des.insertNormal(0, elev[i].psn[k].destinationF);  
								}
								ch = 'd';
								deleteSameFloor(elev[i], k, ch);  
								int indicator = elev_door(i, k, time, elev);
						
								if(indicator == 0)
								{
									doContinue = true;
									break;
								}
								closeDoor(i, k, time, elev, ch);
							}	
						}
					}
					if(doContinue)
						continue;
									
					for(int k=0; k<30; k++)
					{
						if(elev[i].psn[k] != null && elev[i].psn[k].x > 600) 
							elev[i].psn[k] = null;
					}
				}
				else
				{
					if(notusingTime[i] >= 500) 
					{
						if(elev[i].floor < elev[i].initialFloor) 
						{											
							elev[i].subtractY(elev[i].initialFloor);	
						}
						else if(elev[i].floor > elev[i].initialFloor)
						{
							elev[i].addY(elev[i].initialFloor);
						} 
					}
					notusingTime[i]++;
				}
			}
			
			try
			{
				sleep(20);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Set elevator door moving
	 * */
	public int elev_door(int i, int k, int[] time, Elevator[] elev)
	{	
		if(elev[i].havePerson() == true && time[i] == 40) 
		{
			elev[i].door = 2;
			elev[i].isMoving = false; 
		}
		if(elev[i].door == 2 && time[i] == 60)
		{
			elev[i].door = 3;
			elev[i].doorOpened(); 
		}

		if(elev[i].getoutComplete() == false)  
		{											
			time[i]++;
			if(time[i] > 60)
				time[i] = 61; 
			return 0;
		}
		
		if(elev[i].pickupComplete() == false) 
		{
			time[i]++;
			if(time[i] > 60)
				time[i] = 61; 
			return 0;
		}
		return 1;
	}
	
	/*
	 * Set elevator door close timing
	 * */
	public void closeDoor(int i, int k, int[] time, Elevator[] elev, char ch)
	{
		if(elev[i].door == 3 && time[i] == 70) 
		{
			elev[i].door = 2;
			
			for(int d=0; d<10; d++)
			{
				if(elev[i].btn[d] != null && elev[i].btnNum[d] == elev[i].floor)
				{
					elev[i].btn[d].setIcon(new ImageIcon("floor\\f"+elev[i].btnDes[d]+".png"));
					elev[i].btn[d] = null;
					elev[i].btnNum[d] = 0;
					elev[i].btnDes [d] = 0;
				}
			}
		}
		
		if(elev[i].door == 2 && time[i] == 100) 
		{
			elev[i].door = 1;
			
			elev[i].rideElevator();
			time[i] = 0;
			
			deleteSameFloor(elev[i], k, ch); 
			if(ch == 's')
				elev[i].psn[k].startF = -1;
			else
				elev[i].psn[k].destinationF = -1;
			elev[i].des.deleteQ(); 
		}
		
		if(elev[i].des.qPeek() == null)
		{
			elev[i].using = false;
			elev[i].isMoving = false; 
			for(int m=0; m<30; m++)
			{
				if(elev[i].psn[m] != null)
					elev[i].psn[m] = null;
			}
		}
		time[i]++;
	}
	
	/*
	 * If the conditions of the i_th passenger and other passengers are the same
	 * Return -1
	 * */
	public void deleteSameFloor(Elevator elev, int k, char ch)
	{
		int count = 0;
		if(ch == 's')
		{
			for(int i=0; i<30; i++)
			{
				if(elev.psn[i] != null && i != k)
				{
					if(elev.psn[i].startF == elev.psn[k].startF)
					{
						elev.psn[i].startF = -1;
					}
					if(elev.psn[i].destinationF == elev.psn[k].startF && elev.psn[i].startF == -1 )
					{
						elev.psn[i].destinationF = -1;
					}
					if(elev.psn[i].destinationF == elev.psn[k].startF && elev.psn[i].startF != -1) 
						count++;
				}
			}
			
			if(count == 0)
			{
				
				for(int i=1; i<elev.des.getSize(); i++)
				{
					if(elev.des.getValue(i) == elev.psn[k].startF)
						elev.des.deleteByIdx(i);
				}
			}
		}
		else
		{
			for(int i=0; i<30; i++)
			{
				if(elev.psn[i] != null && i != k)
				{
					if(elev.psn[i].destinationF == elev.psn[k].destinationF && elev.psn[i].startF == -1)  
					{
						elev.psn[i].destinationF = -1;
					}
					if( (elev.psn[i].startF == elev.psn[k].destinationF ) || (elev.psn[i].destinationF == elev.psn[k].destinationF && elev.psn[i].startF != -1) ) 
						count++;
				}
			}
			
			if(count == 0)
			{
				
				for(int i=1; i<elev.des.getSize(); i++)
				{
					if(elev.des.getValue(i) == elev.psn[k].destinationF)
						elev.des.deleteByIdx(i);
				}
			}
		}
	}
}