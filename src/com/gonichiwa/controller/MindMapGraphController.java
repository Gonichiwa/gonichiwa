package com.gonichiwa.controller;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
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
import com.gonichiwa.view.ResizableBorder;
import com.gonichiwa.util.MindMapVector;

public class MindMapGraphController {
	private MindMapModel model;
	private MindMapGraphView graphView;
	private MindMapAttributeView attributeView;
	
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

		
		public void mouseClicked(MouseEvent e) {
		
			graphView.requestFocus();
			graphView.repaintAllNodes();
			graphView.revalidate();
			graphView.repaint();

		}
		
		public void mousePressed(MouseEvent e) {
			attributeView.dismissNode();
			graphView.setCursor(Cursor.getDefaultCursor());
			graphView.requestFocus();
			graphView.repaintAllNodes();
			graphView.repaint();
			graphView.revalidate();
		}
		
		public void mouseDragged(MouseEvent e) {

		}
	}
	
	private class NodeMouseListener extends MouseAdapter {
		int xPressed;
		int yPressed;
		
		private int cursor;
        private Point startPos = null;
		
		@Override
		public void mouseClicked(MouseEvent e) {
			MindMapNodeView node = (MindMapNodeView) e.getSource();
//			ResizableBorder border = (ResizableBorder) node.getBorder();          
            node.repaint();
			graphView.repaintAllNodes();
			graphView.revalidate();
			graphView.repaint();
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			MindMapNodeView node = (MindMapNodeView) e.getSource();
			if (node.hasFocus()) {
				ResizableBorder border = (ResizableBorder) node.getBorder();
				graphView.setCursor(Cursor.getPredefinedCursor(border.getCursor(e)));
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// maybe we can fix here later on.
			MindMapNodeView node = (MindMapNodeView) e.getSource();
			attributeView.setNode(node.getNode());
//			xPressed = e.getLocationOnScreen().x;
//			yPressed = e.getLocationOnScreen().y;
//			MindMapNodeView node = (MindMapNodeView) e.getSource();
			ResizableBorder border = (ResizableBorder) node.getBorder();          
			cursor = border.getCursor(e);
            startPos = e.getPoint();
            node.requestFocus();
            node.repaint();
			graphView.repaintAllNodes();
			graphView.revalidate();
			graphView.repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			startPos = null;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// set cursor to HAND shape.
			MindMapNodeView node = (MindMapNodeView) e.getSource();
			ResizableBorder border = (ResizableBorder) node.getBorder();
			border.setHighlighted(true);
			node.repaint();
			graphView.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			graphView.repaint();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// set cursur back to normal.
			MindMapNodeView node = (MindMapNodeView) e.getSource();
			ResizableBorder border = (ResizableBorder) node.getBorder();
    		border.setHighlighted(false);
    		node.repaint();
			graphView.setCursor(Cursor.getDefaultCursor());
			graphView.repaint();
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			MindMapNodeView node = (MindMapNodeView) e.getSource();
			// moving node
//			int dx = e.getLocationOnScreen().x - xPressed;
//			int dy = e.getLocationOnScreen().y - yPressed;
//			
//			int newX = node.getX() + dx;
//			int newY = node.getY() + dy;
//			model.setNodeLocation(node.getID(), newX, newY);
//			xPressed += dx;
//			yPressed += dy;
			
			if (startPos != null) {

                int x = node.getX();
                int y = node.getY();
                int w = node.getWidth();
                int h = node.getHeight();

                int dx = e.getX() - startPos.x;
                int dy = e.getY() - startPos.y;

                switch (cursor) {
                    case Cursor.N_RESIZE_CURSOR:
                        if (!(h - dy < 30)) {
                        	model.setNodeLocation(node.getID(), x, y+dy);
                        	model.setNodeSize(node.getID(), w, h-dy);
//                            setBounds(x, y + dy, w, h - dy);
//                            resize();
                        	graphView.revalidate();
                        }
                        break;

                    case Cursor.S_RESIZE_CURSOR:
                        if (!(h + dy < 30)) {
                        	model.setNodeLocation(node.getID(), x, y);
                        	model.setNodeSize(node.getID(), w, h+dy);
//                            setBounds(x, y, w, h + dy);
                            startPos = e.getPoint();
//                            resize();
                            graphView.revalidate();
                        }
                        break;

                    case Cursor.W_RESIZE_CURSOR:
                        if (!(w - dx < 30)) {
                        	model.setNodeLocation(node.getID(), x+dx, y);
                        	model.setNodeSize(node.getID(), w-dx, h);
//                            setBounds(x + dx, y, w - dx, h);
//                            resize();
                        	graphView.revalidate();
                        }
                        break;

                    case Cursor.E_RESIZE_CURSOR:
                        if (!(w + dx < 30)) {
                        	model.setNodeLocation(node.getID(), x, y);
                        	model.setNodeSize(node.getID(), w+dx, h);
//                            setBounds(x, y, w + dx, h);
                            startPos = e.getPoint();
//                            resize();
                            graphView.revalidate();
                        }
                        break;

                    case Cursor.NW_RESIZE_CURSOR:
                        if (!(w - dx < 30) && !(h - dy < 30)) {
                        	model.setNodeLocation(node.getID(), x+dx, y+dy);
                        	model.setNodeSize(node.getID(), w-dx, h-dy);
                        	graphView.revalidate();
//                            setBounds(x + dx, y + dy, w - dx, h - dy);
//                            resize();
                        }
                        break;

                    case Cursor.NE_RESIZE_CURSOR:
                        if (!(w + dx < 30) && !(h - dy < 30)) {
                        	model.setNodeLocation(node.getID(), x, y+dy);
                        	model.setNodeSize(node.getID(), w+dx, h-dy);
                        	graphView.revalidate();
//                            setBounds(x, y + dy, w + dx, h - dy);
                            startPos = new Point(e.getX(), startPos.y);
//                            resize();
                        }
                        break;

                    case Cursor.SW_RESIZE_CURSOR:
                        if (!(w - dx < 30) && !(h + dy < 30)) {
                        	model.setNodeLocation(node.getID(), x+dx, y);
                        	model.setNodeSize(node.getID(), w-dx, h+dy);
                        	graphView.revalidate();
//                            setBounds(x + dx, y, w - dx, h + dy);
                            startPos = new Point(startPos.x, e.getY());
//                            resize();
                        }
                        break;

                    case Cursor.SE_RESIZE_CURSOR:
                        if (!(w + dx < 30) && !(h + dy < 30)) {
                        	model.setNodeLocation(node.getID(), x, y);
                        	model.setNodeSize(node.getID(), w+dx, h+dy);
                        	graphView.revalidate();
//                            setBounds(x, y, w + dx, h + dy);
                            startPos = e.getPoint();
//                            resize();
                        }
                        break;

                    case Cursor.HAND_CURSOR:
                    	model.setNodeLocation(node.getID(), x + dx, y + dy);
//                    	model.setNodeSize(node.getID(), w, h);
                    	graphView.revalidate();
//                        Rectangle bounds = getBounds();
//                        bounds.translate(dx, dy);
//                        setBounds(bounds);
//                        resize();
                }

                graphView.setCursor(Cursor.getPredefinedCursor(cursor));
            }
			
			
		}
		
		
		
	}
}












