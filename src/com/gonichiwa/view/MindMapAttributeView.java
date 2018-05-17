package com.gonichiwa.view;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.gonichiwa.mindmapinterface.NodeDataDeliver;

/**
 * MindMapAttributeView
 * 
 * It is one of the main five sub views for MindMap User Interface.
 * It is subclass of JPanel which is a container of other views.
 * It contains MindMapAttributeContainer, JButton.
 * It uses BoxLayout Manager.
 * 
 * 
 * @author YONG_JOON_KIM
 * 
 *
 */
public class MindMapAttributeView extends JPanel {
	
	private MindMapAttributeContainer attributePane;
	private JButton changeButton;
	private BoxLayout layout;

	/**
	 * Constructor
	 */
	public MindMapAttributeView() {
		super();
		
		layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		attributePane = new MindMapAttributeContainer(NodeDataDeliver.attributes);
		attributePane.setEditable("NAME", false);		// I don't like this part.
		changeButton = new JButton("change");
		
		changeButton.setAlignmentX(CENTER_ALIGNMENT);
		//TODO: how to set Alignment something to put attributePane on CENTER.
		add(new JScrollPane(attributePane));
		add(changeButton);
	}
	
	/**
	 * Modifier
	 * 
	 * Add ActionListener instance to changeButton instance.
	 * @param l
	 * 		the given ActionListener instance.
	 */
	public void addChangeButtonActionListener(ActionListener l) {
		changeButton.addActionListener(l);
	}
	
	/**
	 * Modifier
	 * 
	 * Set the given attribute value to the given key.
	 * if there isn't attribute value for the given key, then throw NoSuchElementException.
	 * 
	 * @param key
	 * 		the given key which is specific attribute name.
	 * @param value
	 * 		the given value which is going to be set.
	 * @exception NoSuchElementException.
	 * 		if there isn't a value for the given key.
	 */
	public void setValue(String key, String value) {
		attributePane.setValue(key, value);
	}
	/**
	 * Overloading method
	 * 
	 * if user send int value, then change it to String and set it.
	 * @param key
	 * @param value
	 */
	public void setValue(String key, int value) {
		setValue(key, String.valueOf(value));
	}
	
	/**
	 * Accessor 
	 * 
	 * Get the attribute value of the given key.
	 * 
	 * @param key
	 * 		the given key which is specific attribute name.
	 * @return
	 * 		value of the attribute matched by the given key.
	 */
	public String getValue(String key) {
		return attributePane.getValue(key);
	}
}
