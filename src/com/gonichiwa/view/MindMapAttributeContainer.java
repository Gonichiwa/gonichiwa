package com.gonichiwa.view;

import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MindMapAttributeContainer extends JPanel {
	
	 
	private LinkedHashMap<String, MindMapAttributeTextField> attributePanelDict; // preserve order
	private BoxLayout layout;
	
	public MindMapAttributeContainer() {
		this(new String[] {});
	}
	
	public MindMapAttributeContainer(String...attributeNames) {
		//TODO: remove duplicate attributeNames
		super();
		attributePanelDict = new LinkedHashMap<String, MindMapAttributeTextField>();
		
		layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(layout);
		
		for(String name: attributeNames) {
			attributePanelDict.put(name, new MindMapAttributeTextField(name));
		}

		for(MindMapAttributeTextField attributeComponent : attributePanelDict.values()) {
			add(attributeComponent);
		}
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
	
	public void setValue(String key, String value) {
		validateKey(key);
		attributePanelDict.get(key).setValue(value);	
	}
	
	public void setEditable(String key, boolean editable) {
		validateKey(key);
		attributePanelDict.get(key).setEditableOfTheAttributeTextField(editable);
	}
	
	private void validateKey(String key) {
		if(!attributePanelDict.containsKey(key)) 
			throw new NoSuchElementException("there is no such attribute");
	}
	
}
