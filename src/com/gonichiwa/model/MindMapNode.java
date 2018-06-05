package com.gonichiwa.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.gonichiwa.mindmapinterface.NodeDataDeliver;

public class MindMapNode extends Observable implements NodeDataDeliver {
	private static int idGenerator = 0;
	private final int id;
	private double x, y, width, height; 
	private int red, green, blue, alpha;
	private String name;
	private String note;
	private List<MindMapNode> children = new ArrayList<MindMapNode>();;
	
	// 1. node field implementation
	
	/**
	 * constructor
	 * please do not think the way to initialize x, y ,width, height values at this point OK?
	 */

	MindMapNode(String name) {
		this(name, 0, 0, 0, 0, 0, 0, 0, ""); 
		// 2. node initailize implementation
	}
	
	MindMapNode(String name, double x, double y, double width, double height, int red, int green, int blue, String note) {
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
	}
	
	public MindMapNode(MindMapNode node) {
		this(node.name, node.x, node.y, node.width, node.height, node.getRedColor(), node.getGreenColor(), node.getBlueColor(), node.getNote());
	}
	
	// 3. node method implementation
	public void initViewAttribute(double x, double y, double width, double height, int red, int green, int blue) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		setRed(red);
		setGreen(green);
		setBlue(blue);
	}
	
	public void addChild(MindMapNode child) {
		children.add(child);
	}
	
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
	
	public void removeAllChildren() {
		children.clear();
	}
	
//	public void setParent(MindMapNode parent) {
//		this.parent = parent;
//	}
	/*
	 * getter method
	 * 
	 */
	public List<MindMapNode> getChildren() {
		return children;
	}

//	public MindMapNode getParent() {
//		return parent;
//	}
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public int getRedColor() {
		return red;
	}
	
	public int getGreenColor() {
		return green;
	}
	
	public int getBlueColor() {
		return blue;
	}
	
	public int getAlpha() {
		return alpha;
	}
	
	public String getNote() {
		return note;
	}
	
	/*
	 * setter method
	 */

	public void setName(String name) {
		this.name = name;
	}
	
	public void setX(double x) {
		this.x = x;
		setChanged();
		notifyObservers();
	}
	
	public void setY(double y) {
		this.y = y;
		setChanged();
		notifyObservers();
	}
	
	public void setWidth(double width) {
		this.width = width;
		setChanged();
		notifyObservers();
	}
	
	public void setHeight(double height) {
		this.height = height;
		setChanged();
		notifyObservers();
	}
	
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
	
	public void setAlpha(int alphaValue) {
		if (alphaValue > 255)
			alpha = 255;
		else if (alphaValue < 0) 
			alpha = 0;
		else
			alpha = alphaValue;
		setChanged();
		notifyObservers();
	}
	
	public void setNote(String note) {
		this.note = note;
		setChanged();
		notifyObservers();
	}
	
	public static void initIDGenerator() {
		idGenerator = 0;
	}

}









