package test.controller;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.gonichiwa.controller.MindMapGraphController;
import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.view.MindMapGraphView;

public class MindMapGraphControllerTest extends JFrame {
	MindMapModel model;
	MindMapGraphView view;
	MindMapGraphController controller; 
	
	final int INITIAL_WIDTH = 400;
	final int INITIAL_HEIGHT = 400;
	
	public MindMapGraphControllerTest() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		model = new MindMapModel();
		view = new MindMapGraphView(model, INITIAL_WIDTH, INITIAL_HEIGHT);
		controller = new MindMapGraphController(model, view);
		this.add(view);
		this.setSize(400, 400);
		this.setVisible(true);

		
		model.tree.buildTree("hi\n\tnice\n");
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MindMapGraphControllerTest();
	}

}
