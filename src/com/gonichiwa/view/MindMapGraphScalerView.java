package com.gonichiwa.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

/**
 * MindMapGraphScalerView
 * 
 * this view shows how much zoomFactor currently applied
 * on GraphView. 
 * 
 * this view includes JSlider and JButton so that user control
 * zoomFactor or reset all offsets on GraphView.
 * 
 * @author YONG_JOON_KIM
 *
 */
public class MindMapGraphScalerView extends JPanel {
	private JButton resetButton;
	private JSlider scaler;
	private JLabel percentageLabel;
	
	/**
	 * constructor
	 * 
	 * initialize all JSlider, JButton, JLabel
	 * 
	 */
	public MindMapGraphScalerView() {
		setLayout(new GridLayout(1, 3));
		setBackground(Color.DARK_GRAY);
		scaler = new JSlider(JSlider.HORIZONTAL, 
							 MindMapGraphView.getMinZoomPercentage(), 
							 MindMapGraphView.getMaxZoomPercentage(), 
							 100);
		scaler.setPaintTicks(false);
		scaler.setPaintLabels(false);
		resetButton = new JButton("CENTER");
		resetButton.setFont(new Font("Monaco", Font.BOLD, 14));
		percentageLabel = new JLabel("100%");
		percentageLabel.setFont(new Font("Monaco", Font.BOLD, 16));
		percentageLabel.setForeground(Color.white);
		add(resetButton);
		add(scaler);
		add(percentageLabel);
	}
	
	/**
	 * Modifier method
	 * 
	 * add ChangeListener to scaler instance
	 * 
	 * @param l
	 * 		ChangeListener Object
	 */
	public void addScaleListener(ChangeListener l) {
		scaler.addChangeListener(l);
	}
	
	/**
	 * Modifier method
	 * 
	 * add ActionListener to resetButton
	 * 
	 * @param l
	 * 		ActionListener Object
	 */
	public void addResetListener(ActionListener l) {
		resetButton.addActionListener(l);
	}
	
	/**
	 * Accessor method
	 * 
	 * return current int value of the scaler
	 * 
	 * @return
	 * 		int value of the scaler
	 */
	public int getScalerValue() {
		return scaler.getValue();
	}
	
	/**
	 * Modifier method
	 * 
	 * set new zoomFactor value to scaler object
	 * if zoomFactor exceeds range of the zoom, then
	 * trim it's size automatically
	 * 
	 * @param zoomFactor
	 * 		value to be set to scaler object
	 */
	public void setScalerPoint(double zoomFactor) {
		int percentage = (int) (zoomFactor * 100);
		int maxPercentage = MindMapGraphView.getMaxZoomPercentage();
		int minPercentage = MindMapGraphView.getMinZoomPercentage();
		
		if(percentage < minPercentage) 
			percentage = minPercentage;
		else if(percentage > maxPercentage)
			percentage = maxPercentage;
		
		scaler.setValue(percentage);
		changePercentageLabelTextTo(percentage);
	}
	
	/**
	 * Modifier method
	 * 
	 * set percentage to percentageLabel with %
	 * 
	 * @param percentage
	 */
	public void changePercentageLabelTextTo(int percentage) {
		percentageLabel.setText(percentage + "%");
	}
	
	/**
	 * Modifier method
	 * 
	 * reset scaler
	 */
	public void reset() {
		setScalerPoint(1);
	}
}
