package test.controller;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.gonichiwa.controller.MindMapAttributeController;
import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.view.MindMapAttributeView;

public class ListenerAsInstanceVarTest extends JFrame {
	MindMapModel model;
	MindMapAttributeView view;
	MindMapAttributeController controller;
	
	public ListenerAsInstanceVarTest() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		model = new MindMapModel();
		view = new MindMapAttributeView();
		controller = new MindMapAttributeController(model, view);
		JButton testButton = new JButton("test");
		
		model.buildNewTree("hi\n\tkim");		// build small tree
		model.setNodeColor(1, 255, 255, 0);		// modify one node here
		model.setNodeLocation(1, 120, 120);		// modify one node here
		model.setNodeSize(1, 50, 50);			// modify one node here
		controller.setNode(model.tree.getNode(1));		// set the node in attributeView
		testButton.addActionListener(controller.getListener());	// you can pass the listener!!
		
		this.add(view, BorderLayout.CENTER);
		this.add(testButton, BorderLayout.WEST);
		setSize(500, 500);
		setVisible(true);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ListenerAsInstanceVarTest();
	}

}
