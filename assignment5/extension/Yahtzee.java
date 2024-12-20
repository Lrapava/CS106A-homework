/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	// boilerplate code
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	// entry point
	public void run() {
		IODialog dialog = getDialog();
		
		// making sure nPlayers is within bounds 
		boolean wasInLoop = false;
		while (nPlayers < 1 || nPlayers > MAX_PLAYERS) {
			String message = "Enter number of players";
			if (wasInLoop) {
				if (nPlayers < 1) {
					message = "Number of players too small";
				} else if (nPlayers > MAX_PLAYERS) {
					message = "Limit of players is " + MAX_PLAYERS;
				}
			}
			nPlayers = dialog.readInt(message);
			wasInLoop = true;
		}
		
		scoreTable = new int[nPlayers][18];
		wasSelected = new boolean[nPlayers][18];
		playerNames = new String[nPlayers];
		
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}
	
	// game logic
	private void playGame() {
	
		int[] dice = new int[N_DICE];
		
		for (int i = 0; i < 13*nPlayers; i++) {
			
			int player = i % nPlayers + 1;
			display.printMessage(playerNames[player - 1] + "'s turn. ");
			display.waitForPlayerToClickRoll(player);
			
			int[] fixMask = new int[N_DICE];
			dice = roll(N_DICE, new int[N_DICE], fixMask);
			display.displayDice(dice);
			
			for (int j = 0; j < 2; j++) {
				display.waitForPlayerToSelectDice();
				fixMask = getFixMask(N_DICE, display);
				dice = roll(N_DICE, dice, fixMask);
				display.displayDice(dice);
			}
			
			int category = getNewCategory(player, display);
			evaluate(category, player, dice);
			display.updateScorecard(category, player, scoreTable[player-1][category]);
		}
		
		endScreen();
		
	}
	
	// rolls dices based on fixMask
	private int[] roll(int nDice, int[] oldDice, int[] fixed) {
		int[] dice = oldDice;
		for (int i = 0; i < nDice; i++) {
			if (fixed[i] == 0) {
				dice[i] = rgen.nextInt(6) + 1;
			}
		}
		return dice;
	}
	
	// returns array (fixMask) which lets the roll method know what dices not to change 
	private int[] getFixMask(int nDice, YahtzeeDisplay display) {
		int[] fixMask = new int[nDice];
		for (int i = 0; i < nDice; i++) {
			if (!display.isDieSelected(i)) {
				fixMask[i] = 1;
			}
		}
		return fixMask;
	}
	
	// returns category id, makes sure players don't select any category twice
	private int getNewCategory(int player, YahtzeeDisplay display) {
		int category = display.waitForPlayerToSelectCategory();
		while (wasSelected[player-1][category]) {
			display.printMessage("Same category cannot be selected twice.");
			category = display.waitForPlayerToSelectCategory();
		}
		wasSelected[player-1][category] = true;
		return category;
	}
	
	// evaluates player's score after choosing a category
	private void evaluate(int category, int player, int[] dice) {
		
		int score = 0;
		int sum = 0;
		
		// calculating frequency array & sum of all dice
		int f[] = new int[7];
		for (int i = 0; i < N_DICE; i++) {
			f[dice[i]]++;
			sum += dice[i];
		}
		
		// calculating larger consecutive sequence & 2 most common elements
		int max_cons = 1;
		int cur_cons = 0;
		int m1 = 0;
		int m2 = 0;
		for (int i = 1; i <= 6; i++) {
			if (f[i] == 0) {
				max_cons = Math.max(max_cons, cur_cons);
				cur_cons = 0;
			} else {
				cur_cons++;
			}
			
			if (f[i] > f[m1]) {
				m1 = i;
			} else if (f[i] > f[m2]) {
				m2 = i;
			}
		}
		max_cons = Math.max(max_cons, cur_cons);
		
		if ((category == THREE_OF_A_KIND || category == FOUR_OF_A_KIND) && f[m1] > category - 7) {
			score = sum;
		} else if ((category == SMALL_STRAIGHT || category == LARGE_STRAIGHT) && max_cons > category - 9) {
			score = (category - 9) * 10;
		} else if (category == FULL_HOUSE && f[m1]+f[m2] == 5 && f[m1] != 4) {
			score = 25;
		} else if (category == YAHTZEE && f[m1] == 5) {
			score = 50;
		} else if (category == CHANCE) {
			score = sum;
		} else if (category <= SIXES) {
			score = f[category]*category;
			scoreTable[player-1][UPPER_SCORE] += score;
			scoreTable[player-1][LOWER_SCORE] -= score;
			if (scoreTable[player-1][UPPER_SCORE] >= 63) {
				scoreTable[player-1][UPPER_BONUS] = 35;
			}
		}
		
		// 100 point yahtzee bonus
		if (scoreTable[player-1][YAHTZEE] != 0 && f[m1] == 5) {
			scoreTable[player-1][YAHTZEE] += 100;
			scoreTable[player-1][LOWER_SCORE] += 100;
			scoreTable[player-1][TOTAL] += 100;
		}
		
		scoreTable[player-1][LOWER_SCORE] += score;
		scoreTable[player-1][TOTAL] += score;
		scoreTable[player-1][category] = score;	
	}
	
	// prints final scores and outputs name of the player with highest score
	private void endScreen() {
		int mt = 0;
		for (int i = 0; i < nPlayers; i++) {
			display.updateScorecard(UPPER_SCORE, i+1, scoreTable[i][UPPER_SCORE]);
			display.updateScorecard(UPPER_BONUS, i+1, scoreTable[i][UPPER_BONUS]);
			display.updateScorecard(LOWER_SCORE, i+1, scoreTable[i][LOWER_SCORE]);
			display.updateScorecard(TOTAL, i+1, scoreTable[i][TOTAL]);
			if (scoreTable[i][TOTAL] > scoreTable[mt][TOTAL]) {
				mt = i;
			}
		}
		display.printMessage(playerNames[mt] + "'s the winner. ");
	}
	
/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	
	// this matrix stores what categories each player has selected during the game
	private boolean[][] wasSelected;
	// score for each player & category
	private int[][] scoreTable;

}
