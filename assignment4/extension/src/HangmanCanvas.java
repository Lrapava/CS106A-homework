/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;
import acm.util.*;
import java.awt.*;

public class HangmanCanvas extends GCanvas {

/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		
		final int TEXT_TOTAL_HEIGHT = 2*TEXT_OFFSET+FONT_SIZE_NOTE+FONT_SIZE;
		
		px = (getWidth() - BEAM_LENGTH - UPPER_ARM_LENGTH) / 2;
		py = (getHeight() - SCAFFOLD_HEIGHT - TEXT_TOTAL_HEIGHT) / 2;		
		state = 0;
		letters = "";
		
		add(new GLine(px, py, px, py+SCAFFOLD_HEIGHT));
		add(new GLine(px, py, px+BEAM_LENGTH, py));
		add(new GLine(px+BEAM_LENGTH, py, px+BEAM_LENGTH, py + ROPE_LENGTH));
		
		revealedLabel = new GLabel("");
		revealedLabel.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
		revealedLabel.setLocation(px, py + SCAFFOLD_HEIGHT + FONT_SIZE + TEXT_OFFSET);
		
		lettersLabel = new GLabel(letters);
		lettersLabel.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE_NOTE));
		lettersLabel.setLocation(px, py + SCAFFOLD_HEIGHT + TEXT_TOTAL_HEIGHT);
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		revealedLabel.setLabel(word);
		add(revealedLabel);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {
		letters += letter;
		lettersLabel.setLabel(letters);
		add(lettersLabel);
		
		int headx = px+BEAM_LENGTH-HEAD_RADIUS;
		int heady = py+ROPE_LENGTH;
		int headd = 2*HEAD_RADIUS;
		
		int torsox = headx + HEAD_RADIUS;
		int torsoy = heady + headd;
		int shouldery = torsoy + ARM_OFFSET_FROM_HEAD;
		
		int hipy = torsoy+BEAM_LENGTH;
		int footy = hipy + LEG_LENGTH;
		
		// left shoulder x
		int lsx = torsox - UPPER_ARM_LENGTH;
		
		// right shoulder x
		int rsx = torsox + UPPER_ARM_LENGTH;
		
		// left hip x
		int lhx = torsox - HIP_WIDTH;
		
		// right hip x
		int rhx = torsox + HIP_WIDTH;
		
		switch (state) {
			case 0:
				GOval head = new GOval(headd, headd);
				head.setLocation(headx, heady);
				add(head);
				break;
			case 1:
				GLine torso = new GLine(torsox, torsoy, torsox, hipy);
				add(torso);
				break;
			case 2:
				GLine lshoulder = new GLine(lsx, shouldery, torsox, shouldery);
				GLine lforearm = new GLine(lsx, shouldery, lsx, shouldery+LOWER_ARM_LENGTH);
				add(lshoulder);
				add(lforearm);
				break;
			case 3:
				GLine rshoulder = new GLine(rsx, shouldery, torsox, shouldery);
				GLine rforearm = new GLine(rsx, shouldery, rsx, shouldery+LOWER_ARM_LENGTH);
				add(rshoulder);
				add(rforearm);
				break;
			case 4:
				GLine lhip = new GLine(torsox, hipy, lhx, hipy);
				GLine lleg = new GLine(lhx, hipy, lhx, footy);
				add(lhip);
				add(lleg);
				break;
			case 5:
				GLine rhip = new GLine(torsox, hipy, rhx, hipy);
				GLine rleg = new GLine(rhx, hipy, rhx, footy);
				add(rhip);
				add(rleg);
				break;
			case 6:
				GLine lfoot = new GLine(lhx, footy, lhx-FOOT_LENGTH, footy);
				add(lfoot);
				break;
			case 7:
				GLine rfoot = new GLine(rhx, footy, rhx+FOOT_LENGTH, footy);
				add(rfoot);
				break;
			default:
		}
		
		state++;
	}
	
	// coordinates of the upper left corner of the smallest possible imaginary rectangle 
	// which completely encompasses the diagram and all labels on the canvas.
	private int px;
	private int py;
	
	// keeps track of the number of wrong guesses the user has made
	private int state;
	
	// history of mistakes the user has made
	private String letters;
	
	// label to display user's mistakes
	private GLabel lettersLabel;
	
	// labe for displaying parts of the word correctly guessed & dashes
	private GLabel revealedLabel;

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	
	// text offset and font sizes for the labels
	private static final int TEXT_OFFSET = 20;
	private static final int FONT_SIZE = 20;
	private static final int FONT_SIZE_NOTE = 15;

}
