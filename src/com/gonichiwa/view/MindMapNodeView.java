package com.gonichiwa.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.gonichiwa.model.MindMapNode;

public class MindMapNodeView extends JPanel implements Observer {
	public static final int MIN_DISTANCE = 60;
	public static final int MIN_WIDTH = 60;
	public static final int MIN_HEIGHT = 40;
	public static final int MAX_WIDTH = 100;
	public static final int MAX_HEIGHT = 100;
	private int id;
	private JLabel label;
	private MindMapNode node;
	private double offsetX = 0;
	private double offsetY = 0;
	private double zoomFactor = 1;
	private Color color;
	private final int BORDER_DIST = 6;
	private final double LABEL_SIZE_RATIO = 0.5;
	private final int INITIAL_FONT_SIZE = 30;
	
	public MindMapNodeView(MindMapNode node, double centerX, double centerY, Color color) {
		if(node == null)
			throw new IllegalArgumentException("NodeViewConstructor -> can not make mull node View");

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

		Font font = new Font("Monaco", Font.BOLD, INITIAL_FONT_SIZE);
		label.setFont(font);
		setOpaque(false);
		add(label);

		// set geometry.
		this.color = color;
		setPreferredSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		setLocation((int) (centerX-(this.getPreferredSize().width/2)), (int) (centerY-(this.getPreferredSize().height/2)));
		updateLabelFont();
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
		offsetX = ((node.getX() + offsetX - mouseX) * (zoomFactor / this.zoomFactor) + mouseX ) - node.getX();
		offsetY = ((node.getY() + offsetY - mouseY) * (zoomFactor / this.zoomFactor) + mouseY ) - node.getY();
		this.zoomFactor = zoomFactor;
		
		setLocation((int) (node.getX() + offsetX), (int) (node.getY() + offsetY));
		setSize((int) (node.getWidth() * zoomFactor), (int) (node.getHeight() * zoomFactor));
		updateLabelFont();
		repaint();
	}
	
	public void updateLabelFont() {
		label.setSize((int) (getWidth() * LABEL_SIZE_RATIO), (int) (getHeight() * LABEL_SIZE_RATIO));
		Font labelFont = label.getFont();
		String labelText = label.getText();

		int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
		int componentWidth = label.getWidth();

		// Find out how much the font can grow in width.
		double widthRatio = (double)componentWidth / (double)stringWidth;

		int newFontSize = (int)(labelFont.getSize() * widthRatio);
		int componentHeight = label.getHeight();

		// Pick a new font size so it will not be larger than the height of label.
		int fontSizeToUse = Math.min(newFontSize, componentHeight);
		if (!(fontSizeToUse > 0)) {
			fontSizeToUse = 1;
			label.setVisible(false);
		} else {
		// Set the label's font size to the newly determined size.
			label.setFont(new Font(labelFont.getName(), Font.BOLD, fontSizeToUse));
			label.setVisible(true);
		}
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
		updateLabelFont();
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
	
	public void resetOffset() {
		setOffset(0, 0);
		zoomFactor = 1;
	}
	
	public void setOffset(int dx, int dy) {
		offsetX = dx;
		offsetY = dy;
	}
}
