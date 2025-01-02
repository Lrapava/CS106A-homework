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
	private int interpMode = 0;
	
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
		
		// int deltaX = getWidth() / NDECADES;
		for (int i = 1; i < NDECADES; i++) {
			double x = getWidth()*i/NDECADES;
			add(new GLine(x, 0, x, getHeight()));
		}
		
		add(new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE));
		add(new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE));
		
		for (int i = 0; i < NDECADES; i++) {
			double x = getWidth()*i/NDECADES;
			int decade = START_DECADE + i*10;
			GLabel decadeLabel = new GLabel(""+decade);
			decadeLabel.setLocation(x + 5, getHeight() - 5);
			add(decadeLabel);
		}
	}
	
	/**
	* Draws all active entries on the screen
	*/
	private void drawEntries() {
		String mode = "";
		for (int i = 0; i < entries.size(); i++) {
			switch (interpMode) {
				case 0:
					drawEntryLinear(entries.get(i), colors.get(i));
					break;
				case 1:
					drawEntryLagrange(entries.get(i), colors.get(i));
					break;
				case 2:
					drawEntryCubic(entries.get(i), colors.get(i));
					break;
				case 3:
					drawEntryPres2d(entries.get(i), colors.get(i));
					break;
			}
			drawLabels(entries.get(i), colors.get(i));
		}
	}
	
	/**
	* Returns the name of active interpolation method
	*/
	public String getModeName() {
		String[] names = {
			"Linear interpolation",
			"Lagrange interpolation",
			"Cubic interpolation",
			"Cubic interpolation (preserve 2nd derivative)"
		};
		return names[interpMode];
	}
	
	/**
	* Linear interpolation
	*/
	private void drawEntryLinear(NameSurferEntry entry, Color color) {
		int prevY = getHeight() - GRAPH_MARGIN_SIZE; 
		int prevX = 0;
		for (int i = 0; i < NDECADES; i++) {
			int rank = entry.getRank(i);
			if (rank == 0) {
				rank = 1000;
			}
			int x = getWidth()*i/NDECADES;
			int y = (getHeight() - 2*GRAPH_MARGIN_SIZE)*rank/1000 + GRAPH_MARGIN_SIZE;
			if (i != 0) {
				GLine line = new GLine(prevX, prevY, x, y);
				line.setColor(color);
				add(line);
			}
			prevX = x;
			prevY = y;
		}
	}
	/**
	* Evaluates polynomial at specific point
	*/
	private double poly(double[] v, double x) {
		double res = 0;
		double xp = 1;
		for (int i = 0; i < v.length; i++) {
			res += v[i] * xp;
			xp *= x;
		}
		return res;
	}
	
	/**
	* Vector multiplication by scalar value
	*/
	private double[] times(double x, double[] v) {
		double[] res = new double[v.length];
		for (int i = 0; i < v.length; i++) {
			res[i] = v[i] * x;
		}
		return res;
	}
	
	/**
	* Vector sum
	*/
	private double[] plus(double[] u, double[] v) {
		double[] res = new double[v.length];
		for (int i = 0; i < v.length; i++) {
			res[i] = u[i] + v[i];
		}
		return res;
	}
	
	/**
	* Solves a system of linear equations Ax = b
	*/
	private double[] solve(double[][] A, double[] b) {
		int n = A.length;
		int m = A[0].length;
		
		double[][] B = new double[n][m+1];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				B[i][j] = A[i][j];
			}
			B[i][m] = b[i];
		}
		// Gaussian elimination
		for (int i = 0; i < n; i++) {
			B[i] = times(1/B[i][i], B[i]);
			for (int j = i+1; j < n; j++) {
				B[j] = plus(B[j], times(-B[j][i], B[i]));
			}
		}
		// Back substitution
		for (int i = n-1; i >= 0; i--) {
			for (int j = i-1; j >= 0; j--) {
				B[j] = plus(B[j], times(-B[j][i], B[i]));
			}
		}
		
		double[] x = new double[n];
		for (int i = 0; i < n; i++) {
			x[i] = B[i][m];
		}
		return x;
	}
	
	/**
	* returns derivative of a polynomial
	*/
	private double[] derive(double[] p) {
		double[] d = new double[p.length-1];
		for (int i = 0; i < d.length; i++) {
			d[i] = p[i+1]*(i+1); 
		}
		return d;
	}

	/**
	* Lagrange interpolation
	*/
	private void drawEntryLagrange(NameSurferEntry entry, Color color) {
		double[] yl = new double[NDECADES];
		for (int i = 0; i < NDECADES; i++) {
			yl[i] = entry.getRank(i);
			if (yl[i] == 0) {
				yl[i] = 1000;
			}
		}
		
		double[] b = new double[NDECADES];
		double[][] A = new double[NDECADES][NDECADES];
		for (int i = 0; i < NDECADES; i++) {
			b[i] = yl[i];
			A[i][0] = 1;
			for (int j = 1; j < NDECADES; j++) {
				A[i][j] = A[i][j-1] * i;
			}
		}
		
		double[] p = solve(A, b);
		
		double pgx = 0;
		double pgy = ((getHeight() - 2*GRAPH_MARGIN_SIZE)*yl[0]/1000) + GRAPH_MARGIN_SIZE;
				
		for (int i = 0; i+1 < NDECADES; i++) {			
			for (int j = 0; j <= GRAPH_RESOLUTION; j++) {
				double x = i + (double)(j)/GRAPH_RESOLUTION;
				double y = poly(p, x);
				
				double gx = getWidth() * x / NDECADES;
				double gy = ((getHeight() - 2*GRAPH_MARGIN_SIZE)*y/1000) + GRAPH_MARGIN_SIZE;
				
				GLine line = new GLine(pgx, pgy, gx, gy);
				line.setColor(color);
				add(line);
				pgx = gx;
				pgy = gy;
			}
		}
	}
	
	/**
	* This interpolation method assumes derivative at i-th point is average derivative
	* between it's neighbors and uses cubic splines to fill in the gaps between
	* the given points.
	*/
	private void drawEntryCubic(NameSurferEntry entry, Color color) {
		double[] yl	= new double[NDECADES];
		for (int i = 0; i < NDECADES; i++) {
			yl[i] = entry.getRank(i);
			if (yl[i] == 0) {
				yl[i] = 1000;
			}
		}
		double[] dl = new double[NDECADES];
		dl[0] = yl[1] - yl[0];
		dl[NDECADES-1] = yl[NDECADES-1] - yl[NDECADES-2];
		
		for (int i = 1; i+1 < NDECADES; i++) {
			dl[i] = (yl[i+1]-yl[i-1])/2;
		}
		
		double pgx = 0;
		double pgy = ((getHeight() - 2*GRAPH_MARGIN_SIZE)*yl[0]/1000) + GRAPH_MARGIN_SIZE;
		for (int i = 1; i < NDECADES; i++) {
			double[] p = new double[4];
			p[0] = yl[i-1];
			p[1] = dl[i-1];
			p[2] = 2*(yl[i]-yl[i-1])-dl[i-1]-dl[i];
			p[3] = dl[i]-yl[i]+yl[i-1];

			for (int j = 0; j <= GRAPH_RESOLUTION; j++) {
				double x = (i-1) + (double)(j) / GRAPH_RESOLUTION;
				double y = poly(p, x-i+1);
				double gx = getWidth() * x / NDECADES;
				double gy = ((getHeight() - 2*GRAPH_MARGIN_SIZE)*y/1000) + GRAPH_MARGIN_SIZE;
				GLine line = new GLine(pgx, pgy, gx, gy);
				line.setColor(color);
				add(line);
				pgx = gx;
				pgy = gy;
			}
		}
	}
	
	/**
	* This interpolation uses cubic splines and preserves continuity
	* in 1st and 2nd derivatives
	*/
	private void drawEntryPres2d(NameSurferEntry entry, Color color) {

		if (NDECADES < 5) {
			drawEntryLagrange(entry, color);
			return;
		}
		
		// finding cubic which works for first 4 points
		double[] yl = new double[NDECADES];
		for (int i = 0; i < NDECADES; i++) {
			yl[i] = entry.getRank(i);
			if (yl[i] == 0) {
				yl[i] = 1000;
			}
		}
		
		double[] b = new double[4];
		double[][] A = new double[4][4];
		for (int i = 0; i < 4; i++) {
			b[i] = yl[i];
			A[i][0] = 1;
			for (int j = 1; j < 4; j++) {
				A[i][j] = A[i][j-1] * i;
			}
		}
		
		double[] p = solve(A, b);
		
		double pgx = 0;
		double pgy = ((getHeight() - 2*GRAPH_MARGIN_SIZE)*yl[0]/1000) + GRAPH_MARGIN_SIZE;
				
		for (int i = 0; i+1 < NDECADES; i++) {
			
			// generating spline polynomial
			if (i > 2) {
				double[] dp = derive(p);
				double[] ddp = derive(dp);
				double[] y = { yl[i], yl[i+1] };
				double[] d;
				if (i == 3) {
					d = new double[] { poly(dp, 3), poly(ddp, 3) };
				} else {
					d = new double[] { poly(dp, 1), poly(ddp, 1) };
				}
				p[1] = d[0];
				p[2] = d[1]/2;
				p[3] = y[1]-y[0]-p[1]-p[2];
				p[0] = y[0];
			}
			
			// drawing spline
			for (int j = 0; j <= GRAPH_RESOLUTION; j++) {
				double x = i + (double)(j)/GRAPH_RESOLUTION;
				double y = 0; 
				
				if (i < 3) {
					y = poly(p, x);
				} else {
					y = poly(p, x-i);
				}
				
				double gx = getWidth() * x / NDECADES;
				double gy = ((getHeight() - 2*GRAPH_MARGIN_SIZE)*y/1000) + GRAPH_MARGIN_SIZE;
				
				GLine line = new GLine(pgx, pgy, gx, gy);
				line.setColor(color);
				add(line);
				pgx = gx;
				pgy = gy;
			}
		}
	}
	
	/**
	* Draws labels near data points
	*/
	private void drawLabels(NameSurferEntry entry, Color color) {
		String name = entry.getName();
		for (int i = 0; i < NDECADES; i++) {
			int rank = entry.getRank(i);
			String rankText = " " + rank;
			if (rank == 0) {
				rank = 1000;
				rankText = "*";
			}
			int x = getWidth()*i/NDECADES;
			int y = (getHeight() - 2*GRAPH_MARGIN_SIZE)*rank/1000 + GRAPH_MARGIN_SIZE;
			
			GLabel nameLabel = new GLabel(name + rankText);
			nameLabel.setColor(color);
			add(nameLabel, x + 5, y);
		}
	}
	
	/** 
	* Picks a random color out of pre-defined options
	*/
	private Color randColor() {
		int rand = rng.nextInt(6);
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
	
	/**
	* Changes interpolation mode in sequential order
	*/
	public void changeInterpMode() {
		interpMode++;
		interpMode %= 4;
	}
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
