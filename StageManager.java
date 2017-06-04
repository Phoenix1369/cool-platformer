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
	
}	// end class StageManager
