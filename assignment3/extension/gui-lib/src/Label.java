import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

// Label class. For additional information view Button.java 
public class Label extends Button {
	// Label constructor
	Label(String text, double x, double y, double width, double height, GraphicsProgram canvas) {
		super(text, x, y, width, height, canvas);
		background.setVisible(false);
		object.setVisible(false);
	}
}
