package com.gonichiwa.controller;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.gonichiwa.model.MindMapModel;
import com.gonichiwa.view.MindMapAttributeView;
import com.gonichiwa.view.MindMapGraphScalerView;
import com.gonichiwa.view.MindMapGraphView;
import com.gonichiwa.view.MindMapNodeView;
import com.gonichiwa.view.ResizableBorder;


/**
 * MindMapGraphController
 *
 * it is controller class of the MindMapGraphView and MindMapAttributeView.
 *
 * it changes the position and size of the node in the node model 
 * by handling mouse event on MindMapGraphView or MindMapNodeView or 
 * by handling key event.
 *
 * @author YONG_JOON_KIM
 *
 */
public class MindMapGraphController implements Observer {
	private MindMapModel model;
	private JPanel graphViewContainer;	// it contains graphView and scalerView both.
	private MindMapGraphView graphView;
	private MindMapGraphScalerView graphScalerView;
	private MindMapAttributeView attributeView;
	private MouseAdapter mouseListener = new GraphViewPaneMouseListener();

	
	public MindMapGraphController(MindMapModel model, MindMapAttributeView attributeView) {
		this.model = model;
		this.attributeView = attributeView;

		this.graphView = new MindMapGraphView(model);
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
				resetOffsets();
			}
		});

		this.graphViewContainer.add(graphView, BorderLayout.CENTER);
		this.graphViewContainer.add(graphScalerView, BorderLayout.SOUTH);
		this.model.addObserver(this);
	}

	/**
	 * Accessor method
	 * 
	 * @return
	 * 		the view that represents GraphPane.
	 */
	public JPanel getView() {
		return graphViewContainer;
	}

	/**
	 * NodeKeyListener inner class
	 * 
	 * this is KeyListener for MindMapNodeView.
	 * this handles DELETE Key event when node has foucs.
	 * 
	 * @author YONG_JOON_KIM
	 *
	 */
	private class NodeKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
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

	/**
	 * GraphViewPaneMouseListener inner class
	 * 
	 * this is MouseListener for MindMapGraphView.
	 * this handles moving and zooming the screen.
	 * 
	 * @author YONG_JOON_KIM
	 *
	 */
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

			if(rotation < 0 && graphView.getZoomFactor() < MindMapGraphView.MAX_ZOOM_FACTOR)
				graphView.zoom(e.getX(), e.getY(), graphView.getZoomFactor()*1.1);
			else if(rotation > 0 && graphView.getZoomFactor() > MindMapGraphView.MIN_ZOOM_FACTOR)
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

	/**
	 * NodeMouseListener inner class
	 * 
	 * this is MouseListener for MindMapNodeView.
	 * this handles resizing and moving the selected node.
	 * 
	 * @author YONG_JOON_KIM
	 *
	 */
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
                        if (!(h - dy < MindMapNodeView.MIN_HEIGHT)) {
                        	model.setNodeLocation(node.getID(), x, y+dy);
                        	model.setNodeSize(node.getID(), w, h-dy);
                        	node.moveNode(0, dy * (node.getZoomFactor() - 1));

                        	graphView.repaint();
                        }
                        break;

                    case Cursor.S_RESIZE_CURSOR:
                        if (!(h + dy < MindMapNodeView.MIN_HEIGHT)) {
                        	model.setNodeLocation(node.getID(), x, y);
                        	model.setNodeSize(node.getID(), w, h+dy);
                            startPos = e.getPoint();
                            graphView.repaint();
                        }
                        break;

                    case Cursor.W_RESIZE_CURSOR:
                        if (!(w - dx < MindMapNodeView.MIN_WIDTH)) {
                        	model.setNodeLocation(node.getID(), x+dx, y);
                        	model.setNodeSize(node.getID(), w-dx, h);
                        	node.moveNode(dx * (node.getZoomFactor() - 1), 0);
                        	graphView.repaint();
                        }
                        break;

                    case Cursor.E_RESIZE_CURSOR:
                        if (!(w + dx < MindMapNodeView.MIN_WIDTH)) {
                        	model.setNodeLocation(node.getID(), x, y);
                        	model.setNodeSize(node.getID(), w+dx, h);
                            startPos = e.getPoint();
                            graphView.repaint();
                        }
                        break;

                    case Cursor.NW_RESIZE_CURSOR:
                        if (!(w - dx < MindMapNodeView.MIN_WIDTH) && !(h - dy < MindMapNodeView.MIN_HEIGHT)) {
                        	model.setNodeLocation(node.getID(), x+dx, y+dy);
                        	model.setNodeSize(node.getID(), w-dx, h-dy);
                        	node.moveNode(dx * (node.getZoomFactor() - 1), dy * (node.getZoomFactor() - 1));
                        	graphView.repaint();
                        }
                        break;

                    case Cursor.NE_RESIZE_CURSOR:
                        if (!(w + dx < MindMapNodeView.MIN_WIDTH) && !(h - dy < MindMapNodeView.MIN_HEIGHT)) {
                        	model.setNodeLocation(node.getID(), x, y+dy);
                        	model.setNodeSize(node.getID(), w+dx, h-dy);
                        	node.moveNode(0, dy * (node.getZoomFactor() - 1));

                        	graphView.repaint();
                            startPos = new Point(e.getX(), startPos.y);
                        }
                        break;

                    case Cursor.SW_RESIZE_CURSOR:
                        if (!(w - dx < MindMapNodeView.MIN_WIDTH) && !(h + dy < MindMapNodeView.MIN_HEIGHT)) {
                        	model.setNodeLocation(node.getID(), x+dx, y);
                        	model.setNodeSize(node.getID(), w-dx, h+dy);
                        	node.moveNode(dx * (node.getZoomFactor() - 1), 0);
                        	graphView.repaint();
                            startPos = new Point(startPos.x, e.getY());
                        }
                        break;

                    case Cursor.SE_RESIZE_CURSOR:
                        if (!(w + dx < MindMapNodeView.MIN_WIDTH) && !(h + dy < MindMapNodeView.MIN_HEIGHT)) {
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
	
	/**
	 * Modifier method
	 * 
	 * reset the whole offsets in graphView and every nodeView.
	 * and set all the offsets on the center of the current screen.
	 * 
	 */
	private void resetOffsets() {
		graphView.resetOffsetsToCenter();
		graphScalerView.setScalerPoint(graphView.getZoomFactor());
	}

	/**
	 * Observer update method.
	 * 
	 * when MindMapModel calls notifyObservers() methods this will be
	 * executed. if command is RESET than reset graphView, attributeView and 
	 * graphScalerView. note that there is no break in RESET case. this is 
	 * because when model notify RESET it needs to reset all offsets as well.
	 * 
	 */
	@Override
	public void update(Observable o, Object arg) {
		try {
			String message = (String) arg;
			switch(message) {
			case "RESET":
				graphView.reset();
				attributeView.reset();
				graphScalerView.reset();
			case "NEW":
				resetOffsets();
				break;
			default:
				break;
			}
		} catch (ClassCastException e) {

		}
	}
}
