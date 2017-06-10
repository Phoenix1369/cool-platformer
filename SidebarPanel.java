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
		{	// Saves to File
			jfc = new JFileChooser();
			switch(jfc.showSaveDialog(this))
			{
			case JFileChooser.APPROVE_OPTION:
				ES.saveToManager(jfc.getCurrentDirectory().toString(), jfc.getSelectedFile().getName());
				break;
			case JFileChooser.CANCEL_OPTION:
				break;
			}
		}
		else if(ae.getActionCommand().equals("Load"))
		{	// Loads from File
			jfc = new JFileChooser();
			switch(jfc.showOpenDialog(this))
			{
			case JFileChooser.APPROVE_OPTION:
				ES.loadFromManager(jfc.getCurrentDirectory().toString(), jfc.getSelectedFile().getName());
				break;
			case JFileChooser.CANCEL_OPTION:
				break;
			}
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
