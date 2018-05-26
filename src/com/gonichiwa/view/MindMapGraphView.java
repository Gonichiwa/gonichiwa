package com.gonichiwa.view;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.model.MindMapNode;
import com.gonichiwa.util.MindMapVector;

public class MindMapGraphView extends JPanel implements Observer {
	
	private MindMapModel model;
	private ArrayList<MindMapNodeView> nodes;
	
	public MindMapGraphView(MindMapModel model, int width, int height) {
		this.model = model;
		this.model.tree.addObserver(this);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(width, height));
		nodes = new ArrayList<MindMapNodeView>();
	}
	
	public void addNode(MindMapNodeView node) {
		nodes.add(node);
		add(node);
		this.repaint();
	}
	
	public void removeNode(MindMapNodeView node) {
		nodes.remove(node);
		remove(node);
	}
	
	public void clearNodes() {
		nodes.clear();
		this.removeAll();
	}
	
	public void addMouseListenerToNodes(MouseListener l) {
		for(MindMapNodeView node : nodes) 
			node.addMouseListener(l);
	}
	
	public void addMouseMotionListenerToNodes(MouseMotionListener l) {
		for(MindMapNodeView node : nodes) 
			node.addMouseMotionListener(l);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		this.clearNodes();
		this.removeAll();
		drawGraph();
	}
	

	public void drawGraph() {
		System.out.println("pane size is" + this.getPreferredSize());
		makeNodeView(model.tree.getRoot(), this.getPreferredSize().width/2, this.getPreferredSize().height/2, Math.PI*2, new MindMapVector(0, -1));
		this.repaint();
		this.revalidate();
	}
	
	private void makeNodeView(MindMapNode node, int centerX, int centerY, double availableAngle, MindMapVector direction) {
		// make node first
		// TODO: we might need more better algorithm here. 
		// TODO: using node size not constant.
		
		// for example, angle decision.
		int numberOfChildren = 0;
		double theta = 0;
		double distance = 0;
		
		// make NodeView
		System.out.println(node.getName() + " is making...");
		node.setX(centerX - MindMapNodeView.MIN_SIZE);
		node.setY(centerY - MindMapNodeView.MIN_SIZE);
		MindMapNodeView nodeView = new MindMapNodeView(node);
		node.setWidth(nodeView.getPreferredSize().width);
		node.setHeight(nodeView.getPreferredSize().height);
		this.addNode(nodeView);
				
		// get number of children
		numberOfChildren = node.getChildren().size();
		
		// get theta
		if(availableAngle != 2*Math.PI)
			theta = availableAngle / (numberOfChildren-1);
		else 
			theta = availableAngle / numberOfChildren;
	
		// get distance.
		distance = (numberOfChildren > 1) ? MindMapNodeView.MIN_SIZE/Math.sin(theta/2) + 10 : 40; // 10 is debug offset.
		
		direction.normalize();
		System.out.println(node.getName() + "'s direction is " + direction.getX() +", "+ direction.getY());
		direction.mult(distance);
		if(theta > Math.PI)
			theta = Math.PI/2;
		for(MindMapNode child : node.getChildren()) {
		
			makeNodeView(child,
						 centerX+(int)direction.getX(),
						 centerY+(int)direction.getY(),
						 theta, 
						 direction.copy().normalize().rotate(-theta/2));
			
			direction.rotate(theta);
		}
	}
	
//	public void paint(Graphics g) {
//		Graphics2D g2d = (Graphics2D) g;
//		QuadCurve2D q2 = new QuadCurve2D.Float();
//		g2d.setStroke(new BasicStroke(4));
//		
//		// is that ok for each NodeView to have parent information? 
//		// isn't that domain logic? or is it view logic?
//		for(MindMapNodeView node : nodes) {
//			if(node.hasParent()) {
//				q2.setCurve(node.getLocation(), new Point(400, 0), node.getParentLocation());
//				g2d.draw(q2);
//			}
//		}
//	}
}




