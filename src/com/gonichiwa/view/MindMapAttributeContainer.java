package com.gonichiwa.view;

import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.gonichiwa.mindmapinterface.NodeDataDeliver;

/**
 * 
 * MindMapAttributeContainer
 * 
 * this is Container for all AttributeComponent views.
 * it manages the component views using LinkedHashMap collection
 * why it uses LinkedHashMap is because it can preserve the order 
 * of Object which is added.
 * 
 * @author YONG_JOON_KIM
 *
 */
class MindMapAttributeContainer extends JPanel {

	private LinkedHashMap<String, MindMapAttributeComponent> attributePanelDict; // preserve order
	private BoxLayout layout;
	private MindMapAttributeTextArea noteTextArea;

	public MindMapAttributeContainer() {
		this(new String[] {});
	}

	/**
	 * constructor
	 * 
	 * initialize attribute views adding LinkedHashMap
	 * 
	 * @param attributeNames
	 * 		the names of Attribute Textfields.
	 */
	public MindMapAttributeContainer(String... attributeNames) {
		super();
		attributePanelDict = new LinkedHashMap<String, MindMapAttributeComponent>();

		layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);

		noteTextArea = new MindMapAttributeTextArea("Note");

		for(String name: attributeNames) {
			attributePanelDict.put(name, new MindMapAttributeTextField(name));
		}

		attributePanelDict.put("NOTE", noteTextArea);

		for(MindMapAttributeComponent attributeComponent : attributePanelDict.values()) {
			if(attributeComponent != null)
				add(attributeComponent);
		}

		add(noteTextArea);
		setVisible(false);
	}

	/**
	 * Modifier method
	 * 
	 * display node's information appropriately.
	 * if node is null then set this JPanel unvisible
	 * 
	 * @param node
	 * 		node data to be display on AttributeView
	 */
	public void displayNode(NodeDataDeliver node) {
		if(node == null) {
			setVisible(false);
			return;
		}
		setValue("NAME", node.getName());
		setValue("X", node.getX());
		setValue("Y", node.getY());
		setValue("WIDTH", node.getWidth());
		setValue("HEIGHT", node.getHeight());
		String red = toHexString(node.getRedColor()).toUpperCase();
		String green = toHexString(node.getGreenColor()).toUpperCase();
		String blue = toHexString(node.getBlueColor()).toUpperCase();
		setValue("COLOR", red+green+blue);
		setValue("NOTE", node.getNote());
		setVisible(true);
	}

	/**
	 * Modifier method
	 * 
	 * set editability of the selected AttributeTextField
	 * 
	 * @param key
	 * 		name of the AttributeTextField
	 * @param editable
	 * 		if false, then it will be ineditable.
	 */
	public void setEditable(String key, boolean editable) {
		validateKey(key);
		try {
			MindMapAttributeTextField textField = (MindMapAttributeTextField) attributePanelDict.get(key);
			textField.setEditableOfTheAttributeTextField(editable);
		} catch (ClassCastException err) {
			System.out.println("can not modify editability of this Key");
		}
	}
	
	/**
	 * Accessor method
	 * 
	 * get data of the Attribute View
	 * 
	 * @param key
	 * 		name of the Attribute Component
	 * @return
	 * 		String data of the selected view.
	 */
	public String getValue(String key) {
		validateKey(key);
		return attributePanelDict.get(key).getValue();
	}

	/**
	 * Modifier method
	 * 
	 * set new String value to an attribute component
	 * with the given key value
	 * 
	 * @param key
	 * 		name of the Attribute Component
	 * @param value
	 * 		String value to be set
	 */
	private void setValue(String key, String value) {
		validateKey(key);
		attributePanelDict.get(key).setValue(value);
	}

	/**
	 * Modifier method
	 * 
	 * set new String value to an attribute component
	 * with the given key value
	 * 
	 * @param key
	 * 		name of the Attribute Component
	 * @param value
	 * 		double value to be set
	 */
	private void setValue(String key, double value) {
		setValue(key, String.valueOf( (int) value));
	}

	/**
	 * Helper method
	 * 
	 * see if LinkedHashMap contains the given key
	 * if there is no such key, then throw an Exception
	 * 
	 * @param key
	 * 		String key to be examined
	 */
	private void validateKey(String key) {
		if(!attributePanelDict.containsKey(key))
			throw new NoSuchElementException("there is no such attribute");
	}

	/**
	 * Helper method
	 * 
	 * change the integer value to Hex String value
	 * 
	 * @param value
	 * 		integer value to be transformed to hex format.
	 * @return
	 * 		Hexadecimal format of the given value 
	 */
	private String toHexString(int value) {
		String answer = Integer.toHexString(value);
		if(answer.length() == 1)
			answer = "0"+answer;
		return answer;
	}

}
