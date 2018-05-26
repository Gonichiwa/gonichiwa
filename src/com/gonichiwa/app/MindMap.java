package com.gonichiwa.app;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import com.gonichiwa.controller.MindMapAttributeController;
import com.gonichiwa.controller.MindMapGraphController;
import com.gonichiwa.controller.MindMapTextAreaController;
import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.view.MindMapGraphView;
import com.gonichiwa.view.MindMapMenuBar;
import com.gonichiwa.view.MindMapToolBar;

public class MindMap extends JFrame implements Runnable {
	MindMapModel model;
	
	MindMapGraphView graphView;
	
	
	MindMapTextAreaController textAreaController;
	MindMapAttributeController attributeController;
	MindMapGraphController graphController;
//	MindMapMenuBar menuBar;
//	MindMapToolBar toolBar;
	// MindMapUtilController utilController;
	
	JSplitPane centerPane;
	JSplitPane graphPane;

	private static final int GRAPH_WIDTH = 600;
	private static final int GRAPH_HEIGHT = 600;
	
	public MindMap() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		model = new MindMapModel();
		
		graphView = new MindMapGraphView(model, GRAPH_WIDTH, GRAPH_HEIGHT);
		textAreaController = new MindMapTextAreaController(model);
		attributeController = new MindMapAttributeController(model);
		graphController = new MindMapGraphController(model, graphView, attributeController.getView());
		//utilController = new MindMapUtilController(model);

//		menuBar = new MindMapMenuBar();
//		toolBar = new MindMapToolBar();
		
		// utilController.addApplyListener(textAreaControoler.getListener());
		
//		menuBar.addApplyListener(textAreaController.getLister());
//		toolBar.addApplyListener(textAreaController.getListener());
		
//		menuBar.addChangeListener(attributeController.getListener());
//		toolBar.addChangeListener(attributeController.getListener());
		
		centerPane = new JSplitPane();
		graphPane = new JSplitPane();
		
		centerPane.setResizeWeight(0.2);
		graphPane.setResizeWeight(0.9);
		graphPane.setLeftComponent(graphController.getView());
		graphPane.setRightComponent(attributeController.getView());
		centerPane.setLeftComponent(textAreaController.getView());
		centerPane.setRightComponent(graphPane);
		
		add(centerPane, BorderLayout.CENTER);
		//setMenuBar(utilController.getMenubar());
		//add(utilController.getToolBar(), BorderLayout.NORTH);
//		setSize(800, 600);      may be we don't need this.
		pack();
		setVisible(true);
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MindMap().run();
	}

}
