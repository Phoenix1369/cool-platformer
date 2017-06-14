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
		super(bLen*2, bLen*11);
		this.M_SPD = 200.0 / GameScreen.FPS; // Was 200
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
		// g2D.fillRect((int)tl.X, (int)tl.Y, (int)(br.X - tl.X), (int)(br.Y - tl.Y)); // Bounding Box
		g2D.drawImage(Images.sprites[0][ getField() ][ movingRel(LEFT)?0:1 ],
			(int)Math.round(pos.X), (int)Math.round(pos.Y), Block.getLen(), Block.getLen(), null);
	}	// end method draw
}	// end class
