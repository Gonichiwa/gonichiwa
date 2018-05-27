package com.gonichiwa.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.view.MindMapMenuBar;
import com.gonichiwa.view.MindMapToolBar;

public class MindMapMenuController {

	private MenuActionListener actionListener;
	
	private MindMapMenuBar menuBar;
	private MindMapToolBar toolBar;
	private MindMapModel model;
	
	public MindMapMenuController(MindMapModel model,MindMapMenuBar menuBar,MindMapToolBar  toolBar){
		
		this.menuBar = menuBar;
		this.toolBar = toolBar;
		this.model = model;
		
		
		menuBar.addNewwListener(getActionListener());
		menuBar.addOpenListener(getActionListener());
		menuBar.addCloseListener(getActionListener());
		
	}
	
	public MindMapMenuController(MindMapModel model){
		this(model,new MindMapMenuBar(),new MindMapToolBar());
	}
	
	public MindMapMenuBar getMenuBar() {
		return menuBar;
	}
	public MindMapToolBar getToolBar() {
		return toolBar;
	}
	public void addApplyListener(ActionListener l) {
		menuBar.addApplyListener(l);
		toolBar.addApplyListener(l);
	}
	public void addChangeListener(ActionListener l) {
		menuBar.addChangeListener(l);
		toolBar.addChangeListener(l);
	}
	public void addDeleteListener(ActionListener l) {
		menuBar.addDeleteListener(l);
	}
	
	//make action listener later;
	
	public MenuActionListener getActionListener() {
		return actionListener;
	}
	class MenuActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	class newwActionListener implements ActionListener{
				
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	class openActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	class saveActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	class saveasActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	class closeActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	class exportActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	class undoActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	class redoActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	
}
