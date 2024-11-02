/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	
	// Once this input is accepted, the program stops accepting input
	private static final String SPECIAL = "0";
	
	public void run() {
	
		println("This program finds the largest and the smallest numbers.");
		
		boolean wasInLoop = false;
		int maxx = Integer.MIN_VALUE;
		int minx = Integer.MAX_VALUE;
		String s;
		
		while (!(s = readLine("?: ")).equals(SPECIAL)) {
			if (isNumeric(s)) {
				int x = parseInt(s);
				maxx = max(maxx, x);
				minx = min(minx, x);
				wasInLoop = true;
			} else {
				println("Wrong input.");
			}
		}
		
		if (wasInLoop) {
			println("smallest: " + minx);
			println("largest: " + maxx);
		} else {
			println("No valid input detected.");
		}
	}
	
	// Converts a String to an Integer
	private int parseInt(String s) {
		
		int k = 1;
		int absolute = 0;
		int startPoint = 0;
		int len = s.length();
		
		if (s.charAt(0) == '-') {
			k = -1;
			startPoint = 1;
		}
		
		for (int i = len-1; i >= startPoint; i--) {
			absolute = absolute * 10 + (s.charAt(i) - '0');
		}
		
		return k * absolute;
		
	}
	
	// Checks if a String is parseable to Integer
	private boolean isNumeric(String str) {
		final int len = str.length();
		boolean numeric = true;
		
		for (int i = 1; i < len && numeric; i++) {
			numeric = isDigit(str.charAt(i));
		}
		
		return len > 0 && numeric && (isDigit(str.charAt(0)) || str.charAt(0) == '-');
	}
	
	// Checks if a Character is a digit
	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}
	
	// Returns maximum between two integer values
	private int max(int a, int b) {
		if (a > b) {
			return a;
		}
		return b;
	}
	
	// Returns minumum between two integer values
	private int min(int a, int b) {
		if (a < b) {
			return a;
		}
		return b;
	}

}
