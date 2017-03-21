/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Playable Entity class
*******/
import java.awt.*;
import java.awt.event.*;

class Player extends Entity {
	private boolean[] keysPressed = new boolean[4]; //whether directional keys are pressed or not: [up][down][left][right]
	private final double J_SPD = 240.0 / GameScreen.FPS;
	private final double J_SPD_MIN = 120.0 / GameScreen.FPS;
	private final double M_SPD = 120.0 / GameScreen.FPS;
	
	Player() {
		super();
	}	// end constructor()

	@Override // Superclass: Entity
	public void advance() {
		updateVectors();
		this.vel.add(this.acc);
		if(vel.getY() > 0.0) { // Only Checks "Below" the Player for Solid Earth
			posInt[0] = (int)Math.round(pos.getX() + Block.getSize() / 2) / Block.getSize();
			posInt[1] = (int)Math.round(pos.getY() + Block.getSize() / 2) / Block.getSize() + 1;
			if(GameScreen.getBlocks(posInt[1], posInt[0]).getBlock() == 1)
				// Reduce Velocity to Size of Gap between Player and Block to avoid going through the ground
				this.vel.setY(Math.min(posInt[1] * Block.getSize() - pos.getY() - Block.getSize(), vel.getY()));
		}	// end if
		this.pos.add(this.vel);
	}	// end method advance

	public void accl(final Vector velo) {
		this.vel.add(velo);
	}	// end method accl

	@Override // Superclass: Entity
	public void draw(Graphics g) {
		// Hardcode image for Demo
		g2D = (Graphics2D)g;
		g2D.drawImage(Images.demo[2], (int)Math.round(pos.getX()), (int)Math.round(pos.getY()), Block.getSize(), Block.getSize(), null);
	}	// end method draw

	public final Vector getVel() {
		return this.vel;
	}	// end method getVel

	public void move(final Vector disp) {
		this.pos.add(disp);
	}	// end method move
	
	public void setKey(int indexToSet, boolean pressedDown)
	{
		keysPressed[indexToSet] = pressedDown;
	}	// end method setKey
	
	public void updateVectors() //move based on the keys currently being pressed
	{
		if(keysPressed[0])
		{
			if(Math.abs(vel.getY()) < 1E-6) //if the player is on the ground
				accl(new Vector(0.0, -1 * J_SPD));
		}
		else if(!keysPressed[0]) //"cut" the jump if the button is released early
		{
			if(vel.getY() < -1 * J_SPD_MIN)
				vel.setY(-1 * J_SPD_MIN);
		}
		//currently no actions for pressing down (this method will have to take into account fields later)
		if(keysPressed[2])
			move(new Vector(-1 * M_SPD, 0.0));
		if(keysPressed[3])
			move(new Vector(+1 * M_SPD, 0.0));
	}	// end method updateVectors
}	// end class