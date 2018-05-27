package com.gonichiwa.view;

import javax.swing.*;
import java.awt.event.*;
import java.util.EventListener;
import java.util.HashMap;
import java.awt.*;
import javax.swing.filechooser.*;


public class MindMapMenuBar {

	
	
	
	private JMenuItem neww,open,save,saveas,close,export,undo,redo,delete,apply,change;
	private JMenuItem zoomin,zoomout,fitmap,hideeditorpane,hideattributepane;
	
	public JMenuBar menuBar = new JMenuBar();
	public JMenuBar getMenu() {
		return menuBar;
	}
	public MindMapMenuBar() {
		createMenu();
	}
	
	//filemenu
	public void addNewwListener(ActionListener l) {
		neww.addActionListener(l);
	}
	public void addOpenListener(ActionListener l) {
		open.addActionListener(l);
	}
	public void addSaveListener(ActionListener l) {
		save.addActionListener(l);
	}
	public void addSaveasListener(ActionListener l) {
		saveas.addActionListener(l);
	}
	public void addCloseListener(ActionListener l) {
		close.addActionListener(l);
	}
	public void addExportListener(ActionListener l) {
		export.addActionListener(l);
	}
	//editmenu
	public void addUndoListener(ActionListener l) {
		undo.addActionListener(l);
	}public void addRedoListener(ActionListener l) {
		redo.addActionListener(l);
	}
	public void addDeleteListener(ActionListener l) {
		delete.addActionListener(l);
	}
	public void addApplyListener(ActionListener l) {
		apply.addActionListener(l);	
	}
	public void addChangeListener(ActionListener l) {
		change.addActionListener(l);
	}
	//viewmenu
	public void addZoominListener(ActionListener l) {
		zoomin.addActionListener(l);
	}
	public void addZoomoutListener(ActionListener l) {
		zoomout.addActionListener(l);
	}
	public void addFitmapListener(ActionListener l) {
		fitmap.addActionListener(l);
	}
	public void addHideEditorPaneListener(ActionListener l) {
		hideeditorpane.addActionListener(l);
	}
	public void addHideAttributePaneListener(ActionListener l) {
		hideattributepane.addActionListener(l);
	}
	
	public void createMenu() {
		
		
		//FileMenu
		JMenu fileMenu = new JMenu("File");
		String[] fileItemTitle = {"New", "Open", "Save", "Save As..", "Close", "Export To.."};


		neww = new JMenuItem(fileItemTitle[0]);
		open = new JMenuItem(fileItemTitle[1]);
		save = new JMenuItem(fileItemTitle[2]);
		saveas = new JMenuItem(fileItemTitle[3]);
		close = new JMenuItem(fileItemTitle[4]);
		export = new JMenuItem(fileItemTitle[5]);
	
		fileMenu.add(neww);
		fileMenu.add(open);
		fileMenu.add(save);
		fileMenu.add(saveas);
		fileMenu.add(close);
		fileMenu.add(export);
		
		//EditMenu
		JMenu editMenu = new JMenu("Edit");
		JMenuItem[] editItem = new JMenuItem[5];
		String[] editItemTitle = { "Undo", "Redo", "Delete", "Apply", "Change"};


		undo = new JMenuItem(editItemTitle[0]);
		redo = new JMenuItem(editItemTitle[1]);
		delete = new JMenuItem(editItemTitle[2]);
		apply = new JMenuItem(editItemTitle[3]);
		change = new JMenuItem(editItemTitle[4]);
		
		editMenu.add(undo);
		editMenu.add(redo);
		editMenu.add(delete);
		editMenu.add(apply);
		editMenu.add(change);
		
		//ViewMenu
		JMenu viewMenu = new JMenu("View");
		JMenuItem[] viewItem = new JMenuItem[5];
		String[] viewItemTitle = {"Zoom In", "Zoom Out", "Fit Map", "Hide Editor Pane", "Hide Attribute Pane"};

		
		zoomin = new JMenuItem(viewItemTitle[0]);
		zoomout = new JMenuItem(viewItemTitle[1]);
		fitmap = new JMenuItem(viewItemTitle[2]);
		hideeditorpane = new JMenuItem(viewItemTitle[3]);
		hideattributepane = new JMenuItem(viewItemTitle[4]);

		viewMenu.add(zoomin);
		viewMenu.add(zoomout);
		viewMenu.add(fitmap);
		viewMenu.add(hideeditorpane);
		viewMenu.add(hideattributepane);
		
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(viewMenu);
		
		/*
		setJMenuBar(menuBar);
		*/
	}
}
