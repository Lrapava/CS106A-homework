/*
* File: Hangman.java
* ------------------
* This program will eventually play the Hangman game from
* Assignment #4.
*/

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {
	
	// random number generator
	private RandomGenerator rng;
	
	// lexicon
	private HangmanLexicon lexicon;
	
	// canvas for drawing the hangman
	private HangmanCanvas canvas;
	
	// number of guesses left
	private int guessesLeft;
	
	// the secret word
	private String secret;
	
	// parts of the word known
	private String revealed;
	
	// initialization function
	public void init() {
		rng = RandomGenerator.getInstance();
		lexicon = new HangmanLexicon("HangmanLexicon.txt");
		canvas = new HangmanCanvas();
		add(canvas);
	}
	
	// run method. ran directly after init()
	public void run() {

		println("Welcome to Hangman!");
		boolean gameRunning = true;
		
		while (gameRunning) {
			reset();
			while (guessesLeft > 0) {
				println("The word now looks like this: " + revealed);
				if (guessesLeft > 1) {
					println("You have " + guessesLeft + " guesses left.");
				} else {
					println("You have only one guess left.");
				}
				
				char guess = safeInput();
				reveal(guess);
				
				// checking if the user emerged victorious
				if (secret.equals(revealed)) {
					println("You guessed the word: " + secret);
					println("You win.");
					
					awaitEnter();
					reset();
				}
				
				add(canvas);
			}
			// checking if the user had been defeated
			println("The word was: " + secret);
			println("You lose.");
			awaitEnter();
		}
	}
	
	// pauses the program till enter is pressed and prints the relevant message
	private void awaitEnter() {
		println("");
		println("Press enter to play again");
		readLine();
	}
	
	// resets the game
	private void reset() {
		chooseWord();
		canvas.reset();
		canvas.displayWord(revealed);
	}
	
	// choosing a word from the dictionary
	private void chooseWord() {
		int rand = Math.abs(rng.nextInt()) % lexicon.getWordCount();
		secret = lexicon.getWord(rand);
		
		revealed = "";
		for (int i = 0; i < secret.length(); i++) {
			if (secret.charAt(i) != ' ') {
				revealed += "-";
			} else {
				revealed += " ";
			}
		}
		
		guessesLeft = 8;
	}
	
	// safe input method for reading user's guesses
	private char safeInput() {
	
		String guess = "";
		boolean wasInLoop = false;
		
		while (guess.length() != 1 || guess.charAt(0) > 'Z' || guess.charAt(0) < 'A') {
			if (wasInLoop) {
				println("Wrong input, try again.");
			}
			guess = readLine("Your guess: ");
			guess = guess.toUpperCase();
			wasInLoop = true;
		}
	
		return guess.charAt(0);
	
	}
	
	// evaluating user's guess
	private void reveal(char c) {
		boolean contains = false;
		for (int i = 0; i < secret.length(); i++) {
			if (secret.charAt(i) == c) {
				contains = true;
				revealed = revealed.substring(0, i) + c + revealed.substring(i+1);
			}
		}
		if (!contains) {
			println("There are no " + c + "'s in the word.");
			guessesLeft--;
			canvas.noteIncorrectGuess(c);
		} else {
			canvas.displayWord(revealed);
		}
	}

}
