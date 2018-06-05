package com.gonichiwa.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MindMapAttributeTextArea extends MindMapAttributeComponent<String> {
	JTextArea textArea;
	JLabel label;
	public MindMapAttributeTextArea() {
		setLayout(new BorderLayout());
		textArea = new JTextArea();
		textArea.setSize(400, 200);
		textArea.setMargin(new Insets(8, 8, 8, 8));
		textArea.setBackground(new Color(250, 230, 180));
		
		label = new JLabel("Note");
		
		add(new JScrollPane(textArea), BorderLayout.CENTER);
		add(label, BorderLayout.NORTH);
	}
	
	@Override
	public void setValue(String value) {
		// TODO Auto-generated method stub
		textArea.setText(null);
		textArea.setText(value);
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return textArea.getText();
	}

}
