/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Cool Platformer
*******/
import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.util.*;

import javax.swing.*;

public class CoolPlatformer {
	public static JFrame JF;
	public static final Dimension dim = new Dimension(800, 600);

	public static JPanel[] menu;

	public CoolPlatformer() {
		JF = new JFrame("Cool Platformer");
		JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JF.setLayout(new BorderLayout());
		JF.setResizable(false);
		JF.setSize(dim);
		JF.setLocationRelativeTo(null); // Puts JF at Centre of Screen

		menu = new JPanel[1]; // Number of Menu Screens
		menu[0] = new GameScreen();

		JF.setVisible(true);
	}	// end constructor

	public static void main(String[] args) {
		new CoolPlatformer();
	}	// end method main
}	// end class CoolPlatformer
