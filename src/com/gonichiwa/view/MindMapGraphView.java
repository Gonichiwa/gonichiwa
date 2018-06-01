package com.gonichiwa.view;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
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
	private ArrayList<MindMapEdge> edges;
	private MouseAdapter nodeMouseListener;
	private KeyListener nodeKeyListener;
	
	public MindMapGraphView(MindMapModel model, int width, int height) {
		this.model = model;
		this.model.tree.addObserver(this);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(width, height));
		nodes = new ArrayList<MindMapNodeView>();
		edges = new ArrayList<MindMapEdge>();
		this.setFocusable(true);
		this.setRequestFocusEnabled(true); 		// now we can request this panel for focus.
	}
	
	public void addNodeMouseAdapter(MouseAdapter l) {
		nodeMouseListener = l;
	}
	
	public void addNodeKeyListener(KeyListener l) {
		nodeKeyListener = l;
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
		edges.clear();
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
	
//	@Override 
//	public void repaint() {
//		super.repaint();
//		revalidate();
//		repaintAllNodes();
//	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if(o instanceof MindMapNode) {
			this.repaint();
			return;
		}

		switch((String) arg) {
		case "NEW":
			
			drawGraph();
			break;
		case "LOAD":
		
			loadGraph();
			break;
		default:
			break;
		}
	}

	public void repaintAllNodes() {
		for(MindMapNodeView node: nodes) {
			node.repaint();
			node.revalidate();
		}
	}
	
	public void drawGraph() {
		this.clearNodes();
		this.removeAll();
		System.out.println("pane size is" + this.getPreferredSize());
		recMakeNodeView(model.tree.getRoot(), this.getPreferredSize().width/2, this.getPreferredSize().height/2, Math.PI*2, new MindMapVector(0, -1));
		this.repaint();
		this.revalidate();
	}
	
	/**
	 * load Graph from already built tree.
	 */
	public void loadGraph() {
		this.clearNodes();
		this.removeAll();
		recLoadNode(model.tree.getRoot());
		this.repaint();
		this.revalidate();
	}

	private void recLoadNode(MindMapNode node) {
		
		MindMapNodeView nodeView = new MindMapNodeView(node);
		nodeView.addMouseListener(nodeMouseListener);
		nodeView.addMouseMotionListener(nodeMouseListener);
		nodeView.addKeyListener(nodeKeyListener);
		addNode(nodeView);
		node.addObserver(this);
		for(MindMapNode child : node.getChildren()) {
			recLoadNode(child);
			edges.add(new MindMapEdge(node, child));
		}
	}
	
	private void recMakeNodeView(MindMapNode node, int centerX, int centerY, double availableAngle, MindMapVector direction) {
		// make node first
		// TODO: we might need more better algorithm here. 
		// TODO: using node size not constant.
		
		// for example, angle decision.
		int numberOfChildren = 0;
		double theta = 0;
		double distance = 0;
		
		// make NodeView
		
		System.out.println(node.getName() + " is making...");
		MindMapNodeView nodeView = new MindMapNodeView(node, centerX, centerY);
		nodeView.addMouseListener(nodeMouseListener);
		nodeView.addMouseMotionListener(nodeMouseListener);
		nodeView.addKeyListener(nodeKeyListener);
		System.out.println(nodeView.getLocation().x + " " + nodeView.getLocation().y);
		node.initViewAttribute(nodeView.getX(), nodeView.getY(), nodeView.getPreferredSize().width, nodeView.getPreferredSize().height);
		node.addObserver(this);
		// node.setColor()
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
		
			recMakeNodeView(child,
						 centerX+(int)direction.getX(),
						 centerY+(int)direction.getY(),
						 theta, 
						 direction.copy().normalize().rotate(-theta/2));
			edges.add(new MindMapEdge(node, child));
			direction.rotate(theta);
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		QuadCurve2D q2 = new QuadCurve2D.Float();
		g2d.setStroke(new BasicStroke(1));
		
		// is that ok for each NodeView to have parent information? 
		// isn't that domain logic? or is it view logic?
		for(MindMapEdge edge : edges) {
			g2d.drawLine(edge.getX1(), edge.getY1(), edge.getX2(), edge.getY2());
//			q2.setCurve(edge.x1, edge.y1, 0, 0, edge.x2, edge.y2);
//			g2d.draw(q2);
		}
	}
	
	private class MindMapEdge {
		private MindMapNode from;
		private MindMapNode to;
		public MindMapEdge(MindMapNode from, MindMapNode to) {
			this.from = from;
			this.to = to;
		}
		
		public int getX1() {
			return from.getX() + (from.getWidth()/2); 
		}
		
		public int getX2() {
			return to.getX() + (to.getWidth()/2); 
		}
		
		public int getY1() {
			return from.getY() + (from.getHeight()/2); 
		}
		
		public int getY2() {
			return to.getY() + (to.getHeight()/2); 
		}
	}
	
	
}




