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
		JF.setPreferredSize(sizeJF);
		JF.setResizable(false);
		
		JP = new JPanel();
		JP.setLayout(new CardLayout());
		JP.setFocusable(true);
		JP.requestFocusInWindow();
		JP.addKeyListener(new KeyAdapter(){
				public void keyPressed(KeyEvent e){                
					if(e.getKeyCode() == KeyEvent.VK_P){
						CardLayout clayoutJP = (CardLayout)JP.getLayout();
						((GameScreen)menu[1]).freeze(state == 0); //freeze the game screen if its being switched away from (current state is 0)
						sidebar.setVisible(state == 0); //only let the sidebar be seen if the game is being switched away from
						if(state == 0)
						{
							sidebar.setLocation((int)(JF.getLocation().getX() + JF.getWidth() + offY), (int)(JF.getLocation().getY()));
							JF.toFront(); //don't give the sidebar focus
						}
						if(state == 1)
						{
							((GameScreen)menu[1]).updateMap();
						}
						clayoutJP.next(JP);
						state = (state + 1) % 2;
					}
				}
			}
		);
		
		try 
		{
			Images.loadAll();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}	// end catch

		menu = new JPanel[2]; // Number of Menu Screens
		menu[0] = new EditorScreen(size, SP);
		SP.registerEditor((EditorScreen)menu[0]); //give the sidebar a reference to the editor screen
		menu[1] = new GameScreen(size);
		JP.add(menu[1], "GameScreen");
		JP.add(menu[0], "EditorScreen");
		
		JF.add(JP, BorderLayout.CENTER);

		((GameScreen)menu[1]).init();
		
		JF.pack();
		JF.setLocationRelativeTo(null); // Puts JF at Centre of Screen
		sidebar.pack();
		JF.setVisible(true);
	}	// end constructor()

	public static void main(String[] args) 
	{
		new CoolPlatformer();
	}	// end method main
}	// end class CoolPlatformer
