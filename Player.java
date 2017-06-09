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
	Player()
	{
		super();
		this.pos.X = this.pos.Y = bLen*2;
	}	// end constructor()

	@Override // Superclass: Entity
	public void advance()
	{
		if(frozen) return; // Skips Action if Frozen
		updateField();
		updateVectors();
		this.vel.add(this.acc);
		move(this.vel);
	}	// end method advance

	@Override // Superclass: Entity
	public void draw(Graphics g)
	{	// Hardcode image for Demo
		g2D = (Graphics2D)g;
		g2D.fillRect((int)tl.X, (int)tl.Y, (int)(br.X - tl.X), (int)(br.Y - tl.Y));
		g2D.drawImage(Images.sprites[0], (int)Math.round(pos.X), (int)Math.round(pos.Y), Block.getLen(), Block.getLen(), null);
	}	// end method draw
}	// end class
