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
}
