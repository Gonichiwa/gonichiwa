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
	private double offsetX = 0;
	private double offsetY = 0;
	private int widthOffset = 0;
	private int heightOffset = 0;
	private int zoomX = 0;
	private int zoomY = 0;
	private double zoomFactor = 1;

	public MindMapNodeView(MindMapNode node, double centerX, double centerY) {
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
		this.setLocation((int) (centerX-(this.getPreferredSize().width/2)), (int) (centerY-(this.getPreferredSize().height/2)));
		this.setSize(this.getPreferredSize());
		
		// add observer.
		node.addObserver(this);
	}

	public MindMapNodeView(MindMapNode node) {
		this(node, node.getX(), node.getY());
		setLocation((int) node.getX(), (int) node.getY());
		setSize((int) node.getWidth(), (int) node.getHeight());
	}

	public double getOffsetX() {
		return offsetX;
	}
	
	public double getOffsetY() {
		return offsetY;
	}
	
	public void moveNode(double dx, double dy) {
		offsetX += dx;
		offsetY += dy;
		System.out.println("offset is " + offsetX + " " + offsetY);
		System.out.println("relative node position " + (node.getX() + offsetX) + " " + (node.getY() + offsetY));
		this.setLocation((int) (node.getX() + offsetX), (int) (node.getY() + offsetY));
//		this.repaint();
	}

	public void zoomNode(double zoomFactor, int mouseX, int mouseY) {
		offsetX = ((node.getX() + offsetX - mouseX) * (zoomFactor / this.zoomFactor) + mouseX )- node.getX();
		offsetY = ((node.getY() + offsetY - mouseY) * (zoomFactor / this.zoomFactor) + mouseY )- node.getY();
		this.zoomFactor = zoomFactor;

		this.setLocation((int) (node.getX() + offsetX), (int) (node.getY() + offsetY));
		this.setSize((int)(node.getWidth()*zoomFactor + widthOffset), (int)(node.getHeight()* zoomFactor + heightOffset));
		this.repaint();
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
		g.setColor(Color.BLUE);
		System.out.println(this.getX()+" "+ this.getY()+" "+ this.getWidth()+" "+ this.getHeight());
		g.fillOval(0+4, 0+4, this.getWidth()-8, this.getHeight()-8);
		this.paintChildren(g);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		// set location and size here;
		this.label.setText(node.getName());
		this.label.revalidate();
		this.setLocation((int) (node.getX() + offsetX), (int) (node.getY() + offsetY));
		this.setSize((int)(node.getWidth() * zoomFactor + widthOffset), (int)(node.getHeight() * zoomFactor + heightOffset));
		this.revalidate();
	}
	
	public void scale(double factor) {
		zoomFactor = factor;
	}
	
	public void translate(double x, double y) {
		offsetX += x;
		offsetY += y;
	}
	
	public MindMapNode getNode() {
		return node;
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
	
	public double getZoomFactor() {
		return zoomFactor;
	}
	
	public double getActualX() {
//		return this.getX() - offsetX;
		return node.getX();
	}
	
	public double getActualY() {
//		return this.getY() - offsetY;
		return node.getY();
	}
	
	public void setZoomFactor(double factor) {
		zoomFactor = factor;
	}
	
	public void scaleWidth(int dx) {
		widthOffset += dx;
		this.setSize((int)(node.getWidth()*zoomFactor + widthOffset), (int)(node.getHeight()* zoomFactor + heightOffset));

	}
	
	public void scaleHeight(int dy) {
		heightOffset += dy;
		this.setSize((int)(node.getWidth()*zoomFactor + widthOffset), (int)(node.getHeight()* zoomFactor + heightOffset));
	}
}
