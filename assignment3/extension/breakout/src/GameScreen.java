import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class GameScreen extends Screen {

	// Paddle class to manage paddle
	private class Paddle extends GameObject {

		public Paddle(GraphicsProgram canvas) {
			super(canvas);
			long w = Global.PADDLE_WIDTH;
			long h = Global.PADDLE_HEIGHT;
			long x = (Global.WIDTH - w)/2; 
			long y = Global.HEIGHT-Global.PADDLE_Y_OFFSET;
			GRect object = new GRect(x, y, w, h);
			object.setFilled(true);
			object.setColor(Color.black);
			this.object = object;
		}
		
		// This method is called every frame to recalculate postion and 
		// other properties of a given sublass of GameObject before drawing
		@Override
		public void update() {
			final int mouseX = State.mouseX;
			
			int paddleX = mouseX - Global.PADDLE_WIDTH/2; 
			
			if (State.autopilot) {
				GameObject currentBall = objects.getHead().next;
				GameObject closestBall = currentBall;
				while (currentBall != null) {
					double y = currentBall.object.getY();
					double beyondSalvation = Global.HEIGHT-Global.PADDLE_Y_OFFSET-2*Global.BALL_RADIUS;
					if (y > closestBall.object.getY() && y < beyondSalvation) {
						closestBall = currentBall;
					}
					currentBall = currentBall.next;
				}
				if (closestBall != null) {
					paddleX = (int)(closestBall.object.getX() + Global.BALL_RADIUS - Global.PADDLE_WIDTH/2);
				}
			}
			
			paddleX = Integer.max(0, paddleX);
			paddleX = Integer.min(paddleX, Global.WIDTH-Global.PADDLE_WIDTH);
			final int paddleY = Global.HEIGHT - Global.PADDLE_Y_OFFSET-Global.PADDLE_WIDTH;
			object.setLocation(paddleX, paddleY);

		}		
	}
	
	// Ball class to manage balls
	private class Ball extends GameObject {
		
		// radius
		int r = Global.BALL_RADIUS;		
		
		// velocity
		private double v_x;
		private double v_y;
		
		// This constructor creates a ball in the middle of the screen
		public Ball(GraphicsProgram canvas) {
			super(canvas);
			
			double rx = State.rgen.nextDouble(-1,1);
			double ry = State.rgen.nextDouble(-1,1);
			
			v_x = rx * 300 + 100*rx/Math.abs(rx);
			v_y = ry * 300 + 100*ry/Math.abs(ry);
			
			long x = (Global.WIDTH -2*r)/2; 
			long y = (Global.HEIGHT-2*r)/2;
			
			GOval object = new GOval(x, y, 2*r, 2*r);
			object.setFilled(true);
			object.setColor(Color.black);
			this.object = object;
		}
		
		// This constructor creates a ball at (x, y)
		public Ball(double x, double y, GraphicsProgram canvas) {
			super(canvas);
			
			double rx = State.rgen.nextDouble(-1,1);
			double ry = State.rgen.nextDouble(-1,1);
			
			v_x = rx * 300 + 100*rx/Math.abs(rx);
			v_y = ry * 300 + 100*ry/Math.abs(ry);
						
			GOval object = new GOval(x, y, 2*r, 2*r);
			object.setFilled(true);
			object.setColor(Color.black);
			this.object = object;
		}
		
		// This method is called every frame to recalculate postion and 
		// other properties of a given sublass of GameObject before drawing
		@Override
		public void	update() {
			if (!awaiting) {
				double x = object.getX();
				double y = object.getY();
				double dx = v_x * State.chron.dt() * State.k_v;
				double dy = v_y * State.chron.dt() * State.k_v;
				
				collisionChecks();
				x += dx;
				y += dy;
				object.setLocation(x, y);
			}
		}
		
		// Checks for collisions, changes velocity accordingly, and
		// deltes walls
		private void collisionChecks() {

			final double x = object.getX();
			final double y = object.getY();
			final double dx = v_x * State.chron.dt() * State.k_v;
			final double dy = v_y * State.chron.dt() * State.k_v;
			
			// Collision checks
			boolean collided = false;
			boolean wallBroken = false;
			
			GObject obju = canvas.getElementAt(x+dx+r, y+dy);
			GObject objd = canvas.getElementAt(x+dx+r, y+dy+2*r);
			GObject objl = canvas.getElementAt(x+dx, y+dy+r);
			GObject objr = canvas.getElementAt(x+dx+2*r, y+dy+r);
			
			double random = State.rgen.nextDouble(0, 1);
			
			if (obju != null && (obju == paddle.object || walls.containsGObject(obju))) {
				if (obju != paddle.object) {
					canvas.remove(obju);
					walls.delete(walls.findGObject(obju));
					wallBroken = true;
					nwalls--;
				}
				collided = true;
				v_y *= -1;
			}
			
			if (objd != null && (objd == paddle.object || walls.containsGObject(objd))) {
				if (objd != paddle.object) {
					canvas.remove(objd);
					walls.delete(walls.findGObject(objd));
					wallBroken = true;
					nwalls--;
				}
				collided = true;
				v_y *= -1;
			}
			
			if (objl != null && (objl == paddle.object || walls.containsGObject(objl))) {
				if (objl != paddle.object) {
					canvas.remove(objl);
					walls.delete(walls.findGObject(objl));
					wallBroken = true;
					nwalls--;
				}
				collided = true;
				v_x *= -1;
			}
			
			if (objr != null && (objr == paddle.object || walls.containsGObject(objr))) {
				if (objr != paddle.object) {
					canvas.remove(objr);	
					walls.delete(walls.findGObject(objr));
					wallBroken = true;
					nwalls--;
				}
				collided = true;
				v_x *= -1;
			}
			
			// Making sure the sign is right after collision with borders.
			
			if (x + 2*r + dx > Global.WIDTH || x < 0) {
				v_x = -1*x/Math.abs(x)*Math.abs(v_x);
				collided = true;
			}
			
			if (y < 0) {
				v_y = -1*y/Math.abs(y)*Math.abs(v_y);
				collided = true;
			}
			
			if (y + 2*r + dy > Global.HEIGHT) {
				nballs--;
				objects.delete(this);
				canvas.remove(object);
			}
			
			if (wallBroken) {
				if (random <= State.karma) {
					nballs++;
					objects.insert(new Ball(x, y, canvas));
				}
			}
			
			if (collided) {
				State.bounceClip.play();
			}
			
		}		
	}
	
	// The paddle
	private Paddle paddle;
	
	// Linked list of all walls on the screen
	private ObjectList walls;
	
	// Number of active balls on the screen
	private int nballs = 1;
	
	// Number of active walls on the screen
	private int nwalls = Global.NBRICK_ROWS * Global.NBRICKS_PER_ROW;
	
	// Number of times the player has lost all balls
	private int ndeaths = 0;
	
	// If awaiting is true, the balls are frozen, but the pause screen
	// is not displayed. Once the user clicks the screen, the awaiting
	// mode is disabled.
	private boolean awaiting = true;
	
	// deafualt constructor
	public GameScreen(GraphicsProgram canvas) {
		super(canvas);
		createWalls();
		paddle = new Paddle(canvas);
		objects.insert(paddle);
		objects.insert(new Ball(canvas));
		
	}
	
	// This method is called every time a new frame is about to be
	// drawn to recalculate position and other properties of all 
	// GameObjects.
	@Override 
	public void update() {
		objects.update();		
		if (nballs == 0) {
			ndeaths++;
			nballs++;
			awaiting = true;
			objects.insert(new Ball(canvas));
		}
		paddle.update();
		if (ndeaths >= Global.NTURNS || nwalls == 0) {
			State.victorious = (nwalls == 0);
			State.setActiveScreen(State.endScreen);
		}
	}

	// Mouse click even listener
	@Override 
	public void mouseClicked(MouseEvent e) {
		if (awaiting) {
			awaiting = false;
		} else {
			State.setActiveScreen(State.pauseScreen);
		}
	}
	
	// This method is meant to be ran right after switching 
	// to given subclass of Screen
	@Override
	public void redraw() {
		canvas.removeAll();
		walls.draw();
		objects.draw();
	}
	
	// Creates all the walls
	private void createWalls() {
		
		walls = new ObjectList();
		
		for (int j = 0; j < Global.NBRICK_ROWS; j++) {
			final Color c = switch (j/2) {
				case 0 -> Color.red;
				case 1 -> Color.orange;
				case 2 -> Color.yellow;
				case 3 -> Color.green;
				default -> Color.cyan;
			};
			createWallLayer(j, c);
		}
	}
	
	// Creates one layer of walls
	private void createWallLayer(int layer, Color color) {
		for (int i = 0; i < Global.NBRICKS_PER_ROW; i++) {
			
			final int x = i*(Global.BRICK_WIDTH+Global.BRICK_SEP);
			final int y = Global.BRICK_Y_OFFSET+layer*(Global.BRICK_HEIGHT+Global.BRICK_SEP);
			
			GRect wall = new GRect(x, y, Global.BRICK_WIDTH, Global.BRICK_HEIGHT);
			wall.setFilled(true);
			wall.setColor(color);
			
			// walls do not have to be redrawn
			GameObject WallObject = new GameObject(wall, canvas);
			walls.insert(WallObject);
			
		}
	}

}
