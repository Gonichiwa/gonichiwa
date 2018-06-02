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

	public void pushNewState() {
		stateTracker.pushNewState(tree);
	}
	
	/**
	 * remove the certain node which has given Id.
	 * @param nodeID
	 * 		id of the node which is target to be deleted in the tree.
	 */
	public void remove(int nodeID) {
		tree.removeNode(nodeID);
		pushNewState();
		setChanged();
		notifyObservers(null);
	}
	
	public int getTreeSize() {
		return tree.size();
	}
	
	public void backward() {
		tree.loadTree(stateTracker.getBackwardState());
	}
	
	public void forward() {
		tree.loadTree(stateTracker.getForwardState());
	}
	
	public void saveTo(String path, String name) {
		// TODO: save data for Joon
		fileManager.setPath(path);
		fileManager.setFileName(name);
		fileManager.save(this);
	}
	
	public void save(String path) {
		fileManager.setPath(path);
		fileManager.save(this);
	}
	
	public void load(String path) {
		// TODO: load data for Joon
		fileManager.setPath(path);
		System.out.println(tree);
		tree.loadTree(fileManager.loadRoot());
//		if(tree != null) {
//			// if there is a actual loadedModel than update model.
//			System.out.println(loadedModel.stateTracker);
//			System.out.println(loadedModel.fileManager);
//
//			tree.loadTree(loadedModel.tree);
//			this.stateTracker = loadedModel.stateTracker;
//			this.fileManager = loadedModel.fileManager;
//		}
		setChanged();
		notifyObservers("LOAD");
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
	
	public String toString() {
		return tree.toString();
	}
	
}












