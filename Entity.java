/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Generic entity class
*******/
import java.awt.*;

class Entity
{
	private static final double EPS = 1E-9;
	protected final double GRAVITY = 50.0 / GameScreen.FPS;

	protected static final int UP = 0;
	protected static final int RIGHT = 1;
	protected static final int DOWN = 2;
	protected static final int LEFT = 3;
	protected static final int bLen = Block.getLen();

	protected final double J_SPD = 480.0 / GameScreen.FPS;
	protected final double J_SPD_MIN = 240.0 / GameScreen.FPS;
	protected final double M_SPD = 200.0 / GameScreen.FPS; // Normal: 120.0

	protected static Graphics2D g2D;
	protected static int[] posInt = new int[2];

	protected boolean[] boundsFlags = new boolean[4]; //whether ground is detected in a direction
	protected boolean[] keysPressedABS = new boolean[4]; //absolute keypress values
 	protected boolean[] keysPressed = new boolean[4]; //whether directional keys are pressed

	protected Rectangle area;
	protected Vector2 acc;
	protected Vector2 pos;
	protected Vector2 vel;

	// Bounding Box vectors
	protected Vector2 tl = new Vector2();
	protected Vector2 br = new Vector2();

	Entity()
	{
		this.acc = new Vector2();
		this.pos = new Vector2();
		this.vel = new Vector2();
		this.area= new Rectangle((int)Math.round(pos.X), (int)Math.round(pos.Y), Block.getLen(), Block.getLen());
	}	// end constructor()

	public void advance()
	{
	}	// end method advance

	public boolean checkBlock(int dir)
	{	// Determines if the player is standing on a Block
		switch(dir)
		{
			case  DOWN:
			return	(GameScreen.getBlocks((int)Math.floor(pos.Y / bLen) + 1, (int)Math.floor(pos.X / bLen)).getBlock() == 1) ||
					(GameScreen.getBlocks((int)Math.floor(pos.Y / bLen) + 1, (int)Math.ceil (pos.X / bLen)).getBlock() == 1);
			case    UP:
			return	(GameScreen.getBlocks((int)Math.floor(pos.Y / bLen) - 1, (int)Math.floor(pos.X / bLen)).getBlock() == 1) ||
					(GameScreen.getBlocks((int)Math.floor(pos.Y / bLen) - 1, (int)Math.ceil (pos.X / bLen)).getBlock() == 1);
			case RIGHT:
			return	(GameScreen.getBlocks((int)Math.floor(pos.Y / bLen), (int)Math.floor(pos.X / bLen) + 1).getBlock() == 1) ||
					(GameScreen.getBlocks((int)Math.ceil (pos.Y / bLen), (int)Math.floor(pos.X / bLen) + 1).getBlock() == 1);
			case  LEFT:
			return	(GameScreen.getBlocks((int)Math.floor(pos.Y / bLen), (int)Math.floor(pos.X / bLen) - 1).getBlock() == 1) ||
					(GameScreen.getBlocks((int)Math.ceil (pos.Y / bLen), (int)Math.floor(pos.X / bLen) - 1).getBlock() == 1);
		}
		return false;
	}	// end method checkBlock

	public void draw(Graphics g)
	{
	}	// end method draw

	public int getField()
	{	// Returns the current Field of the Entity
		return GameScreen.getBlocks((int)Math.floor(pos.Y / bLen + 0.5), (int)Math.floor(pos.X / bLen + 0.5)).getField();
	}	// end method getField

	public final Vector2 getVel()
	{
		return this.vel;
	}	// end method getVel

	public void move(final Vector2 disp)
	{
		updateBounds();
		if(acc.X != 0) //decelerate in the vertical direction if there is horizontal gravity
		{
			vel.Y = Math.signum(vel.Y) * Math.max(Math.abs(vel.Y) - GRAVITY, 0.0);
		}
		else if(acc.Y != 0) //decelerate in the horizontal direction if there is vertical gravity
		{
			vel.X = Math.signum(vel.X) * Math.max(Math.abs(vel.X) - GRAVITY, 0.0);
		}
		if(disp.X > 0)
			disp.X = Math.min(disp.X, br.X - (pos.X + bLen));
		else
			disp.X = Math.max(disp.X, tl.X - pos.X);
		if(disp.Y > 0)
			disp.Y = Math.min(disp.Y, br.Y - (pos.Y + bLen));
		else
			disp.Y = Math.max(disp.Y, tl.Y - pos.Y);
		this.pos.add(disp);
		pos.X = (Math.abs(Math.round(pos.X) - pos.X) < EPS) ? Math.round(pos.X) : pos.X; //Round to avoid floating point calculation errors
		pos.Y = (Math.abs(Math.round(pos.Y) - pos.Y) < EPS) ? Math.round(pos.Y) : pos.Y;
	}	// end method move

