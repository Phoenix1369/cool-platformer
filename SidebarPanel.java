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
	
	private JButton[] actions = new JButton[4];
	private String[] actionNames = {"Save", "Load", "Return", "Clear"};
	
	private JButton[] fieldList = new JButton[4];
	private String[] fieldListNames = {"^", ">", "v", "<"};

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
		//Tools
		JPanel toolsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
		toolsPanel.add(new JLabel("Current Tool:", JLabel.CENTER));
		for(int i = 0; i < tools.length; i++)
		{
			tools[i] = new JButton(toolNames[i]);
			tools[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			tools[i].addActionListener(this);
			toolsPanel.add(tools[i]);
		}
		add(toolsPanel);
		tools[0].setEnabled(false);
		currTool = "Draw";
		
		//Brushes
		JPanel brushesPanel = new JPanel(new GridLayout(1, 2, 5, 5));
		JPanel brushesPanelL = new JPanel(new GridLayout(3, 1, 0, 5));
		JPanel brushesPanelR = new JPanel(new GridLayout(5, 1, 0, 5));
		
		brushesPanelL.add(new JLabel("LMB", JLabel.CENTER));
		brushesPanelR.add(new JLabel("RMB", JLabel.CENTER));
		
		brushesPanelL.add(new JButton(new ImageIcon(Images.tiles[1][0])));
		brushesPanelL.add(new JButton("1"));
		for(int i = 0; i < fieldList.length; i++)
		{
			fieldList[i] = new JButton(fieldListNames[i]);
			fieldList[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			fieldList[i].addActionListener(this);
			brushesPanelR.add(fieldList[i]);
		}
		fieldList[0].setEnabled(false);
		currField = 0;
		brushesPanel.add(brushesPanelL);
		brushesPanel.add(brushesPanelR);
		add(brushesPanel);
		
		//Actions
		JPanel actionsPanel = new JPanel(new GridLayout(5, 1, 0, 5));
		actionsPanel.add(new JLabel("Available Actions:", JLabel.CENTER));
		for(int i = 0; i < actions.length; i++)
		{
			actions[i] = new JButton(actionNames[i]);
			actions[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			actions[i].addActionListener(this);
			actionsPanel.add(actions[i]);
		}
		add(actionsPanel);
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
