package Elevator;

import javax.swing.*;

public class Elevator
{
   
   int floor; 
   int initialFloor; 
   int low; 
   int high; 
   boolean updown; 
   boolean updownRT; 
   char serial_num; 
   int x, y;
   int door = 1; 
   boolean isMoving = false; 

   Person[] psn = new Person[30]; 
   int num_person = 0; 
   
   boolean using = false; 
   boolean exactFloor = true; 
   
   JButton[] btn = new JButton[10];
   int[] btnNum = new int[10];
   int[] btnDes = new int[10];
   
   Queue<Integer> des = new Queue<Integer>();  
   
   ImageIcon closed_E = new ImageIcon("img\\basket1.png");
   ImageIcon open1_E = new ImageIcon("img\\basket2.png");
   ImageIcon open2_E = new ImageIcon("img\\basket3.png");
   
   /*
    * Set initial elevator
    * */
   public Elevator(int low, int high, char sn)
   {
      
      this.low = low;
      this.high = high;
      serial_num = sn;
      
      if(serial_num == 'A'){
         floor = 5;
         initialFloor = 5;
         x = 70; 
         y = 300; 
      }
      else if(serial_num == 'B') {
         floor = 6;
         initialFloor = 6;
         x = 270;
         y = 240;
      }
      else if(serial_num == 'C'){
         floor = 8;
         initialFloor = 8;
         x = 470;
         y = 120;
      }
      else{  
         floor = 3;
         initialFloor = 3;
         x = 470;
         y = 420;
      }
   }
   
   public void setInitialFloor(int initialFloor)
   {
      this.initialFloor = initialFloor;
   }
   
   /*
    * Get destination by button
    **/
   public void getDesButton(JButton btn, int btnNum, int btnDes)
   {
      for(int i=0; i<10; i++)
      {   
         if(this.btn[i] == null)
         {
            this.btn[i] = btn;
            this.btnNum[i] = btnNum;
            this.btnDes[i] = btnDes;
            break;
         }
      }
   }
   
   /*
    * Store next destination
    * */
   public void storeNextDes(int curF, int desF)
   {
      if(des.rearValue() != null ) 
      {
         insertFunction(curF, desF); 
      }
      else 
      {
         if(curF < desF)
         {
            updown = true;
            updownRT = true;
         }
         else
         {
            updown = false;
            updownRT = false;
         }
         
         des.insertQ(curF);
         des.insertQ(desF);
      }
   }
   
   /*
    * If the condition is met
    * assign true the inserted value 
    * */
   public void insertFunction(int curF, int desF)
   {
      int size = des.getSize();
      int i=1, j;
      int istart = 1;
      
      if(updown == true) 
      {
         boolean inserted = false;
         
         if(curF != des.qPeek()) 
         {
            if( (floor < curF && curF < des.qPeek()) || (floor > curF && curF > des.qPeek()) ) 
               istart = 0;                         
            
            if( (curF < des.qPeek() && curF < floor) || (curF >des.qPeek() && curF > floor) )   
               istart = 0;  
            
            for( i=istart; i<size; i++)  
            {
               if(600-(curF*60) > y && updownRT == true) 
                  break;
               
               if( curF < des.getValue(i) )
               {
                  des.insertNormal(i, curF);
                  inserted = true;
                  i++;
                  break;
               }
               else if( curF == des.getValue(i))  
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
                  if(curF == des.getValue(k)) 
                  {
                     i = k;
                     repeated = true;
                     break;
                  }
               }
               if(repeated ==  false) 
               {
                  des.insertQ(curF);
                  i = des.getSize()-1;
               }
            }
         }
         if(curF > desF) 
         {
            if(des.rearValue() != desF)
               des.insertQ(desF);
            return;
         }
         inserted = false;
         
         size = des.getSize();  
         for(j = i; j <size; j++)
         {
            if( desF < des.getValue(j) )
            {
               des.insertNormal(j, desF);
               inserted = true;
               j++;
               break;
            }
            else if( desF == des.getValue(j)) 
            {
               j++;
               inserted = true;
               break;
            }
            else
               continue;
         }
         if(inserted == false) 
            des.insertQ(desF); 
      }
      else 
      {
         boolean inserted = false;
         
         if(curF != des.qPeek()) 
         {
            if( (floor < curF && curF < des.qPeek()) || (floor > curF && curF > des.qPeek()) ) 

            	if( (curF < des.qPeek() && curF < floor) || (curF >des.qPeek() && curF > floor) )   
               istart = 0;
            
            for( i=istart; i<size; i++) 
            {
               if(600-(curF*60) < y  && updownRT == false) 
                  break;
               if( curF > des.getValue(i) )
               {
                  des.insertNormal(i, curF);
                  inserted = true;
                  i++;
                  break;
               }
               else if( curF == des.getValue(i)) 
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
                  if(curF == des.getValue(k)) 
                  {
                     i = k;
                     repeated = true;
                     break;
                  }
               }
               if(repeated ==  false)
               {
                  des.insertQ(curF);
                  i = des.getSize()-1;
               }
            }
         }
         if(curF < desF) 
         {
            if(des.rearValue() != desF)
               des.insertQ(desF);
            return;
         }
         inserted = false;
         
