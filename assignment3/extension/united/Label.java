import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Label extends Button {
	Label(String text, double x, double y, double width, double height, GraphicsProgram canvas) {
		super(text, x, y, width, height, canvas);
		background.setVisible(false);
		object.setVisible(false);
	}
}
