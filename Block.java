/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Partition the screen into Block Objects
*******/
import java.awt.*;

class Block extends Rectangle
{
	public static final int AIR    = 0;
	public static final int EARTH  = 1;
	public static final int GOAL   = 2;
	public static final int ENEMY1 = 3;
	public static final int ENEMY2 = 4;
	public static final int PLAYER = 5;

	private static final int LEN = 20;

	private static Graphics2D g2D;

	private int[] type; // Field / Block type

	Block(int x, int y, int field, int block)
	{
		super(x, y, LEN, LEN);
		this.type = new int[2];
		this.type[0] = field;
		this.type[1] = block;
	}	// end constructor()

	public void draw(Graphics g, boolean above, boolean lvledit)
	{
		g2D = (Graphics2D)g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	
		// Draws the Block in layers
		if(this.type[1] != EARTH)
			g2D.drawImage(Images.tint[ this.type[0] ], this.x, this.y, this.width, this.height, null); // Field
		g2D.drawImage(Images.tiles[AIR][1], this.x, this.y, this.width, this.height, null); // Gridlines
		
		g2D.drawImage(Images.tiles[ this.type[1] ][0], this.x, this.y, this.width, this.height, null);

		if(!above || (this.type[1] != EARTH)) // Draws grass only on topmost earth block
			g2D.drawImage(Images.tiles[ this.type[1] ][1], this.x, this.y, this.width, this.height, null);	
		if((this.type[1] == EARTH) && (this.type[0] != Entity.DOWN))
			g2D.drawImage(Images.tint[ this.type[0] ], this.x, this.y, this.width, this.height, null); // Field
	}	// end method draw

	public int getBlock()
	{
		return this.type[1];
	}	// end method getBlock

	public int getField()
	{
		return this.type[0];
	}	// end method getField

	public static int getLen()
	{
		return LEN;
	}	// end method getSize

	public void setBlock(int block)
	{
		this.type[1] = block;
	}	// end method setBlock

	public void setField(int field)
	{
		this.type[0] = field;
	}	// end method setField
}	// end class Block
