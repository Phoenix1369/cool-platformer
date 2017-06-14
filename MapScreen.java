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

class MapScreen extends JPanel implements ActionListener, ComponentListener
{
	JButton[] defaultMapsArr = new JButton[8];
	JButton[] userMapsArr = new JButton[8];
	String purpose = "Start";
	EditorScreen ES;
	
	MapScreen(Dimension dim, EditorScreen editor)
	{
		addComponentListener(this);
		
		ES = editor;
		
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
		if(purpose.equals("Start"))
		{
			//char
			String mapToPlay = "";
			for(int i = 0; i < defaultMapsArr.length; i++)
			{
				if(ae.getSource() == defaultMapsArr[i])
					mapToPlay = "D" + i + ".txt";
			}
			for(int i = 0; i < userMapsArr.length; i++)
			{
				if(ae.getSource() == userMapsArr[i])
					mapToPlay = "U" + i + ".txt";
			}
			CoolPlatformer.changeScreen("GameScreen", mapToPlay);
		}
		else if(purpose.equals("Save"))
		{
			String mapToSave = "";
			for(int i = 0; i < userMapsArr.length; i++)
			{
				if(ae.getSource() == userMapsArr[i])
					mapToSave = "U" + i + ".txt";
			}
			ES.saveToManager(System.getProperty("user.dir") + "/include/levels", mapToSave);
			CoolPlatformer.changeScreen("TitleScreen");
		}
		else if(purpose.equals("Load"))
		{
			String mapToLoad = "";
			for(int i = 0; i < defaultMapsArr.length; i++)
			{
				if(ae.getSource() == defaultMapsArr[i])
					mapToLoad = "D" + i + ".txt";
			}
			for(int i = 0; i < userMapsArr.length; i++)
			{
				if(ae.getSource() == userMapsArr[i])
					mapToLoad = "U" + i + ".txt";
			}
			ES.loadFromManager(System.getProperty("user.dir") + "/include/levels", mapToLoad);
			CoolPlatformer.changeScreen("EditorScreen");
		}
	}	// end method actionPerformed
	
	public void setPurpose(String purp)
	{
		purpose = purp;
	}
	
	public void componentShown(ComponentEvent e)
	{
		if(purpose.equals("Save"))
		{
			for(int i = 0; i < defaultMapsArr.length; i++)
			{
				defaultMapsArr[i].setEnabled(false);
			}
		}
	}
	public void componentHidden(ComponentEvent e)
	{
		for(int i = 0; i < defaultMapsArr.length; i++)
		{
			defaultMapsArr[i].setEnabled(true);
		}
	}
	public void componentResized(ComponentEvent e){   }
	public void componentMoved(ComponentEvent e){   }
}	// end class MapScreen
