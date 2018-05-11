package com.gonichiwa.model;

class MindMapTree {
	private MindMapNode root;
	
	/**
	 * constructor
	 */
	MindMapTree() {
		root = null;
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
		//TODO: cheer up~!
	}
	
	/**
	 * Accessor
	 * 
	 * traverse the tree and return the number of nodes.
	 * @return
	 * 		the number of nodes in the tree.
	 */
	public int size() {
		//TODO: good luck!
		return 0;
	}
	
	/**
	 * Accessor
	 * 
	 * find the certain node by it's id.
	 * @param nodeID
	 * 		id of the node which we want to modify.
	 * @return
	 * 		the node which has given id in the tree.
	 */
	public MindMapNode getNode(int nodeID) {
		//TODO: Thank you!~
		return null;
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
		//TODO: for Joon
		return null;
	}
	
}
