/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Image Loader Class
*******/
import java.awt.*;
import java.awt.image.*;

import java.io.IOException;
import java.io.File;

import javax.imageio.*;

class Images 
{	// Loads Image Sets
	public static BufferedImage[] sprites;
	public static BufferedImage[][] tiles;
	public static BufferedImage[][][] tint; // tint[i][j][k]: tiles[i][j] tintBG[k]
	public static final Color[] tintBG = {
		new Color(255,   0,   0, 100), new Color(255, 255, 0, 100),
		new Color(255, 255, 255,   0), new Color(  0, 255, 0, 100)
	};
	public static final String imgDir = new File("include", "demo").getPath();
	// Temporary Variables
	private static BufferedImage temImg, temImg2;
	private static Graphics2D g2D;

	public static void loadAll() throws IOException 
	{	// Loads Sprites
		sprites = new BufferedImage[2];
		temImg = ImageIO.read(new File(imgDir, "sprites.png"));
		for(int i = 0; i < sprites.length; ++i)
			loadSubimage(sprites, i, 0, i * Block.getLen(), Block.getLen(), Block.getLen());
		// Loads Tiles
		tiles = new BufferedImage[2][2];
		temImg = ImageIO.read(new File(imgDir, "tiles.png"));
		for(int i = 0; i < tiles.length; ++i)
			for(int j = 0; j < tiles[i].length; ++j)
				loadSubimage(tiles[i], j, j * Block.getLen(), i * Block.getLen(), Block.getLen(), Block.getLen());
		// Tints the Tiles
		tint = new BufferedImage[2][2][4];
		for(int i = 0; i < tint.length; ++i)
		for(int j = 0; j < tint[i].length; ++j)
		for(int k = 0; k < tint[i][j].length; ++k) // Iterate on Colours
		{
			tint[i][j][k] = new BufferedImage(tiles[i][j].getWidth(), tiles[i][j].getHeight(), BufferedImage.TYPE_INT_ARGB);
			g2D = tint[i][j][k].createGraphics();
			g2D.drawImage(tiles[i][j], 0, 0, null);
			g2D.setComposite(AlphaComposite.SrcAtop); // Composes (src in des) onto destination
			g2D.setColor(tintBG[k]); // Opacity: 100 / 255
			g2D.fillRect(0, 0, tint[i][j][k].getWidth(), tint[i][j][k].getHeight());
			g2D.dispose();
		}
	}	// end method loadAll

	private static void loadSubimage(BufferedImage[] bi, int idx, int x, int y, int w, int h)
	{	// Subimage of Spritesheet (x,y,w,h)
		temImg2 = temImg.getSubimage(x, y, w, h);
		// New Blank Buffered Image
		bi[idx] = new BufferedImage(temImg2.getWidth(), temImg2.getHeight(), BufferedImage.TYPE_INT_ARGB);
		g2D = bi[idx].createGraphics();
		g2D.drawImage(temImg2, 0, 0, null);
		g2D.dispose();
	}	// end method loadSubimage
}	// end class Images
