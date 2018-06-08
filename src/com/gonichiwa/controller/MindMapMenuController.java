package com.gonichiwa.controller;

import java.awt.Color;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.*;

import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.view.MindMapMenuBar;
import com.gonichiwa.view.MindMapToolBar;

import javafx.scene.control.ToolBar;
import javafx.stage.FileChooser;

public class MindMapMenuController {

	private MindMapMenuBar menuBar;
	private MindMapToolBar toolBar;
	private MindMapModel model;
	private MindMapGraphController graphController;
	private MindMapAttributeController attributeController;
	private MindMapTextAreaController textAreaController;
	
	private JFileChooser fileChooser;
	
	public MindMapMenuController(MindMapModel model, 
			MindMapTextAreaController textAreaController, 
			MindMapGraphController graphController,
			MindMapAttributeController attributeController) {
		
		this.model = model;

		Path currentRelativePath = Paths.get("save");
		
		menuBar = new MindMapMenuBar();
		toolBar = new MindMapToolBar();
		this.textAreaController = textAreaController;
		this.graphController = graphController;
		this.attributeController = attributeController;
		
		fileChooser = new JFileChooser(currentRelativePath.toFile());

		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(".json", "json"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		menuBar.addNewwListener(new newwActionListener());
		menuBar.addOpenListener(new openActionListener());
		menuBar.addSaveListener(new saveActionListener());
		menuBar.addSaveasListener(new saveasActionListener());
		menuBar.addCloseListener(new closeActionListener());
		menuBar.addApplyListener(textAreaController.getListener());
		menuBar.addChangeListener(attributeController.getListener());
		
		toolBar.addApplyListener(textAreaController.getListener());
		toolBar.addNewwListener(new newwActionListener());
		toolBar.addOpenListener(new openActionListener());
		toolBar.addSaveListener(new saveActionListener());
		toolBar.addSaveasListener(new saveasActionListener());
		toolBar.addCloseListener(new closeActionListener());
		toolBar.addChangeListener(attributeController.getListener());
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
	//make action listener later;
	// TODO: unify new, save, save as to one Listener Class. because there are too many copy and paste code.
	class newwActionListener implements ActionListener{
				
		public void actionPerformed(ActionEvent e) {
			
				int saveCheck = JOptionPane.showConfirmDialog(null, "Do you want to save?", "New", JOptionPane.YES_NO_CANCEL_OPTION);
				
				if(saveCheck == JOptionPane.YES_OPTION) {
					if(model.isSaved()) {
						model.save();
					} else {
						int returnValue = fileChooser.showSaveDialog(null);
						if(returnValue == JFileChooser.APPROVE_OPTION) {
							System.out.println(fileChooser.getFileFilter());
							model.save(fileChooser.getSelectedFile().getPath() + fileChooser.getFileFilter().getDescription());
						}
					}
				}
				else if(saveCheck == JOptionPane.NO_OPTION) {
					
				}
				else {
					return;
				}
				
				System.out.println("New");
				model.reset();
			
		}
	}
	class openActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {

			
			int saveCheck = JOptionPane.showConfirmDialog(null, "Do you want to save?","Open",JOptionPane.YES_NO_CANCEL_OPTION);
			
			if(saveCheck == JOptionPane.YES_OPTION) {
				if(model.isSaved()) {
					model.save(); 
				} else {
					int returnValue = fileChooser.showSaveDialog(null);
					if(returnValue == JFileChooser.APPROVE_OPTION) {
						System.out.println(fileChooser.getFileFilter());
						model.save(fileChooser.getSelectedFile().getPath() + fileChooser.getFileFilter().getDescription());
					}
				}
			}
			else if(saveCheck == JOptionPane.NO_OPTION) {
				
			}
			else {
				return;
			}
			
			System.out.println("Open");
			int returnValue = fileChooser.showOpenDialog(null);
			if(returnValue == JFileChooser.APPROVE_OPTION) {
				model.load(fileChooser.getSelectedFile().getPath());
			}
		}
	}
	class saveActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {

			if(model.isSaved()) {
				model.save(); 
				return;
			} else {
				int returnValue = fileChooser.showSaveDialog(null);
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					System.out.println(fileChooser.getFileFilter());
					model.save(fileChooser.getSelectedFile().getPath() + fileChooser.getFileFilter().getDescription());
				}
			}

		}
	}
	class saveasActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			int returnValue = fileChooser.showSaveDialog(null);
			if(returnValue == JFileChooser.APPROVE_OPTION) {
				System.out.println(fileChooser.getFileFilter());
				model.save(fileChooser.getSelectedFile().getPath() + fileChooser.getFileFilter().getDescription());
			}
			System.out.println("saveas");
		}
	}
	class closeActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
		
			int saveCheck = JOptionPane.showConfirmDialog(null, "Do you want to save?", "Close", JOptionPane.YES_NO_CANCEL_OPTION);
			
			if(saveCheck == JOptionPane.YES_OPTION) {
				if(model.isSaved()) {
					model.save(); 
				} else {
					int returnValue = fileChooser.showSaveDialog(null);
					if(returnValue == JFileChooser.APPROVE_OPTION) {
						System.out.println(fileChooser.getFileFilter());
						model.save(fileChooser.getSelectedFile().getPath() + fileChooser.getFileFilter().getDescription());
					}
				}
			}
			else if(saveCheck == JOptionPane.NO_OPTION) {
				
			}
			else {
				return;
			}
			System.out.println("close");
			
			System.exit(-1);
			
			
		}
	}
	class ColorPickerActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Color selectedColor = JColorChooser.showDialog(null, "Color", Color.blue);
			if(selectedColor != null) {
				
			}
		}
	}
}
