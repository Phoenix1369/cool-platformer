/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Generic character class
*******/
import java.awt.*;

class Character {
	protected static Graphics2D g2D;
	protected Vector acc;
	protected Vector pos;
	protected Vector vel;

	Character() {
		Vector acc = new Vector();
		Vector pos = new Vector();
		Vector vec = new Vector();
	}	// end constructor()

	public void advance() {
	}	// end method advance

	public void draw(Graphics g) {
	}	// end method draw
}	// end class Character
