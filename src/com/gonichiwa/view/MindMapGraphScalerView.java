package com.gonichiwa.view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

public class MindMapGraphScalerView extends JPanel {
	private JButton resetButton;
	private JSlider scaler;
	private JLabel percentageLabel;
	
	public MindMapGraphScalerView() {
		scaler = new JSlider(JSlider.HORIZONTAL, 
							 MindMapGraphView.getMinZoomPercentage(), 
							 MindMapGraphView.getMaxZoomPercentage(), 
							 100);
		scaler.setPaintTicks(false);
		scaler.setPaintLabels(false);
		resetButton = new JButton("reset");
		percentageLabel = new JLabel("100%");
		add(resetButton);
		add(scaler);
		add(percentageLabel);
	}
	
	public void addScaleListener(ChangeListener l) {
		scaler.addChangeListener(l);
	}
	
	public void addResetListener(ActionListener l) {
		resetButton.addActionListener(l);
	}
	
	public int getScalerValue() {
		return scaler.getValue();
	}
	
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
	
	public void changePercentageLabelTextTo(int percentage) {
		percentageLabel.setText(percentage + "%");
	}
	
	public void reset() {
		setScalerPoint(1);
	}
}
