import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

// Button class. For additional information view GameObject.java
public class Button extends GameObject {
	
	// Button position
	private double x;
	private double y;
	
	// Button dimensions
	private double width;
	private double height;
	
	// Button text
	private String text = "";
	
	// Button background & label
	public GRect background;
	public GLabel label;
	
	// Button constructor
	public Button(String text, double x, double y, double width, double height, GraphicsProgram canvas) {
		super(canvas);
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		background = new GRect(x, y, width, height);
		background.setFilled(true);
		background.setColor(Color.gray);
		
		GRect collider = new GRect(x, y, width, height);
		collider.setFilled(false);
		object = collider;
		
		label = new GLabel(text);
		final double XOffset = (width - label.getWidth()) / 2;
		final double YOffset = (height + label.getAscent()) / 2;
		label.setLocation(x+XOffset, y+YOffset);
	}
	
	// Changes label of the button
	public void setLabel(String text) {
		this.text = text;
		label.setLabel(text);
		final double XOffset = (width - label.getWidth()) / 2;
		final double YOffset = (height + label.getAscent()) / 2;
		label.setLocation(x+XOffset, y+YOffset);
	}
	
	@Override
	public void draw() {
		canvas.add(background);
		canvas.add(label);
		canvas.add(object);
	}

}
