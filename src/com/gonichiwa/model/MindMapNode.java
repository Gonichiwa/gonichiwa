package com.gonichiwa.model;

class MindMapNode {
	private static int idGenerator = 0;
	private final int id;
	private int x, y, width, height;
	private int red, green, blue, alpha;
	private String name;
	
	// 1. node field implementation
	
	/**
	 * constructor
	 */
	public MindMapNode() {
		id = ++idGenerator;
		// 2. node initailize implementation
	}
	
	// 3. node method implementation
	
	/*
	 * getter method
	 * 
	 */
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
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
	
	/*
	 * setter method
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setRed(int redValue) {
		if(redValue > 255)
			red = 255;
		else if(redValue < 0) 
			red = 0;
		else
			red = redValue;
	}
	
	public void setGreen(int greenValue) {
		if(greenValue > 255)
			green = 255;
		else if(greenValue < 0) 
			green = 0;
		else
			green = greenValue;
	}
	
	public void setBlue(int blueValue) {
		if(blueValue > 255)
			blue = 255;
		else if(blueValue < 0) 
			blue = 0;
		else
			blue = blueValue;
	}
	
	public void setAlpha(int alphaValue) {
		if(alphaValue > 255)
			alpha = 255;
		else if(alphaValue < 0) 
			alpha = 0;
		else
			alpha = alphaValue;
	}
}









