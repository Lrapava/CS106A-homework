/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class NameSurfer extends Program implements NameSurferConstants {
	
	private JTextField nameField;
	private JLabel nameLabel;
	private JLabel modeLabel;
	private NameSurferGraph nsg;
	private NameSurferDataBase nsdb;
	
/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	public void init() {
		
		nsg = new NameSurferGraph();
		nameField = new JTextField(20);
		nsdb = new NameSurferDataBase("names-data.txt");
		
		modeLabel = new JLabel(nsg.getModeName());
		JLabel nameLabel = new JLabel("Name");
		JButton graphButton = new JButton("Graph");
		JButton clearButton = new JButton("Clear");
		JButton interpButton = new JButton("Change Interpolation Mode");
		
		add(nsg, CENTER);
		add(nameLabel, SOUTH);
		add(nameField, SOUTH);
		add(graphButton, SOUTH);
		add(clearButton, SOUTH);
		add(interpButton, SOUTH);
		add(modeLabel, NORTH);
		
		addActionListeners();
		
	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Graph")) {
			String key = nameField.getText().toLowerCase();
			NameSurferEntry nse = nsdb.findEntry(key);
			if (nse != null) {
				nsg.addEntry(nse);
			}
			nsg.update();
		} else if (e.getActionCommand().equals("Clear")) {
			nsg.clear();
			nsg.update();
		} else if (e.getActionCommand().equals("Change Interpolation Mode")) {
			nsg.changeInterpMode();
			nsg.update();
			modeLabel.setText(nsg.getModeName());
			add(modeLabel, NORTH);
		}
	}
}
