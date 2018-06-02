package com.gonichiwa.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.view.MindMapTextAreaView;

/**
 * 
 * MindMapTextAreaController
 * 
 * it is Controller class of the MindMapTextArea
 * 
 * it handles apply button action logic.
 * it changes model (tree) when user apply new mind map through text.
 * when model has changed, it synchronized with model using Observer pattern.
 * 
 * @author YONG_JOON_KIM
 *
 */
public class MindMapTextAreaController implements Observer {
	private MindMapTextAreaView view;
	private MindMapModel model;
	private ApplyActionListener listener;
	
	public MindMapTextAreaController(MindMapModel model) {
		this(model, new MindMapTextAreaView(model));
	}
	
	/**
	 * Constructor
	 * 
	 * @param model
	 * 		the MindMapModel which is main model of this application
	 * @param view
	 * 		the MindMapTextAreaView which is boundary class for TextArea.
	 */
	public MindMapTextAreaController(MindMapModel model, MindMapTextAreaView view) {
		listener = new ApplyActionListener();
		this.model = model;
		this.view  = view;
		this.view.addApplyButtonListener(listener);
		this.model.addObserver(this);
	}
	
	/**
	 * Accessor
	 * 
	 * return the ApplyActionListener instance.
	 * 
	 * @return
	 * 		the listener which handles apply action on TextAreaView.
	 */
	public ApplyActionListener getListener() {
		return listener;
	}
	
	/**
	 * Accessor
	 * 
	 * return the MindMapTextAreaView.
	 * 
	 * @return
	 * 		the text area pane view.
	 */
	public MindMapTextAreaView getView() {
		return view;
	}
	
	/**
	 * @exception	IllegalArgumentException
	 * 		when user type wrong input then throw exception.
	 * 		it creates JOptionPane when exception occurs.
	 * 		
	 * @author YONG_JOON_KIM
	 *
	 */
	private class ApplyActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				model.tree.buildTree(view.getText());
			} catch (IllegalArgumentException formatErr) {
				JOptionPane.showMessageDialog(view, formatErr.getMessage(), "Invalid format", 2);
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if(((String) arg).equals("RESET"))
			view.reset();
	}
}
