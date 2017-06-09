/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: NormalEnemy character - avoids walking off ledges
*******/
import java.awt.*;

class NormalEnemy extends Enemy
{
	NormalEnemy(double x, double y)
	{
		super();
		this.pos = new Vector2(x, y);
	}	// end constructor(double,double)

	public void advance()
	{
		if(frozen) return;
		// Directions are RELATIVE here ie. "R" in "Up" Field <--> "L" in "Down" (Normal) Field
		// Stops at the edge of a cliff
		if(movingRel(RIGHT))
		{
			if(!checkBeyondR(getField(), LOW) || checkBeyondR(getField(), ADJ))
				setKey((getField() - RIGHT + 4) % 4, false); // Releases "Right"
		}
		else if(movingRel(LEFT))
		{
			// System.out.println("LEFT");
			if(!checkBeyondL(getField(), LOW) || checkBeyondL(getField(), ADJ))
				setKey((getField() - LEFT + 4) % 4, false); // Releases "Left"
		}
		else if(checkBlock(getField()))
		{	// Begin Moving
			boolean moveL = !checkBeyondL(getField(), ADJ);
			// System.out.println("checkBlock: " + ( (getField() - LEFT + 4) % 4 ) + " -> " + moveL);
			setKey((getField() - LEFT  + 4) % 4, moveL); // Toggles "Left"
			setKey((getField() - RIGHT + 4) % 4, moveL ^ true); // Toggles "Right"
			// System.out.println(keysPressedABS[(getField() - LEFT + 4) % 4] + " << ");
		}
		updateField();
		updateVectors();
		this.vel.add(this.acc);
		move(this.vel);
		// System.out.println(keysPressedABS[(getField() - LEFT + 4) % 4] + " << ");
	}	// end method advance

	@Override // Superclass: Entity
	public void draw(Graphics g)
	{	// Hardcode image for Demo
		g2D = (Graphics2D)g;
		g2D.drawImage(Images.sprites[1], (int)Math.round(pos.X), (int)Math.round(pos.Y), Block.getLen(), Block.getLen(), null);
	}	// end method draw
}	// end class Entity
