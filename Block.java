/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Partition the screen into Block Objects
*******/
import java.awt.*;

class Block {
	private static final int SIZE = 20;
	private static Graphics2D g2D;

	private int[] type; // Field / Block type

	private Rectangle area;

	Block(int x, int y, int field, int block) {
		this.area = new Rectangle(x, y, SIZE, SIZE);
		this.type = new int[2];
		this.type[0] = field;
		this.type[1] = block;
	}	// end constructor()

	public void draw(Graphics g) {
		g2D = (Graphics2D)g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Blits image of Block type at the Rectangle Area
		g2D.drawImage(Images.demo[ this.type[1] ], this.area.x, this.area.y, SIZE, SIZE, null);
	}	// end method draw

	public int getBlock() {
		return this.type[1];
	}	// end method getBlock

	public static int getSize() {
		return SIZE;
	}	// end method getSize

	public void setBlock(int block) {
		this.type[1] = block;
	}	// end method setBlock
}	// end class Block