         size = des.getSize();  
         for(j = i; j <size; j++)
         {
            if( desF > des.getValue(j) )
            {
               des.insertNormal(j, desF);
               inserted = true;
               j++;
               break;
            }
            else if( desF == des.getValue(j))  
            {
               j++;
               inserted = true;
               break;
            }
            else
               continue;
         }
         if(inserted == false) 
            des.insertQ(desF); 
      }
   }
   
   
   /*
    * If the passenger was picked up
    * Return true
    * */
   public boolean pickupComplete()
   {
      int count = 0;
      int p_num = 0;
      for(int i=0; i<30; i++)
      {
         if(psn[i] != null)
         {
            if(psn[i].willRide == true && psn[i].floor == floor && isMoving == false) 
            {
               p_num++;
               if(x <= psn[i].x && psn[i].x <= x + 60)
                  count++;
            }
         }
      }

      if(count == p_num)
         return true;
      else
         return false;
   }
   
   public boolean havePerson()
   {
      int count = 0;
      for(int i=0; i<30; i++)
      {
         if(psn[i] != null)
         {
            if(psn[i].x <= x + 60) 
               count++;
         }
      }
      if(count != 0) 
         return true;
      else
         return false;
   }
   public boolean getoutComplete() 
   {
      int count = 0;
      for(int i=0; i<30; i++)
      {
         if(psn[i] != null && psn[i].willRide == true)
         {
            if(psn[i].des == floor && psn[i].floor == floor) 
            {
               if( psn[i].x <= x + 60) 
                  count++;
               else
               {
                  psn[i].willRide = false; 
                  num_person--; 
               }
            }
         }
      }
      if(count == 0) 
         return true;
      else
         return false;
   }
   public void doorOpened()
   {
      for(int i=0; i<30; i++)
      {
         if(psn[i] != null)
         {
            if(psn[i].des == floor && psn[i].floor == floor) 
            {                                          
               psn[i].isOpen = true;
            }
         }
      }
   }

   public void pickupPerson(Person newPerson)
   {
      if(num_person < 10)  
      {
         for(int i=0; i<30; i++)
         {
            if(psn[i] == null)
            {
               psn[i] = newPerson;
               break;
            }
         }
         num_person++;  
      }
      else
         System.out.println("Elevator" + this.serial_num + " is Full !");
   }

   public ImageIcon img() 
   {
      if(door == 1)
         return closed_E;
      else if(door == 2)
         return open1_E;
      else
         return open2_E;
   }
   public void addY(int destination) 
   { 
      isMoving = true; 
      updownRT = false; 
      
      if(des.getSize() >= 2 && des.getValue(0) < des.getValue(1))
         updown = true;
      else
         updown = false;
      if(y < 600-(60*destination))
      {
         y++; 
         if(y%60 == 0)
         {
            floor = 10 - (y/60);
            exactFloor = true;
         }
         else 
            exactFloor = false;
      }
   } 
   public void subtractY(int destination) 
   { 
      isMoving = true; 
      updownRT = true; 
      
      if(des.getSize() >= 2 && des.getValue(0) < des.getValue(1))
         updown = true;
      else
         updown = false;
      if(y > 600-(60*destination))
      {
         y--;
         if(y%60 == 0)
         {
            floor = 10 - (y/60);
            exactFloor = true;
         }
         else 
            exactFloor = false;
      }
   } 
   public boolean isReach(int destination) 
   {
      if(y == 600-(60*destination))
         return true;
      else
         return false;
   }
   public void rideElevator()
   {
      for(int i=0; i<30; i++)
      {
         if(psn[i] != null)
         {
            if(psn[i].floor == floor && isMoving == false)  
               psn[i].rideElev = true;
         }
      }
   }
}