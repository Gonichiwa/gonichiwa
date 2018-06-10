package com.gonichiwa.model;

import java.util.Observable;

/**
 * MindMapModel class
 * 
 * this is Broker class for interacting with four different Controller classes.
 * 
 * this has MindMapTree, MindMapStateTracker and MindMapFileManager. so that it can
 * manage all the functioning that models can do.
 * 
 * MindMapModel object will only notify to Controller classes not View classes.
 * 
 * 
 * @author YONG_JOON_KIM
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
	 * Modifier Method
	 * 
	 * this will build new MindMapTree given the Text from
	 * MindMapTextAreaController.
	 *
	 * it will notify observers with NEW command.
	 * 
	 * @param text
	 * 		the given text from MindMapTextAreaController
	 */
	public void buildTree(String text) {
		tree.buildTree(text);
		setChanged();
		notifyObservers("NEW");
	}
	
	/**
	 * Modifier Method.
	 * 
	 * remove the certain node which has given Id.
	 * 
	 * @param nodeID
	 * 		id of the node which is target to be deleted in the tree.
	 */
	public void remove(int nodeID) {
		tree.removeNode(nodeID);
	}
	
	public void backward() {
		tree = stateTracker.getBackwardState();
	}
	
	public void forward() {
		tree = stateTracker.getForwardState();
	}

	/**
	 * Modifier method
	 * 
	 * performs the saving functions with fileManager object.
	 * 
	 * @param path
	 * 		the given path from MindMapMenuController
	 */
	public void save(String path) {
		fileManager.setPath(path);
		fileManager.save(tree);
	}
	
	/**
	 * Modifier method
	 * 
	 * performs the saving functions with fileManager object.
	 * 
	 */
	public void save() {
		if(fileManager.getPath() != "")
			fileManager.save(tree);
	}
	
	/**
	 * Accessor Method.
	 * 
	 * performs the loading functions with fileManager object.
	 * 
	 * @param path
	 * 		the given path from the MindMapMenuController
	 */
	public void load(String path) {
		// TODO: load data for Joon
		fileManager.setPath(path);
		tree.setRoot(fileManager.loadRoot());
		setChanged();
		notifyObservers("NEW");
	}

	/**
	 * Modifier method
	 * 
	 * set new Location to the selected node.
	 * 
	 * @param nodeID
	 * 		the id which represents selected node from view.
	 * @param x
	 * 		new X position.
	 * @param y
	 * 		new Y position.
	 */
	public void setNodeLocation(int nodeID, double x, double y) {
		try {
			MindMapNode node = tree.getNode(nodeID);
			node.setX(x);
			node.setY(y);
			
		} catch (NullPointerException e) {
			System.out.println(e.getClass() + "setNodeLocation NullPointerException");
		}

	}

	/** 
	 * Modifier method
	 * 
	 * set new size to the selected node.
	 * 
	 * @param nodeID
	 * 		the id which represents selected node from view.
	 * @param width
	 * 		new width value
	 * @param height
	 * 		new height value
	 */
	public void setNodeSize(int nodeID, double width, double height) {
		try {
			MindMapNode node = tree.getNode(nodeID);
			node.setWidth(width);
			node.setHeight(height);
			
		} catch (NullPointerException e) {
			System.out.println(e.getClass() + "setNodeSize NullPointerException");
		}
	}

	/** 
	 * Modifier method
	 * 
	 * set new color to the selected node.
	 * 
	 * @param nodeID
	 * 		the id which represents selected node from view.
	 * @param red
	 * 		new Red Value of the color 0 to 255
	 * @param green
	 * 		new Green value of the color 0 to 255
	 * @param blue
	 * 		new Blue value of the color 0 to 255
	 */
	public void setNodeColor(int nodeID, int red, int green, int blue) {
		try {
			MindMapNode node = tree.getNode(nodeID);
			node.setRed(red);
			node.setGreen(green);
			node.setBlue(blue);

		} catch (NullPointerException e) {
			System.out.println(e.getClass() + "setNodeColor NullPointerException");
		}
	}
		
	/** 
	 * Modifier method
	 * 
	 * set new note to the selected node.
	 * 
	 * @param nodeID
	 * 		the id which represents selected node from view.
	 * @param note
	 * 		new note text.
	 */
	public void setNodeNote(int nodeID, String note) {
		try {
			MindMapNode node = tree.getNode(nodeID);
			node.setNote(note);
		}catch (NullPointerException e) {
			System.out.println(e.getClass() + "setNodeNote NullPointerException");
		}
	}
	
	/**
	 * Accessor method
	 * 
	 * see if the editing mindmap has been saved before.
	 * 
	 * @return
	 * 		true if the mindmap has been saved before
	 */
	public boolean isSaved() {
		return fileManager.getFileName() != "" && fileManager.getPath() != "";
	}
	
	/**
	 * Modifier method
	 * 
	 * reset all the modules. and notify RESET command to 
	 * all Controllers to reset other Views.
	 */
	public void reset() {
		tree.removeAllNodes();
		stateTracker = new MindMapStateTracker();
		fileManager = new MindMapFileManager();	
		setChanged();
		notifyObservers("RESET");
	}

}












