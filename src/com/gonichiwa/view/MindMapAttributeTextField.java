package com.gonichiwa.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * MindMapAttributeTextField
 *
 * It is component view of the MindMapAttributeContainer.
 * It contains JLabel, which is the name of the attribute, and JTextField, which indicates the value.
 *
 * @author YONG_JOON_KIM
 *
 */
class MindMapAttributeTextField extends MindMapAttributeComponent {

    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 30;
    private static final int LABEL_WIDTH = 50;
    private static final int LABEL_HEIGHT = 30;
    private static final int TEXT_FIELD_COLUMN = 7;

	private JLabel attributeName;
	private JTextField attributeValueTextField;

	public MindMapAttributeTextField(String name) {
		this(name, true);
	}

	/**
	 * Constructor
	 *
	 * Initialize attributeName and attributeValueTextField.
	 *
	 * @param name
	 * 		the given name of attribute.
	 * @param editable
	 * 		editable value of the attributeValueTextField.
	 */
	public MindMapAttributeTextField(String name, boolean editable) {
        this.setLayout(new FlowLayout());
        attributeName = new JLabel(name, JLabel.CENTER);
        attributeName.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        attributeValueTextField = new JTextField("", TEXT_FIELD_COLUMN);
        attributeValueTextField.setHorizontalAlignment(JLabel.CENTER);
        setEditableOfTheAttributeTextField(editable);
        setMaximumSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
        add(attributeName);
        add(attributeValueTextField);
    }

	/**
	 * Accessor method
	 *
	 * Return the value of the attribute.
	 *
	 * @return
	 * 		the attribute value.
	 */
     @Override
    public String getValue() {
        return attributeValueTextField.getText();
    }


    /**
     * Modifier method
     *
     * Set the given attribute value.
     *
     * @param value
	 * 		the given value which is going to be set.
     */
     @Override
    public void setValue(String value) {
        attributeValueTextField.setText(value);
    }

    /**
     * Modifier method
     *
     * Set the TextField, which indicates attribute value, to weather editable or not.
     * After set TextField ineditable, it changes the background of it.
     *
     * @param editable
     * 		the given boolean value.
     */
    public void setEditableOfTheAttributeTextField(boolean editable) {
    	attributeValueTextField.setEditable(editable);
    	if (!editable)
    		attributeValueTextField.setBackground(Color.GRAY);
    }

}
