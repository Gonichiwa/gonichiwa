package com.gonichiwa.mindmapinterface;

import java.util.ArrayList;
import java.util.List;

import com.gonichiwa.model.MindMapNode;

public interface NodeDataDeliver {
	
	public static final String[] attributes = {"NAME", "X", "Y", "WIDTH", "HEIGHT", "COLOR"};
	
	public int getID();
		
	public List<MindMapNode> getChildren();
	
	public String getName();
	
	public double getX();
	
	public double getY(); 
	
	public double getWidth();
	
	public double getHeight();
	
	public int getRedColor();
	
	public int getGreenColor();
	
	public int getBlueColor();
	
	public int getAlpha();
}
