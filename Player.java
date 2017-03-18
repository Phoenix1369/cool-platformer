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

		if(vel.getY() > 0.0) { // Only Checks "Below" the Player
			posInt[0] = (int)Math.round(pos.getX() + Block.getSize() / 2) / Block.getSize();
			posInt[1] = (int)Math.round(pos.getY() + Block.getSize() / 2) / Block.getSize() + 1;
			if(GameScreen.getBlocks(posInt[1], posInt[0]).getBlock() == 1)
				this.vel.setY(0.0); // Block below is Solid Earth
		}	// end if
		this.pos.add(this.vel);
	}	// end method advance

	@Override // Superclass: Character
	public void draw(Graphics g) {
		// Hardcode image for Demo
		g2D = (Graphics2D)g;
		g2D.drawImage(Images.demo[2], (int)Math.round(pos.getX()), (int)Math.round(pos.getY()), Block.getSize(), Block.getSize(), null);
	}	// end method draw
}	// end class
