/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {

  public void run() {

    // repair all columns besides last
    while (frontIsClear()) {
      fixCollumn();
      moveToNextColumn();
    }

    // repair the last column
    fixCollumn();
  }

  // repairs the current column from the bottom up & returns to the bottom of the
  // current column
  public void fixCollumn() {

    // glide to the top & repair the damaged the column
    turnLeft();
    glideColumn();

    // move back to initial position & orientation
    turnAround();
    glideColumn();
    turnLeft();

  }

  // glides the current column in a single direction depending on KAREL's initial
  // orientation & repairs any damaged areas
  public void glideColumn() {
    if (noBeepersPresent()) putBeeper();
    while (frontIsClear()) {
      if (noBeepersPresent()) putBeeper();
      move();
    }
  }

  // moves from the bottom of the current column to the bottom of the next column
  public void moveToNextColumn() {
    for (int i = 0; i < 4; i++) {
      move();
    }
  }

}

