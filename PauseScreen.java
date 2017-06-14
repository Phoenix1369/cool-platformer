/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Pause Screen
*******/
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.imageio.ImageIO;

class PauseScreen extends JPanel implements ActionListener
{
	JButton resume, title;
	PauseScreen(Dimension dim)
	{
		setLayout(new BorderLayout());
		
		try
		{
			BufferedImage pauseImage = ImageIO.read(new File(System.getProperty("user.dir") + "/include/demo/pauseImage.png"));
			JLabel pauseGraphic = new JLabel(new ImageIcon(pauseImage));
			add(pauseGraphic, BorderLayout.CENTER);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		JPanel btnPanel = new JPanel(new GridLayout(1, 2));
		
		resume = new JButton("Resume");
		title = new JButton("Back to Title");
		resume.addActionListener(this);
		title.addActionListener(this);
		btnPanel.add(resume);
		btnPanel.add(title);
		add(btnPanel, BorderLayout.SOUTH);
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
