/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Generic entity class
*******/
import java.awt.*;

class Entity {
	protected static Graphics2D g2D;
	protected static int[] posInt = new int[2];

	protected Rectangle area;
	protected Vector acc;
	protected Vector pos;
	protected Vector vel;

	Entity() {
		this.acc = new Vector();
		this.pos = new Vector();
		this.vel = new Vector();
		this.area= new Rectangle((int)Math.round(pos.getX()), (int)Math.round(pos.getY()), Block.getSize(), Block.getSize());
	}	// end constructor()

	public void advance() {
	}	// end method advance

	public void draw(Graphics g) {
	}	// end method draw

	public void setAcc(Vector acc) {
		this.acc = acc;
	}	// end method setAcc
}	// end class Entity
