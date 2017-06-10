/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Cool Platformer
*******/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CoolPlatformer 
{
	// Offset so JF Dimensions = JP
	private static final int offX = 6;
	private static final int offY = 29;

	public static final Dimension size = new Dimension(800, 600);
	public static final Dimension sizeJF = new Dimension(size.width+offX, size.height+offY);
	public static JFrame JF;
	public static JPanel JP;
	public static JPanel[] menu;

	public CoolPlatformer() 
	{
		JF = new JFrame("Cool Platformer");
		JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JF.setPreferredSize(sizeJF);
		JF.setResizable(false);
		
		JP = new JPanel();
		JP.setLayout(new CardLayout());
		JP.setFocusable(true);
		JP.requestFocusInWindow();
		
		try 
		{
			Images.loadAll();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}	// end catch

		menu = new JPanel[2]; // Number of Menu Screens
		menu[0] = new EditorScreen(size);
		menu[1] = new GameScreen(size);
		JP.add(menu[1], "GameScreen");
		JP.add(menu[0], "EditorScreen");
		
		JF.add(JP, BorderLayout.CENTER);

		((GameScreen)menu[1]).init();
		
		JF.pack();
		JF.setLocationRelativeTo(null); // Puts JF at Centre of Screen
		JF.setVisible(true);
	}	// end constructor()

	public static void main(String[] args) 
	{
		new CoolPlatformer();
	}	// end method main
}	// end class CoolPlatformer
