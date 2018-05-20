package com.gonichiwa.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.gonichiwa.mindmapinterface.NodeDataDeliver;
import com.gonichiwa.model.MindMapModel;
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
	
	public MindMapAttributeController(MindMapModel model, MindMapAttributeView view) {
		this.model = model;
		this.view  = view;
		this.view.addChangeButtonActionListener(new ChangeActionListener());
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
			
			try {
				// get All the value from MindMapAttributeView
				int x = Integer.parseInt(view.getValue("X"));
				int y = Integer.parseInt(view.getValue("Y"));
				int width = validateSizeValue(view.getValue("WIDTH"));
				int height = validateSizeValue(view.getValue("HEIGHT"));
				System.out.println(Integer.parseInt(view.getValue("COLOR"), 16)); //error here
				Color color = new Color(validateColorValue(view.getValue("COLOR")));
				int red = color.getRed();
				int green = color.getGreen();
				int blue = color.getBlue();
				int alpha = color.getAlpha();
				
				// update model
				model.setNodeLocation(view.getNode().getID(), x, y);
				model.setNodeSize(view.getNode().getID(), width, height);
				model.setNodeColor(view.getNode().getID(), red, green, blue, alpha);
				
			} catch (Exception err) {
				JOptionPane.showMessageDialog(view, err.getMessage(), "invalid format", 2);
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
	public void setNode(NodeDataDeliver node) {
		view.setNode(node);
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
	private int validateSizeValue(String sizeValue) {
		int answer = Integer.parseInt(sizeValue);
		if(answer < 0)
			throw new IllegalArgumentException("negative size doesn't make sense.");
		if(answer > 100) 	//TODO: must change this to constant value from MindMapNodeView
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
