package com.gonichiwa.controller;

import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.view.MindMapGraphView;

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
}
