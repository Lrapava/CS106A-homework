/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class NameSurferGraph extends GCanvas 
	implements NameSurferConstants, ComponentListener {

	private ArrayList<NameSurferEntry> entries;
	private ArrayList<Color> colors;
	private RandomGenerator rng = RandomGenerator.getInstance();

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
		entries = new ArrayList<NameSurferEntry>();
		colors = new ArrayList<Color>();
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		entries = new ArrayList<NameSurferEntry>();
	}
	
	/**
	* Draws the grid on top of which graphs will be displayed
	*/
	private void drawGrid() {
		removeAll();
		
		int deltaX = getWidth() / NDECADES;
		for (int i = 1; i < NDECADES; i++) {
			add(new GLine(i*deltaX, 0, i*deltaX, getHeight()));
		}
		
		add(new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE));
		add(new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE));
		
		for (int i = 0; i < NDECADES; i++) {
			int decade = START_DECADE + i*10;
			GLabel decadeLabel = new GLabel(""+decade);
			decadeLabel.setLocation(i*deltaX + 5, getHeight() - 5);
			add(decadeLabel);
		}
	}
	
	/**
	* Draws all active entries on the screen
	*/
	private void drawEntries() {
		for (int i = 0; i < entries.size(); i++) {
			drawEntry(entries.get(i), colors.get(i));
		}
	}
	
	/**
	* Draws graph for a single entry
	*/	
	private void drawEntry(NameSurferEntry entry, Color color) {
		String name = entry.getName();
		int deltaX = getWidth() / NDECADES;
		int prevY = getHeight() - GRAPH_MARGIN_SIZE; 
		int prevX = 0;
		for (int i = 0; i < NDECADES; i++) {
			int rank = entry.getRank(i);
			String rankText = " " + rank;
			if (rank == 0) {
				rank = 1000;
				rankText = "*";
			}
			int x = deltaX * i;
			int y = (getHeight() - 2*GRAPH_MARGIN_SIZE)*rank/1000 + GRAPH_MARGIN_SIZE;
			
			GLabel nameLabel = new GLabel(name + rankText);
			nameLabel.setColor(color);
			GLine line = new GLine(prevX, prevY, x, y);
			line.setColor(color);
			
			add(nameLabel, x + 5, y);
			add(line);
			
			prevX = x;
			prevY = y;
		}
	}
	
	/** 
	* Picks a random color out of pre-defined options
	*/
	private Color randColor() {
		int rand = rng.nextInt(6);
		System.out.printf(""+rand+"\n");
		Color arr[] = { 
			Color.RED, Color.GREEN, Color.BLUE, 
			Color.CYAN, Color.MAGENTA, Color.GRAY
		};
		return arr[rand];
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		entries.add(entry);
		colors.add(randColor());
	}
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		drawGrid();
		drawEntries();
	}
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
