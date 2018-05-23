package com.gonichiwa.mindmapinterface;

import java.util.ArrayList;

import com.gonichiwa.model.MindMapNode;

public interface NodeDataDeliver {
	
	public static final String[] attributes = {"NAME", "X", "Y", "WIDTH", "HEIGHT", "COLOR"};
	
	public int getID();
	
	public NodeDataDeliver getParent();
	
	public ArrayList<MindMapNode> getChildren();
	
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
