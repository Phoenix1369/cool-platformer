/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Title Screen
*******/
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.*;

class TitleScreen extends JPanel implements ActionListener
{
	JButton start, editor, instruct;
	TitleScreen(Dimension dim)
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		start = new JButton("Start Game");
		instruct = new JButton("Instructions");
		editor = new JButton("Level Editor");
		start.setAlignmentX(Component.CENTER_ALIGNMENT);
		instruct.setAlignmentX(Component.CENTER_ALIGNMENT);
		editor.setAlignmentX(Component.CENTER_ALIGNMENT);
		start.addActionListener(this);
		instruct.addActionListener(this);
		editor.addActionListener(this);
		add(start);
		add(instruct);
		add(editor);		
	}	// end constructor(Dimension)
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == start)
		{
			CoolPlatformer.changeScreen("MapScreen");
		}
		if(ae.getSource() == instruct)
		{
			CoolPlatformer.changeScreen("InstructScreen");
		}
		if(ae.getSource() == editor)
		{
			CoolPlatformer.changeScreen("EditorScreen");
		}
	}	// end method actionPerformed
}	// end class EditorScreen
