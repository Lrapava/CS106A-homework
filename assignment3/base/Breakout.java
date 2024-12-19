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

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 42;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
	// paddle y position (fixed)
	private static final int PADDLE_Y = HEIGHT-PADDLE_Y_OFFSET-PADDLE_HEIGHT;

	// object representing paddle
	private GRect paddle;

	// object representing ball
	private GOval ball;
		
	// dt/delta time/time "differential". the rough amount paused between frames
	// in milliseconds/rough time between frames
	private static final double dt = 15;
	
	// velocity along x axis (pixels/second)
	private double v_x;
	
	// velocity along y axis (pixels/second)
	private double v_y;
	
	// random number generator instance
	private RandomGenerator rng = RandomGenerator.getInstance();
	
	// number of times the user has lost balls
	private int ndead;
	
	// number of walls left untouched
	private int nwalls;
	
	// variable tracking paused state
	private boolean paused = true;

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		createStuff();
		while (ndead < NTURNS) {
			if (!paused) {
				update();
			}
			pause(dt);
		}
		endScreen();
	}
	
	// Clears everything and lets the user know whether they won or lost
	private void endScreen() {
		String message = "";
		if (nwalls > 0) {
			message = "You lose!";
		} else {
			message = "You win!";
		}
		GLabel msg = new GLabel(message);
		final double x = (WIDTH - msg.getWidth()) / 2;
		final double y = (HEIGHT + msg.getAscent()) / 2;
		removeAll();
		add(msg, x, y);
	}
	
	// Update function. Ran every time by the game loop.
	// Main purpose: Ball movement, collision detection, and updates to game
	// state/logic in accordance to that.
	private void update() {
		double x_b = ball.getX();
		double y_b = ball.getY();
		
		double dx = v_x * (dt/1000);
		double dy = v_y * (dt/1000);

		// Note: I'm exressing velocity with module to prevent the ball from
		// getting stuck in other objects and to make sure it always leaves if 
		// it gets stuck. This is a workaround for any potential rounding errors

		// collision with walls & paddle
		GObject obju = getElementAt(x_b+dx+BALL_RADIUS, y_b+dy);
		GObject objd = getElementAt(x_b+dx+BALL_RADIUS, y_b+dy+2*BALL_RADIUS);
		GObject objl = getElementAt(x_b+dx, y_b+dy+BALL_RADIUS);
		GObject objr = getElementAt(x_b+dx+2*BALL_RADIUS, y_b+dy+BALL_RADIUS);

		if (obju != null && obju != ball) {
			if (obju != paddle) {
				remove(obju);
			}
			v_y = Math.abs(v_y);
		}

		if (objd != null && objd != ball) {
			if (objd != paddle) {
				remove(objd);
			}
			v_y = -1*Math.abs(v_y);
		}

		if (objl != null && objl != ball) {
			if (objl != paddle) {
				remove(objl);
			}
			v_x = Math.abs(v_y);
		}

		if (objr != null && objr != ball) {
			if (objr != paddle) {
				remove(objr);
			}
			v_x = -1*Math.abs(v_y);
		}

		// moving ball. this step should happen specifically after collision 
		// calculations with other objects, since otherwise getElementAt()
		// will return the ball.
		add(ball, x_b+dx, y_b+dy);

		// collision with window borders
		if (x_b+dx < 0) {
			v_x = Math.abs(v_x);
		}
		if (x_b+dx > WIDTH-2*BALL_RADIUS) {
			v_x = -1*Math.abs(v_x);
		}
		if (y_b+dy < 0) {
			v_y = Math.abs(v_y);
		}
		
		if (y_b+dy > HEIGHT) {
			ndead++;
			resetBall();
			paused = true;
		}
		
	}
	
	// Mouse movement event listener. Makes that paddle updates to the right position
	public void mouseMoved(MouseEvent e) {
		final int x_m = e.getX();
		int x_p = x_m - PADDLE_WIDTH/2;
		x_p = Integer.min(x_p, WIDTH-PADDLE_WIDTH);
		x_p = Integer.max(x_p, 0);
		paddle.setLocation(x_p, PADDLE_Y);
	}
	
	// Mouse click event listener. Makes sure the user can enter/exit paused state
	public void mouseClicked(MouseEvent e) {
		paused = !paused;
	}
	
	// Creates all the stuff
	private void createStuff() {
		ndead = 0;
		nwalls = 100;
		createBall();
		createPaddle();
		createWalls();
		addMouseListeners();
	}

	// Creates ball
	private void createBall() {
		ball = new GOval(2*BALL_RADIUS,2*BALL_RADIUS);
		ball.setFilled(true);
		resetBall();
	}
	
	// Resets ball to initial state after creation or death
	private void resetBall() {
		final int x = WIDTH/2-BALL_RADIUS;
		final int y = HEIGHT/2-BALL_RADIUS;
		v_x = rng.nextDouble(1.0, 3.0) * 100;
		v_y = 300;
		if (rng.nextBoolean(0.5)) {
			v_x = -v_x;
		}
		add(ball, x, y);		
	}
	
	// Creates paddle
	private void createPaddle() {
		paddle = new GRect((WIDTH-PADDLE_WIDTH)/2, PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		paddle.setColor(Color.black);
		add(paddle);		
	}
	
	// Creates all the walls
	private void createWalls() {
		for (int j = 0; j < NBRICK_ROWS; j++) {
			Color c;
			switch ((j/2)%5) {
				case 0:
					c = Color.red;
					break;
				case 1:
					c = Color.orange;
					break;
				case 2:
					c = Color.yellow;
					break;
				case 3: 
					c = Color.green;
					break;
				default:
					c = Color.cyan;
			};
			createWallLayer(j, c);
		}
	}
	
	// Creates one layer of walls
	private void createWallLayer(int layer, Color color) {
		for (int i = 0; i < NBRICKS_PER_ROW; i++) {
			
			final int x = i*(BRICK_WIDTH+BRICK_SEP);
			final int y = BRICK_Y_OFFSET+layer*(BRICK_HEIGHT+BRICK_SEP);
			
			GRect wall = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
			wall.setFilled(true);
			wall.setColor(color);
			
			add(wall);
			
		}
	}

}
