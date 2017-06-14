/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: JPanel containing the sidebar for level editor tools
*******/
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

public class SidebarPanel extends JPanel implements ActionListener
{
	private EditorScreen ES;
	private JButton[] tools = new JButton[3];
	private String[] toolNames = {"Draw", "Erase", "Fill"};
	private String currTool;
	
	private JButton[] fieldList = new JButton[4];
	private String[] fieldListNames = {"Up", "Right", "Down", "Left"};

	private JButton save;
	private JButton load;
	private JButton clear;
	private JButton ret;
	private JFileChooser jfc;

	private int currField;
	
	public SidebarPanel()
	{
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel toolsPanel = new JPanel(new GridLayout(4, 1, 0, 5));
		toolsPanel.add(new JLabel("Current Tool:", JLabel.CENTER));
		for(int i = 0; i < tools.length; i++)
		{
			tools[i] = new JButton(toolNames[i]);
			tools[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			tools[i].setMinimumSize(new Dimension(getWidth() - 10, 50));
			tools[i].addActionListener(this);
			toolsPanel.add(tools[i]);
		}
		add(toolsPanel);
		
		tools[0].setEnabled(false);
		currTool = "Draw";
		// "Save" Button
		add(Box.createRigidArea(new Dimension(0, 30)));
		save = new JButton("Save");
		save.setAlignmentX(Component.CENTER_ALIGNMENT);
		save.addActionListener(this);
		add(save);
		// "Load" Button
		add(Box.createRigidArea(new Dimension(0, 10)));
		load = new JButton("Load");
		load.setAlignmentX(Component.CENTER_ALIGNMENT);
		load.addActionListener(this);
		add(load);
		// "Clear" Button
		add(Box.createRigidArea(new Dimension(0, 10)));
		clear = new JButton("Clear");
		clear.setAlignmentX(Component.CENTER_ALIGNMENT);
		clear.addActionListener(this);
		add(clear);
		// "Return" Button
		add(Box.createRigidArea(new Dimension(0, 10)));
		ret = new JButton("Return");
		ret.setAlignmentX(Component.CENTER_ALIGNMENT);
		ret.addActionListener(this);
		add(ret);
		
		add(Box.createRigidArea(new Dimension(0, 20)));
		for(int i = 0; i < fieldList.length; i++)
		{
			add(Box.createRigidArea(new Dimension(0, 10)));
			fieldList[i] = new JButton(fieldListNames[i]);
			fieldList[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			fieldList[i].addActionListener(this);
			add(fieldList[i]);
		}
		fieldList[0].setEnabled(false);
		currField = 0;
	}	// end constructor()
	
	public void actionPerformed(ActionEvent ae)
	{
		for(int i = 0; i < toolNames.length; i++) //only one tool can be active at a time
		{
			if(ae.getActionCommand().equals(toolNames[i]))
			{
				for(JButton btn : tools)
					btn.setEnabled(true);
				tools[i].setEnabled(false);
				currTool = ae.getActionCommand();
			}
		}

		for(int i = 0; i < fieldListNames.length; i++) //only one field can be active at a time
		{
			if(ae.getActionCommand().equals(fieldListNames[i]))
			{
				for(JButton btn : fieldList)
					btn.setEnabled(true);
				fieldList[i].setEnabled(false);
				currField = i; //cheap trick that will need to be fixed with proper constants
			}
		}

		if(ae.getActionCommand().equals("Save"))
		{	
			CoolPlatformer.changeScreen("MapScreen", "Save");
		}
		else if(ae.getActionCommand().equals("Load"))
		{	
			CoolPlatformer.changeScreen("MapScreen", "Load");
		}
		else if(ae.getActionCommand().equals("Clear"))
		{
			ES.clear();
		}
		else if(ae.getActionCommand().equals("Return"))
		{
			CoolPlatformer.changeScreen("TitleScreen");
		}
	}	// end method actionPerformed
	
	public void registerEditor(EditorScreen es)
	{
		ES = es;
	}
	
	public String getCurrTool()
	{
		return currTool;
	}
	
	public int getCurrField()
	{
		return currField;
	}
}	// end class Sidebar
