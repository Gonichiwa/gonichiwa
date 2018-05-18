package com.gonichiwa.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MindMapTextAreaView extends JPanel {
	
	JScrollPane textAreaScrollPane;
	JTextArea mindMapBuildingTextArea;
	JButton textApplyToModelButton;
	
	public MindMapTextAreaView() {
		this.setLayout(new BorderLayout());
		initSubViews();
		this.add(textAreaScrollPane, BorderLayout.CENTER);
		this.add(textApplyToModelButton, BorderLayout.SOUTH);
	}
	
	public void initSubViews() {
		mindMapBuildingTextArea = new JTextArea();
		mindMapBuildingTextArea.setTabSize(1);
		textAreaScrollPane = new JScrollPane(mindMapBuildingTextArea);
		textApplyToModelButton = new JButton("APPLY");
	}
	
	public void setFont() {
		//TODO: study font
	}
	
	public String getText() {
		return mindMapBuildingTextArea.getText();
	}
	
	public boolean isEmpty() {
		return mindMapBuildingTextArea.getText().isEmpty();
	}
	
	public void setText(String str) {
		mindMapBuildingTextArea.setText(null);
		mindMapBuildingTextArea.setText(str);
	}
	
	public void addApplyButtonListener(ActionListener l) {
		textApplyToModelButton.addActionListener(l);
	}
	
}
