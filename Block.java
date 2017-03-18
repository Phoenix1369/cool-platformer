/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Partition the screen into Block Objects
*******/
import java.awt.Rectangle;

class Block {
	private int[] type; // Field / Block type
	private static final int SIZE = 20;

	private Rectangle rect;

	Block(int x, int y, int field, int block) {
		this.rect.setBounds(x, y, SIZE, SIZE);
		type = new int[2];
		type[0] = field;
		type[1] = block;
	}	// end constructor()

	public static int getSize() {
		return SIZE;
	}	// end method getSize

	public void setBlock(int block) {
		this.type[1] = block;
	}
}
