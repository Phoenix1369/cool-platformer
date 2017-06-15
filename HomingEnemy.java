/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: HomingEnemy character - floats around obstacles targeting Player
*******/
import java.awt.*;

class HomingEnemy extends Enemy
{
	private Dimension idx, nxt;

	HomingEnemy(double x, double y)
	{
		super(x, y);
		this.M_SPD = 160.0 / GameScreen.FPS;
	}	// end constructor(double,double)

	public void advance()
	{
		if(GameScreen.frozen) return;
		// Directions are RELATIVE here ie. "R" in "Up" Field <--> "L" in "Down" (Normal) Field
		updateField();
		idx = getIdx(); // Current Array Index Position
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
			nxt = new Dimension(idx.width+Player.moves[k][0], idx.height+Player.moves[k][1]);
			if(Player.invalid(nxt)) continue;
			if(Player.getG()[nxt.height][nxt.width] <= Player.getG()[idx.height][idx.width])
				setKey(k, true); // Picks tile closer to Player
		}
		if(keysPressed[UP] && keysPressed[DOWN])
			setKey((Player.getG()[idx.height-1][idx.width] < Player.getG()[idx.height+1][idx.width]) ? DOWN  :   UP, false);
		if(keysPressed[RIGHT] && keysPressed[LEFT])
			setKey((Player.getG()[idx.height][idx.width-1] < Player.getG()[idx.height][idx.width+1]) ? RIGHT : LEFT, false);
		vx = vy = false;
		updateVectors();
		move(this.vel);
	}	// end method advance

	@Override // Superclass: Entity
	public void draw(Graphics g)
	{
		g2D = (Graphics2D)g;
		g2D.drawImage(Images.sprites[2][ DOWN ][ movingRel(LEFT)?0:1 ], this.x, this.y, lenB, lenB, null);
	}	// end method draw

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
