import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class ObjectList {
	
	private GameObject head;
	private GameObject tail;
		
	public ObjectList() {

	}
	
	public ObjectList(GameObject obj) {
		head = obj;
		tail = obj;
	}
	
	public void insert(GameObject object) {
		if (head == null) {
			head = object;
			tail = object;
		} else {
			tail.next = object;
			object.prev = tail;
			tail = object;
		}
	}
	
	public void delete(GameObject object) {
		if (object != null) {
			if (object.next != null) {
				object.next.prev = object.prev; 
			} else {
				tail = object.prev;
			}
			if (object.prev != null) {
				object.prev.next = object.next; 
			} else {
				head = object.next;
			}
		}
	}
	
	public GameObject getHead() {
		return head;
	}
	
	public GameObject getTail() {
		return tail;
	}
	
	public boolean contains(GameObject obj) {
		GameObject current = head;
		boolean res = false;
		while (current != null && res == false) {
			res = (current == obj);
			current = current.next;
		}
		return res;
	}

	public boolean containsGObject(GObject obj) {
		boolean res = false;
		GameObject current = head;
		while (current != null && res == false) {
			res = (current.object == obj);
			current = current.next;
		}
		return res;
	}
	
	public GameObject findGObject(GObject obj) {
		GameObject current = head;
		while (current != null && current.object != obj) {
			current = current.next;
		}
		return current;
	}

	
	public void update() {
		GameObject current = head;
		while (current != null) {
			current.update();
			current = current.next;
		}
	}
	
	public void draw() {
		GameObject current = head;
		while (current != null) {
			current.draw();
			current = current.next;
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		GameObject current = head;
		while (current != null) {
			current.mouseMoved(e);
			current = current.next;
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		GameObject current = head;
		while (current != null) {
			current.mouseClicked(e);
			current = current.next;
		}
	}

	public void mousePressed(MouseEvent e) {
		GameObject current = head;
		while (current != null) {
			current.mousePressed(e);
			current = current.next;
		}
	}

	public void mouseReleased(MouseEvent e) {
		GameObject current = head;
		while (current != null) {
			current.mouseReleased(e);
			current = current.next;
		}
	}

	
};
