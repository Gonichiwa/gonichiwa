package com.gonichiwa.view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

public class MindMapGraphScalerView extends JPanel {
	JButton resetButton;
	JSlider scaler;
	
	public MindMapGraphScalerView() {
		scaler = new JSlider(JSlider.HORIZONTAL, 80, 300, 100);
		scaler.setPaintTicks(false);
		scaler.setPaintLabels(false);
		resetButton = new JButton("reset");
		
		this.add(scaler);
		this.add(resetButton);
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
		int maxPercentage = (int) MindMapGraphView.MAX_ZOOM_FACTOR * 100;
		int minPercentage = (int) MindMapGraphView.MIN_ZOOM_FACTOR * 100;
		
		if(percentage < minPercentage) 
			percentage = minPercentage;
		else if(percentage > maxPercentage)
			percentage = maxPercentage;
		
		scaler.setValue(percentage);
	}
}
