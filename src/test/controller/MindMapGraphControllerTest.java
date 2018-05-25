package test.controller;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.gonichiwa.controller.MindMapGraphController;
import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.view.MindMapGraphView;

public class MindMapGraphControllerTest extends JFrame {
	MindMapModel model;
	MindMapGraphView view;
	MindMapGraphController controller; 
	
	public MindMapGraphControllerTest() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		model = new MindMapModel();
		view = new MindMapGraphView();
		controller = new MindMapGraphController(model, view);
		model.buildNewTree("hi\n\tkim\n");
		controller.drawGraph();
		
		JButton button = new JButton("test");
		button.setLocation(10, 10);
		view.add(button);

		System.out.println(view.getComponentCount());
		this.add(view);
		
		this.setSize(400, 400);
		this.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MindMapGraphControllerTest();
	}

}
