/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Cool Platformer
*******/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SidebarPanel extends JPanel implements ActionListener
{
	private EditorScreen ES;
	private JButton[] tools = new JButton[3];
	private String[] toolNames = {"Draw", "Erase", "Fill"};
	private String currTool;
	
	private JButton[] fieldList = new JButton[4];
	private String[] fieldListNames = {"Up", "Right", "Down", "Left"};
	private int currField;
	
	private JButton save;
	private JButton clear;
	
	public SidebarPanel(Dimension sidebarSize)
	{
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		for(int i = 0; i < tools.length; i++)
		{
			add(Box.createRigidArea(new Dimension(0, 10)));
			tools[i] = new JButton(toolNames[i]);
			tools[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			tools[i].addActionListener(this);
			add(tools[i]);
		}
		tools[0].setEnabled(false);
		currTool = "Draw";
				
		add(Box.createRigidArea(new Dimension(0, 30)));
		save = new JButton("Save");
		save.setAlignmentX(Component.CENTER_ALIGNMENT);
		save.addActionListener(this);
		add(save);
		
		add(Box.createRigidArea(new Dimension(0, 10)));
		clear = new JButton("Clear");
		clear.setAlignmentX(Component.CENTER_ALIGNMENT);
		clear.addActionListener(this);
		add(clear);
		
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
			ES.saveToManager();
		}
		else if(ae.getActionCommand().equals("Clear"))
		{
			ES.clear();
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
