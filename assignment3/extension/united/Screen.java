import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;


public class Screen {
	
	public GraphicsProgram canvas;
	public ObjectList objects;
	public Screen parentScreen;
	
	public Screen(GraphicsProgram canvas) {
		this.canvas = canvas;
		this.objects = new ObjectList();
	}
	
	public void update() {
		objects.update();
	}
	
	public void draw() {
		objects.draw();
	}

	public void redraw() {
		canvas.removeAll();
		objects.draw();
	}
	
	public void mouseMoved(MouseEvent e) {
		objects.mouseMoved(e);
	}
	
	public void mouseClicked(MouseEvent e) {
		objects.mouseClicked(e);
	}

	public void mousePressed(MouseEvent e) {
		objects.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		objects.mouseReleased(e);
	}
	
}
