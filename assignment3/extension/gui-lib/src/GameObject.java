import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

// Stores doubly linked list element containing a GObject and canvas on which
// the object is supposed to be drawn. A superclass for Label, Button, Slider.
public class GameObject {
	
	// GObject to be drawn
	public GObject object;
	
	// Canvas on which the object should be drawn
	public GraphicsProgram canvas;
	
	// Next & previous elements in linked list
	public GameObject next;
	public GameObject prev;

	// Constructor with GObject
	public GameObject(GObject object, GraphicsProgram canvas) {
		 this.object = object;
		 this.canvas = canvas;
	}

	// Constructor without GObject
	public GameObject(GraphicsProgram canvas) {
		 this.canvas = canvas;
	}
	
	// Update function. supposed to be called before rendering every frame on
	// which the GameObject should be displayed
	public void update() {
		return;
	}
	
	// This function renders the object
	public void draw() {
		if (object != null) {
			canvas.add(object);
		}
	}
	
	// mouseMoved even listener
	public void mouseMoved(MouseEvent e) {
		if (canvas.getElementAt(e.getX(), e.getY()) == this.object) {
			onHover(e);
		}
	}
	
	// mouseClicked even listener
	public void mouseClicked(MouseEvent e) {
		if (canvas.getElementAt(e.getX(), e.getY()) == this.object) {
			onClick(e);
		}
	}

	// mousePressed even listener
	public void mousePressed(MouseEvent e) {
		if (canvas.getElementAt(e.getX(), e.getY()) == this.object) {
			onPress(e);
		}
	}
	
	// mouseReased even listener
    public void mouseReleased(MouseEvent e) {
		if (canvas.getElementAt(e.getX(), e.getY()) == this.object) {
			onRelease(e);
		}
    }
	
	// Called if the user hovers on top of the GameObject
	public void onHover(MouseEvent e) {
		return;		
	}
	
	// Called if the user clicks on top of the GameObject
	public void onClick(MouseEvent e) {
		return; 
	}
	
	// Called if the user presses on top of the GameObject
	public void onPress(MouseEvent e) {
		return;
	}

	// Called if the user releases on top of the GameObject
	public void onRelease(MouseEvent e) {
		return;
	}
}
