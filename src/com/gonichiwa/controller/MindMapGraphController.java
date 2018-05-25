package com.gonichiwa.controller;

import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.model.MindMapNode;
import com.gonichiwa.view.MindMapGraphView;
import com.gonichiwa.view.MindMapNodeView;

import com.gonichiwa.util.MindMapVector;

public class MindMapGraphController {
	private MindMapModel model;
	private MindMapGraphView view;
	
	public MindMapGraphController(MindMapModel model) {
		this(model, new MindMapGraphView());
	}
	
	public MindMapGraphController(MindMapModel model, MindMapGraphView view) {
		this.model = model;
		this.view = view;
	}
	
	public MindMapGraphView getView() {
		return view;
	}
	
	public void drawGraph() {
		makeNodeView(model.tree.getRoot(), view.getWidth()/2, view.getHeight()/2, Math.PI*2, new MindMapVector(0, -1));
	}
	
	private void makeNodeView(MindMapNode node, int centerX, int centerY, double availableAngle, MindMapVector direction) {
		// make node first
		System.out.println(node.getName() + "making");
		if(node == null)
			return;
		node.setX(centerX - MindMapNodeView.MIN_SIZE);
		node.setY(centerY - MindMapNodeView.MIN_SIZE);
		node.setWidth(MindMapNodeView.MIN_SIZE);	// how to get label size.
		node.setHeight(MindMapNodeView.MIN_SIZE);	// how to get label size.
		view.addNode(new MindMapNodeView(node));
		
		// calculate children
		int numberOfChildren = node.getChildren().size();
		double theta = availableAngle / numberOfChildren;
//		double distance = MindMapNodeView.MIN_SIZE/Math.sin(theta) + 10; // 10 is debug offset.
		double distance = 100;
		direction.normalize();
		direction.mult(distance);
		for(MindMapNode child : node.getChildren()) {
			System.out.println(child.getName());
			makeNodeView(child,
					centerX+(int)direction.getX(),
					centerY+(int)direction.getY(),
					theta, 
					direction.copy().normalize().rotate(-theta/2));
			direction.rotate(theta);
			System.out.println("x is "+direction.getX() + " y is " + direction.getY());
		}
	}
}











