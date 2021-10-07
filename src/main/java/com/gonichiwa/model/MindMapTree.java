package com.gonichiwa.model;

import java.util.NoSuchElementException;
import java.util.Observable;

/**
 * 
 * MindMapTree
 * 
 * tree structure for the MindMapNode.
 * there is no limited number of children to any nodes.
 * each node has it's own unique id to be found.
 * 
 * @author YONG_JOON_KIM
 *
 */
public class MindMapTree extends Observable {
	private MindMapNode root;

	/**
	 * constructor
	 */
	MindMapTree() {
		root = null;
	}

	/**
	 * Modifier method
	 * 
	 * build tree with a given text using MindMapTreeFactory class
	 * notify observers with NEW command
	 * 
	 * @param text
	 * 		the text which is given from MindMapTextController.
	 */
	public void buildTree(String text) {
		root = MindMapTreeFactory.build(text);
		setChanged();
		notifyObservers("NEW");
	}

	/**
	 * Accessor method
	 * 
	 * @return
	 * 		root node of this tree.
	 */
	public MindMapNode getRoot() {
		return root;
	}
	
	/**
	 * Accessor method
	 *
	 * traverse the tree and return the number of nodes.
	 * 
	 * @return
	 * 		the number of nodes in the tree.
	 */

	public int size() {
		return recGetSize(root);
	}
	
	 /**
	  * Helper Method
	  * 
	  * get size of the tree recursively
	  * 
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
	 * Accessor method
	 *
	 * find the certain node by it's id.
	 * 
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
	 * 
	 * find the certain node recursively.
	 * 
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
	 * Modifier method
	 * 
	 * set new root to this tree.
	 * 
	 * @param node
	 * 		new node to be set.
	 */
	public void setRoot(MindMapNode node) {
		root = node;
		setChanged();
		notifyObservers("LOAD");
	}

	/**
	 * Modifier method
	 *
	 * remove all nodes in the current tree.
	 */
	public void removeAllNodes() {
		MindMapNode.initIDGenerator();
		if(root != null)
			root = null;
	}

	/**
	 * Modifier method
	 * 
	 * remove certain node which has given id.
	 * @param nodeID
	 * 		id of the node to be removed.
	 */
	public void removeNode(int nodeID) {
		recRemoveNode(root, nodeID);
		setChanged();
		notifyObservers("LOAD");
	}

	/**
	 * Helper method
	 * 
	 * find node to remove recursively and remove it.
	 * 
	 * @param node
	 * 		the parent node to be examined this iteration.
	 * @param nodeID
	 * 		id of the node to be removed.
	 */
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
	 * convert tree to String.
	 * see the example below.
	 *
	 * e.g.
	 *
	 * Animal('\n')
	 * ('\t')Mammal('\n')
	 *       ('\t')Human('\n')
	 *       ('\t')Monkey('\n')
	 */
	@Override
	public String toString() {
		return recToString(root, 0);
	}

	/**
	 * Helper method 
	 * 
	 * transform this tree structure to String data recursively
	 * 
	 * @param node
	 * 		the current node being examined.
	 * @param level
	 * 		the current level of the tree hierarchy.
	 * @return
	 * 		accumulated String data.
	 */
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
