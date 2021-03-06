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
	private JButton[] tools = new JButton[3]; // Available tools
	private String[] toolNames = {"Draw", "Erase", "Fill"};
	private String currTool;
	
	private JButton[] actions = new JButton[4]; // Available actions
	private String[] actionNames = {"Save", "Load", "Return", "Clear"};
	
	private JButton[] blockList = new JButton[5]; // Block brushes
	private String[] blockListNames = {"B_GROUND", "B_GOAL", "B_ENEMY1", "B_ENEMY2", "B_PLAYER"};
	private ImageIcon[] blockListIcons = {new ImageIcon(Images.tiles[1][0]), new ImageIcon(Images.tiles[2][0]),
											new ImageIcon(Images.sprites[1][2][0]), new ImageIcon(Images.sprites[2][2][0]),
											new ImageIcon(Images.sprites[0][2][0])};
											
	private JButton[] fieldList = new JButton[4]; // Field brushes
	private String[] fieldListNames = {"F_UP", "F_RIGHT", "F_DOWN", "F_LEFT"};
	private ImageIcon[] fieldListIcons;

	private String[] fieldListDisp = {"^", ">", "v", "<"};
											
	private JButton save;
	private JButton load;
	private JButton clear;
	private JButton ret;

	private int currField;
	private int currBlock;
	
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
		tools[0].setEnabled(false); // The first tool is in use by default
		currTool = "Draw";
		
		//Brushes
		JPanel brushesPanel = new JPanel(new GridLayout(1, 2, 5, 5));
		JPanel brushesPanelL = new JPanel(new GridLayout(6, 1, 0, 5));
		JPanel brushesPanelR = new JPanel(new GridLayout(6, 1, 0, 5));
		
		brushesPanelL.add(new JLabel("LMB", JLabel.CENTER)); // Left Mouse Button
		brushesPanelR.add(new JLabel("RMB", JLabel.CENTER)); // Right Mouse Button
		
		for(int i = 0; i < blockList.length; i++)
		{
			blockList[i] = new JButton(blockListIcons[i]);
			blockList[i].setDisabledIcon(blockListIcons[i]);
			blockList[i].setActionCommand(blockListNames[i]);
			blockList[i].addActionListener(this);
			brushesPanelL.add(blockList[i]);
		}
		blockList[0].setEnabled(false); // The first block is in use by default
		currBlock = 1;
		
		fieldListIcons = new ImageIcon[4];
		for(int i = 0; i < fieldListIcons.length; ++i)
			fieldListIcons[i] = new ImageIcon(Images.tint[i]); // Colour-coded Field selection

		for(int i = 0; i < fieldList.length; i++)
		{
			fieldList[i] = new JButton(fieldListDisp[i], fieldListIcons[i]);
			fieldList[i].setDisabledIcon(fieldListIcons[i]);
			fieldList[i].setHorizontalTextPosition(JButton.CENTER);
			fieldList[i].setVerticalTextPosition(JButton.CENTER);
			fieldList[i].setActionCommand(fieldListNames[i]);
			fieldList[i].addActionListener(this);
			brushesPanelR.add(fieldList[i]);
		}
		fieldList[0].setEnabled(false); // The first field is in use by default
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
				currField = i;
			}
		}
		
		for(int i = 0; i < blockListNames.length; i++) //only one block can be active at a time
		{
			if(ae.getActionCommand().equals(blockListNames[i]))
			{
				for(JButton btn : blockList)
					btn.setEnabled(true);
				blockList[i].setEnabled(false);
				currBlock = i + 1;
			}
		}

		if(ae.getActionCommand().equals("Save"))
		{	
			if(ES.hasAllReqs()) // Ensure the map contains a player and a goal
				CoolPlatformer.changeScreen("MapScreen", "Save");
			else
				JOptionPane.showMessageDialog(new JFrame(), "A player object (pink block) AND a goal object (flag) have to be placed before saving.",
												"Warning", JOptionPane.WARNING_MESSAGE);
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
	
	public int getCurrBlock()
	{
		return currBlock;
	}
}	// end class Sidebar
