package com.gonichiwa.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.gonichiwa.model.MindMapModel;

public class MindMapTextAreaView extends JPanel implements Observer {
	
	JScrollPane textAreaScrollPane;
	JTextArea mindMapBuildingTextArea;
	JButton textApplyToModelButton;
	MindMapModel model;
	
	public MindMapTextAreaView(MindMapModel model) {
		setLayout(new BorderLayout());
		initSubViews();
		add(textAreaScrollPane, BorderLayout.CENTER);
		add(textApplyToModelButton, BorderLayout.SOUTH);
		this.model = model;
		this.model.tree.addObserver(this);
	}
	
	public void reset() {
		mindMapBuildingTextArea.setText(null);
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

	@Override
	public void update(Observable o, Object arg) {
		setText(model.tree.toString());
		mindMapBuildingTextArea.repaint();
		mindMapBuildingTextArea.revalidate();
	}
}
