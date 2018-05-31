package test.view;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import com.gonichiwa.controller.MindMapMenuController;
import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.view.MindMapMenuBar;
import com.gonichiwa.view.MindMapToolBar;

public class MindMapMenuBarTest extends JFrame {

	private MindMapModel model;
	
	public MindMapMenuBarTest() {
		//MenuBar
		MindMapMenuBar menuTest = new MindMapMenuBar();
		setJMenuBar(menuTest.getMenu());
		//ToolBar
		Container contentPane = getContentPane();
		MindMapToolBar toolTest = new MindMapToolBar();
		contentPane.add(toolTest.getToolBar(),BorderLayout.NORTH);
		
		MindMapMenuController ctrl = new MindMapMenuController(model,menuTest,toolTest);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,800);
		setVisible(true);	
	}
	
	public static void main(String[] args) {
		new MindMapMenuBarTest();
	}

}
