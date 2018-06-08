package com.gonichiwa.view;

import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import com.gonichiwa.mindmapinterface.NodeDataDeliver;

import java.awt.Color;
import java.awt.Insets;

class MindMapAttributeContainer extends JPanel {

	private LinkedHashMap<String, MindMapAttributeComponent> attributePanelDict; // preserve order
	private BoxLayout layout;
	private MindMapAttributeTextArea noteTextArea;

	public MindMapAttributeContainer() {
		this(new String[] {});
	}

	public MindMapAttributeContainer(String...attributeNames) {
		super();
		attributePanelDict = new LinkedHashMap<String, MindMapAttributeComponent>();

		layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);

		noteTextArea = new MindMapAttributeTextArea();

		for(String name: attributeNames) {
			attributePanelDict.put(name, new MindMapAttributeTextField(name));
		}


		attributePanelDict.put("NOTE", noteTextArea);

		for(MindMapAttributeComponent attributeComponent : attributePanelDict.values()) {
			if(attributeComponent != null)
				add((JComponent) attributeComponent);
		}

		add(noteTextArea);

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
		setValue("NOTE", node.getNote());
		setVisible(true);
	}

	public void setEditable(String key, boolean editable) {
		validateKey(key);
		try {
			MindMapAttributeTextField textField = (MindMapAttributeTextField) attributePanelDict.get(key);
			textField.setEditableOfTheAttributeTextField(editable);
		} catch (ClassCastException err) {
			System.out.println("can not modify editability of this Key");
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
