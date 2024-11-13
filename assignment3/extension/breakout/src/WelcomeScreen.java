import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomeScreen extends GameScreen {

	// Parameters for button dimensions & spacing	
	private static final int BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 75;
	private static final int BUTTON_SEPARATION = 0;
	private static final int NBUTTONS = 2;
	
	private Button playButton;
	private Button settingsButton;
	private Label BGLabel;
	
	// Default constructor
	public WelcomeScreen(GraphicsProgram canvas) {
		super(canvas);
		awaiting = false;
		final int initialX = (Global.WIDTH - BUTTON_WIDTH)/2;
		final int initialY = (Global.HEIGHT - (NBUTTONS*BUTTON_HEIGHT + (NBUTTONS-1)*BUTTON_SEPARATION))/2;
		final int Y_STEP = BUTTON_HEIGHT + BUTTON_SEPARATION;
		
		BGLabel = new Label("", initialX, initialY, BUTTON_WIDTH, 2*BUTTON_WIDTH+Y_STEP, canvas);
		// objects.insert(BGLabel);
		playButton = new Button("PLAY", initialX, initialY, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);
		objects.insert(playButton);
		settingsButton = new Button("SETTINGS", initialX, initialY + Y_STEP, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);
		objects.insert(settingsButton);
		
	}
		
	// Mouse click event listener
	@Override
	public void mouseClicked(MouseEvent e) {
		
		GObject obj = canvas.getElementAt(e.getX(), e.getY());
		
		if (obj != null) {
			// unable to use switch because expressions are not constant
			if (obj == playButton.object) {
				State.gameScreen = new GameScreen(canvas);
				State.setActiveScreen(State.gameScreen);
			} else if (obj == settingsButton.object) {
				State.setActiveScreen(State.settingsScreen);
			}
		}
	}

}
