package test.view;

import javax.swing.JFrame;

import com.gonichiwa.mindmapinterface.NodeDataDeliver;
import com.gonichiwa.view.MindMapAttributeView;

import test.view.MindMapTextAreaViewTest.ApplyButtonListener;

public class MindMapAttributeViewTest extends JFrame {

	MindMapAttributeView view;
	NodeDataDeliver node;
	
	public MindMapAttributeViewTest() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		view = new MindMapAttributeView();
	
		add(view);
		
		setSize(400, 400);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MindMapAttributeViewTest();
	}
	
	void initSubView() {
		
	}

}
