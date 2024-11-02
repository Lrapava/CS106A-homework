/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {
	
	// Radius of the smallest circle of the tarfet
	private static final int CENTER_RADIUS = 22;
	
	// Difference in radii between neighboring layer
	private static final int DELTA_RADIUS = 25;
	
	// Number of layers diplayed on the target
	private static final int NUMBER_OF_LAYERS = 100;
	
	// Draws all layers from outer towards inner	
	public void run() {
		for (int i = 0; i < NUMBER_OF_LAYERS; i++) {
			drawLayer(NUMBER_OF_LAYERS - i);
		}
	}
	
	// Draws n-th layer of the target on top of everything
	private void drawLayer(int n) {
		
		final int r = CENTER_RADIUS + (n-1) * DELTA_RADIUS;
		final int w = getWidth();
		final int h = getHeight();
	
		GOval myOval = new GOval((w-r)/2, (h-r)/2, r, r);
		myOval.setColor(Color.red);
		myOval.setFilled(true);
		
		if (n % 2 == 0) {
			myOval.setColor(Color.white);
		}
		
		add(myOval);
		
	}
	
}
