package com.gonichiwa.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.model.MindMapNode;
import com.gonichiwa.view.MindMapAttributeView;
import com.gonichiwa.view.MindMapGraphView;
import com.gonichiwa.view.MindMapNodeView;
import com.gonichiwa.util.MindMapVector;

public class MindMapGraphController {
	private MindMapModel model;
	private MindMapGraphView view;
	private MindMapAttributeView attributeView;
	
	public MindMapGraphController(MindMapModel model, MindMapGraphView view, MindMapAttributeView attributeView) {
		this.model = model;
		this.view = view;
		this.attributeView = attributeView;
		this.view.addMouseListener(new GraphViewPaneMouseListener());
		this.view.addMouseMotionListener(new GraphViewPaneMouseListener());
		this.view.addNodeMouseAdapter(new NodeMouseListener());
	}
	
	public MindMapGraphView getView() {
		return view;
	}
	
	private class GraphViewPaneMouseListener extends MouseAdapter { 
		
		private int x, y;

		public void mousePressed(MouseEvent e) {
			attributeView.dismissNode();
//			x = e.getX();
//			y = e.getY();
//			System.out.println("hi");
		}
		
		public void mouseDragged(MouseEvent e) {
			
//			int dx = e.getX() - x;
//			int dy = e.getY() - y;
//
//			if (view.getBounds().contains(x, y)) {
//				view.setLocation(view.getX() + dx, view.getY() + dy);
//				view.repaint();
//			}
//			x += dx;
//			y += dy;
		}
	}
	
	private class NodeMouseListener extends MouseAdapter {
		int x;
		int y;
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
			System.out.println("clicked");
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			MindMapNodeView node = (MindMapNodeView) e.getSource();
			System.out.println(node);
			attributeView.setNode(node.getNode());
			System.out.println("pressed");
			x = e.getLocationOnScreen().x;
			y = e.getLocationOnScreen().y;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
			System.out.println("enter");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.println("hi");
			int dx = e.getLocationOnScreen().x - x;
			int dy = e.getLocationOnScreen().y - y;
			MindMapNodeView node = (MindMapNodeView) e.getSource();
			
//			if (node.getBounds().contains(x, y)) {
				int newX = node.getX() + dx;
				int newY = node.getY() + dy;
				model.setNodeLocation(node.getID(), newX, newY);
//				node.setLocation(node.getX() + dx, node.getY() + dy);
//				node.repaint();
//			}
			x += dx;
			y += dy;
		}
		
	}
}












