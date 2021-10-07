package test.view;

import javax.swing.JFrame;

import com.gonichiwa.view.MindMapGraphScalerView;

public class MindMapGraphScalerViewTest extends JFrame {

	MindMapGraphScalerView view;
	
	public MindMapGraphScalerViewTest() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		view = new MindMapGraphScalerView();
		
		this.add(view);
		this.setSize(200, 20);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MindMapGraphScalerViewTest();
	}

}
