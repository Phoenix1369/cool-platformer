/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Playable Entity class
*******/
import java.awt.*;
import java.awt.event.*;

class Player extends Entity
{
	private static final double EPS = 1E-9;
	private final double GRAVITY = 50.0 / GameScreen.FPS; // Before (Floaty): 30
	private final double J_SPD = 480.0 / GameScreen.FPS;
	private final double J_SPD_MIN = 240.0 / GameScreen.FPS;
	private final double M_SPD = 200.0 / GameScreen.FPS; // Normal: 120.0
	private final int bLen = Block.getLen();

	private Vector2 tl = new Vector2();
	private Vector2 br = new Vector2();

	private boolean[] boundsFlags = new boolean[4]; //whether ground is detected in a direction
 	private boolean[] keysPressed = new boolean[4]; //whether directional keys are pressed
	
	Player() 
	{
		super();
		this.pos.X = this.pos.Y = bLen*2;
	}	// end constructor()

	@Override // Superclass: Entity
	public void advance() 
	{
		updateField();
		updateVectors();
		this.vel.add(this.acc);
		move(this.vel);
	}	// end method advance

	public void accl(final Vector2 vel) 
	{
		this.vel = vel;
	}	// end method accl

	public boolean checkBlock(int dir) 
	{	// Determines if 
		switch(dir)
		{
		case DOWN:
		return	(GameScreen.getBlocks((int)Math.floor(pos.Y / bLen) + 1, (int)Math.floor(pos.X / bLen)).getBlock() == 1) ||
			(GameScreen.getBlocks((int)Math.floor(pos.Y / bLen) + 1, (int)Math.ceil (pos.X / bLen)).getBlock() == 1);
		case   UP:
		return	(GameScreen.getBlocks((int)Math.ceil (pos.Y / bLen) - 1, (int)Math.floor(pos.X / bLen)).getBlock() == 1) ||
			(GameScreen.getBlocks((int)Math.ceil (pos.Y / bLen) - 1, (int)Math.ceil (pos.X / bLen)).getBlock() == 1);
		}
		return false;
	}	// end method checkBlock

	@Override // Superclass: Entity
	public void draw(Graphics g) 
	{
		// Hardcode image for Demo
		g2D = (Graphics2D)g;
		g2D.fillRect((int)tl.X, (int)tl.Y, (int)(br.X - tl.X), (int)(br.Y - tl.Y));
		g2D.drawImage(Images.demo[2], (int)Math.round(pos.X), (int)Math.round(pos.Y), Block.getLen(), Block.getLen(), null);
	}	// end method draw

	public int getField()
	{	// Returns the current Field of the Player
		return GameScreen.getBlocks((int)Math.floor(pos.Y / bLen + 0.5), (int)Math.floor(pos.X / bLen + 0.5)).getField();
	}	// end method getField

	public final Vector2 getVel() 
	{
		return this.vel;
	}	// end method getVel

	public void move(final Vector2 disp) 
	{
		updateBounds();
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
	
	public void setKey(int indexToSet, boolean pressedDown)
	{
		keysPressed[indexToSet] = pressedDown;
	}	// end method setKey
	
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
		switch(this.getField())
		{
		case DOWN: this.acc.X = 0; this.acc.Y = +GRAVITY; break;
		case   UP: this.acc.X = 0; this.acc.Y = -GRAVITY; break;
		}
	}	// end method updateField
	
	public void updateVectors() //move based on the keys currently being pressed
	{
		if(keysPressed[UP])
		{	// Jump Query
			if(checkBlock(getField()))
			{	// Block exists in Field Direction
				switch(getField())
				{
				case DOWN: accl(new Vector2(0.0, -J_SPD)); break;
				case   UP: accl(new Vector2(0.0, +J_SPD)); break;
				}
			}
		}
		else if(!keysPressed[UP]) //"cut" the jump if the button is released early
		{
			if(vel.Y < -1 * J_SPD_MIN)
				vel.Y = (-1 * J_SPD_MIN);
		}
		//currently no actions for pressing down (this method will have to take into account fields later)
		if(keysPressed[LEFT])
			move(new Vector2(-1 * M_SPD, 0.0));
		if(keysPressed[RIGHT])
			move(new Vector2(+1 * M_SPD, 0.0));
	}	// end method updateVectors
}	// end class
