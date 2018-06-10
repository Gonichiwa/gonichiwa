package com.gonichiwa.view;

import javax.swing.JPanel;
/**
 * MindMapAttributeComponent
 * 
 * abstract class for managing AttributeComponents at once.
 * it has only setValue and getValue methods.
 * 
 * @author YONG_JOON_KIM
 *
 */
abstract class MindMapAttributeComponent extends JPanel {
	  public abstract void setValue(String value);
	  public abstract String getValue(); 
}
