/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Playable Entity class
*******/
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayDeque;

class Player extends Entity
{
	private static final int INFN = 0x3F3F3F3F;
	private static final int delayLim = GameScreen.FPS * 3 / 4;
	public  static final int[][] moves = { { 0, -1 }, { +1, 0 }, { 0, +1 }, { -1, 0 } };

	private Dimension cur, idx, nxt;
	private ArrayDeque<Dimension> Q;

	public static int[][] grid;
	private boolean facing; // Direction Player faces
	private int bfsDelay;

	Player()
	{
		super(lenB*2, lenB*11);
		this.M_SPD = 200.0 / GameScreen.FPS;

		this.bfsDelay = delayLim;
		cur = GameScreen.getDlen();
		grid = new int[cur.height][cur.width];
		Q = new ArrayDeque<Dimension>();
	}	// end constructor()

	@Override // Superclass: Entity
	public void advance()
	{
		updateField();
		if(++bfsDelay >= delayLim)
		{	// Don't BFS every single tick
			bfs();
			bfsDelay = 0;
		}
		double pxOld = pos.X;
		updateVectors();
		move(this.vel);
		double diff = pos.X - pxOld;
		// Remembers which direction it faces
		if(diff > EPS)
			facing = false;
		else if(diff < -EPS)
			facing = true;
	}	// end method advance

	public void bfs()
	{	// Performs a breadth-first search
		Q.clear();
		for(int i = 0; i < GameScreen.getDlen().height; ++i)
			for(int j = 0; j < GameScreen.getDlen().width; ++j)
				grid[i][j] = INFN; // Initializes the grid
		Dimension src = getIdx();
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
	{	// Hardcode image for Demo
		g2D = (Graphics2D)g;
		g2D.drawImage(Images.sprites[0][ getField() ][ facing?0:1 ], this.x, this.y, lenB, lenB, null);
	}	// end method draw

	public static final int[][] getG() { return grid; } // end method getG

	public static boolean invalid(final Dimension d)
	{	// Dimension goes out of bounds, or wall block
		return	(d.width < 0) || (d.width >= GameScreen.getDlen().width-GameScreen.edW) ||
			(d.height < 0) || (d.height >= GameScreen.getDlen().height-GameScreen.edW) ||
			(GameScreen.getBlocks(d.height, d.width).getBlock() == Block.EARTH);
	}	// end method invalid
}	// end class Player
