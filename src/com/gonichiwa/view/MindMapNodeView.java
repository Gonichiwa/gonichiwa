package com.gonichiwa.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gonichiwa.model.MindMapNode;

public class MindMapNodeView extends JPanel implements Observer {
	public static final int MIN_SIZE = 40;
	private int id;
	private JLabel label;
	private MindMapNode node;
	private double offsetX = 0;
	private double offsetY = 0;
	private double zoomFactor = 1;
	private Color color;
	private final int BORDER_DIST = 6;

	public MindMapNodeView(MindMapNode node, double centerX, double centerY, Color color) {
		if(node == null)
			throw new IllegalArgumentException("NodeViewConstructor -> can not make mull node View");

//		this.setBackground(Color.blue);
		// make label center
		GridBagLayout gridbag = new GridBagLayout();
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.CENTER;
	    gridbag.setConstraints(this, constraints);
	    setLayout(gridbag);

	    // set custom border
		setBorder(new ResizableBorder(BORDER_DIST));
		// make label.
		this.node = node;
		this.id = node.getID();
		label = new JLabel(node.getName());
		label.setOpaque(false);
		label.setAlignmentX(JLabel.CENTER);
		label.setAlignmentY(JLabel.CENTER);
		setOpaque(false);
		add(label);

		// set geometry.
		this.color = color;
		setLocation((int) (centerX-(this.getPreferredSize().width/2)), (int) (centerY-(this.getPreferredSize().height/2)));
		
		// add observer.
		node.addObserver(this);
	}

	public MindMapNodeView(MindMapNode node) {
		this(node, node.getX(), node.getY(), new Color(node.getRedColor(), node.getGreenColor(), node.getBlueColor()));
		setLocation((int) node.getX(), (int) node.getY());
		setSize((int) node.getWidth(), (int) node.getHeight());
	}

	public void moveNode(double dx, double dy) {
		offsetX += dx;
		offsetY += dy;
		setLocation((int) (node.getX() + offsetX), (int) (node.getY() + offsetY));
	}

	public void zoomNode(double zoomFactor, int mouseX, int mouseY) {
		offsetX = ((node.getX() + offsetX - mouseX) * (zoomFactor / this.zoomFactor) + mouseX )- node.getX();
		offsetY = ((node.getY() + offsetY - mouseY) * (zoomFactor / this.zoomFactor) + mouseY )- node.getY();
		this.zoomFactor = zoomFactor;

		setLocation((int) (node.getX() + offsetX), (int) (node.getY() + offsetY));
		setSize((int)(node.getWidth()*zoomFactor), (int)(node.getHeight()* zoomFactor));
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(color);
		g.fillOval(0+BORDER_DIST, 0+BORDER_DIST, this.getWidth()-BORDER_DIST*2, this.getHeight()-BORDER_DIST*2);
		paintChildren(g);
	}

	@Override
	public void update(Observable o, Object arg) {
		label.setText(node.getName());
		label.revalidate();
		setLocation((int) (node.getX() + offsetX), (int) (node.getY() + offsetY));
		setSize((int)(node.getWidth() * zoomFactor), (int)(node.getHeight() * zoomFactor));
		color = new Color(node.getRedColor(), node.getGreenColor(), node.getBlueColor());
		revalidate();
	}
	
	public int getID() {
		return id;
	}

	public MindMapNode getNode() {
		return node;
	}
	
	public Color getColor() {
		return color;
	}
	
	public double getOffsetX() {
		return offsetX;
	}
	
	public double getOffsetY() {
		return offsetY;
	}
	
	public double getRelativeX() {
		return node.getX() + offsetX;
	}
	
	public double getRelativeY() {
		return node.getY() + offsetY;
	}
	
	public double getActualWidth() {
		return node.getWidth();
	}
	
	public double getActualHeight() {
		return node.getHeight();
	}
	
	public double getActualX() {
		return node.getX();
	}
	
	public double getActualY() {
		return node.getY();
	}

	public double getZoomFactor() {
		return zoomFactor;
	}
	
	public void setZoomFactor(double factor) {
		zoomFactor = factor;
	}
	
	
	public void setOffset(double x, double y) {
		offsetX = x;
		offsetY = y;
	}
}
