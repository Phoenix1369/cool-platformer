/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Stage Loader Class
*******/
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;
import javax.swing.*;

class StageManager 
{	
	static BufferedReader in;
	static BufferedWriter out;
	static StringTokenizer st;
	
	public static String gets() throws IOException 
	{
		while(st==null || !st.hasMoreTokens())
		{
			String ss = in.readLine().trim();
			st = new StringTokenizer(ss);
		}
		return st.nextToken();
	}
	
	public static void loadMap(Block[][] blockArr) throws IOException 
	{
		// Load level
		in = new BufferedReader(new FileReader(new File("Map.txt")));
		for(int i = 0; i < blockArr.length; i++)
			for(int j = 0; j < blockArr[i].length; j++)
				blockArr[i][j].setBlock(Integer.parseInt(gets()));
		for(int i = 0; i < blockArr.length; i++)
			for(int j = 0; j < blockArr[i].length; j++)
				blockArr[i][j].setField(Integer.parseInt(gets()));
		in.close();
	}	// end method loadMap
	
	public static void saveMap(Block[][] blockArr) throws IOException 
	{
		// Save level
		out = new BufferedWriter(new FileWriter(new File("Map.txt")));
		for(int i = 0; i < blockArr.length; i++)
		{
			for(int j = 0; j < blockArr[i].length; j++)
			{
				out.write(blockArr[i][j].getBlock() + " ");
			}
			out.newLine();
		}
		out.newLine();
		for(int i = 0; i < blockArr.length; i++)
		{
			for(int j = 0; j < blockArr[i].length; j++)
			{
				out.write(blockArr[i][j].getField() + " ");
			}
			out.newLine();
		}
		out.close();
		System.out.println("Saved");
	}	
}	// end class StageManager
