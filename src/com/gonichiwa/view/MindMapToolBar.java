package com.gonichiwa.view;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MindMapToolBar{

	JToolBar toolBar = new JToolBar();
	public JToolBar getToolBar() {
		return toolBar;
	}
	public void createToolBar() {
	
		toolBar.setFloatable(false);
		toolBar.setBackground(Color.LIGHT_GRAY);
		
		ImageIcon icon;
		JButton[] btItem= new JButton[12];
		String[] btName = {"New","Open", "Save", "Save As..", "Close", "Apply text pane", "Apply attribute pane", "Zoom In", "Zoom Out","Fit Map", "Undo", "Redo"};
		String[] btImages = {""}; //Image Directory
		ToolBarActionListener toolBarListener = new ToolBarActionListener();
		for(int i=0;i<btItem.length;i++) {
			btItem[i] = new JButton("BT");
			btItem[i].setToolTipText(btName[i]);
			btItem[i].addActionListener(toolBarListener);
			toolBar.add(btItem[i]);
		}
		
		ToolTipManager manageTool = ToolTipManager.sharedInstance();
		manageTool.setInitialDelay(0);

		
		//contentPane.add(toolBar,BorderLayout.NORTH);
	}
	
	class ToolBarActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			System.out.println(cmd);
		}
	}
}
