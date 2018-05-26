package com.gonichiwa.view;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.gonichiwa.mindmapinterface.NodeDataDeliver;
import com.gonichiwa.model.MindMapNode;

public class MindMapNodeView extends JPanel implements Observer {
	public static final int MIN_SIZE = 40;
	private MindMapNodeView parentView;
	private int id;
	private JLabel label;
	private MindMapNode node;
	
	public MindMapNodeView(MindMapNode node, int centerX, int centerY) {
		this.node = node;
		this.id = node.getID();
		label = new JLabel(node.getName(), JLabel.CENTER);
		this.add(label);
		this.setLocation(centerX-(this.getPreferredSize().width/2), centerY-(this.getPreferredSize().height/2));
		this.setSize(this.getPreferredSize());
		node.addObserver(this);
	}
	
	public boolean hasParent() {
		return false;
	}
	
	public Point getParentLocation() {
		return parentView.getLocation();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		// set location and size here;
		this.setLocation(node.getX(), node.getY());
		this.setSize(node.getWidth(), node.getHeight());
		this.revalidate();
	}
	
	public NodeDataDeliver getNode() {
		return (NodeDataDeliver) node;
	}
}
