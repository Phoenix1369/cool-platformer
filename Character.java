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
		this.acc = new Vector();
		this.pos = new Vector();
		this.vel = new Vector();
	}	// end constructor()

	public void advance() {
	}	// end method advance

	public void draw(Graphics g) {
	}	// end method draw

	public void setAcc(Vector acc) {
		this.acc = acc;
	}	// end method setAcc
}	// end class Character
