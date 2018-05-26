package com.gonichiwa.view;

import javax.swing.*;
import java.awt.event.*;
import java.util.EventListener;
import java.util.HashMap;
import java.awt.*;
import javax.swing.filechooser.*;


public class MindMapMenuBar {

	private MenuItem apply, change;
	
	private HashMap<String, JMenuItem> itemDict;
	
	
	public JMenuBar menuBar = new JMenuBar();
	public JMenuBar getMenu() {
		return menuBar;
	}
	public void createMenu() {
		
		
		//FileMenu
		JMenu fileMenu = new JMenu("File");
		JMenuItem[] fileItem = new JMenuItem[6];
		String[] fileItemTitle = {"New", "Open", "Save", "Save As..", "Close", "Export To.."};
		for(int i=0;i<fileItem.length;i++) {
			fileItem[i] = new JMenuItem(fileItemTitle[i]);
			itemDict.put(fileItemTitle[i], fileItem[i]);
//			fileItem[i].addActionListener(fileListener);
			fileMenu.add(fileItem[i]);
		}
		//EditMenu
		JMenu editMenu = new JMenu("Edit");
		JMenuItem[] editItem = new JMenuItem[5];
		String[] editItemTitle = { "Undo", "Redo", "Delete", "Apply", "Change"};
		for(int i=0;i<editItem.length;i++) {
			editItem[i] = new JMenuItem(editItemTitle[i]);
			editMenu.add(editItem[i]);
		}
		//ViewMenu
		JMenu viewMenu = new JMenu("View");
		JMenuItem[] viewItem = new JMenuItem[5];
		String[] viewItemTitle = {"Zoom In", "Zoom Out", "Fit Map", "Hide Editor Pane", "Hide Attribute Pane"};
		for(int i=0;i<viewItem.length;i++) {
			viewItem[i] = new JMenuItem(viewItemTitle[i]);
			viewMenu.add(viewItem[i]);
		}
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(viewMenu);
		
		/*
		setJMenuBar(menuBar);
		*/
	}

	public void addListenerToItem(String name, ActionListener l) {
		if(itemDict.containsKey(name))
			itemDict.get(name).addActionListener(l);
	}
	
}
