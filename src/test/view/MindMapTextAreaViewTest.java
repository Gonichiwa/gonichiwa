package test.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.view.MindMapTextAreaView;

public class MindMapTextAreaViewTest extends JFrame {

	MindMapTextAreaView view;
	String resultText;
	MindMapModel model = new MindMapModel();
	
	public MindMapTextAreaViewTest() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		initSubView();
		view.addApplyButtonListener(new ApplyButtonListener());
		
		this.add(view);
		
		this.setSize(800, 600);
		this.setVisible(true);
	}
	
	public void initSubView() {
		view = new MindMapTextAreaView(model);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MindMapTextAreaViewTest();
	}
	
	/*
	 * must add to controller
	 */
	class ApplyButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			resultText = view.getText();
			try {
				model.tree.buildTree(resultText);
			} catch(IllegalArgumentException modelerr) {
				System.out.println(modelerr.getMessage());
			}
			System.out.println(model.getTreeSize());
		}
		
	}

}
