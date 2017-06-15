/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Title Screen
*******/
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.imageio.ImageIO;

class TitleScreen extends JPanel implements ActionListener
{
	JButton start, editor, instruct;
	TitleScreen()
	{
		setLayout(new BorderLayout());
		
		try // Add image
		{
			BufferedImage titleImage = ImageIO.read(new File(System.getProperty("user.dir") + "/include/demo/titleImage.png"));
			JLabel titleGraphic = new JLabel(new ImageIcon(titleImage));
			add(titleGraphic, BorderLayout.CENTER);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		start = new JButton("Start Game");
		instruct = new JButton("Instructions");
		editor = new JButton("Level Editor");
		start.addActionListener(this);
		instruct.addActionListener(this);
		editor.addActionListener(this);
		JPanel btnPanel = new JPanel(new GridLayout(1,3));
		btnPanel.add(start);
		btnPanel.add(instruct);
		btnPanel.add(editor);
		add(btnPanel, BorderLayout.SOUTH);
	}	// end constructor()
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == start)
		{
			CoolPlatformer.changeScreen("MapScreen", "Start");
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
}	// end class TitleScreen
