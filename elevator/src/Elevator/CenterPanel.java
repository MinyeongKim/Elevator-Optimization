package Elevator;

import java.awt.Graphics;
import javax.swing.*;

// select current floor
public class CenterPanel extends JPanel
{
	ImageIcon bg;
	ImageIcon elevImg;

	Elevator elevA = new Elevator(1, 10, 'A');
	Elevator elevB = new Elevator(1, 10, 'B');
	Elevator elevC = new Elevator(1, 5, 'C');
	Elevator elevD = new Elevator(6, 10, 'D');

	
	Person[] psn = new Person[100]; 
	
	
	public CenterPanel()
	{
		bg = new ImageIcon("img\\background.png");
		setLayout(null);	
	}
	
	// Set background position
	protected void paintComponent(Graphics g)  
	{
		g.drawImage(bg.getImage(), 0, 0, null);
		setOpaque(false);
		super.paintComponent(g);
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		
		// Insert elevator image
		g.drawImage(this.elevA.img().getImage(), this.elevA.x, this.elevA.y, this);
		g.drawImage(this.elevB.img().getImage(), this.elevB.x, this.elevB.y, this);
		g.drawImage(this.elevC.img().getImage(), this.elevC.x, this.elevC.y, this);
		g.drawImage(this.elevD.img().getImage(), this.elevD.x, this.elevD.y, this);
		
		// Insert elevator passengers images
		for(int i=0; i<100; i++)
		{
			if(psn[i] != null)
			{
				g.drawImage(this.psn[i].img().getImage(), this.psn[i].x, this.psn[i].y, this);
				if(psn[i].x > 630) 
					psn[i] = null;
			}
			
		}	
	}
	
	
	public void getPerson(int curF, int desF, Elevator elev)
	{
		int i;
		for(i=0; i<100; i++)
		{
			if(psn[i] == null)
			{
				psn[i] = new Person(curF, desF, elev);
				elev.pickupPerson(psn[i]);  
				break;
			}
		}
		if(i == 100)
		{
			System.out.println("exceed possible input");
		}
	}
}