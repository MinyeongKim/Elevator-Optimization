package Elevator;

import java.awt.event.*;
import javax.swing.*;

public class ButtonHandler implements ActionListener
{
	int curF;
	CenterPanel cPanel;
	E_Handler elev_handler;
	public ButtonHandler(int curF, CenterPanel cPanel, E_Handler elev_handler)
	{
		this.curF = curF;
		this.cPanel = cPanel;
		this.elev_handler = elev_handler;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		SubFrame sbtn = new SubFrame("Floor "+curF, curF, cPanel, elev_handler);
	}
}