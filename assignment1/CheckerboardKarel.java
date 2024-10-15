/*
 // * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {

	public void run() {
	
		// fill the first line
		turnLeft();
		fillLine();
		keepLine();
		
		while (frontIsClear()) {
		
			// fill even-numbered lines
			move();
			fillLine();
			invertLine();
			
			// fill odd-numbered lines
			if (frontIsClear()) {
				move();
				fillLine();
				keepLine();
			}
			
		}
		
	}
	
	// expected start state: facing north on leftmost square of a line
	// end state: facing west on the rightmost square of the inital line, 
	// beeper placed on every other square on the line startin from the 
	// initial square.
	
	private void fillLine() {
		turnRight();
		putBeeper();
		while (frontIsClear()) {
			move();
			if (frontIsClear()) {
				move();
				putBeeper();
			}
		}
		turnAround();
	}
	
	// expected start state: facing west on the rightmost square of a line 
	// end state: facing west on the leftmost square of the same line
	
	private void keepLine() {
		while (frontIsClear()) {
			move();
		}
		turnRight();
	}

	
	// expected start state: facing west on the rightmost square of a line
	// end state: facing west on the leftmost square of the same line, all
	// squares between start and end position that used to contain beepers
	// are empty, those that used to be empty contain beepers.

	private void invertLine() {
		invertSquare();
		while (frontIsClear()) {
			move();
			invertSquare();
		}
		turnRight();
	}
	
	// if a given square used to contain a beeper, it should no longer 
	// contain one, if it didn't - vice versa.
	
	private void invertSquare() {
		if (beepersPresent()) {
			pickBeeper();
		} else {
			putBeeper();
		}
	}
	
}

