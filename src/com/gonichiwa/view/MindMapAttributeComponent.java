package com.gonichiwa.view;

import javax.swing.JPanel;

public abstract class MindMapAttributeComponent <T> extends JPanel {
	  public abstract void setValue(T value);
	  public abstract T getValue(); 
}
