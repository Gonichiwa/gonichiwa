package com.gonichiwa.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * MindMapAttributeTextArea
 * 
 * subclass of MindMapAttributeComponent.
 * it is basically JTextArea for AttributeView 
 * 
 * @author YONG_JOON_KIM
 *
 */
class MindMapAttributeTextArea extends MindMapAttributeComponent {
	JTextArea textArea;
	JLabel label;
	
	/**
	 * constructor
	 * 
	 * initialize JTextArea and JLabel for this Component
	 * 
	 */
	public MindMapAttributeTextArea(String labelName) {
		setLayout(new BorderLayout());
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setColumns(10);
		textArea.setRows(10);
		textArea.setMargin(new Insets(8, 8, 8, 8));
		textArea.setBackground(new Color(250, 230, 180));
		textArea.setTabSize(2);
		label = new JLabel(labelName);

		add(new JScrollPane(textArea), BorderLayout.CENTER);
		add(label, BorderLayout.NORTH);
	}
	
	/**
	 * Modifier method
	 * 
	 * set new String value to JTextArea
	 * anything was being displayed will be disappear
	 */
	@Override
	public void setValue(String value) {
		textArea.setText(null);
		textArea.setText(value);
	}
	
	/**
	 * Accessor method
	 * 
	 * get current String text of the JTextArea
	 */
	@Override
	public String getValue() {
		return textArea.getText();
	}

}
