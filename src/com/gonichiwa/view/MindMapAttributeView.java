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
	private NodeDataDeliver node;

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
	
	public NodeDataDeliver getNode() {
		return node;
	}
	
	public void setNode(NodeDataDeliver node) {
		this.node = node;
		attributePane.displayNode(node);
		this.revalidate();
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
