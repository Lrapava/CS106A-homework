import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsScreen extends Screen {

	// Parameters for button dimensions & spacing	
	private static final int BUTTON_WIDTH = 200;
	private static final int BUTTON_HEIGHT = 30;
	private static final int BUTTON_SEPARATION = 10;
	private static final int NBUTTONS = 13;
	
	// Labels displayed on the screen
	private Label FPSLabel;
	private Label KVLabel;
	private Label karmaLabel;
	private Label autopilotLabel;

	// Sliders displayed on the screen
	private Slider FPSSlider;	
	private Slider KVSlider;
	private Slider karmaSlider;
	
	// Buttons displayed on the screen
	private Button autopilotButton;
	private Button resetWorldButton;
	private Button resetSettingsButton;
	private Button backButton;
	
	// Default constructor
	public SettingsScreen(GraphicsProgram canvas) {
		super(canvas);
		final int initialX = (Global.WIDTH - BUTTON_WIDTH)/2;
		final int initialY = (Global.HEIGHT - (NBUTTONS*BUTTON_HEIGHT + (NBUTTONS-1)*BUTTON_SEPARATION))/2;
		final int Y_STEP = BUTTON_HEIGHT + BUTTON_SEPARATION;
		
		FPSLabel = new Label("FPS limit: " + State.chron.FPSLimit(), initialX, initialY+0*Y_STEP, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);
		FPSSlider = new Slider(Math.log(State.chron.FPS())/Math.log(1000), initialX, initialY+1*Y_STEP, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);
		
		KVLabel = new Label("Velocity coefficient: " + State.k_v, initialX, initialY+2*Y_STEP, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);
		KVSlider = new Slider(0.5, initialX, initialY+3*Y_STEP, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);

		karmaLabel = new Label("Karma: " + State.karma, initialX, initialY+4*Y_STEP, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);
		karmaSlider = new Slider(State.karma, initialX, initialY+5*Y_STEP, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);
		
		autopilotLabel = new Label("Autopilot", initialX, initialY+6*Y_STEP, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);
		autopilotButton = new Button(isActivated(State.autopilot), initialX, initialY+7*Y_STEP, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);
		
		resetWorldButton = new Button("RESET GAME WORLD", initialX, initialY+9*Y_STEP, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);
		resetSettingsButton = new Button("RESET GAME SETTINGS", initialX, initialY+10*Y_STEP, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);
		backButton = new Button("BACK", initialX, initialY+11*Y_STEP, BUTTON_WIDTH, BUTTON_HEIGHT, canvas);
		
		// Inserting all the gui elemenents to objects ObjectList  
		// so they are drawn & updated regularly.
		objects.insert(FPSLabel);
		objects.insert(FPSSlider);
		
		objects.insert(KVLabel);
		objects.insert(KVSlider);
		
		objects.insert(karmaLabel);
		objects.insert(karmaSlider);
		
		objects.insert(autopilotLabel);
		objects.insert(autopilotButton);
		
		objects.insert(resetWorldButton);
		objects.insert(resetSettingsButton);
		objects.insert(backButton);

	}

	// This method is meant to be ran right after switching 
	// to given subclass of Screen
	@Override
	public void redraw() {
		canvas.removeAll();
		FPSLabel.setLabel("FPS Limit: " + State.chron.FPSLimit());
		FPSSlider.setValue(Math.log(State.chron.FPSLimit())/Math.log(1000));
		KVLabel.setLabel("Velocity coefficient: " + State.k_v);
		KVSlider.setValue((Math.log(State.k_v)/Math.log(4)+2)/4);
		karmaLabel.setLabel("Karma: " + State.karma);
		karmaSlider.setValue(State.karma);
	}
	
	// Mouse click event listener
	@Override
	public void mouseClicked(MouseEvent e) {
		
		GObject obj = canvas.getElementAt(e.getX(), e.getY());
		
		if (obj != null) {
			// unable to use switch because expressions are not constant
			if (obj == FPSSlider.object) {
				State.chron.setFPS((int)Math.pow(1000, FPSSlider.getValue()));
				FPSLabel.setLabel("FPS limit: " + State.chron.FPSLimit());
			} else if (obj == KVSlider.object) {
				State.k_v = Math.round(Math.pow(4, 4*KVSlider.getValue()-2)*100.0) / 100.0;
				KVLabel.setLabel("Velocity coefficient: " + State.k_v);
			} else if (obj == karmaSlider.object) {
				State.karma = karmaSlider.getValue();
				karmaLabel.setLabel("Karma: " + State.karma);				
			} else if (obj == autopilotButton.object) {
				State.autopilot = !State.autopilot;
				autopilotButton.setLabel(isActivated(State.autopilot));
				if (State.autopilot) {
					autopilotButton.background.setColor(Color.green);
				} else {
					autopilotButton.background.setColor(Color.gray);
				}
			} else if (obj == resetWorldButton.object) {
				State.gameScreen = new GameScreen(canvas);
			} else if (obj == resetSettingsButton.object) {
				State.reset();
				redraw();
			} else if (obj == backButton.object) {
				State.goBack();
			}
		}
	}
	
	// Returns text to be displayed in the toggle button.
	private String isActivated(boolean x) {
		if (x) return "ACTIVATED";
		return "OFFLINE";
	}

}
