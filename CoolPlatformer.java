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
	public static final Dimension sizeSidebar = new Dimension((size.width+offX) / 10, size.height+offY);
	public static JFrame JF;
	public static JFrame sidebar;
	public static SidebarPanel SP;
	public static JPanel JP;
	public static JPanel[] menu;
	public static int state;

	public CoolPlatformer() 
	{
		state = 0; //0 = game screen, 1 = editor screen
		
		sidebar = new JFrame();
		sidebar.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //prevent sidebar from being closed
		sidebar.setResizable(false);
		sidebar.setPreferredSize(sizeSidebar);
		SP = new SidebarPanel(sizeSidebar);
		sidebar.add(SP);
		
		JF = new JFrame("Cool Platformer");
		JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JF.setLayout(new BorderLayout());
		JF.setPreferredSize(sizeJF);
		JF.setResizable(false);

		try 
		{
			Images.loadAll();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}	// end catch

		menu = new JPanel[1]; // Number of Menu Screens
		menu[0] = new GameScreen(size);
		JF.add(menu[0], BorderLayout.CENTER);

		JF.pack();
		JF.setLocationRelativeTo(null); // Puts JF at Centre of Screen
		JF.setVisible(true);

		((GameScreen)menu[0]).init();
	}	// end constructor()

	public static void main(String[] args) 
	{
		new CoolPlatformer();
	}	// end method main
}	// end class CoolPlatformer
