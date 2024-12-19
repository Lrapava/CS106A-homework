import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

// ObjectList class. Doubly linked list with linked list element GameObject
public class ObjectList {
	
	// First GameObject
	private GameObject head;
	
	// Last GameObject
	private GameObject tail;
	
	// Default constructor. Constructs empty linked list
	public ObjectList() {
		
	}
	
	// Constructor creates linked list with only one GameObject obj
	public ObjectList(GameObject obj) {
		head = obj;
		tail = obj;
	}
	
	// Inserts a GameObject at the end of the list
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
	
	
	// Inserts a GameObject in the beginning of the list
	public void insertFront(GameObject object) {
		if (head == null) {
			head = object;
			tail = object;
		} else {
			head.prev = object;
			object.next = head;
			head = object;
		}
	}
	
	// Deletes the passed GameObject from the list
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
	
	// Returns first element of the list
	public GameObject getHead() {
		return head;
	}
	
	// Returns last element of the list
	public GameObject getTail() {
		return tail;
	}
	
	// Checks if GameObject obj is in the linked list via iteration
	public boolean contains(GameObject obj) {
		GameObject current = head;
		boolean res = false;
		while (current != null && res == false) {
			res = (current == obj);
			current = current.next;
		}
		return res;
	}
	
	// Checks if linked list contains GameObject with object obj
	public boolean containsGObject(GObject obj) {
		boolean res = false;
		GameObject current = head;
		while (current != null && res == false) {
			res = (current.object == obj);
			current = current.next;
		}
		return res;
	}
	
	// Returns first GameObject in the list  with object obj
	public GameObject findGObject(GObject obj) {
		GameObject current = head;
		while (current != null && current.object != obj) {
			current = current.next;
		}
		return current;
	}

	// Updates all elements in the list
	public void update() {
		GameObject current = head;
		while (current != null) {
			current.update();
			current = current.next;
		}
	}
	
	// Draws all elements in the list
	public void draw() {
		GameObject current = head;
		while (current != null) {
			current.draw();
			current = current.next;
		}
	}
	
	// Calls mouseMoved event listener for all elements in the list
	public void mouseMoved(MouseEvent e) {
		GameObject current = head;
		while (current != null) {
			current.mouseMoved(e);
			current = current.next;
		}
	}
	
	// Calls mouseClicked event listener for all elements in the list
	public void mouseClicked(MouseEvent e) {
		GameObject current = head;
		while (current != null) {
			current.mouseClicked(e);
			current = current.next;
		}
	}

	// Calls mousePressed event listener for all elements in the list
	public void mousePressed(MouseEvent e) {
		GameObject current = head;
		while (current != null) {
			current.mousePressed(e);
			current = current.next;
		}
	}

	// Calls mouseReleased event listener for all elements in the list
	public void mouseReleased(MouseEvent e) {
		GameObject current = head;
		while (current != null) {
			current.mouseReleased(e);
			current = current.next;
		}
	}

	
};
