/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Main Game Screen
*******/
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

class GameScreen extends JPanel implements ActionListener, Runnable {
	public static final int FPS = 30;
	public static final int delay = 1000 / FPS;
	private static final int WIFW = JComponent.WHEN_IN_FOCUSED_WINDOW;

	private static final String JUMP = "p.jump";
	private static final String MOVE_LEFT = "p.m_left";
	private static final String MOVE_RIGHT = "p.m_right";
	private static final String JUMP_R = "r.jump";
	private static final String MOVE_LEFT_R = "r.m_left";
	private static final String MOVE_RIGHT_R = "r.m_right";

	private static Block[][] blocks;
	private static Player mainChar;
	private static Thread gameScreen;
	private static Timer timer;

	GameScreen(Dimension dim) {
		blocks = new Block[dim.height / Block.getSize()][dim.width / Block.getSize()];
		for(int i = 0; i < blocks.length; ++i)
			for(int j = 0; j < blocks[i].length; ++j) // Default Tiling
				blocks[i][j] = new Block(j * Block.getSize(), i * Block.getSize(), 0, 0);
		mainChar = new Player();

		// Key Bindings
		/*
		this.getInputMap(WIFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), JUMP);
		this.getActionMap().put(JUMP, new AccelerateAction(0.0, -120.0 / FPS, true));
		this.getInputMap(WIFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), MOVE_LEFT);
		this.getActionMap().put(MOVE_LEFT, new AccelerateAction(-60.0 / FPS, 0.0));
		this.getInputMap(WIFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), MOVE_RIGHT);
		this.getActionMap().put(MOVE_RIGHT, new AccelerateAction(+60.0 / FPS, 0.0)); 
		*/
		
		// Key Bindings ver.2 (on release)
		this.getInputMap(WIFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), JUMP);
		this.getActionMap().put(JUMP, new SetKeyAction(0, true));
		this.getInputMap(WIFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), JUMP_R);
		this.getActionMap().put(JUMP_R, new SetKeyAction(0, false));
		
		this.getInputMap(WIFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), MOVE_LEFT);
		this.getActionMap().put(MOVE_LEFT, new SetKeyAction(2, true));
		this.getInputMap(WIFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), MOVE_LEFT_R);
		this.getActionMap().put(MOVE_LEFT_R, new SetKeyAction(2, false));
		
		this.getInputMap(WIFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), MOVE_RIGHT);
		this.getActionMap().put(MOVE_RIGHT, new SetKeyAction(3, true));
		this.getInputMap(WIFW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), MOVE_RIGHT_R);
		this.getActionMap().put(MOVE_RIGHT_R, new SetKeyAction(3, false));


		timer = new Timer(delay, this);
	}	// end constructor()

	public void init() {
		gameScreen = new Thread(this);

		// Hardcode for Demo [should Load Level here]
		for(int j = 15; j >= 0; --j)
			blocks[18][j].setBlock(1);
		for(int j = 0; j < blocks[29].length; ++j)
			blocks[29][j].setBlock(1);
		blocks[27][10].setBlock(1);
		blocks[27][11].setBlock(1);
		blocks[26][10].setBlock(1);
		blocks[25][12].setBlock(1);
		// Hardcode Default Acceleration
		mainChar.setAcc(new Vector2(0.0, 9.8 / FPS));

		gameScreen.start();
	}	// end method init

	@Override // Interface: ActionListener
	public void actionPerformed(ActionEvent ae) {
		mainChar.advance();
		this.repaint();
	}	// end method actionPerformed

	public static Block getBlocks(int y, int x) {
		return blocks[y][x];
	}	// end method getBlocks

	@Override // Superclass: JPanel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Clears JPanel
		g.clearRect(0, 0, getWidth(), getHeight());

		// Draws Blocks
		for(int i = 0; i < blocks.length; ++i)
			for(int j = 0; j < blocks[i].length; ++j)
				blocks[i][j].draw(g);

		// Draws Main Character
		mainChar.draw(g);
	}	// end method paintComponent

	@Override // Interface: Runnable
	public void run() {
		timer.start();
	}	// end method run

	/*class AccelerateAction extends AbstractAction {
		private Vector val;
		private boolean velocity;

		AccelerateAction(double X, double Y) {
			this(X, Y, false);
		}	// end constructor(double, double)

		AccelerateAction(double X, double Y, boolean velocity) {
			this.val = new Vector(X, Y);
			this.velocity = velocity;
		}	// end constructor(double, double, boolean)

		@Override // Superclass: AbstractAction
		public void actionPerformed(ActionEvent ae) {
			if(velocity) {
				if(Math.abs(mainChar.getVel().Y) < 1E-6)
					mainChar.accl(this.val);
			}
			else
				mainChar.move(this.val);
		}	// end method ActionPerformed
	}	// end class AccelerateAction
	*/
	
	class SetKeyAction extends AbstractAction {
		private int indexToSet;
		private boolean pressedDown;
		
		SetKeyAction(int indexToSet, boolean pressedDown)
		{
			this.indexToSet = indexToSet;
			this.pressedDown = pressedDown;
		}	// end constructor(int, double)
		
		@Override // Superclass: AbstractAction
		public void actionPerformed(ActionEvent ae) {
			mainChar.setKey(indexToSet, pressedDown);
		}	// end method ActionPerformed
	}	// end class SetKeyAction
}	// end class GameScreen
