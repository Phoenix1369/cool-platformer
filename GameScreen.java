/******
* name: Patrick Au, James Long
* date: March 2017
* code: ICS4U1
* note: Main Game Screen
*******/
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

class GameScreen extends JPanel implements ActionListener, Runnable {
	private Thread gameScreen;
	private Timer timer;

	private static final int FPS = 30;
	private static final int delay = 1000 / FPS;

	GameScreen() {
		timer = new Timer(delay, this);
	}	// end constructor

	public void init() {
		gameScreen = new Thread(this);
		gameScreen.start();
	}	// end method init

	@Override // Interface: ActionListener
	public void actionPerformed(ActionEvent ae) {
		this.repaint();
	}	// end method actionPerformed

	@Override // Interface: Runnable
	public void run() {
		timer.start();
	}	// end method run
}
