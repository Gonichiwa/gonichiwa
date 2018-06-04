package com.gonichiwa.view;

import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.gonichiwa.mindmapinterface.NodeDataDeliver;

import java.awt.Color;

class MindMapAttributeContainer extends JPanel {
	
	private LinkedHashMap<String, MindMapAttributeTextField> attributePanelDict; // preserve order
	private BoxLayout layout;
	
	public MindMapAttributeContainer() {
		this(new String[] {});
	}
	
	public MindMapAttributeContainer(String...attributeNames) {
		super();
		attributePanelDict = new LinkedHashMap<String, MindMapAttributeTextField>();
		
		layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		for(String name: attributeNames) {
			attributePanelDict.put(name, new MindMapAttributeTextField(name));
		}

		for(MindMapAttributeTextField attributeComponent : attributePanelDict.values()) {
			add(attributeComponent);
		}
		
		setVisible(false);
	}
	
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
		setVisible(true);
	}

	public void addAttributePanel(String key) {
		if(attributePanelDict.containsKey(key)) 
			return;
		
		MindMapAttributeTextField panel = new MindMapAttributeTextField(key);
		attributePanelDict.put(key, panel);
		add(panel);
	}
	
	public String getValue(String key) {
		validateKey(key);
		return attributePanelDict.get(key).getValue();
	}
	
	public void setEditable(String key, boolean editable) {
		validateKey(key);
		attributePanelDict.get(key).setEditableOfTheAttributeTextField(editable);
	}
	
	private void setValue(String key, String value) {
		validateKey(key);
		attributePanelDict.get(key).setValue(value);	
	}
	
	private void setValue(String key, int value) {
		setValue(key, String.valueOf(value));
	}
	
	private void setValue(String key, double value) {
		setValue(key, String.valueOf( (int) value));
		
	}
	
	private void validateKey(String key) {
		if(!attributePanelDict.containsKey(key)) 
			throw new NoSuchElementException("there is no such attribute");
	}
	
	private String toHexString(int value) {
		String answer = Integer.toHexString(value);
		if(answer.length() == 1) 
			answer = "0"+answer;
		return answer;
	}
	
}
