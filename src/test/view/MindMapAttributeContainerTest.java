package test.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.gonichiwa.view.MindMapAttributeContainer;

public class MindMapAttributeContainerTest extends JFrame {

	MindMapAttributeContainer attributeContainer1, attributeContainer2;
	JScrollPane scrollPane1, scrollPane2;
	
	public MindMapAttributeContainerTest() {
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		

		attributeContainer1 = new MindMapAttributeContainer(new String[]{"NAME", "X", "Y", "WIDTH", "HEIGHT", "COLOR"});
		attributeContainer1.setEditable("NAME", false);
		scrollPane1 = new JScrollPane(attributeContainer1);
		
		attributeContainer1.setValue("X", "25");	 	// setValue test with String
		
//		attributeContainer1.setValue("abc", 255);  -> throw NoSuchElementException
		
		attributeContainer2 = new MindMapAttributeContainer();
		attributeContainer2.addAttributePanel("name");
		attributeContainer2.addAttributePanel("name");

		attributeContainer2.addAttributePanel("x");
		attributeContainer2.addAttributePanel("t");
		attributeContainer2.addAttributePanel("a");
		attributeContainer2.addAttributePanel("b");

		scrollPane2 = new JScrollPane(attributeContainer2);
		
		this.add(scrollPane1, BorderLayout.EAST);
		this.add(scrollPane2, BorderLayout.CENTER);
		this.setSize(400, 400);
		this.pack();
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MindMapAttributeContainerTest();
	}

}
