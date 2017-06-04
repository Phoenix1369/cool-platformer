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

class EditorScreen extends JPanel implements MouseListener, MouseMotionListener
{
	private static final int edW = 1; // Edge Width around Screen
	private static Block[][] blocks;
	private SidebarPanel SP;
	
	EditorScreen(Dimension dim, SidebarPanel sidebar)
	{
		setFocusable(true);
		requestFocusInWindow();
		addMouseListener(this);
		addMouseMotionListener(this);
		SP = sidebar; //reference to the sidebar
		blocks = new Block[dim.height / Block.getLen() + edW*2][dim.width / Block.getLen() + edW*2];
		initBlocks();
	}
	
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
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		// Clears JPanel
		g.clearRect(0, 0, getWidth(), getHeight());

		// Draws Blocks
		for(int i = edW; i < blocks.length-edW; ++i)
			for(int j = edW; j < blocks[i].length-edW; ++j)
				blocks[i][j].draw(g);
	}	// end method paintComponent
	
	public void floodFill(int y, int x, boolean fillBlock, int type, int typeToReplace)
	{
		if(type == typeToReplace) return; //no need to fill if you're currently on the same block you want to fill it with
		if(x >= 0 && x < blocks[0].length && y >= 0 && y < blocks.length)
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
	}
	
	public void saveToManager()
	{
		try
		{
			StageManager.saveMap(blocks);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
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
					selectedBlock.setBlock(1); //only type of block that can be drawn
				else if(SP.getCurrTool().equals("Erase"))
					selectedBlock.setBlock(0); //default block: air
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
				selectedBlock.setBlock(1); //only type of block that can be drawn
			else if(SP.getCurrTool().equals("Erase"))
				selectedBlock.setBlock(0); //default block: air
			else if(SP.getCurrTool().equals("Fill"))
				floodFill(e.getY() / Block.getLen() + edW, e.getX() / Block.getLen() + edW, true, 1, selectedBlock.getBlock());
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
    public void mousePressed( MouseEvent e ){   }
	public void mouseExited( MouseEvent e ){   }
	public void mouseEntered( MouseEvent e ){   }
    public void mouseReleased( MouseEvent e ){   }
}	// end class EditorScreen
