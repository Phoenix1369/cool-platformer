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
	protected final double GRAVITY = 40.0 / GameScreen.FPS; // (50, fps=30)

	protected static final int ADJ = 0; // Adjacent Block: Given [i][j], checks [i][j-1] or [i][j+1]
	protected static final int LOW = 1; // Lower Block (stable ground): Given [i][j], checks[i+1][j-1] or [i+1][j+1]

	protected static final int UP = 0;
	protected static final int RIGHT = 1;
	protected static final int DOWN = 2;
	protected static final int LEFT = 3;
	protected static final int bLen = Block.getLen();

	protected final double J_SPD = 640.0 / GameScreen.FPS; // (480, fps=30)
	protected final double J_SPD_MIN = 320.0 / GameScreen.FPS; // (240, fps=30)
	protected double M_SPD;

	protected static Graphics2D g2D;
	protected static int[] posInt = new int[2];

	protected boolean[] boundsFlags = new boolean[4]; //whether ground is detected in a direction
	protected boolean[] keysPressedABS; //absolute keypress values
 	protected boolean[] keysPressed; //whether directional keys are pressed

	protected boolean frozen; // False by default
	protected boolean snapTo; // Snaps the User to the grid
	protected int prevField = DOWN; // previous field the entity was in

	protected Rectangle area;
	protected Vector2 acc;
	protected Vector2 pos;
	protected Vector2 vel;

	// Bounding Box vectors
	protected Vector2 tl = new Vector2();
	protected Vector2 br = new Vector2();

	Entity()
	{
		this(bLen, bLen);
	}	// end constructor()

	Entity(double px, double py)
	{
		this.acc = new Vector2();
		this.pos = new Vector2(px, py);
		this.vel = new Vector2();
		this.area= new Rectangle((int)Math.round(pos.X), (int)Math.round(pos.Y), Block.getLen(), Block.getLen());
		keysPressed    = new boolean[4];
		keysPressedABS = new boolean[4];
	}	// end constructor(double,double)

	public void advance()
	{
	}	// end method advance

	public boolean checkBlock(int dir)
	{	// Determines if the player is standing on a Block
		return checkBlockL(dir) || checkBlockR(dir);
	}	// end method checkBlock

	public boolean checkBlockL(int dir)
	{	// Determines if a block exists to its RELATIVE left (near)
		switch(dir)
		{
			case    UP: return GameScreen.getBlocks((int)Math.ceil (pos.Y / bLen)-1, (int)Math.ceil (pos.X / bLen)  ).getBlock() == 1;
			case RIGHT: return GameScreen.getBlocks((int)Math.ceil (pos.Y / bLen)  , (int)Math.floor(pos.X / bLen)+1).getBlock() == 1;
			case  DOWN: return GameScreen.getBlocks((int)Math.floor(pos.Y / bLen)+1, (int)Math.floor(pos.X / bLen)  ).getBlock() == 1;
			case  LEFT: return GameScreen.getBlocks((int)Math.floor(pos.Y / bLen)  , (int)Math.ceil (pos.X / bLen)-1).getBlock() == 1;
		}
		return false;
	}	// end method checkBlockL

	public boolean checkBlockR(int dir)
	{	// Determines if a block exists to their (RELATIVE) right (near)
		switch(dir)
		{
			case    UP: return GameScreen.getBlocks((int)Math.ceil (pos.Y / bLen)-1, (int)Math.floor(pos.X / bLen)  ).getBlock() == 1;
			case RIGHT: return GameScreen.getBlocks((int)Math.floor(pos.Y / bLen)  , (int)Math.floor(pos.X / bLen)+1).getBlock() == 1;
			case  DOWN: return GameScreen.getBlocks((int)Math.floor(pos.Y / bLen)+1, (int)Math.ceil (pos.X / bLen)  ).getBlock() == 1;
			case  LEFT: return GameScreen.getBlocks((int)Math.ceil (pos.Y / bLen)  , (int)Math.ceil (pos.X / bLen)-1).getBlock() == 1;
		}
		return false;
	}	// end method checkBlockR

	public boolean checkFarL(int dir, int low)
	{	// Checks if a block exists to the left (far)
		// low=0: Check adjacent block; low=1: Check block below (stable ground)
		switch(dir)
		{
			case    UP: return GameScreen.getBlocks((int)Math.floor(pos.Y / bLen)-low, (int)Math.floor(pos.X / bLen)+  1).getBlock() == 1;
			case RIGHT: return GameScreen.getBlocks((int)Math.floor(pos.Y / bLen)+  1, (int)Math.ceil (pos.X / bLen)+low).getBlock() == 1;
			case  DOWN: return GameScreen.getBlocks((int)Math.ceil (pos.Y / bLen)+low, (int)Math.ceil (pos.X / bLen)-  1).getBlock() == 1;
			case  LEFT: return GameScreen.getBlocks((int)Math.ceil (pos.Y / bLen)-  1, (int)Math.floor(pos.X / bLen)-low).getBlock() == 1;
		}
		return false;
	}	// end method checkFarL

	public boolean checkFarR(int dir, int low)
	{	// Checks if a block exists to the right (far)
		switch(dir)
		{
			case    UP: return GameScreen.getBlocks((int)Math.floor(pos.Y / bLen)-low, (int)Math.ceil (pos.X / bLen)-  1).getBlock() == 1;
			case RIGHT: return GameScreen.getBlocks((int)Math.ceil (pos.Y / bLen)-  1, (int)Math.ceil (pos.X / bLen)+low).getBlock() == 1;
			case  DOWN: return GameScreen.getBlocks((int)Math.ceil (pos.Y / bLen)+low, (int)Math.floor(pos.X / bLen)+  1).getBlock() == 1;
			case  LEFT: return GameScreen.getBlocks((int)Math.floor(pos.Y / bLen)+  1, (int)Math.floor(pos.X / bLen)-low).getBlock() == 1;
		}
		return false;
	}	// end method checkFarR

	public void draw(Graphics g)
	{
	}	// end method draw

	public void freeze(boolean yesOrNo)
	{
		frozen = yesOrNo;
		if(!frozen) return;
		// Clears movement if frozen
		for(int i = 0; i < keysPressedABS.length; i++)
			setKey(i, false);
	}

	public int getField()
	{	// Returns the current Field of the Entity
		Dimension d = getIdx();
		return GameScreen.getBlocks(d.height, d.width).getField();
	}	// end method getField

	public Dimension getIdx()
	{	// Returns the current array index of the centre pixel
		return new Dimension((int)Math.floor(pos.X / bLen + 0.5), (int)Math.floor(pos.Y / bLen + 0.5));
	}	// end method getIdx

	public boolean movingRel(int dir)
	{	// Moving Relatively (Left / Right)
		return keysPressedABS[(getField() - dir + 4) % 4];
	}	// end method getKeyRel

	public final Vector2 getVel()
	{
		return this.vel;
	}	// end method getVel

	public final Vector2 getPos()
	{
		return this.pos;
	}	// end method getPos

	public void move(Vector2 disp)
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
		if(	(Math.abs(this.acc.X) > EPS && Math.abs(this.vel.X) < EPS) ||
			(Math.abs(this.acc.Y) > EPS && Math.abs(this.vel.Y) < EPS) )
			snapTo = true;	// Velocity perpendicular to Acceleration
		this.pos.add(disp);
		pos.X = (Math.abs(Math.round(pos.X) - pos.X) < EPS) ? Math.round(pos.X) : pos.X; //Round to avoid floating point calculation errors
		pos.Y = (Math.abs(Math.round(pos.Y) - pos.Y) < EPS) ? Math.round(pos.Y) : pos.Y;
		if(snapTo)
		{	// Rounds the User's position after changing Fields
			pos.X = (int)Math.round(pos.X / M_SPD) * M_SPD;
			pos.Y = (int)Math.round(pos.Y / M_SPD) * M_SPD;
			snapTo = false;
		}
	}	// end method move

	public void setAcc(final Vector2 acc)
	{
		this.acc = acc;
	}	// end method setAcc

	public void setKey(int indexToSet, boolean pressedDown)
	{
		keysPressedABS[indexToSet] = pressedDown;
		if(this.getField() == UP) //Code specifically needed for up-fields: could be shorter, maybe!
		{
			if(indexToSet == UP)
				keysPressed[2] = pressedDown;
			else if(indexToSet == DOWN)
				keysPressed[0] = pressedDown;
			else
				keysPressed[indexToSet] = pressedDown;
		}
		else if(this.getField() == DOWN) //Code specifically needed for down-fields
			keysPressed[indexToSet] = pressedDown;
		else	// Shift key input for left / right fields
			keysPressed[(indexToSet + getField() + 4) % 4] = pressedDown;
	}	// end method setKey

	public void setVel(final Vector2 vel)
	{
		this.vel = vel;
	}	// end method setVel
	
	public void setPos(final Vector2 pos)
	{
		this.pos = pos;
	}	// end method setPos

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

	public void updateField()
	{	// For now, only Fields influence Acceleration, so hardcode
		if(this.getField() != prevField) //Map keys based on absolute keypresses on field switch
		{
			switch(this.getField())
			{
				case  DOWN: keysPressed[0] = keysPressedABS[0]; keysPressed[1] = keysPressedABS[1];
							keysPressed[2] = keysPressedABS[2]; keysPressed[3] = keysPressedABS[3]; break;
				case    UP: keysPressed[0] = keysPressedABS[2]; keysPressed[1] = keysPressedABS[1];
							keysPressed[2] = keysPressedABS[0]; keysPressed[3] = keysPressedABS[3]; break;
				case RIGHT: keysPressed[0] = keysPressedABS[3]; keysPressed[1] = keysPressedABS[0];
							keysPressed[2] = keysPressedABS[1]; keysPressed[3] = keysPressedABS[2]; break;
				case  LEFT: keysPressed[0] = keysPressedABS[1]; keysPressed[1] = keysPressedABS[2];
							keysPressed[2] = keysPressedABS[3]; keysPressed[3] = keysPressedABS[0]; break;
			}
			prevField = this.getField();
			snapTo = true; // Snaps Entity to Grid
		}
		switch(this.getField())
		{
			case  DOWN: this.acc.X = 0.0; this.acc.Y = +GRAVITY; break;
			case    UP: this.acc.X = 0.0; this.acc.Y = -GRAVITY; break;
			case RIGHT: this.acc.X = +GRAVITY; this.acc.Y = 0.0; break;
			case  LEFT: this.acc.X = -GRAVITY; this.acc.Y = 0.0; break;
		}
	}	// end method updateField

	public void updateVectors() // move based on the keys currently being pressed
	{
		if(keysPressed[UP] && checkBlock(getField()))
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
				case RIGHT: move(new Vector2(0.0, +1 * M_SPD)); break;
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
				case  LEFT: move(new Vector2(0.0, +1 * M_SPD)); break;
			}
		}
	}	// end method updateVectors
}	// end class Entity
