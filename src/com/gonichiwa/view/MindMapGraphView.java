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
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.model.MindMapNode;
import com.gonichiwa.util.MindMapVector;

/**
 * MindMapGraphView
 * 
 * this is connected with MindMapNode and MindMapTree class as Observer.
 * 
 * using List of nodes and edges it will draw MindMapNodeView and 
 * MindMapEdge on JPanel
 * 
 * it keeps track of dx, dy and zoomFactor of this JPanel so that
 * user can zoom and moving this JPanel
 * 
 * @author YONG_JOON_KIM
 *
 */
public class MindMapGraphView extends JPanel implements Observer {

	private MindMapModel model;
	private List<MindMapNodeView> nodes;
	private List<MindMapEdge> edges;
	private MouseAdapter nodeMouseListener;
	private KeyListener nodeKeyListener;

	private double zoomFactor = 1;
	private double dx, dy;

	private static final int INITIAL_GRAPH_VIEW_WIDTH = 600;
	private static final int INITIAL_GRAPH_VIEW_HEIGHT = 600;
	public static final double MAX_ZOOM_FACTOR = 4.0;
	public static final double MIN_ZOOM_FACTOR = 0.5;
	private static final double NODE_COLOR_FREQUENCY = 0.6;

	/**
	 * constructor
	 * 
	 * initialize JPanel of this view with given width and height
	 * set Observer relationship with MindMapTree
	 * 
	 * @param model
	 * 		MindMapModel object
	 * @param width
	 * 		initial width of JPanel
	 * @param height
	 * 		initial height of JPanel
	 */
	public MindMapGraphView(MindMapModel model, int width, int height) {
		this.model = model;
		this.model.tree.addObserver(this);
		setLayout(null);
		setBackground(Color.DARK_GRAY);
		setPreferredSize(new Dimension(width, height));
		nodes = new ArrayList<>();
		edges = new ArrayList<>();
		setFocusable(true);
		setRequestFocusEnabled(true); 		
	}
	
	public MindMapGraphView(MindMapModel model) {
		this(model, INITIAL_GRAPH_VIEW_WIDTH, INITIAL_GRAPH_VIEW_HEIGHT);
	}

	//  JU_YEONG_JEONG
	public int makeR(int i) {
		int check = i%7;
		return (int) (Math.sin(NODE_COLOR_FREQUENCY*check)*63+192);
	}
	
	//  JU_YEONG_JEONG
	public int makeG(int i) {
		int check = i%7;
		return (int) (Math.sin(NODE_COLOR_FREQUENCY*check+2)*63+192);
	}
	
	//  JU_YEONG_JEONG
	public int makeB(int i) {
		int check = i%7;
		return (int) (Math.sin(NODE_COLOR_FREQUENCY*check+4)*63+192);
	}

	/**
	 * Accessor method
	 * 
	 * @return
	 * 		return maximum zoomFactor as integer value
	 */
	public static int getMaxZoomPercentage() {
		return (int) (MAX_ZOOM_FACTOR * 100);
	}

	/**
	 * Accessor method
	 * 
	 * @return
	 * 		return minimum zoomFactor as integer value
	 */
	public static int getMinZoomPercentage() {
		return (int) (MIN_ZOOM_FACTOR * 100);
	}

	/**
	 * Accessor method
	 * 
	 * @return
	 * 		return zoomFactor
	 */
	public double getZoomFactor() {
		return zoomFactor;
	}

	/**
	 * Modifier method
	 * 
	 * set new zoomFactor
	 * 
	 * @param zoomFactor
	 */
	public void setZoomFactor(double zoomFactor) {
		this.zoomFactor = zoomFactor;
	}


	/**
	 * Modifier method
	 * 
	 * reset offsets to 0 and zoom factor to 1
	 */
	public void resetGraphViewOffset() {
		movePanel(-dx, -dy);
		dx = 0;
		dy = 0;
		zoomFactor = 1;
		zoom((int) dx, (int) dy, 1);
	}
	
	/**
	 * Modifier method
	 * 
	 * reset all the offsets to center of the JPanel
	 * if there is graph which is drawn with nodes, then
	 * calculate center offset of current JPanel and set 
	 * all offsets to that new center offset. zoom factor
	 * goes to 1
	 */
	public void resetOffsetsToCenter() {

		resetGraphViewOffset();

		MindMapNode root = model.tree.getRoot();

		if(root != null) {
			// get center of the root node.
			double nodeCenterX = root.getX() + root.getWidth() / 2;
			double nodeCenterY = root.getY() + root.getHeight() / 2;

			// get center of the graphView.
			double graphCenterX = getWidth() / 2;
			double graphCenterY = getHeight() / 2;

			double rootNodeCenterOffsetX = graphCenterX - nodeCenterX;
			double rootNodeCenterOffsetY = graphCenterY - nodeCenterY;

			for(MindMapNodeView node : nodes) {
				// set all nodes' offset to rootNodeCenterOffsets.
				node.setOffset((int) rootNodeCenterOffsetX, (int) rootNodeCenterOffsetY);
				node.zoomNode(zoomFactor, 0, 0);
				node.moveNode((int) dx, (int) dy);
			}
		}

		repaint();
		revalidate();
	}

	public void addNodeMouseAdapter(MouseAdapter l) {
		nodeMouseListener = l;
	}

	public void addNodeKeyListener(KeyListener l) {
		nodeKeyListener = l;
	}

	/**
	 * Helper method
	 * 
	 * add new NodeView to nodes List
	 * 
	 * @param node
	 */
	private void addNode(MindMapNodeView node) {
		nodes.add(node);
		add(node);
		repaint();
	}

