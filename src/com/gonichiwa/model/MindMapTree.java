package com.gonichiwa.model;

import java.util.ArrayList;

class MindMapTree {
	private MindMapNode root;
	
	/**
	 * constructor
	 */
	MindMapTree() {
		root = null;
	}
	
	public MindMapNode getRoot() {
		return root;
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
		
		MindMapNode tempNode = getNode(nodeID,root);
		
		for(int i= 0;i<tempNode.getParent().getChildren().size();i++) {
			if(tempNode.getParent().getChildren().get(i).getID()== nodeID) {
				tempNode.getParent().getChildren().remove(i);
				break;
			}
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
		int num=0;	
		num += returnArraySize(root);
		return num+1;
	}
	public int returnArraySize(MindMapNode Node) {
		
		int num=0;
		
		for(int i=0;i<Node.getChildren().size();i++) {
			if(Node.getChildren().get(i).getChildren().size()!=0) {
				num += returnArraySize(Node.getChildren().get(i));
			}
		}
		return num;
		
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
	public MindMapNode getNode(int nodeID, MindMapNode StartNode) {
		
		ArrayList<MindMapNode> tempArray;
		MindMapNode NodePointer;
		NodePointer = StartNode;
		MindMapNode TempNode;
		
		if(NodePointer.getID()==nodeID) {
			return NodePointer;
		}

			tempArray = NodePointer.getChildren();
			if(tempArray.size()!=0) {
				for(int i=0;i<tempArray.size();i++)
				{
					if(tempArray.get(i).getID()==nodeID) {
						return tempArray.get(i);
					}
					if(tempArray.get(i).getChildren().size() !=0) {
						TempNode =getNode(nodeID,tempArray.get(i));
						if(TempNode != null) {
							return TempNode;
						}
					}
				}
			}
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
	public String toString(MindMapNode Node) {
		return null;
	}
	
}
