package com.gonichiwa.controller;

import com.gonichiwa.mindmapinterface.NodeDataDeliver;

import com.gonichiwa.model.MindMapModel;
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
		makeNodeView(model.tree.getRoot(), view.getWidth()/2, view.getHeight()/2, Math.PI*2, new MindMapVector(0, 1));
	}
	
	private void makeNodeView(NodeDataDeliver node, int centerX, int centerY, double availableAngle, MindMapVector baseVector) {
		// need to add a coordinate system.
		int numberOfChildren = node.getChildren().size();
		double angle = availableAngle / numberOfChildren;
		
		view.addNode(new MindMapNodeView(node));
		for(NodeDataDeliver child : node.getChildren()) {
			baseVector.rotate(angle);
			makeNodeView(child, 0, 0, angle, baseVector.copy());
		}
	}
}
