/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Pause Screen
*******/
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.*;

class PauseScreen extends JPanel implements ActionListener
{
	JButton resume, title;
	PauseScreen(Dimension dim)
	{
		resume = new JButton("Resume");
		title = new JButton("Back to Title");
		resume.addActionListener(this);
		title.addActionListener(this);
		add(resume);
		add(title);
	}	// end constructor(Dimension)
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == resume)
		{
			CoolPlatformer.changeScreen("GameScreen");
		}
		if(ae.getSource() == title)
		{
			CoolPlatformer.changeScreen("TitleScreen");
		}
	}	// end method actionPerformed
}	// end class PauseScreen
