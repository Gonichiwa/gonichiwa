package test.controller;

import javax.swing.JFrame;

import com.gonichiwa.controller.MindMapAttributeController;
import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.view.MindMapAttributeView;


public class MindMapAttributeControllerTest extends JFrame {
	MindMapModel model;
	MindMapAttributeView view;
	MindMapAttributeController controller;
	
	public MindMapAttributeControllerTest() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		model = new MindMapModel();
		view = new MindMapAttributeView();
		controller = new MindMapAttributeController(model, view);
		
		model.buildNewTree("hi\n\tkim");		// build small tree
		model.setNodeColor(1, 255, 255, 0);		// modify one node here
		model.setNodeLocation(1, 120, 120);		// modify one node here
		model.setNodeSize(1, 50, 50);			// modify one node here
		controller.setNode(model.tree.getNode(1));		// set the node in attributeView
		
		this.add(view);
		this.setSize(400, 600);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new MindMapAttributeControllerTest();
	}
}