	public void setAcc(final Vector2 acc)
	{
		this.acc = acc;
	}	// end method setAcc

	public void setVel(final Vector2 vel)
	{
		this.vel = vel;
	}	// end method setVel

	public void updateBounds()
	{
		for(int i = 0; i < boundsFlags.length; i++)
			boundsFlags[i] = false;
		tl.X = bLen * Math.floor(pos.X / bLen); //Top Left coordinates of the smallest bounding box for the Player
		tl.Y = bLen * Math.floor(pos.Y / bLen);
		br.X = bLen * Math.ceil((pos.X + bLen) / bLen); //Bottom Right coordinates of the smallest bounding box for the Player
		br.Y = bLen * Math.ceil((pos.Y + bLen) / bLen);
		for(int i = (int)(tl.X/bLen); i < (int)(br.X/bLen); i++) //check along the X-axis
		{
			if(GameScreen.getBlocks((int)tl.Y/bLen-1, i).getBlock() == 1)
				boundsFlags[0] = true; //if a collision exists above the bounding box
			if(GameScreen.getBlocks((int)br.Y/bLen, i).getBlock() == 1)
				boundsFlags[1] = true; //if a collision exists below the bounding box
		}
		for(int i = (int)(tl.Y/bLen); i < (int)(br.Y/bLen); i++) //check along the Y-axis
		{
			if(GameScreen.getBlocks(i, (int)tl.X/bLen-1).getBlock() == 1)
				boundsFlags[2] = true; //if a collision exists to the left of the bounding box
			if(GameScreen.getBlocks(i, (int)br.X/bLen).getBlock() == 1)
				boundsFlags[3] = true; //if a collision exists to the right of the bounding box
		}
		if(!boundsFlags[0]) //Expand bounding box out one block if there is no collision detected in that direction
			tl.Y = tl.Y - bLen;
		if(!boundsFlags[1])
			br.Y = br.Y + bLen;
		if(!boundsFlags[2])
			tl.X = tl.X - bLen;
		if(!boundsFlags[3])
			br.X = br.X + bLen;
	}	// end method updateBounds

	public void updateVectors() //move based on the keys currently being pressed
	{
		// 'keysPressed[UP]' doesn't work
		if(keysPressedABS[(getField()+2) % 4] && checkBlock(getField()))
		{	// Jump Query - Block exists in Field Direction
			switch(getField())
			{
				case  DOWN: setVel(new Vector2(0.0, -J_SPD)); break;
				case    UP: setVel(new Vector2(0.0, +J_SPD)); break;
				case RIGHT: setVel(new Vector2(-J_SPD, 0.0)); break;
				case  LEFT: setVel(new Vector2(+J_SPD, 0.0)); break;
			}
		}
		/* 
		else if(!keysPressed[UP]) //"cut" the jump if the button is released early
		{
			if(vel.Y < -1 * J_SPD_MIN)
				vel.Y = (-1 * J_SPD_MIN);
		} */

		if(keysPressed[LEFT])
		{
			switch(getField())
			{
				case  DOWN:
				case    UP: move(new Vector2(-1 * M_SPD, 0.0)); break;
				case RIGHT: move(new Vector2(0.0, 1 * M_SPD)); break;
				case  LEFT: move(new Vector2(0.0, -1 * M_SPD)); break;
			}
		}
		if(keysPressed[RIGHT])
		{
			switch(getField())
			{
				case  DOWN:
				case    UP: move(new Vector2(+1 * M_SPD, 0.0)); break;
				case RIGHT: move(new Vector2(0.0, -1 * M_SPD)); break;
				case  LEFT: move(new Vector2(0.0, 1 * M_SPD)); break;
			}
		}
	}	// end method updateVectors
}	// end class Entity
