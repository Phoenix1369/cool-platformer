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
{
	public static BufferedImage[] demo;
	// Temporary Variables
	private static BufferedImage temImg, temImg2;
	private static Graphics2D g2D;

	public static void loadAll() throws IOException 
	{
		// Loads Demo Images
		demo = new BufferedImage[3];
		temImg = ImageIO.read(new File("include/demo/demotiles.png"));
		for(int j = 0; j < demo.length; ++j)
		{
			// Subimage of Spritesheet (x,y,w,h)
			temImg2 = temImg.getSubimage(j * Block.getLen(), 0, Block.getLen(), Block.getLen());
			// New Blank Buffered Image
			demo[j] = new BufferedImage(temImg2.getWidth(), temImg2.getHeight(), BufferedImage.TYPE_INT_ARGB);
			g2D = demo[j].createGraphics();
			g2D.drawImage(temImg2, 0, 0, null);
			g2D.dispose();
		}
	}	// end method loadAll
}	// end class Images
