package com.gonichiwa.view;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class MenuActionListener implements ActionListener{
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		switch(cmd) {
		case "Open":
			System.out.println("abc");
			break;
		case "Save":
			System.out.println("DEF");
			break;
		}
	}
}


public class Menu extends JFrame {

	//private Container contentPane;
	
	public Menu() {
		setTitle("¸Þ´º");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createMenu();
		//contentPane = getContentPane();
		setSize(250,200);
		setVisible(true);
		
	}
	
	private void createMenu() {
		
		JMenuBar menubar = new JMenuBar();
		JMenu FileMenu  = new JMenu("File");
		JMenu EditMenu  = new JMenu("Edit");
		JMenuItem[] menuItem = new JMenuItem[4];
		String[] ItemTitle = {"Open","Save","ABC","DEF"};
		MenuActionListener listener =new MenuActionListener();
		
		/*
		JToolBar toolBar = new JToolBar("ToolBarTest");
		toolBar.setBackground(Color.LIGHT_GRAY);
		toolBar.add(new JButton("Open"));
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("OPENEN");
		*/
		
		for(int i=0;i<menuItem.length;i++) {
			menuItem[i] = new JMenuItem(ItemTitle[i]);
			menuItem[i].addActionListener(listener);
			FileMenu.add(menuItem[i]);
		}
		
	
		
		menubar.add(FileMenu);
		menubar.add(EditMenu);
		
		setJMenuBar(menubar);
		
		/*
		toolBar.add(combo);
		contentPane.add(toolBar,BorderLayout.NORTH);
		*/
	}
	
	
	public static void main(String[] args) {
		
		
		new Menu();
	}

}
