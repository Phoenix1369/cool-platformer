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
	private boolean frozen; //false by default
	
	private int prevField = DOWN; //previous field the player was in
	
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
		g2D.drawImage(Images.demo[2], (int)Math.round(pos.X), (int)Math.round(pos.Y), Block.getLen(), Block.getLen(), null);
	}	// end method draw

	public void freeze(boolean yesOrNo)
	{
		frozen = yesOrNo;
		if(!frozen) return;
		// Clears movement if frozen
		for(int i = 0; i < keysPressedABS.length; i++)
			setKey(i, false);
	}
	
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
		if(this.getField() == DOWN) //Code specifically needed for down-fields
		{
			keysPressed[indexToSet] = pressedDown;
		}
		else
		{
			keysPressed[(indexToSet + getField() + 4) % 4] = pressedDown; //Shift key input for left/right fields
		}
	}	// end method setKey

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
		}
		switch(this.getField())
		{
			case  DOWN: this.acc.X = 0.0; this.acc.Y = +GRAVITY; break;
			case    UP: this.acc.X = 0.0; this.acc.Y = -GRAVITY; break;
			case RIGHT: this.acc.X = +GRAVITY; this.acc.Y = 0.0; break;
			case  LEFT: this.acc.X = -GRAVITY; this.acc.Y = 0.0; break;
		}
	}	// end method updateField
}	// end class
