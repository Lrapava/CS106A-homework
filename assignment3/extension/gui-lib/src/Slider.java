import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Slider extends GameObject {

	private double x;
	private double y;
	private double width;
	private double height;
	private double value;
	
	private GRect background;
	private GRect slider;
	
	public Slider(double value, double x, double y, double width, double height, GraphicsProgram canvas) {
		super(canvas);
		this.value = value;
		this.x = x;
		this.y = x;
		this.width = width;
		this.height = height;
		
		background = new GRect(x, y, width, height);
		background.setFilled(true);
		background.setColor(Color.gray);
		
		slider = new GRect(x, y, value*width, height);
		slider.setFilled(true);
		slider.setColor(Color.green);
		
		GRect collider = new GRect(x, y, width, height);
		collider.setFilled(false);
		object = collider;
	}
		
	public void setValue(double val) {
		value = val;
		slider.setSize(value*width, height);
	}
	
	public double getValue() {
		return value;
	}

	@Override
	public void draw() {
		canvas.add(background);
		canvas.add(slider);
		canvas.add(object);
	}
	
	@Override
	public void onPress(MouseEvent e) {
		value = (e.getX()-x)/(double)(width);
		slider.setSize(value*width, height);
	}



}
