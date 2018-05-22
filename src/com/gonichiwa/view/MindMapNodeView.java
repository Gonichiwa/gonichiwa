package com.gonichiwa.view;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import com.gonichiwa.mindmapinterface.NodeDataDeliver;

public class MindMapNodeView extends JPanel {
	MindMapNodeView parentView;
	int id;
	
	public MindMapNodeView(NodeDataDeliver node) {
		id = node.getID();
		parentView = new MindMapNodeView(node.getParent()); // is that ok? I'm not sure.
	}
	
	public void addMouseListener(MouseListener l) {
		addMouseListener(l);
	}
	
	public void addMouseMotionListener(MouseMotionListener l) {
		addMouseMotionListener(l);
	}
	
	public boolean hasParent() {
		return false;
	}
	
	public Point getParentLocation() {
		return parentView.getLocation();
	}
}
