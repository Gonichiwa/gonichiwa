package com.gonichiwa.model;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Observable;

public class MindMapTree extends Observable implements Cloneable {
	private MindMapNode root;
	
	/**
	 * constructor
	 */
	MindMapTree() {
		root = null;
	}
	
	public MindMapTree clone() throws CloneNotSupportedException {
		return (MindMapTree) super.clone();
		
	}

	/**
	 * Modifier
	 * build tree with a given text.
	 * @param text
	 * 		the text which is given from TextAreaPane.
	 */

	public void buildTree(String text) {
		root = MindMapTreeFactory.build(text);
		setChanged();
		notifyObservers("NEW");
	}
	
	public void loadTree(MindMapNode root) {
		this.root = MindMapTreeFactory.loadNewTree(root);
		setChanged();
		notifyObservers("LOAD");
	}
	
	public MindMapNode getRoot() {
		return root;
	}
	
	public void setRoot(MindMapNode node) {
		root = node;
	}
	
	public void changeNodeName(int nodeID, String name) {
		MindMapNode node = getNode(nodeID);
		if(node != null) {
			node.setName(name);
			setChanged();
			notifyObservers();
		}
		
	}
	
	/**
	 * Modifier
	 * 
	 * remove all nodes in the current tree.
	 */
	public void removeAllNodes() {
		MindMapNode.initIDGenerator();
		if(root != null)
			root = null;
	}
	
	/**
	 * remove certain node which has given id.
	 * @param nodeID
	 * 		id of the node which we want to modify.
	 */
	public void removeNode(int nodeID) {
		recRemoveNode(root, nodeID);
		setChanged();
		notifyObservers("LOAD");
	}
	
	private void recRemoveNode(MindMapNode node, int nodeID) {
		if(node.getID() == nodeID)
			throw new IllegalArgumentException("can not remove root node");
		for(MindMapNode child : node.getChildren()) {
			if(child.getID() == nodeID) {
				node.removeChild(nodeID);
				return;
			}
			recRemoveNode(child, nodeID);
		}
	}
	
	/**
	 * Accessor
	 * 
	 * traverse the tree and return the number of nodes.
	 * @return
	 * 		the number of nodes in the tree.
	 */
	
	public int size() {
		return recGetSize(root);
	}
	 /**
	  * Helper Method
	  * get size of the tree recursively
	  * @param node
	  * 	the node to be counted in the tree.
	  * @return
	  * 	the size of the tree
	  */
	private int recGetSize(MindMapNode node) {
		int count = 1;
		
		if(node == null) 
			return 0;
		
		for(MindMapNode child : node.getChildren()) 
			count += recGetSize(child);

		return count;
	}
	
	/**
	 * Accessor
	 * 
	 * find the certain node by it's id.
	 * @param nodeID
	 * 		id of the node which we want to find.
	 * @return
	 * 		the node which has given id in the tree.
	 */
	public MindMapNode getNode(int nodeID) {
		MindMapNode foundNode = recGetNode(nodeID, root);
		if(foundNode == null)
			throw new NoSuchElementException("MindMapTree.getNode() -> there is no such node");
		return recGetNode(nodeID, root);
	}
	
	/**
	 * Helper Method
	 * find the certain node recursively.
	 * @param nodeID
	 * 		id of the node which we want to find.
	 * @param target
	 * 		the node to be examined.
	 * @return
	 * 		the node which has given id in the tree.
	 */
	private MindMapNode recGetNode(int nodeID, MindMapNode target) {
		MindMapNode foundNode = null;
		
		if(target.getID() == nodeID) {
			return target;
		} else {
			for(MindMapNode child : target.getChildren()) {
				foundNode = recGetNode(nodeID, child);
				if(foundNode != null) 
					return foundNode;
			}
		}
		return foundNode;
	}
	
	
	/**
	 * convert tree to String.
	 * see the example below.
	 * 
	 * e.g. 
	 * 
	 * Animal('\n')
	 * ('\t')Mammal('\n')
	 *       ('\t')Human('\n')
	 *       ('\t')Monkey('\n')
	 *     
	 * 
	 * 
	 */
	public String toString() {		
		return recToString(root, 0);
	}
	
	private String recToString(MindMapNode node, int level) {
		if(node == null) return null;
		
		String string = "";
		
		for(int i = 0; i < level; i++) 
			string += '\t';
		string += node.getName();
		string += '\n';
		
		for(MindMapNode child : node.getChildren()) {
			string += recToString(child, level+1);
		}
		
		return string;
	}
	
}
