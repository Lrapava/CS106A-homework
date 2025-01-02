/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import java.io.*;
import java.util.*;

public class NameSurferDataBase implements NameSurferConstants {
	
	private HashMap <String, NameSurferEntry> map;
	
/* Constructor: NameSurferDataBase(filename) */
/**
 * Creates a new NameSurferDataBase and initializes it using the
 * data in the specified file.  The constructor throws an error
 * exception if the requested file does not exist or if an error
 * occurs as the file is being read.
 */
	public NameSurferDataBase(String filename) {
		map = new HashMap <String, NameSurferEntry>();
		
		try {
			FileReader file = new FileReader(filename);
			BufferedReader reader = new BufferedReader(file);
			
			String line;
			while ((line = reader.readLine()) != null) {
				NameSurferEntry entry = new NameSurferEntry(line);
				String key = entry.getName().toLowerCase();
				map.put(key, entry);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
/* Method: findEntry(name) */
/**
 * Returns the NameSurferEntry associated with this name, if one
 * exists.  If the name does not appear in the database, this
 * method returns null.
 */
	public NameSurferEntry findEntry(String name) {
		return map.get(name.toLowerCase());
	}
}

