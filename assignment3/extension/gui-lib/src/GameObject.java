import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class GameObject {
	
	public GObject object;
	public GraphicsProgram canvas;
	
	public GameObject next;
	public GameObject prev;
		
	public GameObject(GObject object, GraphicsProgram canvas) {
		 this.object = object;
		 this.canvas = canvas;
	}

	public GameObject(GraphicsProgram canvas) {
		 this.canvas = canvas;
	}
		
	public void update() {
		return;
	}
		
	public void draw() {
		if (object != null) {
			canvas.add(object);
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		if (canvas.getElementAt(e.getX(), e.getY()) == this.object) {
			onHover(e);
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		if (canvas.getElementAt(e.getX(), e.getY()) == this.object) {
			onClick(e);
		}
	}

	public void mousePressed(MouseEvent e) {
		if (canvas.getElementAt(e.getX(), e.getY()) == this.object) {
			onPress(e);
		}
	}
	
    public void mouseReleased(MouseEvent e) {
		if (canvas.getElementAt(e.getX(), e.getY()) == this.object) {
			onRelease(e);
		}    	
    }
	
	public void onHover(MouseEvent e) {
		return;		
	}
	
	public void onClick(MouseEvent e) {
		return; 
	}
	
	public void onPress(MouseEvent e) {
		return;
	}

	public void onRelease(MouseEvent e) {
		return;
	}

	
}
