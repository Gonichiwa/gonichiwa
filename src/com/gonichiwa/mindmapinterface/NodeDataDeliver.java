package com.gonichiwa.mindmapinterface;

public interface NodeDataDeliver {
	
	public static final String[] attributes = {"NAME", "X", "Y", "WIDTH", "HEIGHT", "COLOR"};
	
	public int getID();
	
	public String getName();
	
	public int getX();
	
	public int getY(); 
	
	public int getWidth();
	
	public int getHeight();
	
	public int getRedColor();
	
	public int getGreenColor();
	
	public int getBlueColor();
	
	public int getAlpha();
}
