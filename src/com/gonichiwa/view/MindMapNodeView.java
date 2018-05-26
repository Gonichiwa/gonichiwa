package com.gonichiwa.view;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.gonichiwa.mindmapinterface.NodeDataDeliver;
import com.gonichiwa.model.MindMapNode;

public class MindMapNodeView extends JPanel {
	public static final int MIN_SIZE = 40;
	private MindMapNodeView parentView;
	private int id;
	private JLabel label;
	
	public MindMapNodeView(MindMapNode node) {
		id = node.getID();
//		parentView = node.getParent();		how to connect with it's parent
		label = new JLabel(node.getName());
		this.setLocation(node.getX(), node.getY());
		this.add(label);
		this.setSize(this.getPreferredSize());
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
