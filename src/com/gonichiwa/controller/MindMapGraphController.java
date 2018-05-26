package com.gonichiwa.controller;

import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.model.MindMapNode;
import com.gonichiwa.view.MindMapGraphView;
import com.gonichiwa.view.MindMapNodeView;

import com.gonichiwa.util.MindMapVector;

public class MindMapGraphController {
	private MindMapModel model;
	private MindMapGraphView view;
	
	public MindMapGraphController(MindMapModel model) {
		this(model, new MindMapGraphView(model, 400, 400));
	}
	
	public MindMapGraphController(MindMapModel model, MindMapGraphView view) {
		this.model = model;
		this.view = view;
	}
	
	public MindMapGraphView getView() {
		return view;
	}
	
}











