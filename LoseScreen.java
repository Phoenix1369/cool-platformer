/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Lose condition JPanel
*******/
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.imageio.ImageIO;

class LoseScreen extends JPanel implements ActionListener
{
	JButton title;
	LoseScreen()
	{
		setLayout(new BorderLayout());
		
		try // Add image
		{
			BufferedImage insImage = ImageIO.read(new File(System.getProperty("user.dir") + "/include/demo/loseImage.png"));
			JLabel insGraphic = new JLabel(new ImageIcon(insImage));
			add(insGraphic, BorderLayout.CENTER);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		title = new JButton("Back to Title");
		title.addActionListener(this);
		add(title, BorderLayout.SOUTH);
	}	// end constructor()
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == title)
		{
			CoolPlatformer.changeScreen("TitleScreen");
		}
	}	// end method actionPerformed
}	// end class LoseScreen
