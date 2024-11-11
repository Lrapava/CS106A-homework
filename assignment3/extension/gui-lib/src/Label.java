import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Label extends Button {
	Label(String text, int x, int y, int width, int height, GraphicsProgram canvas) {
		super(text, x, y, width, height, canvas);
		background.setVisible(false);
		object.setVisible(false);
	}
}
