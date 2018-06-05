package com.gonichiwa.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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

	private double zoomFactor = 1;
	private double dx, dy;
	
	private static final int INITIAL_GRAPH_VIEW_WIDTH = 600;
	private static final int INITIAL_GRAPH_VIEW_HEIGHT = 600;
	public static final double MAX_ZOOM_FACTOR = 4.0;
	public static final double MIN_ZOOM_FACTOR = 0.5;

	public MindMapGraphView(MindMapModel model, int width, int height) {
		this.model = model;
		this.model.tree.addObserver(this);
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(width, height));
		nodes = new ArrayList<MindMapNodeView>();
		edges = new ArrayList<MindMapEdge>();
		setFocusable(true);
		setRequestFocusEnabled(true); 		// now we can request this panel for focus.
	}
	
	public MindMapGraphView(MindMapModel model) {
		this(model, INITIAL_GRAPH_VIEW_WIDTH, INITIAL_GRAPH_VIEW_HEIGHT);
	}
	
	public static int getMaxZoomPercentage() {
		return (int) (MAX_ZOOM_FACTOR * 100);
	}
	
	public static int getMinZoomPercentage() {
		return (int) (MIN_ZOOM_FACTOR * 100);
	}

	public double getZoomFactor() {
		return zoomFactor;
	}

	public void setZoomFactor(double zoomFactor) {
		this.zoomFactor = zoomFactor;
	}


	public void reset() {
		resetAllOffset();
		clearNodes();
		repaint();
	}
	
	public void resetAllOffset() {
		
		this.zoom((int) dx, (int) dy, 1);
		this.movePanel(-dx, -dy);

		dx = 0;
		dy = 0;
		zoomFactor = 1;
		
		repaint();
		revalidate();
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
		repaint();
	}

	public void removeNode(MindMapNodeView node) {
		nodes.remove(node);
		remove(node);
	}

	public void clearNodes() {
		nodes.clear();
		edges.clear();
		removeAll();
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
		if(o instanceof MindMapNode) {
			repaint();
			return;
		}
		
		switch(String.valueOf(arg)) {
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

	public void drawGraph() {
		clearNodes();
		int colorOffset = 255 / model.tree.size();
		recMakeNodeView(model.tree.getRoot(),
						this.getPreferredSize().width/2,
						this.getPreferredSize().height/2,
						Math.PI*2, new MindMapVector(0, -1),
						1, colorOffset);
		revalidate();
	}

	/**
	 * load Graph from already built tree.
	 */
	public void loadGraph() {
		clearNodes();
		recLoadNode(model.tree.getRoot());
		revalidate();
	}

	private void recLoadNode(MindMapNode node) {

		MindMapNodeView nodeView = new MindMapNodeView(node);
		nodeView.addMouseListener(nodeMouseListener);
		nodeView.addMouseMotionListener(nodeMouseListener);
		nodeView.addKeyListener(nodeKeyListener);
		addNode(nodeView);
		nodeView.zoomNode(zoomFactor, 0, 0);
		nodeView.moveNode((int)dx, (int)dy);
		node.addObserver(this);
		for(MindMapNode child : node.getChildren()) {
			recLoadNode(child);
			edges.add(new MindMapEdge(node, child));
		}
	}

	private void recMakeNodeView(MindMapNode node, int centerX, int centerY, double availableAngle, MindMapVector direction, int colorLevel, int colorOffset) {
		// make node first
		// TODO: using node size not constant.

		// for example, angle decision.
		int numberOfChildren = 0;
		double theta = 0;
		double distance = 0;

		// make NodeView

		MindMapNodeView nodeView = new MindMapNodeView(node,
				centerX, centerY, 
				new Color(colorLevel * colorOffset, 0, 255 - colorLevel * colorOffset));
		nodeView.addMouseListener(nodeMouseListener);
		nodeView.addMouseMotionListener(nodeMouseListener);
		nodeView.addKeyListener(nodeKeyListener);
		
		node.initViewAttribute(nodeView.getX(),
							   nodeView.getY(),
							   nodeView.getPreferredSize().width, 
							   nodeView.getPreferredSize().height,
							   nodeView.getColor().getRed(),
							   nodeView.getColor().getGreen(),
							   nodeView.getColor().getBlue());
		node.addObserver(this);
		
		// node.setColor()
		addNode(nodeView);
		nodeView.zoomNode(zoomFactor, 0, 0);
		nodeView.moveNode((int)dx, (int)dy);

		// get number of children
		numberOfChildren = node.getChildren().size();

		// get theta
		if(availableAngle != 2*Math.PI)
			theta = availableAngle / (numberOfChildren-1);
		else
			theta = availableAngle / numberOfChildren;

		// get distance.
		distance = (numberOfChildren > 1) ? MindMapNodeView.MIN_SIZE/Math.sin(theta/2) : MindMapNodeView.MIN_SIZE; // 10 is debug offset.

		direction.normalize();
		direction.mult(distance);

		if(theta > Math.PI)
			theta = Math.PI/2;

		for(MindMapNode child : node.getChildren()) {

			recMakeNodeView(child,
							centerX+(int)direction.getX(),
							centerY+(int)direction.getY(),
							theta,
							direction.copy().normalize().rotate(-theta/2),
							colorLevel + 1, colorOffset);

			edges.add(new MindMapEdge(node, child));
			direction.rotate(theta);
		}
	}

	
	public void zoom(int x, int y, double factor) {
		dx = (int) ((dx - x) * (factor / zoomFactor) + x);
		dy = (int) ((dy - y) * (factor / zoomFactor) + y);
		
		setZoomFactor(factor);
	
		for(MindMapNodeView node : nodes) {
			node.zoomNode(factor, x, y);
		}
		
		repaint();
		revalidate();
	}

	public void movePanel(double dx, double dy) {
		this.dx += dx;
		this.dy += dy;
		
		for(MindMapNodeView node : nodes) {
			node.moveNode((int)dx,  (int)dy);
		}
		
		repaint();
	}

	public double getDX() {
		return dx;
	}

	public double getDY() {
		return dy;
	}
	
	@Override 
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setStroke(new BasicStroke(1));

		for(MindMapEdge edge : edges) {
			g2d.drawLine(edge.getX1(),
						 edge.getY1(), 
						 edge.getX2(),
						 edge.getY2());
		}
		paintChildren(g2d);
	}
	
	private MindMapNodeView getNodeView(int nodeID) {
		for(MindMapNodeView node : nodes) {
			if(node.getID() == nodeID) {
				return node;
			}
		}
		return null;
	}

	private class MindMapEdge {
		private MindMapNodeView from;
		private MindMapNodeView to;
		
		public MindMapEdge(MindMapNode from, MindMapNode to) {
			this.from = getNodeView(from.getID());
			this.to = getNodeView(to.getID());
		}

		public int getX1() {
			return (int) (from.getRelativeX() + (from.getWidth()/2));
		}

		public int getX2() {
			return (int) (to.getRelativeX() + (to.getWidth()/2));
		}

		public int getY1() {
			return (int) (from.getRelativeY() + (from.getHeight()/2));
		}

		public int getY2() {
			return (int) (to.getRelativeY() + (to.getHeight()/2));
		}
	}
}
