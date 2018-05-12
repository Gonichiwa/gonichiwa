package com.gonichiwa.model;

import java.util.Observable;

import com.gonichiwa.mindmapinterface.NodeDataDeliver;

/**
 * Observable
 * 
 * if model send notification with a specific node, view will change that node.
 * else if model send null object as a argument, view will change all nodes.
 * 
 * @author penubo
 *
 */
public class MindMapModel extends Observable{
	
	public MindMapTree tree;
	private MindMapStateTracker stateTracker;
	private MindMapFileManager fileManager;
	
	public MindMapModel() {
		tree = new MindMapTree();
		stateTracker = new MindMapStateTracker();
		fileManager = new MindMapFileManager();
	}

	/**
	 * Modifier
	 * build tree with a given text.
	 * @param text
	 * 		the text which is given from TextAreaPane.
	 */
	public void buildNewTree(String text) {
		tree.removeAllNodes();
		//TODO: build new tree
	}
	
	/**
	 * remove the certain node which has given Id.
	 * @param nodeID
	 * 		id of the node which is target to be deleted in the tree.
	 */
	public void remove(int nodeID) {
		tree.removeNode(nodeID);
		setChanged();
		notifyObservers(null);
	}
	
	public void backward() {
		// TODO:modify tree for Joon
		tree = stateTracker.getBackwardState();
	}
	
	public void forward() {
		// TODO:modify tree for Joon
		tree = stateTracker.getForwardState();
	}
	
	public void save() {
		// TODO: save data for Joon
	}
	
	public void load() {
		// TODO: load data for Joon
	}
	
	public void setNodeLocation(int nodeID, int x, int y) {
		try {
		MindMapNode node = tree.getNode(nodeID);
		node.setX(x);
		node.setY(y);
		setChanged();
		notifyObservers((NodeDataDeliver) node);
		} catch (NullPointerException e) {
			System.out.println(e.getClass() + "setNodeLocation NullPointerException");
		}

	}
	
	public void setNodeSize(int nodeID, int width, int height) {
		try {
		MindMapNode node = tree.getNode(nodeID);
		node.setWidth(width);
		node.setHeight(height);
		setChanged();
		notifyObservers((NodeDataDeliver) node);
		} catch (NullPointerException e) {
			System.out.println(e.getClass() + "setNodeSize NullPointerException");
		}
	}
	
	public void setNodeColor(int nodeID, int red, int green, int blue, int alpha) {
		try {
		MindMapNode node = tree.getNode(nodeID);
		node.setRed(red);
		node.setGreen(green);
		node.setBlue(blue);
		node.setAlpha(alpha);
		setChanged();
		notifyObservers((NodeDataDeliver) node);
		} catch (NullPointerException e) {
			System.out.println(e.getClass() + "setNodeColor NullPointerException");
		}
	}
	
	public void setNodeColor(int nodeID, int red, int green, int blue) {
		setNodeColor(nodeID, red, green, blue, 255);
	}
	

}












