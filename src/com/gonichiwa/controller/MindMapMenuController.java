package com.gonichiwa.controller;

import java.awt.Color;
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

import javafx.stage.FileChooser;

public class MindMapMenuController {

	private MindMapMenuBar menuBar;
	private MindMapToolBar toolBar;
	private MindMapModel model;
	
	private JFileChooser fileChooser;
	
	public MindMapMenuController(MindMapModel model,MindMapMenuBar menuBar,MindMapToolBar  toolBar){
		
		Path currentRelativePath = Paths.get("save");
		String s = currentRelativePath.toAbsolutePath().toString();
		System.out.println("Current relative path is: " + s);
		
		this.menuBar = menuBar;
		this.toolBar = toolBar;
		this.model = model;
		
		fileChooser = new JFileChooser(currentRelativePath.toFile());

		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(".json", "json"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(".XML", "XML"));
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		menuBar.addNewwListener(new newwActionListener());
		menuBar.addOpenListener(new openActionListener());
		menuBar.addSaveListener(new saveActionListener());
		menuBar.addSaveasListener(new saveasActionListener());
		menuBar.addCloseListener(new closeActionListener());
		menuBar.addExportListener(new exportActionListener());
		menuBar.addUndoListener(new undoActionListener());
		menuBar.addRedoListener(new redoActionListener());
		menuBar.addDeleteListener(new deleteActionListener());
		menuBar.addApplyListener(new applyActionListener());
		menuBar.addChangeListener(new changeActionListener());
		menuBar.addZoominListener(new zoominActionListener());
		menuBar.addZoomoutListener(new zoomoutActionListener());
		menuBar.addFitmapListener(new fitmapActionListener());
		menuBar.addHideEditorPaneListener(new hideeditorpaneActionListener());
		menuBar.addHideAttributePaneListener(new hideattributepaneActionListener());
		
		toolBar.addApplyListener(new applyActionListener());
		toolBar.addNewwListener(new newwActionListener());
		toolBar.addOpenListener(new openActionListener());
		toolBar.addSaveListener(new saveActionListener());
		toolBar.addSaveasListener(new saveasActionListener());
		toolBar.addCloseListener(new closeActionListener());
		toolBar.addChangeListener(new changeActionListener());
		toolBar.addFitmapListener(new fitmapActionListener());
		toolBar.addZoominListener(new zoominActionListener());
		toolBar.addZoomoutListener(new zoomoutActionListener());
		toolBar.addUndoListener(new undoActionListener());
		toolBar.addRedoListener(new redoActionListener());
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
	// TODO: unify new, save, save as to one Listener Class. because there are too many copy and paste code.
	class newwActionListener implements ActionListener{
				
		public void actionPerformed(ActionEvent e) {
			
				int saveCheck = JOptionPane.showConfirmDialog(null, "변경사항을 저장하시겠습니까?","New",JOptionPane.YES_NO_CANCEL_OPTION);
				
				if(saveCheck == JOptionPane.YES_OPTION) {
					int returnValue = fileChooser.showSaveDialog(null);
					if(returnValue == JFileChooser.APPROVE_OPTION) {
						System.out.println(fileChooser.getFileFilter());
						model.save(fileChooser.getSelectedFile().getPath() + fileChooser.getFileFilter().getDescription());
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

			
			int saveCheck = JOptionPane.showConfirmDialog(null, "변경사항을 저장하시겠습니까?","New",JOptionPane.YES_NO_CANCEL_OPTION);
			
			if(saveCheck == JOptionPane.YES_OPTION) {
				int returnValue = fileChooser.showSaveDialog(null);
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					System.out.println(fileChooser.getFileFilter());
					model.save(fileChooser.getSelectedFile().getPath() + fileChooser.getFileFilter().getDescription());
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
		
			int saveCheck = JOptionPane.showConfirmDialog(null, "변경사항을 저장하시겠습니까?","New",JOptionPane.YES_NO_CANCEL_OPTION);
			
			if(saveCheck == JOptionPane.YES_OPTION) {
				int returnValue = fileChooser.showSaveDialog(null);
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					System.out.println(fileChooser.getFileFilter());
					model.save(fileChooser.getSelectedFile().getPath() + fileChooser.getFileFilter().getDescription());
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
	class exportActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			System.out.println("export");
		}
	}
	class undoActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			model.backward();
			System.out.println("undo");
		}
	}
	class redoActionListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			System.out.println("redo");
		}
	}
	class deleteActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println("delete");
		}
	}
	class applyActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println("apply");
		}
	}
	class changeActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println("change");
		}
	}
	class zoominActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println("zoomin");
		}
	}
	class zoomoutActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println("zoomout");
		}
	}
	class fitmapActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println("fitmap");	
			
		}
	}
	class hideeditorpaneActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println("hideeditor");
		}
	}
	class hideattributepaneActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println("hideattribute");
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
