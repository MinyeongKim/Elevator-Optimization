package Elevator;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Main
{
   public static void main(String[] args) 
   {
      
      JFrame frm = new JFrame("Elevator");
      CenterPanel cPanel = new CenterPanel(); 
      
      E_Handler elev_handler = new E_Handler(cPanel); 
      FloorPanel fPanel = new FloorPanel(cPanel, elev_handler); 
      
      // Set Frame
      frm.setBounds(10, 10, 700, 640); 
      frm.setLayout(new BorderLayout());
      frm.add(fPanel, BorderLayout.WEST);
      frm.add(cPanel, BorderLayout.CENTER);

      frm.setVisible(true);
      frm.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
   }
}