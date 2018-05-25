package com.gonichiwa.view;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gonichiwa.mindmapinterface.NodeDataDeliver;
import com.gonichiwa.model.MindMapNode;

public class MindMapNodeView extends JPanel {
	public static final int MIN_SIZE = 40;
	MindMapNodeView parentView;
	int id;
	JLabel label;
	
	public MindMapNodeView(MindMapNode node) {
		id = node.getID();
		label = new JLabel(node.getName());
		this.setLocation(node.getX(), node.getY());
		this.setSize(node.getWidth(), node.getHeight());
		this.add(label);
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
