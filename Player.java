/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Playable Character class
*******/
import java.awt.*;
import java.awt.event.*;

class Player extends Character {

	Player() {
		super();
	}	// end constructor()

	@Override // Superclass: Character
	public void advance() {
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

	@Override // Superclass: Character
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
}	// end class
