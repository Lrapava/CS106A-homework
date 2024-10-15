/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does nothing.
 * Your job in the assignment is to add the necessary code to
 * instruct Karel to walk to the door of its house, pick up the
 * newspaper (represented by a beeper, of course), and then return
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {
	
	public void run() {
	  followUniversalPath();
	  pickBeeper();
	  followUniversalPath();
	}
	
	// In the first step, KAREL should be displaced by vector ( 4,  1)
	// In the third step, KAREL should be displaced by vector (-4, -1)
	// Due to specific shape of KAREL's home, this symmetry can be exploited.
	// This method does either the actions required in the first or the last step,
	// depending on KAREL's orientation right before the method was called.
	private void followUniversalPath() {
	  move();
	  move();
	  turnRight();
	  move();
	  turnLeft();
	  move();
	  turnAround();
	}
}

