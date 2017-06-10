/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Instruction Screen
*******/
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.*;

class InstructScreen extends JPanel implements ActionListener
{
	JButton resume;
	InstructScreen(Dimension dim)
	{
		resume = new JButton("Return");
		resume.addActionListener(this);
		add(resume);
	}	// end constructor(Dimension)
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == resume)
		{
			CoolPlatformer.changeScreen("TitleScreen");
		}
	}	// end method actionPerformed
}	// end class InstructScreen
