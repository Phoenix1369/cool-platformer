/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Generic entity class
*******/
import java.awt.*;

class Entity {
	protected static final int UP = 0;
	protected static final int RIGHT = 1;
	protected static final int DOWN  = 2;
	protected static final int RIGHT = 3;

	protected static Graphics2D g2D;
	protected static int[] posInt = new int[2];

	protected Rectangle area;
	protected Vector2 acc;
	protected Vector2 pos;
	protected Vector2 vel;

	Entity() {
		this.acc = new Vector2();
		this.pos = new Vector2();
		this.vel = new Vector2();
		this.area= new Rectangle((int)Math.round(pos.X), (int)Math.round(pos.Y), Block.getSize(), Block.getSize());
	}	// end constructor()

	public void advance() {
	}	// end method advance

	public void draw(Graphics g) {
	}	// end method draw

	public void setAcc(Vector2 acc) {
		this.acc = acc;
	}	// end method setAcc
}	// end class Entity
