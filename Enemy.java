/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Enemy entity character
*******/
import java.awt.*;

class Enemy extends Entity
{
	Enemy(double px, double py)
	{
		super(px, py);
		this.M_SPD = 160.0 / GameScreen.FPS;
	}	// end constructor(double, double)
}	// end class Enemy
