/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	
	public void run() {
		println("c = " + hypLength(readDouble("a: "), readDouble("b: ")));		
	}
	
	// Calculate hypotenuse length given 2 other sides 
	private double hypLength(double a, double b) {
		return Math.sqrt(a*a+b*b);
	}
}
