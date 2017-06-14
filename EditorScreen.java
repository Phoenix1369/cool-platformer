/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Editor Screen
*******/
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.*;

class EditorScreen extends JPanel implements MouseListener, MouseMotionListener, ComponentListener
{
	private static final int edW = 1; // Edge Width around Screen
	private static Block[][] blocks;
	private SidebarPanel SP;
	private JFrame sidebar;
	
	private static final int offX = 6;
	private static final int offY = 29;
	public static final Dimension size = new Dimension(800, 600);
	public static final Dimension sizeSidebar = new Dimension((size.width+offX) / 10, size.height+offY);
	
	EditorScreen(Dimension dim)
	{
		addComponentListener(this);
		
		setFocusable(true);
		requestFocusInWindow();
		addMouseListener(this);
		addMouseMotionListener(this);
		blocks = new Block[dim.height / Block.getLen() + edW*2][dim.width / Block.getLen() + edW*2];
		initBlocks();
		
		sidebar = new JFrame();
		sidebar.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //prevent sidebar from being closed
		sidebar.setResizable(false);
		sidebar.setPreferredSize(sizeSidebar);
		SP = new SidebarPanel();
		sidebar.add(SP);
		SP.registerEditor(this);
		
		sidebar.pack();
	}	// end constructor(Dimension, SidebarPanel)

	public void initBlocks()
	{
		for(int i = 0; i < blocks.length; ++i)
			for(int j = 0; j < blocks[i].length; ++j) // Default Tiling
				blocks[i][j] = new Block((j-edW) * Block.getLen(), (i-edW) * Block.getLen(), Entity.DOWN, 0);

		//initialize a border of walls around the board
		for(int i = 0; i < blocks.length; i++) //vertical walls
		{
			blocks[i][0].setBlock(1);
			blocks[i][blocks[0].length - 1].setBlock(1);
		}
		for(int i = 0; i < blocks[0].length; i++) //horizontal walls
		{
			blocks[0][i].setBlock(1);
			blocks[blocks.length - 1][i].setBlock(1);
		}
	}	// end method initBlocks
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		// Clears JPanel
		g.clearRect(0, 0, getWidth(), getHeight());

