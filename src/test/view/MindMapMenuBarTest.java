package test.view;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import com.gonichiwa.view.MindMapMenuBar;
import com.gonichiwa.view.MindMapToolBar;

public class MindMapMenuBarTest extends JFrame {

	public MindMapMenuBarTest() {
	
		//MenuBar
		MindMapMenuBar menuTest = new MindMapMenuBar();
		menuTest.createMenu();
		setJMenuBar(menuTest.getMenu());
		//ToolBar
		Container contentPane = getContentPane();
		MindMapToolBar toolTest = new MindMapToolBar();
		toolTest.createToolBar();
		contentPane.add(toolTest.getToolBar(),BorderLayout.NORTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,800);
		setVisible(true);	
	}
	
	public static void main(String[] args) {
		new MindMapMenuBarTest();
	}

}
