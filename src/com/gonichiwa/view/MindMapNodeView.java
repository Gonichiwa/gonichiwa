package com.gonichiwa.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
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
		if(node == null) 
			throw new IllegalArgumentException("NodeViewConstructor -> can not make mull node View");
		
//		this.setBackground(Color.blue);
		// make label center
		GridBagLayout gridbag = new GridBagLayout();
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.CENTER;
	    gridbag.setConstraints(this, constraints);
	    this.setLayout(gridbag);
	    
	    // set custom border
		setBorder(new ResizableBorder(6));
		// make label.
		this.node = node;
		this.id = node.getID();
		label = new JLabel(node.getName());
		label.setOpaque(false);
//		label.setBackground(Color.blue);
		label.setAlignmentX(JLabel.CENTER);
		label.setAlignmentY(JLabel.CENTER);
		this.setOpaque(false);
		this.add(label);
		
		// set geometry.
		this.setLocation(centerX-(this.getPreferredSize().width/2), centerY-(this.getPreferredSize().height/2));
		this.setSize(this.getPreferredSize());
		
		// add observer.
		node.addObserver(this);
	}
	
	public MindMapNodeView(MindMapNode node) {
		this(node, node.getX(), node.getY());
		setLocation(node.getX(), node.getY());
		setSize(node.getWidth(), node.getHeight());
	}
	
	public int getID() {
		return id;
	}
	
	public boolean hasParent() {
		return false;
	}
	
	public Point getParentLocation() {
		return parentView.getLocation();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
//		g.setColor(Color.BLUE);
//		System.out.println(this.getX()+" "+ this.getY()+" "+ this.getWidth()+" "+ this.getHeight());
//		g.fillOval(0+2, 0+2, this.getWidth()-4, this.getHeight()-4);
		this.paintComponents(g);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		// set location and size here;
		this.setLocation(node.getX(), node.getY());
		this.setSize(node.getWidth(), node.getHeight());
		this.revalidate();
	}

	
	public MindMapNode getNode() {
		return node;
	}
}
