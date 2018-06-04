package com.gonichiwa.view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;

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
}
