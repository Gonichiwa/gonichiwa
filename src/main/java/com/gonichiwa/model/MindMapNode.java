package com.gonichiwa.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.gonichiwa.mindmapinterface.NodeDataDeliver;

public class MindMapNode extends Observable implements NodeDataDeliver {
	private static int idGenerator = 0;
	private final int id;
	private double x, y, width, height;
	private int red, green, blue;
	private String name;
	private String note;
	private List<MindMapNode> children;

	MindMapNode(String name) {
		this(name, 0, 0, 0, 0, 0, 0, 0, "");
	}

	/**
	 * constructor 
	 * 
	 * initialize the Node
	 * initialize id of the node using static property idGenerator.
	 * 
	 * @param name
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param red
	 * @param green
	 * @param blue
	 * @param note
	 */
	MindMapNode(String name, 
				double x, double y, 
				double width, double height, 
				int red, int green, int blue, 
				String note) 
	{
		super();
		id = ++idGenerator;
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.note = note;
		children = new ArrayList<>();
	}

	/**
	 * Initializer
	 * 
	 * initialize View property of the node on the MindMapGraphView
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param red
	 * @param green
	 * @param blue
	 */
	
	public void initViewAttribute(double x, double y, double width, double height, int red, int green, int blue) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		setRed(red);
		setGreen(green);
		setBlue(blue);
	}

	/**
	 * Modifier method
	 * 
	 * add child node to children List
	 * 
	 * @param child
	 * 		new child to be added
	 */
	public void addChild(MindMapNode child) {
		children.add(child);
	}
	
	/**
	 * Modifier method
	 * 
	 * remove selected node from children List
	 * 
	 * @param nodeID
	 * 		the node id to be removed.
	 */
	public void removeChild(int nodeID) {
		MindMapNode nodeToBeRemoved = null;
		for(MindMapNode child : children) {
			if(child.getID() == nodeID) {
				nodeToBeRemoved = child;
				break;
			}
		}
		children.remove(nodeToBeRemoved);
	}

	/**
	 * Modifier method
	 * 
	 * remove all children
	 */
	public void removeAllChildren() {
		children.clear();
	}

	/**
	 * Accessor method
	 * 
	 * return children List
	 */
	public List<MindMapNode> getChildren() {
		return children;
	}

	/**
	 *  Accessor method
	 *  
	 *  return id of the node
	 */
	public int getID() {
		return id;
	}

	/**
	 *  Accessor method
	 *  
	 *  return name of the node
	 */
	public String getName() {
		return name;
	}

	/**
	 *  Accessor method
	 *  
	 *  return x position value of the node
	 */
	public double getX() {
		return x;
	}

	/**
	 *  Accessor method
	 *  
	 *  return y position value of the node
	 */
	public double getY() {
		return y;
	}

	/**
	 *  Accessor method
	 *  
	 *  return width value of the node
	 */
	public double getWidth() {
		return width;
	}

	/**
	 *  Accessor method
	 *  
	 *  return height value of the node
	 */
	public double getHeight() {
		return height;
	}

	/**
	 *  Accessor method
	 *  
	 *  return red color value of the node
	 */
	public int getRedColor() {
		return red;
	}
	
	/**
	 *  Accessor method
	 *  
	 *  return green color value of the node
	 */
	public int getGreenColor() {
		return green;
	}
	
	/**
	 *  Accessor method
	 *  
	 *  return blue color value of the node
	 */
	public int getBlueColor() {
		return blue;
	}

	/**
	 *  Accessor method
	 *  
	 *  return note String of the node
	 */
	public String getNote() {
		return note;
	}

	/**
	 * Modifier method
	 * 
	 * set new x position value
	 * notify observers.
	 * 
	 * @param x
	 * 		new x position value to be set.
	 */
	public void setX(double x) {
		this.x = x;
		setChanged();
		notifyObservers();
	}

	/**
	 * Modifier method
	 * 
	 * set new y position value
	 * notify observers.
	 * 
	 * @param y
	 * 		new y position value to be set.
	 */
	public void setY(double y) {
		this.y = y;
		setChanged();
		notifyObservers();
	}

	/**
	 * Modifier method
	 * 
	 * set new width value
	 * notify observers.
	 * 
	 * @param width
	 * 		new width position value to be set.
	 */
	public void setWidth(double width) {
		this.width = width;
		setChanged();
		notifyObservers();
	}

	/**
	 * Modifier method
	 * 
	 * set new height value
	 * notify observers.
	 * 
	 * @param height
	 * 		new height position value to be set.
	 */
	public void setHeight(double height) {
		this.height = height;
		setChanged();
		notifyObservers();
	}

	/**
	 * Modifier method
	 * 
	 * set new red color.
	 * if value exceeds color range, then set to 
	 * maximum or minimum value.
	 * notify observers.
	 * 
	 * @param redValue
	 * 		new color value to be set.
	 */
	public void setRed(int redValue) {
		if (redValue > 255)
			red = 255;
		else if (redValue < 0)
			red = 0;
		else
			red = redValue;
		setChanged();
		notifyObservers();
	}

	/**
	 * Modifier method
	 * 
	 * set new green color.
	 * if value exceeds color range, then set to 
	 * maximum or minimum value.
	 * notify observers.
	 * 
	 * @param greenValue
	 * 		new color value to be set.
	 */
	public void setGreen(int greenValue) {
		if (greenValue > 255)
			green = 255;
		else if (greenValue < 0)
			green = 0;
		else
			green = greenValue;
		setChanged();
		notifyObservers();
	}

	/**
	 * Modifier method
	 * 
	 * set new blue color.
	 * if value exceeds color range, then set to 
	 * maximum or minimum value.
	 * notify observers.
	 * 
	 * @param blueValue
	 * 		new color value to be set.
	 */
	public void setBlue(int blueValue) {
		if (blueValue > 255)
			blue = 255;
		else if (blueValue < 0)
			blue = 0;
		else
			blue = blueValue;
		setChanged();
		notifyObservers();
	}

	/**
	 * Modifier method
	 * 
	 * set new note String data.
	 * notify observers.
	 * 
	 * @param note
	 * 		new note to be set.
	 */
	public void setNote(String note) {
		this.note = note;
		setChanged();
		notifyObservers();
	}

	/**
	 * Static Modifier method 
	 * 
	 * initialize idGenerator to 0.
	 */
	public static void initIDGenerator() {
		idGenerator = 0;
	}

}
