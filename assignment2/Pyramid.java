/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;
	
	// Invokes drawLayer() for every layer that needs to be drawn
	public void run() {
		for (int i = 0; i < BRICKS_IN_BASE; i++) {
			drawLayer(i);
		}
	}
	
	// Draws n-th layer of the pyramid
	private void drawLayer(int n) {
		
		final int bricksInLayer = BRICKS_IN_BASE - n;
		final int y = getHeight() - n * BRICK_HEIGHT;
		final int brickOffset = (getWidth() - bricksInLayer * BRICK_WIDTH) / 2;
		
		for (int i = 0; i < bricksInLayer; i++) {
			int x = brickOffset + i * BRICK_WIDTH;
			add(new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT));
		}
		
		
	}
}

