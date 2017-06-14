/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: HomingEnemy character - floats around obstacles targeting Player
*******/
import java.awt.*;
import java.util.ArrayDeque;

class HomingEnemy extends Enemy
{
	private static final int INFN = 0x3F3F3F3F;
	private static final int delayLim = GameScreen.FPS;
	private static int[][] moves = { { 0, -1 }, { +1, 0 }, { 0, +1 }, { -1, 0 } };

	private Dimension cur, idx, nxt;
	private ArrayDeque<Dimension> Q;
	private int[][] grid;
	private int bfsDelay;

	HomingEnemy(double x, double y)
	{
		super(x, y);
		this.M_SPD = 160.0 / GameScreen.FPS;
		this.bfsDelay = delayLim;
		cur = GameScreen.getDlen();
		grid = new int[cur.height][cur.width];
		Q = new ArrayDeque<Dimension>();
	}	// end constructor(double,double)

	public void advance()
	{
		if(frozen) return;
		// Directions are RELATIVE here ie. "R" in "Up" Field <--> "L" in "Down" (Normal) Field
		// Stops at the edge of a cliff
		updateField();
		idx = getIdx(); // Current Array Index Position
		if(++bfsDelay >= delayLim)
		{	// BFS Every second
			bfs();
			bfsDelay = 0; // (int)-1E9;
		}
		// for(int k = 0; k < 4; ++k)
		//	setKey(k, false); // Release All
		for(int k = 0; k < 4; ++k)
		{
			nxt = new Dimension(idx.width+moves[k][0], idx.height+moves[k][1]);
			if(invalid(nxt)) continue;
			if(grid[nxt.height][nxt.width] <= grid[idx.height][idx.width])
			{
				setKey(k, true);
			}
		}
		if(keysPressed[UP] && keysPressed[DOWN])
			setKey(DOWN, false);
		if(keysPressed[RIGHT] && keysPressed[LEFT])
			setKey(RIGHT, false);
		updateVectors();
		// this.vel.add(this.acc);
		// System.out.println(this.vel.X + " " + this.vel.Y);
		move(this.vel);
	}	// end method advance

	public void bfs()
	{	// Performs a breadth-first search
		Q.clear();
		for(int i = 0; i < GameScreen.getDlen().height; ++i)
			for(int j = 0; j < GameScreen.getDlen().width; ++j)
				grid[i][j] = INFN; // Initializes the grid
		Dimension src = GameScreen.getP().getIdx();
		grid[src.height][src.width] = 0;
		Q.add(src);
		boolean run = true;
		while(!Q.isEmpty() && run)
		{
			cur = Q.poll();
			for(int k = 0; k < 4; ++k)
			{
				nxt = new Dimension(cur.width+moves[k][0], cur.height+moves[k][1]);
				if(invalid(nxt)) continue;
				if(grid[nxt.height][nxt.width] > grid[cur.height][cur.width]+1)
				{	// Found a path
					grid[nxt.height][nxt.width] = grid[cur.height][cur.width] + 1;
					Q.offer(nxt);
				}
			}
		}
		// Printing
		// for(int i = 0; i < grid.length; ++i)
		// for(int j = 0; j < grid[i].length; ++j)
		//	System.out.format("%2d " + ((j==grid[i].length-1)?"%n":""), (grid[i][j] < INFN) ? grid[i][j] : -1);
	}	// end method bfs

	@Override // Superclass: Entity
	public void draw(Graphics g)
	{	// Hardcode image for Demo
		g2D = (Graphics2D)g;
		g2D.drawImage(Images.sprites[1][ DOWN ][ movingRel(LEFT)?0:1 ],
			(int)Math.round(pos.X), (int)Math.round(pos.Y), Block.getLen(), Block.getLen(), null);
	}	// end method draw

	public boolean invalid(final Dimension d)
	{
		return	(d.width < 0) || (d.width >= GameScreen.getDlen().width) ||
				(d.height < 0) || (d.height >= GameScreen.getDlen().height) ||
				(GameScreen.getBlocks(d.height, d.width).getBlock() == Block.EARTH);
	}	// end method invalid

	@Override // Superclass: Entity
	public void setKey(int indexToSet, boolean pressedDown)
	{	// Direct Movement
		keysPressed[indexToSet] = pressedDown;
	}	// end method setKey

	@Override // Superclass: Entity
	public void updateVectors()
	{	// Possible diagonal movement
		if(keysPressed[   UP]) move(new Vector2(0.0, -1 * M_SPD));
		if(keysPressed[RIGHT]) move(new Vector2(+1 * M_SPD, 0.0));
		if(keysPressed[ DOWN]) move(new Vector2(0.0, +1 * M_SPD));
		if(keysPressed[ LEFT]) move(new Vector2(-1 * M_SPD, 0.0));
	}	// end method updateVectors
}	// end class HomingEnemy
