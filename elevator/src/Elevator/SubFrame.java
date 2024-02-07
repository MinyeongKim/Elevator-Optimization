package Elevator;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;

public class SubFrame extends JFrame
{	
	CenterPanel cPanel;
	E_Handler elev_handler;
	JLabel[] elevField;
	JButton[] btn;
	JPanel pathPanel;
	JLabel startLabel, arrowLabel, desLabel;
	
	JPanel wPanel;
	JLabel aLabel, bLabel, cLabel, dLabel; 
	JTextField aText, bText, cText, dText;
	
	public SubFrame(String title, int curF, CenterPanel cPanel, E_Handler elev_handler)
	{
		super(title);
		this.cPanel = cPanel; 
		this.elev_handler = elev_handler;  
	
		JLabel elevAField = new JLabel(new ImageIcon("img\\A.png"));
		JLabel elevBField = new JLabel(new ImageIcon("img\\B.png"));
		JLabel elevCField = new JLabel(new ImageIcon("img\\C.png"));
		JLabel elevDField = new JLabel(new ImageIcon("img\\D.png"));
		JLabel elevFField = new JLabel(new ImageIcon("img\\FULL.png"));
		elevField = new JLabel[5];
		elevField[0] = elevAField;
		elevField[1] = elevBField;
		elevField[2] = elevCField;
		elevField[3] = elevDField;
		elevField[4] = elevFField;
		btn = new JButton[10];
		for(int i=0; i<10; i++)
		{
			btn[i] = new JButton(new ImageIcon("floor\\f"+(i+1)+".png"));
			btn[i].setBackground(Color.red);
			btn[i].setBorderPainted(false); 
			btn[i].setContentAreaFilled(false); 
			btn[i].setPressedIcon(new ImageIcon("floor\\f"+(i+1)+"_on.png"));		
		}
		
		setBounds(710, 10, 295, 530);
		setLayout(new FlowLayout());
		Border border = BorderFactory.createEtchedBorder();
		
		//ePanel
		Border elevBorder = BorderFactory.createTitledBorder(border, "Elevator");
		JPanel ePanel = new JPanel(); 
		ePanel.setBorder(elevBorder);
		ePanel.setLayout(new FlowLayout());
		ePanel.add(elevAField);
		ePanel.add(elevBField);
		ePanel.add(elevCField);
		ePanel.add(elevDField);
		ePanel.add(elevFField);
		ePanel.setPreferredSize(new Dimension(280, 92));
		for(int i=0; i<5; i++)
			elevField[i].setEnabled(false);
		
		Border pathBorder = BorderFactory.createTitledBorder(border, "Start ==> Destination");
		pathPanel = new JPanel();
		pathPanel.setBorder(pathBorder);
		pathPanel.setLayout(new FlowLayout());
		pathPanel.setPreferredSize(new Dimension(280, 60));
		
		startLabel = new JLabel("", SwingConstants.LEFT);		startLabel.setFont(new Font("±¼¸²", 30, 30));
		arrowLabel = new JLabel(new ImageIcon("img/arrow.png"), SwingConstants.CENTER);
		desLabel = new JLabel("", SwingConstants.RIGHT);	desLabel.setFont(new Font("±¼¸²", 30, 30));
		pathPanel.add(startLabel); pathPanel.add(arrowLabel); pathPanel.add(desLabel);
		
		Border wBorder = BorderFactory.createTitledBorder(border, "Waiting Time");
		wPanel = new JPanel(); 
		wPanel.setBorder(wBorder);
		wPanel.setLayout(new GridLayout(2, 4, 2, 2));
		wPanel.setPreferredSize(new Dimension(280, 80));
		
		aLabel = new JLabel("Elevator A ", SwingConstants.RIGHT);
		aText = new JTextField(5); 
		
		bLabel = new JLabel(" Elevator B ", SwingConstants.RIGHT);
		bText = new JTextField(5); 
		
		cLabel = new JLabel("Elevator C ", SwingConstants.RIGHT);
		cText = new JTextField(5); 
		
		dLabel = new JLabel(" Elevator D ", SwingConstants.RIGHT);
		dText = new JTextField(5); 
		
		wPanel.add(aLabel); wPanel.add(aText);
		wPanel.add(bLabel); wPanel.add(bText);
		wPanel.add(cLabel); wPanel.add(cText);
		wPanel.add(dLabel); wPanel.add(dText);
		
		
		Border bBorder = BorderFactory.createTitledBorder(border, "Floor Button");
		JPanel bPanel = new JPanel(); 
		bPanel.setBorder(bBorder);
		bPanel.setLayout(new GridLayout(3, 3));
		bPanel.setPreferredSize(new Dimension(280, 240));
		
		for(int i=0; i<10; i++)
		{
			btn[i].addActionListener(new SubButtonHandler(curF, i+1, this, elev_handler) );
		}
		
		for(int i=0; i<10; i++)
		{
			if(i+1 == curF)
				continue;
			else
				bPanel.add(btn[i]);
		}
		
		add(ePanel, BorderLayout.NORTH);
		add(pathPanel);
		add(wPanel, BorderLayout.CENTER);
		add(bPanel, BorderLayout.SOUTH);
		
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
