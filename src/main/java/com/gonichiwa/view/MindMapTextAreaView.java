package com.gonichiwa.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.gonichiwa.model.MindMapModel;

/**
 * MindMapTextAreaView
 * 
 * this is connected with MindMapTree as Observer relationship.
 * this has JTextArea and JButton for fully functioning as a TextPane.
 * 
 * user types mind map names with specific hierarchy to build and draw
 * new mind map
 * 
 * @author YONG_JOON_KIM
 *
 */
public class MindMapTextAreaView extends JPanel implements Observer {

	JScrollPane textAreaScrollPane;
	JTextArea mindMapBuildingTextArea;
	JButton textApplyToModelButton;
	MindMapModel model;

	/**
	 * constructor 
	 * 
	 * initialize JTextArea and JButton.
	 * Using JScrollPane to JTextAreaw before add to this JPanel
	 * so that user can type a lot of text.
	 * 
	 * @param model
	 */
	public MindMapTextAreaView(MindMapModel model) {
		setLayout(new BorderLayout());
		initSubViews();
		add(textAreaScrollPane, BorderLayout.CENTER);
		add(textApplyToModelButton, BorderLayout.SOUTH);
		this.model = model;
		this.model.tree.addObserver(this);
	}

	/**
	 * Modifier method
	 * 
	 * reset JTextArea
	 */
	public void reset() {
		mindMapBuildingTextArea.setText(null);
	}

	/**
	 * Modifier method
	 * 
	 * initialize SubViews to be added to JPanel
	 */
	public void initSubViews() {
		mindMapBuildingTextArea = new JTextArea();
		mindMapBuildingTextArea.setTabSize(1);
		mindMapBuildingTextArea.setBackground(new Color(250, 230, 180));
		textAreaScrollPane = new JScrollPane(mindMapBuildingTextArea);
		textApplyToModelButton = new JButton("APPLY");
	}

	/**
	 * Accessor method
	 * 
	 * return text on JTextArea
	 * 
	 * @return
	 */
	public String getText() {
		return mindMapBuildingTextArea.getText();
	}

	/**
	 * Modifier method
	 * 
	 * set new Text on JTextArea
	 * 
	 * @param str
	 */
	public void setText(String str) {
		mindMapBuildingTextArea.setText(null);
		mindMapBuildingTextArea.setText(str);
	}
	
	public void addApplyButtonListener(ActionListener l) {
		textApplyToModelButton.addActionListener(l);
	}

	/**
	 * Observer update method
	 * 
	 * when tree notify, it updates new Tree structure from tree model
	 * and set new text derived from tree model to JTextArea
	 */
	@Override
	public void update(Observable o, Object arg) {
		setText(model.tree.toString());
		mindMapBuildingTextArea.repaint();
		mindMapBuildingTextArea.revalidate();
	}
}
