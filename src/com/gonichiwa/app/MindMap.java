package com.gonichiwa.app;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import com.gonichiwa.controller.MindMapAttributeController;
import com.gonichiwa.controller.MindMapGraphController;
import com.gonichiwa.controller.MindMapMenuController;
import com.gonichiwa.controller.MindMapTextAreaController;
import com.gonichiwa.model.MindMapModel;

/**
 * MindMap class
 * 
 * Main Application class. it has four controllers and a model.
 * 
 * textAreaController is controlling TextAreaPane on the mindmap 
 * which is in charge of building new MindMap with given text from user.
 * 
 * attributeController is controlling AttributePane on the mindmap
 * which is in charge of modifying attributes of the selected node in GraphPane.
 * 
 * graphController is controlling GraphPane on the mindmap
 * which is in charge of displaying mindmap and interacting with user.
 * 
 * menuController is controlling Menu and Tool bar on the mindmap
 * which is in charge of offering user entire functions in mindmap.
 * 
 * @author YONG_JOON_KIM
 *
 */
public class MindMap extends JFrame {
	
	MindMapModel model;

	MindMapTextAreaController textAreaController;
	MindMapAttributeController attributeController;
	MindMapGraphController graphController;
	MindMapMenuController menuController;
	
	JSplitPane mainSplitPane;
	JSplitPane graphSplitPane;

	public MindMap() {
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		model = new MindMapModel();
		
		textAreaController = new MindMapTextAreaController(model);
		attributeController = new MindMapAttributeController(model);
		graphController = new MindMapGraphController(model, attributeController.getView());
		menuController = new MindMapMenuController(model, textAreaController, graphController, attributeController);

		mainSplitPane = new JSplitPane();
		graphSplitPane = new JSplitPane();
		mainSplitPane.setResizeWeight(0.2);
		graphSplitPane.setResizeWeight(0.9);
		graphSplitPane.setLeftComponent(graphController.getView());
		graphSplitPane.setRightComponent(attributeController.getView());
		mainSplitPane.setLeftComponent(textAreaController.getView());
		mainSplitPane.setRightComponent(graphSplitPane);
				
		add(mainSplitPane, BorderLayout.CENTER);
		add(menuController.getToolBar(), BorderLayout.NORTH);
		setJMenuBar(menuController.getMenuBar());

		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MindMap();
	}

}
