/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	// label that displays the messages after button clicks
	GLabel messageLabel;
	
	// label that displays profile name
	GLabel nameLabel;
	
	// label that displays status
	GLabel statusLabel;
	
	// label that says "friends"
	GLabel friendsLabel;
	
	// list of friend name labels
	ArrayList <GLabel> friendNameLabelList;
	
	// stuff for profile image placeholder
	GRect imageRect;
	GLabel noImageLabel;
	
	// profile image
	GImage profileImage;
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		messageLabel = new GLabel(EMPTY_LABEL_TEXT);
		messageLabel.setFont(MESSAGE_FONT);

		nameLabel = new GLabel(EMPTY_LABEL_TEXT);
		nameLabel.setFont(PROFILE_NAME_FONT);
		nameLabel.setColor(Color.BLUE);
		double xName = LEFT_MARGIN;
		double yName = TOP_MARGIN + nameLabel.getHeight();
		nameLabel.setLocation(xName, yName);
		
		imageRect = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
		double xImage = xName;
		double yImage = yName + IMAGE_MARGIN;
		imageRect.setLocation(xImage, yImage);
		imageRect.setFilled(true);
		imageRect.setFillColor(Color.WHITE);

		noImageLabel = new GLabel("No Image");
		noImageLabel.setFont(PROFILE_IMAGE_FONT);
		double xNoImage = xImage + (IMAGE_WIDTH - noImageLabel.getWidth())/2;
		double yNoImage = yImage + IMAGE_HEIGHT/2;
		noImageLabel.setLocation(xNoImage, yNoImage);

		statusLabel = new GLabel(EMPTY_LABEL_TEXT);
		statusLabel.setFont(PROFILE_STATUS_FONT);
		double xStatus = xImage;
		double yStatus = yImage + IMAGE_HEIGHT + STATUS_MARGIN + statusLabel.getHeight();
		statusLabel.setLocation(xStatus, yStatus);

		friendsLabel = new GLabel("Friends");
		friendsLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		
		friendNameLabelList = new ArrayList<GLabel>();
	
	}
	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		messageLabel.setLabel(msg);
		Point cPos = getLocationOnScreen();
		double xPosition = (APPLICATION_WIDTH-cPos.x-messageLabel.getWidth())/2;
		double yPosition = APPLICATION_HEIGHT-cPos.y-BOTTOM_MESSAGE_MARGIN;	
		messageLabel.setLocation(xPosition, yPosition);
		add(messageLabel, xPosition, yPosition);
	}
		
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		if (profile == null) {
			removeAll();
			add(messageLabel, messageLabel.getX(), messageLabel.getY());
		} else {
			displayName(profile.getName());
			displayImage(profile.getImage());
			displayStatus(profile.getName(), profile.getStatus());
			displayFriends(profile.getFriends());
		}
	}
	
	/**
	* Displays name label if it's supposed to be displayed
	*/
	private void displayName(String name) {
		if (name != null) {
			nameLabel.setLabel(name);
			add(nameLabel);
		}
	}
	
	/**
	* Displays iamge if it's supposed to be displayed, else displays 
	* a placeholder
	*/	
	private void displayImage(GImage img) {
		if (img == null) {
			add(imageRect);
			add(noImageLabel);
		} else {
			img.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			img.setLocation(imageRect.getX(), imageRect.getY());
			add(img);
		}
	}
	
	/**
	* Displays status label if it's supposed to be displayed,
	* if status not set yet displays relevant message
	*/
	private void displayStatus(String name, String status) {
		if (status != null && name != null) {
			statusLabel.setLabel(name + " is " + status);
			add(statusLabel);
		} else {
			statusLabel.setLabel("No current status");
			add(statusLabel);			
		}
	}
	
	/**
	* Updates displayed friend list by deleting previous labels
	* and displaying names of new friends
	*/
	private void displayFriends(Iterator<String> friends) {
		
		for (GLabel friendNameLabel: friendNameLabelList) {
			remove(friendNameLabel);
		}
		
		friendNameLabelList = new ArrayList<GLabel>();

		Point cPos = getLocationOnScreen();
		double xPos = (APPLICATION_WIDTH - cPos.x)/2;
		double yPos = imageRect.getY();
		
		add(friendsLabel, xPos, yPos);
		yPos += friendsLabel.getHeight();
		
		while (friends.hasNext()) {
			String friendName = friends.next();
			GLabel friendNameLabel = new GLabel(friendName);
			friendNameLabel.setFont(PROFILE_FRIEND_FONT);
			friendNameLabel.setLocation(xPos, yPos);
			add(friendNameLabel);
			
			friendNameLabelList.add(friendNameLabel);
			yPos += friendNameLabel.getHeight();
		}
	}
	
}
