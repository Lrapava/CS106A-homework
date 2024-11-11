import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class PauseScreen extends Screen {
	
	// Parameters for button dimensions & spacing
	private static final int BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 30;
	private static final int BUTTON_SEPARATION = 10;
	private static final int NBUTTONS = 4;
	
	// Buttons displayed on the screen
	private Button playButton;
	private Button settingsButton;
	private Button welcomeScreenButton;
	private Button quitButton;
	
	// Default constructor
	public PauseScreen(GraphicsProgram canvas) {
		super(canvas);
		final int initialX = (Global.WIDTH - BUTTON_WIDTH)/2;
		final int initialY = (Global.HEIGHT - (NBUTTONS*BUTTON_HEIGHT + (NBUTTONS-1)*BUTTON_SEPARATION))/2;
		final int Y_STEP = BUTTON_HEIGHT + BUTTON_SEPARATION;
		
		playButton = new Button("CONTINUE", initialX, initialY, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);
		settingsButton = new Button("SETTINGS", initialX, initialY + 1*Y_STEP, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);
		welcomeScreenButton = new Button("MAIN MENU", initialX, initialY + 2*Y_STEP, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);
		quitButton = new Button("QUIT GAME", initialX, initialY + 3*Y_STEP, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);

		objects.insert(playButton);
		objects.insert(settingsButton);
		objects.insert(welcomeScreenButton);
		objects.insert(quitButton);
		
	}
	
	// Mouse click event listener
	@Override
	public void mouseClicked(MouseEvent e) {
		
		GObject obj = canvas.getElementAt(e.getX(), e.getY());
		
		if (obj != null) {
			// unable to use switch because expressions are not constant
			if (obj == playButton.object) {
				State.setActiveScreen(State.gameScreen);
			} else if (obj == settingsButton.object) {
				State.setActiveScreen(State.settingsScreen);
			} else if (obj == welcomeScreenButton.object) {
				State.setActiveScreen(State.welcomeScreen);
			} else if (obj == quitButton.object) {
				State.quit();
			}
		}
		
		
	}

}