	/**
	 * Modifier method
	 * 
	 * remove the given NodeView on the JPanel
	 * 
	 * @param node
	 * 		node view to be removed
	 */
	public void removeNode(MindMapNodeView node) {
		nodes.remove(node);
		remove(node);
	}

	/**
	 * Modifier method
	 * 
	 * clear all nodes on the JPanel
	 */
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

	/**
	 * Modifier method
	 * 
	 * load Graph from already built tree.
	 */
	public void loadGraph() {
		try {
			clearNodes();
			recLoadNode(model.tree.getRoot());
			revalidate();
		} catch (NullPointerException e) {

		}
	}

	/**
	 * Helper method
	 * 
	 * load the given node data and make new MindMapNodeView
	 * recursively
	 * 
	 * @param node
	 * 		the node data to be drawn on JPanel
	 */
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

	/**
	 * Modifier method
	 * 
	 * Firstly, reset this GraphView so that this canvas
	 * could be empty 
	 * Secondly, draw new Graph recursively using recMakeNodeView()
	 * 
	 */
	public void drawGraph() {
		reset();
		recMakeNodeView(model.tree.getRoot(),
						getWidth()/2,
						getHeight()/2,
						Math.PI*2, new MindMapVector(0, -1),
						1);
		revalidate();
	}

	/**
	 * Helper method
	 * 
	 * calculate position of the node to be drawn on JPanel and 
	 * connect that node with this graphView
	 * 
	 * 1. make NodeView on centerX, centerY
	 * 2. calculate theta value with availableAngle and numberOfChildren
	 * 3. calculate distance for children nodes with theta and MIN_DISTANCE
	 * 4. make children views recursively
	 * 
	 * @param node
	 * 		the node model to be initialize as NodeView
	 * @param centerX
	 * 		center x position
	 * @param centerY
	 * 		center y position
	 * @param availableAngle
	 * 		available angle that this node can use to make children nodes
	 * @param direction
	 * 		vector objects indicates heading for this nodes and uses
	 * 		to make decisions for child's heading
	 * @param colorLevel
	 * 		color level of this nodes
	 */
	private void recMakeNodeView(MindMapNode node, int centerX, int centerY, double availableAngle, MindMapVector direction, int colorLevel) {
		// make node first
		// TODO: using node size not constant.

		// for example, angle decision.
		int numberOfChildren = 0;
		double theta = 0;
		double distance = 0;

		// make NodeView

		MindMapNodeView nodeView = new MindMapNodeView(node,
				centerX, centerY,
				new Color(makeR(colorLevel), makeG(colorLevel), makeB(colorLevel)));
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
		distance = (numberOfChildren > 1) ? MindMapNodeView.MIN_DISTANCE/Math.sin(theta/2) : MindMapNodeView.MIN_DISTANCE;
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
							colorLevel + 1);

			edges.add(new MindMapEdge(node, child));
			direction.rotate(theta);
		}
	}

	/**
	 * Modifier method
	 * 
	 * zoom GraphView by the given x, y and factor value
	 * 
	 * update dx, dy which indicates offset of the JPanel
	 * and zoom nodes with new factor and x, y
	 * 
	 * @param mouseX
	 * 		mouse x position which zoom action happened
	 * @param mouseY
	 * 		mouse y position which zoom action happened
	 * @param factor
	 * 		new zoom factor to be applied
	 */
	public void zoom(int mouseX, int mouseY, double factor) {
		dx = ((dx - mouseX) * (factor / zoomFactor) + mouseX);
		dy = ((dy - mouseY) * (factor / zoomFactor) + mouseY);

		setZoomFactor(factor);

		for(MindMapNodeView node : nodes) {
			node.zoomNode(factor, mouseX, mouseY);
		}
		
		repaint();
		revalidate();
	}

	/**
	 * Modifier method
	 * 
	 * move pane by the given dx and dy value.
	 * first of all change offset values, and move all nodes
	 * int the node List
	 * 
	 * @param dx
	 * 		how much mouse moved to x direction
	 * @param dy
	 * 		how much mouse moved to y direction
	 */
	public void movePanel(double dx, double dy) {
		this.dx += dx;
		this.dy += dy;

		for(MindMapNodeView node : nodes) {
			node.moveNode((int)dx,  (int)dy);
		}

		repaint();
	}
	
	/**
	 * Drawing method
	 * 
	 * draw edge in the List<>
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.white);

		for(MindMapEdge edge : edges) {
			g2d.drawLine(edge.getX1(),
						 edge.getY1(),
						 edge.getX2(),
						 edge.getY2());
		}
		paintChildren(g2d);
	}

	/**
	 * Helper method
	 * 
	 * return MindMapNodeView which has given id
	 * 
	 * @param nodeID
	 * @return
	 */
	private MindMapNodeView getNodeView(int nodeID) {
		for(MindMapNodeView node : nodes) {
			if(node.getID() == nodeID) {
				return node;
			}
		}
		return null;
	}
	
	/**
	 * Observer update method
	 * 
	 * if MindMapNode notified, then just repaint JPanel
	 * else if MindMapTree notified, then weather drawGraph
	 * or loadGraph depend on command from tree.
	 * 
	 */
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

	/**
	 * Modifier method
	 * 
	 * reset all subViews and offsets on GraphView
	 */
	public void reset() {
		resetGraphViewOffset();
		clearNodes();
		repaint();
	}

	/**
	 * MindMapEdge
	 * 
	 * this class has from and to node for drawing 
	 * edge on GraphView in the paint() method.
	 * referencing MindMapNodeView, it can always return 
	 * up to date value of the NodeView
	 * 
	 * @author YONG_JOON_KIM
	 *
	 */
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
