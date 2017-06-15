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
	private static final int delayLim = GameScreen.FPS * 3 / 4;
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
		if(GameScreen.frozen) return;
		// Directions are RELATIVE here ie. "R" in "Up" Field <--> "L" in "Down" (Normal) Field
		updateField();
		idx = getIdx(); // Current Array Index Position
		if(++bfsDelay >= delayLim)
		{	// Don't BFS every single tick
			bfs();
			bfsDelay = 0;
		}
		if(!vx)
		{	// Reset X
			setKey(LEFT, false);
			setKey(RIGHT, false);
		}
		if(!vy)
		{	// Reset Y
			setKey(UP, false);
			setKey(DOWN, false);
		}
		for(int k = 0; k < 4; ++k)
		{
			nxt = new Dimension(idx.width+moves[k][0], idx.height+moves[k][1]);
			if(invalid(nxt)) continue;
			if(grid[nxt.height][nxt.width] <= grid[idx.height][idx.width])
				setKey(k, true); // Picks tile closer to Player
		}
		if(keysPressed[UP] && keysPressed[DOWN])
			setKey((grid[idx.height-1][idx.width] < grid[idx.height+1][idx.width]) ? DOWN  :   UP, false);
		if(keysPressed[RIGHT] && keysPressed[LEFT])
			setKey((grid[idx.height][idx.width-1] < grid[idx.height][idx.width+1]) ? RIGHT : LEFT, false);
		vx = vy = false;
		updateVectors();
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
		while(!Q.isEmpty())
		{
			cur = Q.poll();
			for(int k = 0; k < 4; ++k)
			{
				nxt = new Dimension(cur.width+moves[k][0], cur.height+moves[k][1]);
				if(invalid(nxt)) continue;
				if(grid[nxt.height][nxt.width] > grid[cur.height][cur.width]+1)
				{	// Compute distance to new tile
					grid[nxt.height][nxt.width] = grid[cur.height][cur.width] + 1;
					Q.offer(nxt);
				}
			}
		}
	}	// end method bfs

	@Override // Superclass: Entity
	public void draw(Graphics g)
	{
		g2D = (Graphics2D)g;
		g2D.drawImage(Images.sprites[2][ DOWN ][ movingRel(LEFT)?0:1 ], this.x, this.y, lenB, lenB, null);
	}	// end method draw

	public boolean invalid(final Dimension d)
	{	// Dimension goes out of bounds, or wall block
		return	(d.width < 0) || (d.width >= GameScreen.getDlen().width-GameScreen.edW) ||
			(d.height < 0) || (d.height >= GameScreen.getDlen().height-GameScreen.edW) ||
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
