package com.gonichiwa.controller;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
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
		this.graphView.addNodeKeyListener(new NodeKeyListener());
		
		this.graphView.addFocusListener(new FocusAdapter () {

			public void focusGained(FocusEvent e) {
				graphView.repaint();
			}
		});
	}
	
	public MindMapGraphView getView() {
		return graphView;
	}
	
	private class NodeKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			switch(e.getKeyCode()) {
			case KeyEvent.VK_DELETE:
				try {
					MindMapNodeView node = (MindMapNodeView) e.getSource();
					model.remove(node.getID());
					attributeView.dismissNode();
				} catch (Exception err) {
					JOptionPane.showMessageDialog(graphView, err.getMessage(), "can not remove", 2);
				}
			default:
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class GraphViewPaneMouseListener extends MouseAdapter { 
		
		//TODO: moving pane.
		//TODO: zooming pane.
		
		public void mouseClicked(MouseEvent e) {

		}
		
		public void mousePressed(MouseEvent e) {
			
		}
		
		public void mouseReleased(MouseEvent e) {
			attributeView.dismissNode();
			graphView.requestFocus();
		}
		
		public void mouseDragged(MouseEvent e) {

		}
	}
	
	private class NodeMouseListener extends MouseAdapter {

		private int cursor;
        private Point startPos = null;
		
		@Override
		public void mouseClicked(MouseEvent e) {
			MindMapNodeView node = (MindMapNodeView) e.getSource();
            node.requestFocus();
            node.repaint();
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
			node.requestFocus();
			node.repaint();
			graphView.repaint();
			ResizableBorder border = (ResizableBorder) node.getBorder();          
			cursor = border.getCursor(e);
            startPos = e.getPoint();
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
			border.setHighlighted(true);	// border could be highlighted.
			node.repaint();
			graphView.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			graphView.repaint();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// set cursur back to normal.
			MindMapNodeView node = (MindMapNodeView) e.getSource();
			ResizableBorder border = (ResizableBorder) node.getBorder();
    		border.setHighlighted(false);	// border no longer highlighted
    		node.repaint();
			graphView.setCursor(Cursor.getDefaultCursor());
			graphView.repaint();
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			MindMapNodeView node = (MindMapNodeView) e.getSource();
		
			if (startPos != null && node.hasFocus()) {

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
                        	graphView.repaint();
                        }
                        break;

                    case Cursor.S_RESIZE_CURSOR:
                        if (!(h + dy < 30)) {
                        	model.setNodeLocation(node.getID(), x, y);
                        	model.setNodeSize(node.getID(), w, h+dy);
                            startPos = e.getPoint();
                            graphView.repaint();
                        }
                        break;

                    case Cursor.W_RESIZE_CURSOR:
                        if (!(w - dx < 30)) {
                        	model.setNodeLocation(node.getID(), x+dx, y);
                        	model.setNodeSize(node.getID(), w-dx, h);
                        	graphView.repaint();
                        }
                        break;

                    case Cursor.E_RESIZE_CURSOR:
                        if (!(w + dx < 30)) {
                        	model.setNodeLocation(node.getID(), x, y);
                        	model.setNodeSize(node.getID(), w+dx, h);
                            startPos = e.getPoint();
                            graphView.repaint();
                        }
                        break;

                    case Cursor.NW_RESIZE_CURSOR:
                        if (!(w - dx < 30) && !(h - dy < 30)) {
                        	model.setNodeLocation(node.getID(), x+dx, y+dy);
                        	model.setNodeSize(node.getID(), w-dx, h-dy);
                        	graphView.repaint();
                        }
                        break;

                    case Cursor.NE_RESIZE_CURSOR:
                        if (!(w + dx < 30) && !(h - dy < 30)) {
                        	model.setNodeLocation(node.getID(), x, y+dy);
                        	model.setNodeSize(node.getID(), w+dx, h-dy);
                        	graphView.repaint();
                            startPos = new Point(e.getX(), startPos.y);
                        }
                        break;

                    case Cursor.SW_RESIZE_CURSOR:
                        if (!(w - dx < 30) && !(h + dy < 30)) {
                        	model.setNodeLocation(node.getID(), x+dx, y);
                        	model.setNodeSize(node.getID(), w-dx, h+dy);
                        	graphView.repaint();
                            startPos = new Point(startPos.x, e.getY());
                        }
                        break;

                    case Cursor.SE_RESIZE_CURSOR:
                        if (!(w + dx < 30) && !(h + dy < 30)) {
                        	model.setNodeLocation(node.getID(), x, y);
                        	model.setNodeSize(node.getID(), w+dx, h+dy);
                        	graphView.repaint();
                            startPos = e.getPoint();
                        }
                        break;

                    case Cursor.HAND_CURSOR:
                    	model.setNodeLocation(node.getID(), x + dx, y + dy);
                    	graphView.repaint();
                }

                graphView.setCursor(Cursor.getPredefinedCursor(cursor));

            }
		}
	}
}












