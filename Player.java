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
		this.pos.add(this.vel);
	}	// end method advance

	@Override // Superclass: Character
	public void draw(Graphics g) {
		// Hardcode image for Demo
		g2D = (Graphics2D)g;
		g2D.drawImage(Images.demo[2], this.pos.getIntX(), this.pos.getIntY(), Block.getSize(), Block.getSize(), null);
	}	// end method draw
}	// end class
