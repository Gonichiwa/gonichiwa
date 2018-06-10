package com.gonichiwa.mindmapinterface;

import java.util.List;

import com.gonichiwa.model.MindMapNode;

/**
 * NodeDataDeliver interface
 * 
 * this is interface for MindMapNode class
 * which is nothing but all getter methods.
 * this is used for some View class which only needs
 * to get information from MindMapNode instance.
 * 
 * attributes Array is the names of Attributes which
 * will display on AttributePane.
 * 
 * @author YONG_JOON_KIM
 *
 */
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
		
	public String getNote();
}
