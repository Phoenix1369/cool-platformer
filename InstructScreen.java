/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Instruction Screen
*******/
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.imageio.ImageIO;

class InstructScreen extends JPanel implements ActionListener
{
	JButton resume;
	InstructScreen(Dimension dim)
	{
		setLayout(new BorderLayout());
		
		try
		{
			BufferedImage insImage = ImageIO.read(new File(System.getProperty("user.dir") + "/include/demo/instructImage.png"));
			JLabel insGraphic = new JLabel(new ImageIcon(insImage));
			add(insGraphic, BorderLayout.CENTER);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		resume = new JButton("Return");
		resume.addActionListener(this);
		add(resume, BorderLayout.SOUTH);
	}	// end constructor(Dimension)
	
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource() == resume)
		{
			CoolPlatformer.changeScreen("TitleScreen");
		}
	}	// end method actionPerformed
}	// end class InstructScreen
