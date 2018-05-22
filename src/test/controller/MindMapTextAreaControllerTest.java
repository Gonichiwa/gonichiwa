package test.controller;

import javax.swing.JFrame;

import com.gonichiwa.controller.MindMapTextAreaController;
import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.view.MindMapTextAreaView;

public class MindMapTextAreaControllerTest extends JFrame {
	
	MindMapModel model;
	MindMapTextAreaView view;
	MindMapTextAreaController controller;
	
	public MindMapTextAreaControllerTest() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		model = new MindMapModel();
		view = new MindMapTextAreaView();
		controller = new MindMapTextAreaController(model, view);
		
		// set model and test update method in controller.
		model.buildNewTree("kim\n\tjoon\n\thello\n\t\thi");   
		
		this.add(view);
		
		this.setSize(400, 600);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MindMapTextAreaControllerTest();
	}

}
