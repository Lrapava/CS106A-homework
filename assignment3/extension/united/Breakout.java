/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;
	
	// Program entry point
	public void run() {
		
		Chronograph chron = new Chronograph(Global.DEFAULT_FPS_LOCK);
		GLabel FPSLabel = new GLabel("");
		
		State.init(this, chron);
		State.activeScreen().redraw();

		addMouseListeners();
		
		// Game loop
		while (State.gameRunning()) {
			State.activeScreen().draw();
			State.activeScreen().update();
			
			// FPS draw label
			FPSLabel.setLabel(Math.round(1.0/chron.dt()) + " FPS");
			FPSLabel.setVisible(true);
			this.add(FPSLabel, 10, 20);
			
			chron.sync();
		}
		
		System.exit(0);
		
	}
	
	// Saves mouse state in State & calls relevant event listener for active screen
	public void mouseMoved(MouseEvent e) {	
		State.mouseX = e.getX();
		State.mouseY = e.getY();
		State.activeScreen().mouseMoved(e);
	}
	
	// Calls relevant event listener for active screen
	public void mouseClicked(MouseEvent e) {
		State.activeScreen().mouseClicked(e);
	}

	// Saves mouse state in State & calls relevant event listener for active screen
	public void mousePressed(MouseEvent e) {
		State.mousePressed = true;
		State.activeScreen().mousePressed(e);
	}

	// Saves mouse state in State & calls relevant event listener for active screen
	public void mouseReleased(MouseEvent e) {
		State.mousePressed = false;
		State.activeScreen().mouseReleased(e);
	}

}
