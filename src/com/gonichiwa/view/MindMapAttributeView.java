package com.gonichiwa.view;

import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.gonichiwa.mindmapinterface.NodeDataDeliver;
import com.gonichiwa.model.MindMapNode;

/**
 * MindMapAttributeView
 * 
 * It is one of the main five sub views for MindMap User Interface.
 * It is subclass of JPanel which is a container of other views.
 * It contains MindMapAttributeContainer, JButton.
 * It uses BoxLayout Manager.
 * 
 * @author YONG_JOON_KIM
 * 
 *
 */
public class MindMapAttributeView extends JPanel implements Observer {
	
	private MindMapAttributeContainer attributeContainer;
	private JButton changeButton;
	private JScrollPane scrollPane;
	private BoxLayout layout;
	private MindMapNode node;
	private JPanel watingPanel;

	/**
	 * Constructor
	 * 
	 * initialize all subviews. set NAME Attribute Component to be 
	 * ineditable 
	 * 
	 */
	public MindMapAttributeView() {
		super();
		layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		
		attributeContainer = new MindMapAttributeContainer(NodeDataDeliver.attributes);
		attributeContainer.setEditable("NAME", false);
		
		changeButton = new JButton("change");
		changeButton.setAlignmentX(CENTER_ALIGNMENT);
		
		scrollPane  = new JScrollPane(attributeContainer);
		add(scrollPane);
		add(changeButton);
	}
	
	/**
	 * Modifier method
	 * 
	 * reset AttributeView making AttributeContainer invisible
	 */
	public void reset() {
		dismissNode();
	}
	
	/**
	 * Modifier method
	 * 
	 * Add ActionListener instance to changeButton instance.
	 * @param l
	 * 		the given ActionListener instance.
	 */
	public void addChangeButtonActionListener(ActionListener l) {
		changeButton.addActionListener(l);
	}
	
	/**
	 * Accessor method
	 * 
	 * return currently being displayed node data
	 * 
	 * @return
	 * 		NodeDataDeliver object currently being displayed on 
	 * 		AttributeView
	 */
	public NodeDataDeliver getNode() {
		return node;
	}
	
	/**
	 * Accessor method
	 * 
	 * @return
	 * 		false if AttributeView display nothing.
	 */
	public boolean hasNodeToDisplay() {
		return node != null;
	}
	
	/**
	 * Modifier method
	 * 
	 * remove node so that AttributeView displays nothing
	 */
	public void dismissNode() {
		if(node != null) {
			node.deleteObserver(this);
			node = null;
			attributeContainer.displayNode(node);
			revalidate();
		}
	}
	
	/**
	 * Modifier method
	 * 
	 * set new node to be displayed
	 * 
	 * @param node
	 * 		new node to be displayed
	 */
	public void setNode(MindMapNode node) {
		this.node = node;
		node.addObserver(this);
		attributeContainer.displayNode(node);
	}
	
	/**
	 * Accessor method
	 * 
	 * Get the attribute value of the given key.
	 * 
	 * @param key
	 * 		the given key which is specific attribute name.
	 * @return
	 * 		value of the attribute matched by the given key.
	 */
	public String getValue(String key) {
		return attributeContainer.getValue(key);
	}

	/**
	 * Observer update method.
	 * 
	 * when node, which is currently dispalyed, notify
	 * then update view with given updated node data.
	 * 
	 */
	@Override
	public void update(Observable o, Object arg) {

		if(!hasNodeToDisplay())
			setNode((MindMapNode)o);
		else {
			attributeContainer.displayNode(node);
			revalidate();
		}
	}
}
