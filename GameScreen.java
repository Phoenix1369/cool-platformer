/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Main Game Screen
*******/
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

class GameScreen extends JPanel implements ActionListener, Runnable, ComponentListener
{
	public static final int FPS = 40; // 20;
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
	private static final String P_KEY = "p.p";

	private static Block[][] blocks;
	private static Thread gameScreen;
	private static Timer timer;

	// Entity Objects
	private static Player mainChar;
	private static ArrayList<Enemy> enemies;
	private static Vector2 goalPos;
	
	private static boolean winLose;

	GameScreen(Dimension dim)
	{
		addComponentListener(this);
		
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
		
		this.getInputMap(WIFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0, false), P_KEY);
		this.getActionMap().put(P_KEY, new ChangeScreenAction());

		timer = new Timer(delay, this);
	}	// end constructor()

	public void freeze(boolean yesOrNo)
	{
		//Placeholder: all relevant game states should be frozen here
		mainChar.freeze(yesOrNo);
		for(Enemy ene: enemies)
			ene.freeze(yesOrNo);
	}	// end method freeze

	public static final Dimension getDlen() { return dlen; } // end method getDlen

	public void init(String fileName)
	{
		gameScreen = new Thread(this);

		winLose = false;
		enemies.clear();
		
		// Load level
		StageManager.loadMap(System.getProperty("user.dir") + "/include/levels", fileName, blocks);
		goalPos = StageManager.getGoalPos(System.getProperty("user.dir") + "/include/levels", fileName, blocks);
		gameScreen.start();
		
		// Replace Enemy blocks with Enemies
		for(int i = 0; i < blocks.length; ++i)
		{
			for(int j = 0; j < blocks[i].length; ++j)
			{
				if(blocks[i][j].getBlock() == Block.ENEMY1)
					enemies.add(new NormalEnemy(j * Block.getLen(),  i * Block.getLen()));
				else if(blocks[i][j].getBlock() == Block.ENEMY2)
					enemies.add(new HomingEnemy(j * Block.getLen(),  i * Block.getLen()));
				else if(blocks[i][j].getBlock() == Block.PLAYER)
				{
					mainChar.setPos(new Vector2((j-1) * Block.getLen(),  (i-1) * Block.getLen()));
					mainChar.setVel(new Vector2());
				}
				else	// Not an Enemy
					continue;
				blocks[i][j].setBlock(Block.AIR);
			}
		}					
		
	}	// end method init

	@Override // Interface: ActionListener
	public void actionPerformed(ActionEvent ae)
	{
		mainChar.advance();
		for(Enemy ene: enemies)
			ene.advance();
		this.repaint();
		if(intersects(mainChar.getPos(), goalPos) && !winLose)
		{
			winLose = true;
			CoolPlatformer.changeScreen("WinScreen");
		}
		else
		{
			for(Enemy ene: enemies)
			{
				if(intersects(mainChar.getPos(), ene.getPos()) && !winLose) 
				{
					winLose = true;
					CoolPlatformer.changeScreen("LoseScreen");
				}
			}
		}
	}	// end method actionPerformed
	
	public static Block getBlocks(int y, int x)
	{
		return blocks[y+edW][x+edW];
	}	// end method getBlocks

	public static Player getP() { return mainChar; } // end method getP

	@Override // Superclass: JPanel
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		// Clears JPanel
		g.clearRect(0, 0, getWidth(), getHeight());

		// Draws Blocks
		for(int i = edW; i < blocks.length-edW; ++i)
			for(int j = edW; j < blocks[i].length-edW; ++j)
				blocks[i][j].draw(g, blocks[i-1][j].getBlock()==1);

		// Draws Enemies
		for(Enemy ene: enemies)
			ene.draw(g);
		// Draws Main Character
		mainChar.draw(g);
	}	// end method paintComponent

	public boolean intersects(Vector2 a, Vector2 b)
	{
		int bLen = Block.getLen();
		return (a.X < b.X + bLen && b.X < a.X + bLen && a.Y < b.Y + bLen && b.Y < a.Y + bLen);
	}
	
	public void componentShown(ComponentEvent e)
	{
		freeze(false);
	}
	public void componentHidden(ComponentEvent e)
	{
		freeze(true);
	}
	public void componentResized(ComponentEvent e){   }
	public void componentMoved(ComponentEvent e){   }
	
	@Override // Interface: Runnable
	public void run()
	{
		timer.start();
	}	// end method run
	
	class ChangeScreenAction extends AbstractAction
	{		
		@Override // Superclass: AbstractAction
		public void actionPerformed(ActionEvent ae)
		{
			CoolPlatformer.changeScreen("PauseScreen");
		}	// end method ActionPerformed
	}	// end class ChangeScreenAction
	
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
