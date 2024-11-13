import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

// Superclass Screen
public class Screen {
	
	// Canvas on which the elements are supposed to be drawn.
	public GraphicsProgram canvas;
	
	// Doubly Linked List of objects to be drawn.
	public ObjectList objects;
	
	// Screen from which the user switched to current screen.
	public Screen parentScreen;
	
	// Default constructor
	public Screen(GraphicsProgram canvas) {
		this.canvas = canvas;
		this.objects = new ObjectList();
	}
	
	// Updates all objects on the list
	public void update() {
		objects.update();
	}
	
	// Draws all objects on the list
	public void draw() {
		objects.draw();
	}

	// Redraws the screen. Must be falled before setting active screen to
	// given instance
	public void redraw() {
		canvas.removeAll();
		objects.draw();
	}
	
	
	// Calls mouseMoved event listener for all elements in the list
	public void mouseMoved(MouseEvent e) {
		objects.mouseMoved(e);
	}
	
	// Calls mouseClicked event listener for all elements in the list
	public void mouseClicked(MouseEvent e) {
		objects.mouseClicked(e);
	}

	// Calls mousePressed event listener for all elements in the list
	public void mousePressed(MouseEvent e) {
		objects.mousePressed(e);
	}

	// Calls mouseReleased event listener for all elements in the list
	public void mouseReleased(MouseEvent e) {
		objects.mouseReleased(e);
	}
	
}
