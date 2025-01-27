/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {

	// WEST UI ELEMENTS
	private JTextField statusField;
	private JButton statusButton;
	
	private JTextField pictureField;
	private JButton pictureButton;

	private JTextField friendField;
	private JButton friendButton;
	
	// NORTH UI ELEMENTS
	private JLabel nameLabel;
	private JTextField searchField;
	private JButton addButton;
	private JButton deleteButton;
	private JButton lookupButton;
	
	private FacePamphletDatabase db;
	private FacePamphletProfile activeProfile;
	
	// main part
	private FacePamphletCanvas pamphletCanvas;
	
	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		
		db = new FacePamphletDatabase();
		
		// WEST UI ELEMENTS
		statusField		= new JTextField(TEXT_FIELD_SIZE);
		statusButton	= new JButton("Change Status");
		pictureField	= new JTextField(TEXT_FIELD_SIZE);
		pictureButton	= new JButton("Change Picture");
		friendField		= new JTextField(TEXT_FIELD_SIZE);
		friendButton	= new JButton("Add Friend");
		
		JLabel[] separators = new JLabel[2];
		separators[0] = new JLabel(EMPTY_LABEL_TEXT);
		separators[1] = new JLabel(EMPTY_LABEL_TEXT);
		
		add(statusField, WEST);
		add(statusButton, WEST);
		add(separators[0], WEST);
		
		add(pictureField, WEST);
		add(pictureButton, WEST);
		add(separators[1], WEST);
		
		add(friendField, WEST);
		add(friendButton, WEST);
		
		// NORTH UI ELEMENTS
		nameLabel = new JLabel("Name");
		searchField = new JTextField(TEXT_FIELD_SIZE);
		addButton = new JButton("Add");
		deleteButton = new JButton("Delete");
		lookupButton = new JButton("Lookup");
		
		pamphletCanvas = new FacePamphletCanvas();
		
		add(nameLabel, NORTH);
		add(searchField, NORTH);
		add(addButton, NORTH);
		add(deleteButton, NORTH);
		add(lookupButton, NORTH);
		add(pamphletCanvas, CENTER);

		addActionListeners();
		
    }
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
				
		String statusText = statusField.getText();
		String pictureText = pictureField.getText();
		String friendText = friendField.getText();
		String searchText = searchField.getText();
		
		Object source = e.getSource();
		
		if (source == statusField || source == statusButton) {
			if (activeProfile != null) {
				activeProfile.setStatus(statusText);
				pamphletCanvas.showMessage("Status updated to " + statusText);
			} else {
				pamphletCanvas.showMessage("Please select a profile to update status");
			}
		} else if (source == pictureField || source == pictureButton) {
			if (activeProfile != null) {
				try {
					GImage img = new GImage("images/"+pictureText);
					activeProfile.setImage(img);	
					pamphletCanvas.showMessage("Picture updated");
				} catch (ErrorException ex) {
					pamphletCanvas.showMessage("Unable to open image");
				}
			} else {
				pamphletCanvas.showMessage("Please select a profile to update picture");
			}
		} else if (source == friendField || source == friendButton) {
				pamphletCanvas.showMessage("Please select a profile to update picture");
			if (activeProfile != null) {
				FacePamphletProfile newFriend = db.getProfile(friendText);
				if (newFriend != null) {
					if (newFriend == activeProfile) {
						pamphletCanvas.showMessage("Can't added yourself as a friend");						
					} else if (activeProfile.addFriend(friendText)) {
						newFriend.addFriend(activeProfile.getName());
						pamphletCanvas.showMessage(friendText + " added as a friend");
					} else {
						pamphletCanvas.showMessage(newFriend.getName() + " is already your friend");
					}
				} else {
					pamphletCanvas.showMessage("User " + friendText + " not found");
				}
			} else {
				pamphletCanvas.showMessage("Please select a profile to add friend");
			}
		}
		
		if (searchText != "") {
			if (source == addButton) {
				if (db.getProfile(searchText) == null) {				
					FacePamphletProfile newUser = new FacePamphletProfile(searchText);
					db.addProfile(newUser);
					pamphletCanvas.showMessage("New profile created");
				} else {
					pamphletCanvas.showMessage("Profile already exists");
				}
				activeProfile = db.getProfile(searchText);
			} else if (source == deleteButton) {
				FacePamphletProfile obsoleteProfile = db.getProfile(searchText);
				if (activeProfile == obsoleteProfile) {
					activeProfile = null;
				}
				db.deleteProfile(searchText);
				pamphletCanvas.showMessage("Profile of " + searchText + " deleted");
			} else if (source == lookupButton) {
				activeProfile = db.getProfile(searchText);
				if (activeProfile != null) {
					pamphletCanvas.showMessage("Displaying " + searchText);
				} else {
					pamphletCanvas.showMessage("Profile of " + searchText + " does not exist");
				}
			}
			pamphletCanvas.displayProfile(activeProfile);
		}		
	}
}
