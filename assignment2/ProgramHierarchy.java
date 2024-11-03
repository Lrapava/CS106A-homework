/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {	
	
	// Dimentsions of text boxes
	private static final int BOX_WIDTH = 120;
	private static final int BOX_HEIGHT = 40;
	
	// Vertical & horizontal padding between text boxes
	private static final int LAYER_SEPARATION = 40;
	private static final int BOX_SEPARATION = 20;
	
	public void run() {
		
		// Calculating offset so that the diagram is centred
		
		final int diagramWidth = 3*BOX_WIDTH + 2*BOX_SEPARATION;
		final int diagramHeight = 2*BOX_HEIGHT + 1*LAYER_SEPARATION;
		final int XOffset = (getWidth() - diagramWidth) / 2;
		final int YOffset = (getHeight() - diagramHeight) / 2;
		
		drawDiagram(XOffset, YOffset);
		
	}
	
	// Draws the heirarchy diagram at (x, y)
	
	private void drawDiagram(int x, int y) {
		
		final int diagramWidth = 3*BOX_WIDTH + 2*BOX_SEPARATION;
		final int diagramHeight = 2*BOX_HEIGHT + 1*LAYER_SEPARATION;
		final int X_STEP = BOX_WIDTH + BOX_SEPARATION;
		final int Y_STEP = BOX_HEIGHT + LAYER_SEPARATION;

		// Draw top layer boxes
		
		drawTextBox(x + (diagramWidth - BOX_WIDTH) / 2, y, "Program");
		
		// Draw bottom layer boxes
		
		drawTextBox(x + 0*X_STEP, y+Y_STEP, "GraphicsProgram");
		drawTextBox(x + 1*X_STEP, y+Y_STEP, "ConsoleProgram");
		drawTextBox(x + 2*X_STEP, y+Y_STEP, "DialogProgram");
		
		// Connect boxes with lines
		
		final int rootX = x + diagramWidth / 2;
		final int rootY = y + BOX_HEIGHT;
		final int leafX = x + BOX_WIDTH / 2;
		final int leafY = y + Y_STEP;
		
		for (int i = 0; i < 3; i++) {
			add(new GLine(rootX, rootY, leafX + i*X_STEP, leafY));
		}
		
	}
	
	// Draws text box of predefined size at coordinates (x, y) with text inside

	private void drawTextBox(int x, int y, String text) {
		GRect textBox = new GRect(x, y, BOX_WIDTH, BOX_HEIGHT);
		GLabel label = new GLabel(text);

		final int XOffset = (BOX_WIDTH - (int)label.getWidth()) / 2;
		final int YOffset = (BOX_HEIGHT + (int)label.getAscent()) / 2;

		add(textBox);
		add(label, x+XOffset, y+YOffset);
	}
	
}
