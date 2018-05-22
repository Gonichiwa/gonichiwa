package com.gonichiwa.app;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import com.gonichiwa.controller.MindMapAttributeController;
import com.gonichiwa.controller.MindMapGraphController;
import com.gonichiwa.controller.MindMapTextAreaController;
import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.view.MindMapMenuBar;
import com.gonichiwa.view.MindMapToolBar;

public class MindMap extends JFrame implements Runnable {
	MindMapModel model;
	MindMapTextAreaController textAreaController;
	MindMapAttributeController attributeController;
	MindMapGraphController graphController;
//	MindMapMenuBar menuBar;
//	MindMapToolBar toolBar;
	
	JSplitPane centerPane;
	JSplitPane graphPane;
	
	
	public MindMap() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		model = new MindMapModel();
		textAreaController = new MindMapTextAreaController(model);
		attributeController = new MindMapAttributeController(model);
		graphController = new MindMapGraphController(model);
		
//		menuBar = new MindMapMenuBar();
//		toolBar = new MindMapToolBar();
		
//		menuBar.addApplyListener(textAreaController.getLister());
//		toolBar.addApplyListener(textAreaController.getListener());
		
//		menuBar.addChangeListener(attributeController.getListener());
//		toolBar.addChangeListener(attributeController.getListener());
		
		centerPane = new JSplitPane();
		graphPane = new JSplitPane();
		
		graphPane.setLeftComponent(graphController.getView());
		graphPane.setRightComponent(attributeController.getView());
		centerPane.setLeftComponent(textAreaController.getView());
		centerPane.setRightComponent(graphPane);
		
		add(centerPane);
		setSize(800, 600);
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
