import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class EndScreen extends Screen {

	// Parameters for button dimensions & spacing
	private static final int BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 30;
	private static final int BUTTON_SEPARATION = 10;
	private static final int NBUTTONS = 4;

	// Buttons displayed on the screen
	private Button statusLabel;
	private Button playAgainButton;
	private Button welcomeScreenButton;
	private Button quitButton;

	// Default constructor
	public EndScreen(GraphicsProgram canvas) {
		super(canvas);

		final int initialX = (Global.WIDTH - BUTTON_WIDTH)/2;
		final int initialY = (Global.HEIGHT - (NBUTTONS*BUTTON_HEIGHT + (NBUTTONS-1)*BUTTON_SEPARATION))/2;
		final int Y_STEP = BUTTON_HEIGHT + BUTTON_SEPARATION;
		
		statusLabel = new Label("", initialX, initialY + 0*Y_STEP, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);
		playAgainButton = new Button("PLAY AGAIN", initialX, initialY + 1*Y_STEP, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);
		welcomeScreenButton = new Button("MAIN SCREEN", initialX, initialY + 2*Y_STEP, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);
		quitButton = new Button("QUIT GAME", initialX, initialY + 3*Y_STEP, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);
		
		objects.insert(statusLabel);
		objects.insert(playAgainButton);
		objects.insert(welcomeScreenButton);
		objects.insert(quitButton);
	}
	
	// This method is meant to be ran right after switching 
	// to given subclass of Screen
	@Override
	public void redraw() {
		canvas.removeAll();
		
		String msg = "YOU HAVE LOST.";
		if (State.victorious) {
			msg = "YOU EMERGE VICTORIOUS!";
		}
		statusLabel.setLabel(msg);
		
		objects.draw();
	}
	
	// Mouse click event listener
	@Override
	public void mouseClicked(MouseEvent e) {
		
		GObject obj = canvas.getElementAt(e.getX(), e.getY());

		if (obj != null) {
			// unable to use switch because expressions are not constant
			if (obj == playAgainButton.object) {
				State.gameScreen = new GameScreen(canvas);
				State.setActiveScreen(State.gameScreen);
			} else if (obj == welcomeScreenButton.object) {
				State.welcomeScreen = new WelcomeScreen(canvas);
				State.setActiveScreen(State.welcomeScreen);
			} else if (obj == quitButton.object) {
				State.quit();
			}
		}
	}
	
}
