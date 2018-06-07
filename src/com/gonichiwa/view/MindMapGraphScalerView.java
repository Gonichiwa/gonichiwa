package com.gonichiwa.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
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
//		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//		setLayout(new FlowLayout(FlowLayout.RIGHT));
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