		// Draws Blocks
		for(int i = edW; i < blocks.length-edW; ++i)
			for(int j = edW; j < blocks[i].length-edW; ++j)
				blocks[i][j].draw(g, blocks[i-1][j].getBlock()==1);
	}	// end method paintComponent
	
	public void floodFill(int y, int x, boolean fillBlock, int type, int typeToReplace)
	{
		if(type == typeToReplace) return; //no need to fill if you're currently on the same block you want to fill it with
		if(fillBlock && (type == Block.PLAYER || type == Block.GOAL)) return; //can't flood fill unique entities
		if(x >= 1 && x < blocks[0].length - 1 && y >= 1 && y < blocks.length - 1)
		{
			if(fillBlock) //if the flood fill is for blocks
			{
				if(blocks[y][x].getBlock() == typeToReplace)
					blocks[y][x].setBlock(type);
				else return;
			}
			if(!fillBlock) //if the flood fill is for fields
			{
				if(blocks[y][x].getField() == typeToReplace)
					blocks[y][x].setField(type);
				else return;
			}
			floodFill(y-1, x, fillBlock, type, typeToReplace);
			floodFill(y+1, x, fillBlock, type, typeToReplace);
			floodFill(y, x-1, fillBlock, type, typeToReplace);
			floodFill(y, x+1, fillBlock, type, typeToReplace);
		}
	}	// end method floodFill

	public void loadFromManager(final String dir, final String file)
	{	// Loads the level
		StageManager.loadMap(dir, file, blocks);
		repaint();
	}	// end method loadFromManager

	public void saveToManager(final String dir, final String file)
	{	// Saves the Map to the desired file
		try
		{
			StageManager.saveMap(dir, file, blocks);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}	// end method saveToManager
	
	public void clear()
	{
		initBlocks(); //reset the board to the default state
		repaint();
	}

	public void mouseMoved(MouseEvent e){   }
	public void mouseDragged(MouseEvent e) 
	{
		if(e.getX() > 0 && e.getX() < (blocks[0].length - edW*2) * Block.getLen()
			&& e.getY() > 0 && e.getY() < (blocks.length - edW*2) * Block.getLen()) //if the mouse is within the bounds of the screen
		{
			Block selectedBlock = blocks[e.getY() / Block.getLen() + edW][e.getX() / Block.getLen() + edW];
			if(SwingUtilities.isLeftMouseButton(e)) 
			{
				if(SP.getCurrTool().equals("Draw"))
				{
					if(SP.getCurrBlock() == Block.GOAL || SP.getCurrBlock() == Block.PLAYER)
						removeAllPrev(SP.getCurrBlock());
					selectedBlock.setBlock(SP.getCurrBlock()); //only type of block that can be drawn
				}
				else if(SP.getCurrTool().equals("Erase"))
					selectedBlock.setBlock(Block.AIR); //default block: air
			}
			if(SwingUtilities.isRightMouseButton(e))
			{
				if(SP.getCurrTool().equals("Draw"))
					selectedBlock.setField(SP.getCurrField()); //the selected field to be drawn
				else if(SP.getCurrTool().equals("Erase"))
					selectedBlock.setField(2); //default field: down
			}
			repaint();
		}
	}
	public void mouseClicked( MouseEvent e )
	{
		Block selectedBlock = blocks[e.getY() / Block.getLen() + edW][e.getX() / Block.getLen() + edW];
		if(SwingUtilities.isLeftMouseButton(e)) 
		{
			if(SP.getCurrTool().equals("Draw"))
			{
				if(SP.getCurrBlock() == Block.GOAL || SP.getCurrBlock() == Block.PLAYER)
					removeAllPrev(SP.getCurrBlock());
				selectedBlock.setBlock(SP.getCurrBlock()); //only type of block that can be drawn
			}
			else if(SP.getCurrTool().equals("Erase"))
				selectedBlock.setBlock(Block.AIR); //default block: air
			else if(SP.getCurrTool().equals("Fill"))
				floodFill(e.getY() / Block.getLen() + edW, e.getX() / Block.getLen() + edW, true, SP.getCurrBlock(), selectedBlock.getBlock());
		}
		if(SwingUtilities.isRightMouseButton(e))
		{
			if(SP.getCurrTool().equals("Draw"))
				selectedBlock.setField(SP.getCurrField()); //the selected field to be drawn
			else if(SP.getCurrTool().equals("Erase"))
				selectedBlock.setField(2); //default field: down
			else if(SP.getCurrTool().equals("Fill"))
				floodFill(e.getY() / Block.getLen() + edW, e.getX() / Block.getLen() + edW, false, SP.getCurrField(), selectedBlock.getField());
		}
		repaint();
	}	
	
	public void removeAllPrev(int blockType)
	{
		for(int i = 0; i < blocks.length; ++i)
		{
			for(int j = 0; j < blocks[i].length; ++j)
			{
				if(blocks[i][j].getBlock() == blockType)
					blocks[i][j].setBlock(Block.AIR); //default block: air
			}
		}
	}
	
	public boolean hasAllReqs()
	{
		boolean gl = false, pl = false;
		for(int i = 0; i < blocks.length; ++i)
		{
			for(int j = 0; j < blocks[i].length; ++j)
			{
				if(blocks[i][j].getBlock() == Block.GOAL)
					gl = true;
				else if(blocks[i][j].getBlock() == Block.PLAYER)
					pl = true;
			}
		}
		return (gl && pl);
	}
	
	public void mousePressed( MouseEvent e ){   }
	public void mouseExited( MouseEvent e ){   }
	public void mouseEntered( MouseEvent e ){   }
	public void mouseReleased( MouseEvent e ){   }
	
	public void componentShown(ComponentEvent e)
	{
		JFrame tempJF = (JFrame)SwingUtilities.getWindowAncestor(this);
		sidebar.setVisible(true); //let the sidebar be seen
		sidebar.setLocation((int)(tempJF.getLocation().getX() + tempJF.getWidth() + offY), (int)(tempJF.getLocation().getY()));
		tempJF.toFront(); //don't give the sidebar focus
	}
	public void componentHidden(ComponentEvent e)
	{
		sidebar.setVisible(false);
	}
	public void componentResized(ComponentEvent e){   }
	public void componentMoved(ComponentEvent e){   }
}	// end class EditorScreen
