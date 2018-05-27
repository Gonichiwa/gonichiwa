package com.gonichiwa.controller;

import java.awt.Cursor;
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
	private MindMapGraphView graphView;
	private MindMapAttributeView attributeView;
	private Cursor cursor;
	
	public MindMapGraphController(MindMapModel model, MindMapGraphView graphView, MindMapAttributeView attributeView) {
		this.model = model;
		this.graphView = graphView;
		this.attributeView = attributeView;
		this.graphView.addMouseListener(new GraphViewPaneMouseListener());
		this.graphView.addMouseMotionListener(new GraphViewPaneMouseListener());
		this.graphView.addNodeMouseAdapter(new NodeMouseListener());
	}
	
	public MindMapGraphView getView() {
		return graphView;
	}
	
	private class GraphViewPaneMouseListener extends MouseAdapter { 
		
		//TODO: moving pane.
		//TODO: zooming pane.
		private int x, y;

		public void mousePressed(MouseEvent e) {
			attributeView.dismissNode();
		}
		
		public void mouseDragged(MouseEvent e) {

		}
	}
	
	private class NodeMouseListener extends MouseAdapter {
		int x;
		int y;
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
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
			cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
			graphView.setCursor(cursor);
			System.out.println("enter");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			cursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
			graphView.setCursor(cursor);
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			
			System.out.println("hi");
			int dx = e.getLocationOnScreen().x - x;
			int dy = e.getLocationOnScreen().y - y;
			MindMapNodeView node = (MindMapNodeView) e.getSource();

			int newX = node.getX() + dx;
			int newY = node.getY() + dy;
			model.setNodeLocation(node.getID(), newX, newY);

			x += dx;
			y += dy;
		}
		
	}
}












