package com.gonichiwa.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.gonichiwa.mindmapinterface.NodeDataDeliver;
import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.model.MindMapNode;
import com.gonichiwa.view.MindMapAttributeView;

/**
 * MindMapAttributeController
 * 
 * it is controller class of the MindMapAttributeView.
 * 
 * it changes the attributes in the tree model by handling change button event.
 * 
 * @author YONG_JOON_KIM
 *
 */
public class MindMapAttributeController {
	private MindMapAttributeView view;
	private MindMapModel model;
	private ChangeActionListener listener;
	
	public MindMapAttributeController(MindMapModel model) {
		this(model, new MindMapAttributeView());
	}
	
	public MindMapAttributeController(MindMapModel model, MindMapAttributeView view) {
		listener = new ChangeActionListener();
		this.model = model;
		this.view  = view;
		this.view.addChangeButtonActionListener(listener);
	}

	/**
	 * Accessor
	 * 
	 * return the ChangeActionListener instance.
	 * 
	 * @return
	 * 		the listener which handles change action on AttributeView.
	 */
	public ChangeActionListener getListener() {
		return listener;
	}
	
	/**
	 * Accessor
	 * 
	 * return the MindMapAttributeView
	 * 
	 * @return
	 * 		the attribute pane view.
	 */
	public MindMapAttributeView getView() {
		return view;
	}
	
	/**
	 * EventListener class for Change Action which is supposed to be happened in MindMapAttributeView.
	 * 
	 * it get All the information that user modified from MindMapAtributeView.
	 * and it update the model by the new information of a node.
	 * if user modify some values undesirable way, then it pop up JOptionPane to notify the users.
	 * 
	 * @author YONG_JOON_KIM
	 *
	 */
	private class ChangeActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!view.hasNodeToDisplay())
				return;
			
			try {
				// get All the value from MindMapAttributeView
				String name = validateNameValue(view.getValue("NAME"));
				double x = Double.parseDouble(view.getValue("X"));
				double y = Double.parseDouble(view.getValue("Y"));
				double width = validateSizeValue(view.getValue("WIDTH"));
				double height = validateSizeValue(view.getValue("HEIGHT"));
				Color color = new Color(validateColorValue(view.getValue("COLOR")));
				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();
				int alpha = color.getAlpha();
				String note = view.getValue("NOTE");
				System.out.println(color);
				// update model
				model.changeNodeName(view.getNode().getID(), name);
				model.setNodeLocation(view.getNode().getID(), x, y);
				model.setNodeSize(view.getNode().getID(), width, height);
				model.setNodeColor(view.getNode().getID(), red, green, blue, alpha);
				model.setNodeNote(view.getNode().getID(), note);
			} catch (Exception err) {
				JOptionPane.showMessageDialog(view, err.getMessage(), "invalid format", 2);
				System.out.println(err);
				err.printStackTrace();
			}
		}
	}
	
	/**
	 * Modifier
	 * 
	 * Set the given node on MindMapAttributeContainer so user can modify its attributes.
	 * 
	 * @param node
	 * 		the given node to display attributes.
	 */
	public void setNode(MindMapNode node) {
		view.setNode(node);
	}
	
	public String validateNameValue(String value) {
		// TODO Auto-generated method stub
		if(!value.equals("")) 
			return value;
		else 
			throw new IllegalArgumentException("name can not be empty");
	}

	/**
	 * Helper Method.
	 * 
	 * check weather the given size value is valid or not.
	 * size value can not be negative value.
	 * size value can not exceed MAXIMUM size of the node.
	 * if it isn't valid, then throw an exception.
	 * 
	 * @exception IllegalArgumentException
	 * 		if the given value is not valid for updating model, then throw this exception.
	 * @param sizeValue
	 * 		the given size (width or height) value from MindMapAttributeView
	 * @return
	 * 		the valid value for the new size.
	 */
	private double validateSizeValue(String sizeValue) {
		double answer = Double.parseDouble(sizeValue);
		if(answer < 0)
			throw new IllegalArgumentException("negative size doesn't make sense.");
		if(answer > 400) 	//TODO: must change this to constant value from MindMapNodeView
			throw new IllegalArgumentException("size can not exceed 100");
		return answer;
	}
	
	/**
	 * Helper Method.
	 * 
	 * check weather the given color value is valid or not.
	 * color value should be 6 hexadecimal number, where 2 of each will be Red, Green and Blue in a row.
	 * if it isn't valid, then throw an exception.
	 * 
	 * @exception IllegalArgumentException.
	 * 		if the given value is not valid for updating model, then throw this exception.
	 * @param colorValue
	 * 		the given color value from MindMapAttributeView
	 * @return
	 * 		the valid value for the new color
	 */
	private int validateColorValue(String colorValue) {
		if (colorValue.length() != 6)
			throw new IllegalArgumentException("invalid colorValue");
		
		return Integer.parseInt(colorValue, 16);
	}

}
