import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

// this is bad..
public class State {
	
	// Mouse stuff
	public static int mouseX = Global.WIDTH/2;
	public static int mouseY = Global.HEIGHT/2;
	public static boolean mousePressed = false;

	// Easy way to quit program
	private static boolean programInUse = true;
	
	// This method is ran to let the game loop know that the user
	// wants to exit program.
	public static void quit() {
		programInUse = false;
	}
	
	// Checks if the game is supposed to be running
	public static boolean gameRunning() {
		return programInUse;
	}
	
	// The "canvas" on which everything is drawn
	private static GraphicsProgram canvas;
	
	// Instance of Chronograph class which syncs the game loop
	public static Chronograph chron;
	
	// Velocity coefficient
	public static double k_v = 1.0;
	
	// Autopilot
	public static boolean autopilot = false;
	
	// RNG
	public static RandomGenerator rgen;
	
	// Chance that a new ball will be created after destruction
	// of a wall 
	public static double karma = 0.1;

	// Screens
	public static Screen welcomeScreen;
	public static Screen gameScreen;
	public static Screen pauseScreen;
	public static Screen settingsScreen;
	public static Screen endScreen;
	public static Screen errorScreen;
	
	// Current screen tracker
	private static Screen currentScreen;
	
	// Returns active screen
	public static Screen activeScreen() {
		return currentScreen;
	}
	
	// Sets active screen
	public static void setActiveScreen(Screen s) {
		Screen prevScreen = currentScreen;
		currentScreen = s;
		s.parentScreen = prevScreen;
		currentScreen.redraw();
	}
	
	// Switch to previos screen;
	public static void goBack() {
		currentScreen = currentScreen.parentScreen;
		currentScreen.redraw();
	}
	
	// Lets the end screen know if the user has won or lost
	public static boolean victorious = true;
	
	// Bounce sound effect
	public static AudioClip bounceClip;
	
	// This method is ran before the game loop to initialize state
	public static void init(GraphicsProgram _canvas, Chronograph _chron) {
		canvas = _canvas;
		chron = _chron;
		rgen = RandomGenerator.getInstance();
		welcomeScreen 	= new WelcomeScreen	(canvas);
		settingsScreen 	= new SettingsScreen(canvas);
		gameScreen 		= new GameScreen	(canvas);
		pauseScreen 	= new PauseScreen	(canvas);
		endScreen		= new EndScreen		(canvas);
		
		bounceClip = MediaTools.loadAudioClip("./bounce.au");
		
		currentScreen = welcomeScreen;
	}
	
	// Resets game settings
	public static void reset() {
		k_v = 1;
		karma = 0.1;
		chron.setFPS(Global.DEFAULT_FPS_LOCK);
	}
	
}
