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
		super(lenB*2, lenB*11);
		this.M_SPD = 200.0 / GameScreen.FPS;
	}	// end constructor()

	@Override // Superclass: Entity
	public void advance()
	{
		if(GameScreen.frozen) return; // Skips Action if Frozen
		updateField();
		updateVectors();
		move(this.vel);
	}	// end method advance

	@Override // Superclass: Entity
	public void draw(Graphics g)
	{	// Hardcode image for Demo
		g2D = (Graphics2D)g;
		g2D.drawImage(Images.sprites[0][ getField() ][ movingRel(LEFT)?0:1 ], this.x, this.y, lenB, lenB, null);
	}	// end method draw
}	// end class Player
