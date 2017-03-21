/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Playable Entity class
*******/
import java.awt.*;
import java.awt.event.*;

class Player extends Entity {
	private static final double EPS = 1E-9;
	private final double GRAVITY = 30.0 / GameScreen.FPS;
	private final double J_SPD = 480.0 / GameScreen.FPS;
	private final double J_SPD_MIN = 240.0 / GameScreen.FPS;
	private final double M_SPD = 120.0 / GameScreen.FPS;
	private final int bLen = Block.getLen();

	private Vector2 tl = new Vector2();
	private Vector2 br = new Vector2();

	private boolean[] boundsFlags = new boolean[4]; //whether ground is detected in a direction
	private boolean[] keysPressed = new boolean[4]; //whether directional keys are pressed
	
	Player() {
		super();
		this.pos.X = this.pos.Y = bLen;
		this.acc = new Vector2(0.0, GRAVITY);
	}	// end constructor()

	@Override // Superclass: Entity
	public void advance() {
		updateVectors();
		this.vel.add(this.acc);
		updateBounds();
		if(checkBlock(DOWN, false) && (this.vel.Y > 0.0)) // Next Block Down [+1]
			this.vel.Y = Math.min( getArrDx(pos.Y, true) * Block.getLen() - pos.Y, vel.Y);
		move(this.vel);
	}	// end method advance

	public void accl(final Vector2 velo) {
		this.vel.add(velo);
	}	// end method accl

	public boolean checkBlock(int dir, boolean next) {
		switch(dir) {
		case DOWN:
			return
				(GameScreen.getBlocks(getArrDx(pos.Y, next) + 1, getArrDx(pos.X, false)).getBlock() == 1) ||
				(GameScreen.getBlocks(getArrDx(pos.Y, next) + 1, getArrDx(pos.X,  true)).getBlock() == 1);
		}
		return false;
	}	// end method checkBlock

	@Override // Superclass: Entity
	public void draw(Graphics g) {
		// Hardcode image for Demo
		g2D = (Graphics2D)g;
		g2D.fillRect((int)tl.X, (int)tl.Y, (int)(br.X - tl.X), (int)(br.Y - tl.Y));
		g2D.drawImage(Images.demo[2], (int)Math.round(pos.X), (int)Math.round(pos.Y), Block.getLen(), Block.getLen(), null);
	}	// end method draw

	public int getArrDx(double val, boolean end) { // Returns the Index of the Block within Array
		return (int)( (val + ((end) ? Block.getLen() : 0)) / Block.getLen() );
	}	// end method getArrDx

	public final Vector2 getVel() {
		return this.vel;
	}	// end method getVel

	public void move(final Vector2 disp) {
		if(disp.X > 0)
			disp.X = Math.min(disp.X, br.X - (pos.X + bLen));
		else
			disp.X = Math.max(disp.X, tl.X - pos.X);
		if(disp.Y > 0)
			disp.Y = Math.min(disp.Y, br.Y - (pos.Y + bLen));
		else
			disp.Y = Math.max(disp.Y, tl.Y - pos.Y);
		this.pos.add(disp);
	}	// end method move
	
	public void setKey(int indexToSet, boolean pressedDown)
	{
		keysPressed[indexToSet] = pressedDown;
	}	// end method setKey
	
	public void updateBounds()
	{
		for(int i = 0; i < boundsFlags.length; i++)
			boundsFlags[i] = false;
		tl.X = bLen * Math.floor(pos.X / bLen);
		tl.Y = bLen * Math.floor(pos.Y / bLen);
		br.X = bLen * Math.ceil((pos.X + bLen) / bLen);
		br.Y = bLen * Math.ceil((pos.Y + bLen) / bLen);
		for(int i = (int)(tl.X/bLen); i < (int)(br.X/bLen); i++)
		{
			if(GameScreen.getBlocks((int)tl.Y/bLen-1, i).getBlock() == 1)
				boundsFlags[0] = true;
			if(GameScreen.getBlocks((int)br.Y/bLen, i).getBlock() == 1)
				boundsFlags[1] = true;
		}
		for(int i = (int)(tl.Y/bLen); i < (int)(br.Y/bLen); i++)
		{
			if(GameScreen.getBlocks(i, (int)tl.X/bLen-1).getBlock() == 1)
				boundsFlags[2] = true;
			if(GameScreen.getBlocks(i, (int)br.X/bLen).getBlock() == 1)
				boundsFlags[3] = true;
		}
		if(!boundsFlags[0])
			tl.Y = tl.Y - bLen;
		if(!boundsFlags[1])
			br.Y = br.Y + bLen;
		if(!boundsFlags[2])
			tl.X = tl.X - bLen;
		if(!boundsFlags[3])
			br.X = br.X + bLen;
	}
	
	public void updateVectors() //move based on the keys currently being pressed
	{
		if(keysPressed[UP])
		{	// Player Jumps
			if(checkBlock(DOWN, false)) // 
				accl(new Vector2(0.0, -1 * J_SPD));
		}
		else if(!keysPressed[0]) //"cut" the jump if the button is released early
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
