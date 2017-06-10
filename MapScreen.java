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
	JButton GO;
	MapScreen(Dimension dim)
	{
		GO = new JButton("Go");
		GO.addActionListener(this);
		add(GO);
	}	// end constructor(Dimension)
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == GO)
		{
			CoolPlatformer.changeScreen("GameScreen");
		}
	}	// end method actionPerformed
}	// end class MapScreen
