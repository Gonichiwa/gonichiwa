package com.gonichiwa.controller;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.model.MindMapNode;
import com.gonichiwa.view.MindMapAttributeView;
import com.gonichiwa.view.MindMapGraphScalerView;
import com.gonichiwa.view.MindMapGraphView;
import com.gonichiwa.view.MindMapNodeView;
import com.gonichiwa.view.ResizableBorder;
import com.gonichiwa.util.MindMapVector;

public class MindMapGraphController implements Observer {
	private MindMapModel model;
	private JPanel graphViewContainer;
	private MindMapGraphView graphView;
	private MindMapGraphScalerView graphScalerView;
	private MindMapAttributeView attributeView;
	private MouseAdapter mouseListener = new GraphViewPaneMouseListener();
	
	public MindMapGraphController(MindMapModel model, MindMapAttributeView attributeView) {
		this.model = model;
		this.graphView = new MindMapGraphView(model);
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
		
		graphViewContainer = new JPanel(new BorderLayout());
		graphScalerView = new MindMapGraphScalerView();
		
		graphScalerView.addScaleListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider scaler = (JSlider) e.getSource();
				double zoomFactor = scaler.getValue() / 100.0;
				graphView.zoom(graphView.getWidth() / 2, graphView.getHeight() / 2, zoomFactor);
				graphScalerView.changePercentageLabelTextTo((int)(zoomFactor * 100));
			}
		});
		
		graphScalerView.addResetListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				graphView.resetAllOffset();
				graphScalerView.setScalerPoint(graphView.getZoomFactor());
			}
		});

		this.graphViewContainer.add(graphView, BorderLayout.CENTER);
		this.graphViewContainer.add(graphScalerView, BorderLayout.SOUTH);
		this.model.addObserver(this);
	}
	
	public JPanel getView() {
		return graphViewContainer;
	}
	
	private class NodeKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {			
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
		}		
	}
	
	private class GraphViewPaneMouseListener extends MouseAdapter { 

		private Point startPos = null;

		@Override
		public void mousePressed(MouseEvent e) {
			startPos = e.getPoint();
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if(startPos != null) {			
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
			
			if(rotation > 0 && graphView.getZoomFactor() < MindMapGraphView.MAX_ZOOM_FACTOR) 
				graphView.zoom(e.getX(), e.getY(), graphView.getZoomFactor()*1.1);
			else if(rotation < 0 && graphView.getZoomFactor() > MindMapGraphView.MIN_ZOOM_FACTOR) 
				graphView.zoom(e.getX(), e.getY(), graphView.getZoomFactor()*0.9);
			
			graphScalerView.setScalerPoint(graphView.getZoomFactor());
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
			// display node data on AttributePane.
			MindMapNodeView node = (MindMapNodeView) e.getSource();
			attributeView.setNode(node.getNode());
			
			// request focus to the selected node.
			node.requestFocus();
			node.repaint();

			// set Z-order of the selected node to be the most higher priority.
			graphView.setComponentZOrder(node, 0);
			
			// set cursor by the border.

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
			MindMapNodeView node = (MindMapNodeView) e.getSource();
			ResizableBorder border = (ResizableBorder) node.getBorder();
			border.setHighlighted(true);	// border will be highlighted.
			node.repaint();
			// set cursor to hand shape.
			graphView.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			graphView.repaint();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			MindMapNodeView node = (MindMapNodeView) e.getSource();
			ResizableBorder border = (ResizableBorder) node.getBorder();
    		border.setHighlighted(false);	// border no longer highlighted
    		node.repaint();
			// set cursor back to normal.
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

                switch (cursor) {
                    case Cursor.N_RESIZE_CURSOR:
                        if (!(h - dy < 30)) {
                        	model.setNodeLocation(node.getID(), x, y+dy);
                        	model.setNodeSize(node.getID(), w, h-dy);
                        	node.moveNode(0, dy * (node.getZoomFactor() - 1));
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
                        	node.moveNode(dx * (node.getZoomFactor() - 1), 0);
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
                        	node.moveNode(dx * (node.getZoomFactor() - 1), dy * (node.getZoomFactor() - 1));
                        	graphView.repaint();
                        }
                        break;

                    case Cursor.NE_RESIZE_CURSOR:
                        if (!(w + dx < 30) && !(h - dy < 30)) {
                        	model.setNodeLocation(node.getID(), x, y+dy);
                        	model.setNodeSize(node.getID(), w+dx, h-dy);
                        	node.moveNode(0, dy * (node.getZoomFactor() - 1));

                        	graphView.repaint();
                            startPos = new Point(e.getX(), startPos.y);
                        }
                        break;

                    case Cursor.SW_RESIZE_CURSOR:
                        if (!(w - dx < 30) && !(h + dy < 30)) {
                        	model.setNodeLocation(node.getID(), x+dx, y);
                        	model.setNodeSize(node.getID(), w-dx, h+dy);
                        	node.moveNode(dx * (node.getZoomFactor() - 1), 0);
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
                    	node.moveNode(dx * (node.getZoomFactor() - 1), dy * (node.getZoomFactor() - 1));

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
			graphScalerView.reset();
		}
	}
}












