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
import java.awt.event.MouseWheelEvent;
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

public class MindMapGraphController implements Observer {
	private MindMapModel model;
	private MindMapGraphView graphView;
	private MindMapAttributeView attributeView;
	private MouseAdapter mouseListener = new GraphViewPaneMouseListener();
	
	public MindMapGraphController(MindMapModel model, MindMapGraphView graphView, MindMapAttributeView attributeView) {
		this.model = model;
		this.graphView = graphView;
		this.attributeView = attributeView;
		this.graphView.addMouseListener(mouseListener);
		this.graphView.addMouseMotionListener(mouseListener);
		this.graphView.addMouseWheelListener(mouseListener);
		this.graphView.addNodeMouseAdapter(new NodeMouseListener());
		this.graphView.addNodeKeyListener(new NodeKeyListener());
		
		this.graphView.addFocusListener(new FocusAdapter () {

			public void focusGained(FocusEvent e) {
				graphView.repaint();
			}
		});
		
		this.model.addObserver(this);
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
		
		private Point startPos = null;
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("pressed");
			startPos = e.getPoint();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-g
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if(startPos != null) {			
				System.out.println(e.getX() + " " + e.getY());
				System.out.println("relative position " + (e.getX() - graphView.getDX()) + " " + (e.getY() - graphView.getDY()));
				double dx = e.getX() - startPos.getX();
				double dy = e.getY() - startPos.getY();

				graphView.movePanel(dx, dy);
				startPos = e.getPoint();
			}
			graphView.repaint();
		}

		@Override 
		public void mouseWheelMoved(MouseWheelEvent e) {
			int rotation = e.getWheelRotation();
			if(rotation > 0)
				graphView.zoom(e.getX(), e.getY(), graphView.getZoomFactor()*1.1);
			else if(rotation < 0)
				graphView.zoom(e.getX(), e.getY(), graphView.getZoomFactor()*0.9);
			graphView.repaint();
		}

		public void mouseReleased(MouseEvent e) {
			attributeView.dismissNode();
			graphView.requestFocus();
			startPos = null;

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
			graphView.setComponentZOrder(node, 0);
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

                double x = node.getActualX(); 
                double y = node.getActualY();
                double w = node.getActualWidth();
                double h = node.getActualHeight();
               
                double dx =  ((e.getX() - startPos.x) / node.getZoomFactor());
                double dy =  ((e.getY() - startPos.y) / node.getZoomFactor());

//                int dx = e.getX() - startPos.x;
//                int dy = e.getY() - startPos.y;
                
                switch (cursor) {
                    case Cursor.N_RESIZE_CURSOR:
                        if (!(h - dy < 30)) {
                        	model.setNodeLocation(node.getID(), x, y+dy*node.getZoomFactor());
                        	model.setNodeSize(node.getID(), w, h-dy);
//                        	node.moveNode(0, 0);
//                        	node.scaleHeight(+dy);
//                        	model.setNodeLocation(node.getID(), x, y+dy);
//                        	model.setNodeSize(node.getID(), w, h-dy);
//                        	node.moveNode(0, (int) -(dy - dy * node.getZoomFactor()));
//                        	node.scaleHeight(-dy);
                        	graphView.repaint();
                        }
                        break;

                    case Cursor.S_RESIZE_CURSOR:
                        if (!(h + dy < 30)) {
                        	model.setNodeLocation(node.getID(), x, y);
                        	model.setNodeSize(node.getID(), w, h+dy);
                        	node.moveNode(0, 0);
                            startPos = e.getPoint();
                            graphView.repaint();
                        }
                        break;

                    case Cursor.W_RESIZE_CURSOR:
                        if (!(w - dx < 30)) {
                        	model.setNodeLocation(node.getID(), x+dx*node.getZoomFactor(), y);
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
                        	model.setNodeLocation(node.getID(), x+dx*node.getZoomFactor(), y+dy*node.getZoomFactor());
                        	model.setNodeSize(node.getID(), w-dx, h-dy);
                        	graphView.repaint();
                        }
                        break;

                    case Cursor.NE_RESIZE_CURSOR:
                        if (!(w + dx < 30) && !(h - dy < 30)) {
                        	model.setNodeLocation(node.getID(), x, y+dy*node.getZoomFactor());
                        	model.setNodeSize(node.getID(), w+dx, h-dy);
                        	graphView.repaint();
                            startPos = new Point(e.getX(), startPos.y);
                        }
                        break;

                    case Cursor.SW_RESIZE_CURSOR:
                        if (!(w - dx < 30) && !(h + dy < 30)) {
                        	model.setNodeLocation(node.getID(), x+dx*node.getZoomFactor(), y);
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
                    	model.setNodeLocation(node.getID(), x + dx *node.getZoomFactor(), y + dy*node.getZoomFactor());
                    	graphView.repaint();
                }

                graphView.setCursor(Cursor.getPredefinedCursor(cursor));

            }
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if(((String) arg).equals("RESET")) {
			graphView.reset();
			attributeView.reset();
		}
	}
}












