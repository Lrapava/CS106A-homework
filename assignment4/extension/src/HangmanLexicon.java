/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acm.util.*;
import java.util.*;
import java.io.*;

public class HangmanLexicon {

	private ArrayList<String> dict;
	private boolean[][] contains;
	
	// constructor which takes path to the dictionary as input
	public HangmanLexicon(String path) {
	
		dict = new ArrayList<String>();
		
		try {
			FileReader file = new FileReader(path);
			BufferedReader reader = new BufferedReader(file);
			
			String word;
			while ((word = reader.readLine()) != null) {
				dict.add(word);
			}	
			
			contains = new boolean[dict.size()][26];
			
			for (int i = 0; i < dict.size(); i++) {
				for (int j = 0; j < dict.get(i).length(); j++) {
					char c = dict.get(i).charAt(j);
					if (c > 'A' && c <= 'Z') {
						contains[i][c-'A'] = true;
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}

/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return dict.size();
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		if (index >= 0 && index < dict.size()) {
			return dict.get(index);
		}
		return "ERRORVALUE";
	};
	
	// returns letter which will elliminate most words based on expected outcome
	// note: the algorithm works by calculating expected outcome for each letter,
	// than looks at how many letters are likely to get elliminated ("expected 
	// amount of information gathered"), and devides that by expected price of the
	// move (the chance that the guess will turn out wrong and the player will lose
	// a move), thereby calculating price per unit of gathered "information", and 
	// choses the cheapest option/"unit of info".
	
	// P.S. the program does not calculate true expected outcome but only an 
	// approximation as that would require additional computation.
	
	public char optimal(String revealed, String blacklist) {
	
		int width = revealed.length()+2;
		int freq[][] = new int[26][width];
		
		ArrayList<Integer> possible = new ArrayList<Integer>();
		
		for (int i = 0; i < dict.size(); i++) {
			if (matches(dict.get(i), revealed, blacklist)) {
				possible.add(i);
			}
		}
		
		System.out.println(possible.size());
		
		if (possible.size() == 1) {
			String word = dict.get(possible.get(0));
			int i = 0;
			while (revealed.charAt(i) == word.charAt(i)) {
				i++;
			}
			return word.charAt(i);
		}
		
		for (int i = 0; i < possible.size(); i++) {
			int w = possible.get(i);
			for (int j = 0; j < 26; j++) {
				if (contains[w][j] == false) {
					freq[j][width-2]++;
				}
			}
		}
		
		for (int i = 0; i < possible.size(); i++) {
			int w = possible.get(i);
			String word = dict.get(w);
			for (int j = 0; j < word.length(); j++) {
				if (word.charAt(j) != ' ') {
					freq[word.charAt(j)-'A'][j]++;
				}
			}
		}
		
		for (int i = 0; i < 26; i++) {
			for (int j = 0; j < width-1; j++) {
				freq[i][width-1] += freq[i][j]*freq[i][j];
			}
		}
		
		int minn = -1;
		for (int i = 0; i < 26; i++) {
			boolean whitelisted = true;
			
			for (int j = 0; j < blacklist.length() && whitelisted; j++) {
				whitelisted = (blacklist.charAt(j)-'A' != i);
			}
			
			if (whitelisted) {
				if (minn == -1) {
					minn = i;
					continue;
				} 
				
				float pfi = freq[i][width-1];
				float pfm = freq[minn][width-1];
				float fi = freq[i][width-2];
				float fm = freq[minn][width-2];
				float n = possible.size();
				
				// greedy optimization for cheapest information
				
				if ( (n-(pfi/n))/(fi) > (n-(pfm/n))/(fm) ) {
					minn = i;
				}
				
				// optimization for least number of total moves
				
				// if (pfi < pfm || (pfi <= pfm && fi < fm)) {
					// minn = i;
				// }
				
			}
		}
		
		return (char)('A'+minn);
	}
	
	private boolean matches(String s, String template, String blacklist) {
		boolean match = (s.length() == template.length());
		for (int i = 0; i < s.length() && match; i++) {
			match = (s.charAt(i) == template.charAt(i)) || (template.charAt(i) == '-');
			for (int j = 0; j < blacklist.length() && match; j++) {
				match = (s.charAt(i) != blacklist.charAt(j));
			}
		}
		return match;
	}
	
}
