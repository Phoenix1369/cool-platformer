/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Map Selection Screen
*******/
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.*;

class MapScreen extends JPanel implements ActionListener
{
	JButton[] defaultMapsArr = new JButton[8];
	JButton[] userMapsArr = new JButton[8];
	String purpose = "Start";
	
	MapScreen(Dimension dim)
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel defaultMaps = new JPanel();
		JPanel userMaps = new JPanel();
		
		defaultMaps.setLayout(new GridLayout(2, 4));
		for(int i = 0; i < 8; i++)
		{
			defaultMapsArr[i] = new JButton("DMap "  + i);
			defaultMapsArr[i].addActionListener(this);
			defaultMaps.add(defaultMapsArr[i]);
		}
		
		userMaps.setLayout(new GridLayout(2, 4));
		for(int i = 0; i < 8; i++)
		{
			userMapsArr[i] = new JButton("UMap "  + i);
			userMapsArr[i].addActionListener(this);
			userMaps.add(userMapsArr[i]);
		}
		
		add(defaultMaps);
		add(userMaps);
	}	// end constructor(Dimension)
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == defaultMapsArr[0])
		{
			CoolPlatformer.changeScreen("GameScreen");
		}
	}	// end method actionPerformed
	
	public void setPurpose(String purp)
	{
		purpose = purp;
	}
}	// end class MapScreen
