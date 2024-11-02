/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	
	public void run() {
		
		int n = readInt("Enter number: ");
		int i = 0;
		
		while (n != 1) {
			n = successor(n);
			i++;
		}
		
		println("The process took " + i + " to reach 1");
		
	}
	
	// Returns successor to n and logs actions
	private int successor(int n) {
		int res;
		if (n % 2 == 0) {
			res = n / 2;
			println(n + " is even, so I take half: " + res);
		} else {
			res = 3 * n + 1;
			println(n + " is odd, so I make 3n + 1: " + res);
		}
		return res;
	}
}

