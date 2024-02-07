package Elevator;

import java.awt.event.*;
import javax.swing.*;

// select destination floor
public class SubButtonHandler implements ActionListener
{
	int curF;
	int desF;
	E_Handler elev_handler;
	SubFrame sb;
	
	public SubButtonHandler(int curF, int desF, SubFrame sb, E_Handler elev_handler)
	{
		this.curF = curF;
		this.desF = desF;
		this.elev_handler = elev_handler;
		this.sb = sb;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		sb.btn[desF-1].setIcon(new ImageIcon("floor\\f"+desF+"_on.png"));
		char serial_num = elev_handler.moving(curF, desF, sb);
		int idx;
		
		if(serial_num == 'A')
		{
			idx = 0;
			elev_handler.elev[0].getDesButton(sb.btn[desF-1], curF, desF);
		}
		else if(serial_num == 'B')
		{
			idx = 1;
			elev_handler.elev[1].getDesButton(sb.btn[desF-1], curF, desF);
		}
		else if(serial_num == 'C')
		{
			idx = 2;
			elev_handler.elev[2].getDesButton(sb.btn[desF-1], curF, desF);
		}
		else if(serial_num == 'D')
		{
			idx = 3;
			elev_handler.elev[3].getDesButton(sb.btn[desF-1], curF, desF);
		}
		else
			idx = 4;
		
		for(int i=0; i<5; i++)
		{
			if(i == idx)
				sb.elevField[i].setEnabled(true);
			else
				sb.elevField[i].setEnabled(false);
		}
	}
}