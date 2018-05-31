package com.gonichiwa.view;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MindMapToolBar{

	private JButton neww,open,save,saveas,close,apply,change,zoomin,zoomout,fitmap,undo,redo;
	
	JToolBar toolBar = new JToolBar();
	public JToolBar getToolBar() {
		return toolBar;
	}
	public MindMapToolBar() {
		createToolBar();
	}
	
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
	public void addApplyListener(ActionListener l) {
		apply.addActionListener(l);
	}
	public void addChangeListener(ActionListener l) {
		change.addActionListener(l);
	}
	public void addZoominListener(ActionListener l) {
		zoomin.addActionListener(l);
	}
	public void addZoomoutListener(ActionListener l) {
		zoomout.addActionListener(l);
	}
	public void addFitmapListener(ActionListener l) {
		fitmap.addActionListener(l);
	}
	public void addUndoListener(ActionListener l) {
		undo.addActionListener(l);
	}
	public void addRedoListener(ActionListener l) {
		redo.addActionListener(l);
	}
	
	public void createToolBar() {
	
		toolBar.setFloatable(false);
		toolBar.setBackground(Color.LIGHT_GRAY);
		
		ImageIcon icon;
		JButton[] btItem= new JButton[12];
		String[] btName = {"New","Open", "Save", "Save As..", "Close", "Apply", "Change", "Zoom In", "Zoom Out","Fit Map", "Undo", "Redo"};
		String[] btImages = {"images/dummy.png"}; //Image Directory

		neww = new JButton(new ImageIcon("images/setsize/newicon.png"));
		open = new JButton(new ImageIcon("images/setsize/openicon.png"));
		save = new JButton(new ImageIcon("images/setsize/saveicon.png"));
		saveas = new JButton(new ImageIcon("images/setsize/saveasicon.png"));
		close= new JButton(new ImageIcon("images/setsize/exiticon.png"));
		apply = new JButton(new ImageIcon("images/setsize/applyicon.png"));
		change = new JButton(new ImageIcon("images/setsize/changeicon.png"));
		zoomin = new JButton(new ImageIcon(btImages[0]));
		zoomout = new JButton(new ImageIcon(btImages[0]));
		fitmap = new JButton(new ImageIcon(btImages[0]));
		undo = new JButton(new ImageIcon(btImages[0]));
		redo = new JButton(new ImageIcon(btImages[0]));
		
		neww.setToolTipText(btName[0]);
		open.setToolTipText(btName[1]);
		save.setToolTipText(btName[2]);
		saveas.setToolTipText(btName[3]);
		close.setToolTipText(btName[4]);
		apply.setToolTipText(btName[5]);
		change.setToolTipText(btName[6]);
		zoomin.setToolTipText(btName[7]);
		zoomout.setToolTipText(btName[8]);
		fitmap.setToolTipText(btName[9]);
		undo.setToolTipText(btName[10]);
		redo.setToolTipText(btName[11]);
		
		toolBar.add(neww);
		toolBar.add(open);
		toolBar.add(save);
		toolBar.add(saveas);
		toolBar.add(close);
		toolBar.add(apply);
		toolBar.add(change);
		toolBar.add(zoomin);
		toolBar.add(zoomout);
		toolBar.add(fitmap);
		toolBar.add(undo);
		toolBar.add(redo);
		
		
		ToolTipManager manageTool = ToolTipManager.sharedInstance();
		manageTool.setInitialDelay(0);

		
		//contentPane.add(toolBar,BorderLayout.NORTH);
	}

}
