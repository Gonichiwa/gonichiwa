package com.gonichiwa.view;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.filechooser.*;


public class MindMapMenuBar {

	public JMenuBar menuBar = new JMenuBar();
	public JMenuBar getMenu() {
		return menuBar;
	}
	public void createMenu() {
		
		
		//FileMenu
		JMenu fileMenu = new JMenu("File");
		JMenuItem[] fileItem = new JMenuItem[6];
		String[] fileItemTitle = {"New", "Open", "Save", "Save As..", "Close", "Export To.."};
		FileActionListener fileListener = new FileActionListener();
		for(int i=0;i<fileItem.length;i++) {
			fileItem[i] = new JMenuItem(fileItemTitle[i]);
			fileItem[i].addActionListener(fileListener);
			fileMenu.add(fileItem[i]);
		}
		//EditMenu
		JMenu editMenu = new JMenu("Edit");
		JMenuItem[] editItem = new JMenuItem[5];
		String[] editItemTitle = { "Undo", "Redo", "Delete", "Apply", "Change"};
		EditActionListener editListener = new EditActionListener();
		for(int i=0;i<editItem.length;i++) {
			editItem[i] = new JMenuItem(editItemTitle[i]);
			editItem[i].addActionListener(editListener);
			editMenu.add(editItem[i]);
		}
		//ViewMenu
		JMenu viewMenu = new JMenu("View");
		JMenuItem[] viewItem = new JMenuItem[5];
		String[] viewItemTitle = {"Zoom In", "Zoom Out", "Fit Map", "Hide Editor Pane", "Hide Attribute Pane"};
		ViewActionListener viewListener = new ViewActionListener();
		for(int i=0;i<viewItem.length;i++) {
			viewItem[i] = new JMenuItem(viewItemTitle[i]);
			viewItem[i].addActionListener(viewListener);
			viewMenu.add(viewItem[i]);
		}
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(viewMenu);
		
		/*
		setJMenuBar(menuBar);
		*/
	}
	
	//ActionListner
	class FileActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			System.out.println(cmd);//test
			
			switch(cmd) {
			case "New":
				break;
			case "Open":
				//FileSelector
				JFileChooser fileSelect = new JFileChooser();
				FileNameExtensionFilter fileFilter = new FileNameExtensionFilter(".json",".json");
				fileSelect.setFileFilter(fileFilter);
				int ret = fileSelect.showOpenDialog(null);
				if(ret != JFileChooser.APPROVE_OPTION) {
					return;
				}
				String filePath = fileSelect.getSelectedFile().getPath();
				break;
			case "Save":
				break;
			case "Save As..":
				break;
			case "Close":
				break;
			case "Export To..":
				break;
			}
		}
	}
	class EditActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			System.out.println(cmd);//test
			
			switch(cmd) {
			case "Undo":
				break;
			case "Redo":
				break;
			case "Delete":
				break;
			case "Apply":
				break;
			case "Change":
				break;
			}
		}
	}
	class ViewActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			System.out.println(cmd);//test
			
			switch(cmd) {
			case "Zoom In":
				break;
			case "Zoom Out":
				break;
			case "Fit Map":
				break;
			case "Hide Editor Pane":
				break;
			case "Hide Attribute Pane":
				break;
			}
		}
	}
	
}
