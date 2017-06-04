/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Main Game Screen
*******/
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.*;

class GameScreen extends JPanel implements ActionListener, Runnable
{
	public static final int FPS = 30;
	public static final int delay = 1000 / FPS;
	private static final int edW = 1; // Edge Width around Screen
	private static final int WIFW = JComponent.WHEN_IN_FOCUSED_WINDOW;

	private static final String JUMP = "p.jump";
	private static final String MOVE_LEFT = "p.m_left";
	private static final String MOVE_RIGHT = "p.m_right";
	private static final String DOWN = "p.m_down";
	private static final String JUMP_R = "r.jump";
	private static final String MOVE_LEFT_R = "r.m_left";
	private static final String MOVE_RIGHT_R = "r.m_right";
	private static final String DOWN_R = "r.m_down";

	private static Block[][] blocks;
	private static Thread gameScreen;
	private static Timer timer;

	// Entity Objects
	private static Player mainChar;
	private static ArrayList<Enemy> enemies;

	GameScreen(Dimension dim)
	{
		blocks = new Block[dim.height / Block.getLen() + edW*2][dim.width / Block.getLen() + edW*2];
		for(int i = 0; i < blocks.length; ++i)
			for(int j = 0; j < blocks[i].length; ++j) // Default Tiling
				blocks[i][j] = new Block((j-edW) * Block.getLen(), (i-edW) * Block.getLen(), Entity.DOWN, 0);
		mainChar = new Player();
		enemies = new ArrayList<Enemy>();

		// Key Bindings on release
		this.getInputMap(WIFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), JUMP);
		this.getActionMap().put(JUMP, new SetKeyAction(Entity.UP, true));
		this.getInputMap(WIFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), JUMP_R);
		this.getActionMap().put(JUMP_R, new SetKeyAction(Entity.UP, false));
		
		this.getInputMap(WIFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), DOWN);
		this.getActionMap().put(DOWN, new SetKeyAction(Entity.DOWN, true));
		this.getInputMap(WIFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), DOWN_R);
		this.getActionMap().put(DOWN_R, new SetKeyAction(Entity.DOWN, false));
		
		this.getInputMap(WIFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), MOVE_LEFT);
		this.getActionMap().put(MOVE_LEFT, new SetKeyAction(Entity.LEFT, true));
		this.getInputMap(WIFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), MOVE_LEFT_R);
		this.getActionMap().put(MOVE_LEFT_R, new SetKeyAction(Entity.LEFT, false));
		
		this.getInputMap(WIFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), MOVE_RIGHT);
		this.getActionMap().put(MOVE_RIGHT, new SetKeyAction(Entity.RIGHT, true));
		this.getInputMap(WIFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), MOVE_RIGHT_R);
		this.getActionMap().put(MOVE_RIGHT_R, new SetKeyAction(Entity.RIGHT, false));

		timer = new Timer(delay, this);
	}	// end constructor()

	public void init()
	{
		gameScreen = new Thread(this);

		// Demo [should Load Level here]
		// Hardcode Level blocks
		for(int i = 0; i < blocks.length; ++i) {
			blocks[i][0].setBlock(1);
			blocks[i][blocks[0].length-1].setBlock(1);			
		}	// Left / Right Off-Screen Walls
		for(int j = 0; j < blocks[0].length; ++j)
			blocks[0][j].setBlock(1); // Off-Screen Walls Above
		// Custom Blocks Arranged in Ascending Y-Axis

		// First Left Region
		// Left-Field Test
		for(int i = 2; i <= 8; ++i)
			for(int j = 5; j <= 13; ++j)
				blocks[i][j].setField(Entity.LEFT);
		blocks[2][4].setBlock(1);
		for(int i = 4; i <= 8; ++i) // Upper-Left Wall
			blocks[i][4].setBlock(1);
		for(int j = 5; j <= 13; ++j) // Platform Below 
			blocks[11][j].setBlock(1);
		// Right-Field Test
		for(int i = 2; i <= 8; ++i)
			for(int j = 15; j <= 22; ++j)
				blocks[i][j].setField(Entity.RIGHT);
		blocks[2][23].setBlock(1);
		for(int i = 4; i <= 8; ++i) // Upper-Right Wall
			blocks[i][23].setBlock(1);
		// Up-Field Test
		for(int i = 12; i <= 19; ++i)
			for(int j = 15; j <= 22; ++j)
				blocks[i][j].setField(Entity.UP);
		blocks[11][15].setBlock(1);
		for(int j = 17; j <= 22; ++j) // Upper-Right Wall
			blocks[11][j].setBlock(1);
		for(int j = 0; j <= 22; ++j) // Platform Below 
			blocks[22][j].setBlock(1);

		gameScreen.start();
	}	// end method init

	@Override // Interface: ActionListener
	public void actionPerformed(ActionEvent ae)
	{
		mainChar.advance();
		this.repaint();
	}	// end method actionPerformed

	public static Block getBlocks(int y, int x)
	{
		return blocks[y+edW][x+edW];
	}	// end method getBlocks

	@Override // Superclass: JPanel
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		// Clears JPanel
		g.clearRect(0, 0, getWidth(), getHeight());

		// Draws Blocks
		for(int i = edW; i < blocks.length-edW; ++i)
			for(int j = edW; j < blocks[i].length-edW; ++j)
				blocks[i][j].draw(g);

		// Draws Main Character
		mainChar.draw(g);
	}	// end method paintComponent

	@Override // Interface: Runnable
	public void run()
	{
		timer.start();
	}	// end method run
	
	class SetKeyAction extends AbstractAction
	{
		private int indexToSet;
		private boolean pressedDown;

		SetKeyAction(int indexToSet, boolean pressedDown)
		{
			this.indexToSet = indexToSet;
			this.pressedDown = pressedDown;
		}	// end constructor(int, double)
		
		@Override // Superclass: AbstractAction
		public void actionPerformed(ActionEvent ae)
		{
			mainChar.setKey(indexToSet, pressedDown);
		}	// end method ActionPerformed
	}	// end class SetKeyAction
}	// end class GameScreen
