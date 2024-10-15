/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {

	public void run() {
		
		// preparation
		fillLineBesidesEnds();
		
		// locate center & move there
		while (beepersPresent()) {
			pickBeeperInTheEnd();
		}
		
		turnAround();
		move();
		
		// fix orientation & put a beeper
		if (facingWest()) {
			turnAround();
		}
		
		putBeeper();
	
	}

	// expected start state: KAREL is locate on the leftmost corner
	// of the first line facing east.
	// end state: there's a beeper on every square of the first line
	// besides rightmost & leftmost. KAREL is located on the leftmost
	// square containing a beeper.
	
	private void fillLineBesidesEnds() {
		while (frontIsClear()) {
			move();
			putBeeper();
		}
		pickBeeper();
		turnAround();
		move();
	}
	
	// expected start state: KAREL is located on one of the ends of
	// the line of consecutive beepers, facing the other end.
	// end state: KAREL removes a beeper from the opposite end of the
	// line and moves to the furthest square from the initial position
	// containing a beeper, facing the opposite of initial direction.

	private void pickBeeperInTheEnd() {
		while (beepersPresent()) {
			move();
		}
		turnAround();
		move();
		pickBeeper();
		move();
	}
	
}

