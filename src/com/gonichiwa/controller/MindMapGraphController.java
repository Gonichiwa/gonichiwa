package com.gonichiwa.controller;

import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;

import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.model.MindMapNode;
import com.gonichiwa.view.MindMapGraphView;
import com.gonichiwa.view.MindMapNodeView;

import com.gonichiwa.util.MindMapVector;

public class MindMapGraphController implements Observer {
	private MindMapModel model;
	private MindMapGraphView view;
	
	public MindMapGraphController(MindMapModel model) {
		this(model, new MindMapGraphView(400, 400));
	}
	
	public MindMapGraphController(MindMapModel model, MindMapGraphView view) {
		this.model = model;
		this.view = view;
		model.tree.addObserver(this);
	}
	
	public Box getView() {
		//TODO: how to put graph view in center of the screen.
		Box box = new Box(BoxLayout.Y_AXIS);
		box.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		box.add(Box.createVerticalGlue());
		box.add(view);     
		box.add(Box.createVerticalGlue());
		return box;
	}
	
	public void drawGraph() {
		System.out.println("pane size is" + view.getPreferredSize());
		makeNodeView(model.tree.getRoot(), view.getPreferredSize().width/2, view.getPreferredSize().height/2, Math.PI*2, new MindMapVector(0, -1));
		view.repaint();
		view.revalidate();
	}
	
	private void makeNodeView(MindMapNode node, int centerX, int centerY, double availableAngle, MindMapVector direction) {
		// make node first
		// TODO: we might need more better algorithm here. 
		// for example, angle decision.
		int numberOfChildren = 0;
		double theta = 0;
		double distance = 0;
		
		// make NodeView
		System.out.println(node.getName() + " is making...");
		node.setX(centerX - MindMapNodeView.MIN_SIZE);
		node.setY(centerY - MindMapNodeView.MIN_SIZE);
		MindMapNodeView nodeView = new MindMapNodeView(node);
		System.out.println("size is" + nodeView.preferredSize());
		node.setWidth(nodeView.getPreferredSize().width);
		node.setHeight(nodeView.getPreferredSize().height);
		view.addNode(nodeView);
				
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

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		view.clearNodes();
		view.removeAll();
		drawGraph();
	}
}











