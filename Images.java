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
	public static BufferedImage[][][] sprites;
	public static BufferedImage[][] tiles;
	public static BufferedImage[] tint;
	public static final Color[] tintBG = {
		new Color(255,   0,   0, 100), new Color(255, 255, 0, 100),
		new Color(165, 255, 255, 100), new Color(  0, 255, 0, 100)
	};
	public static final String imgDir = new File("include", "demo").getPath();
	public static final int PIX = 4; // RGBA Channels(4)
	// Transformation Constants
	public static final int INV = 0, ROT = 1;
	// Temporary Variables
	private static BufferedImage temImg, temImg2;
	private static Graphics2D g2D;

	public static void loadAll() throws IOException
	{	// Loads Sprites
		sprites = new BufferedImage[3][4][2];
		temImg = ImageIO.read(new File(imgDir, "sprites.png"));
		for(int i = 0; i < sprites.length; ++i)
		{
			loadSubimage(sprites[i][Entity.DOWN], 1, 0, i * Block.getLen(), Block.getLen(), Block.getLen());
			mirror(sprites[i][Entity.DOWN], 0, sprites[i][Entity.DOWN][1], Block.getLen(), Block.getLen(), INV);
			for(int j = 1; j < 4; ++j)
				for(int k = 0; k < 2; ++k)
					mirror(sprites[i][(Entity.DOWN+j) % 4], k, sprites[i][(Entity.DOWN+j-1) % 4][k], Block.getLen(), Block.getLen(), ROT);
		}
		// Loads Tiles
		tiles = new BufferedImage[6][2];
		temImg = ImageIO.read(new File(imgDir, "tiles.png"));
		for(int i = 0; i < tiles.length; ++i)
			for(int j = 0; j < tiles[i].length; ++j)
				loadSubimage(tiles[i], j, j * Block.getLen(), i * Block.getLen(), Block.getLen(), Block.getLen());
		tint = new BufferedImage[4];
		for(int i = 0; i < tint.length; ++i)
		{
			tint[i] = new BufferedImage(Block.getLen(), Block.getLen(), BufferedImage.TYPE_INT_ARGB);
			g2D = tint[i].createGraphics();
			g2D.setColor(tintBG[i]); // Opacity: 100 / 255
			g2D.fillRect(0, 0, tint[i].getWidth(), tint[i].getHeight());
			g2D.dispose();
		}
	}	// end method loadAll

	private static void loadSubimage(BufferedImage[] bi, int idx, int x, int y, int w, int h)
	{	// Subimage of Spritesheet (x,y,w,h)
		temImg2 = temImg.getSubimage(x, y, w, h);
		// New Blank Buffered Image
		bi[idx] = new BufferedImage(temImg2.getWidth(), temImg2.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		g2D = bi[idx].createGraphics();
		g2D.drawImage(temImg2, 0, 0, null);
		g2D.dispose();
	}	// end method loadSubimage

	public static void mirror(BufferedImage[] des, int dx, final BufferedImage src, int w, int h, int flag)
	{	// Clones a transformed 'src' into 'des[dx]'
		// Java's DataBufferByte is faster than AffineTransform and img.getRGB(row,col)
		des[dx] = new BufferedImage(w, h, src.getType());
		byte[] dpx = ((DataBufferByte)des[dx].getRaster().getDataBuffer()).getData();
		byte[] spx = ((DataBufferByte)src.getRaster().getDataBuffer()).getData();
		int cur, ptr=0;

		for(int i = 0; i < h; ++i)
			for(int j = 0; j < w; ++j)
			{
				cur = (i * w + j) * PIX;
				if(flag==INV)
					ptr = (i * w + (w-j-1)) * PIX; // Horizontally inverts 'src'
				else if(flag==ROT)
					ptr = ((w-j-1) * w + i) * PIX;
				for(int k = 0; k < 4; ++k)
					dpx[cur+k] = spx[ptr+k]; // Channels: ABGR
			}
	}	// end method mirror
}	// end class Images
