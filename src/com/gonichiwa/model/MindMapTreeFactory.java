package com.gonichiwa.model;

import java.util.ArrayList;
import java.util.Scanner;

class MindMapTreeFactory {
	
	/**
	 * static Factory Method.
	 * 
	 * Algorithm
	 * 1. Tokenize the given text to lines using Scanner. and store as a simple stack structure; ArrayList<String>
	 * 2. First of all, make root node of the tree.
	 * 3. Build tree recursively by recMakeNode method.
	 * 
	 * @exception
	 * 		The first line of the text has a tab or text is empty
	 * 
	 * @param text
	 * 		the text which is given from TextAreaPane.
	 * @return
	 * 		the tree made by the given text.
	 */
	static MindMapNode build(String text) {
		MindMapTree tree = new MindMapTree();   // tree to be return
		Scanner scanner = new Scanner(text);	// scanner 
		ArrayList<String> lines = new ArrayList<String>();   // line stack
		
		// initialize stack
		while(scanner.hasNextLine()) 
			lines.add(scanner.nextLine());
		
		// exception for root node.
		if(lines.isEmpty())
			throw new IllegalArgumentException("invalid format text: check format");
		if(lines.get(0).contains("\t") || lines.get(0).equals(""))
			throw new IllegalArgumentException("invalid format text: invalid format at the root position");

		// make new Root Node
		tree.setRoot(new MindMapNode(lines.remove(0)));
		
		// build tree recursively
		recMakeNode(tree.getRoot(), lines, 1);
		
		return tree.getRoot();
	}
	
	
	/**
	 * 
	 * Helper Method 
	 * build tree recursively.
	 * 
	 * @exception
	 * 		1. there is child node which doesn't have a parent node.
	 * 		2. there are more than one root nodes.
	 * 
	 * @param parent
	 * 		the node, which will store children in method.
	 * @param lines
	 * 		the ArrayList<String> Object which is using as a stack.
	 * @param level
	 * 		the current level of the line.
	 * 		e.g. "\tSun\n" is level one.
	 */
	private static void recMakeNode(MindMapNode parent, ArrayList<String> lines, int level) {
		MindMapNode newChildNode = null;

		// if line has same level add them up to parent node.
		while(!lines.isEmpty()) {
			
			// if line has same level add them up to parent node.
			if(numberOfTabsIn(lines.get(0)) == level) {
				String name = lines.remove(0).replaceAll("\t", "");     // remove tab in the line.
				newChildNode = new MindMapNode(name);				// make new node.
				parent.addChild(newChildNode);
				
			// when nextLine has more big level.
			} else if(numberOfTabsIn(lines.get(0)) > level) {
				if(newChildNode == null)		// exception for child node which doesn't have its own parent node.
					throw new IllegalArgumentException("invalid format text: child node must have its parent");
				recMakeNode(newChildNode, lines, level+1);
				
			// when nextLine has more small level.
			} else if(numberOfTabsIn(lines.get(0)) < level) {
				if(level == 1)    // exception for multiple root node.
					throw new IllegalArgumentException("invalid format text: root node must be one");
				return;
			}
		}
	}
	
	public static MindMapNode loadNewTree(MindMapNode root) {
		MindMapNode newRoot = new MindMapNode(root);
		System.out.println("new Root is " + newRoot);
		recLoadNode(newRoot, root);
		return newRoot;
	}
	
	private static void recLoadNode(MindMapNode node, MindMapNode target) {
		System.out.println("load new node " + node.getName());
		for(int i = 0; i < target.getChildren().size(); i++) {
			node.addChild(new MindMapNode(target.getChildren().get(i)));
			recLoadNode(node.getChildren().get(i), target.getChildren().get(i));
		}
		
	}
	
	/**
	 * Helper Method
	 * 
	 * count how many tabs in the given string.
	 * 
	 * @param line
	 * 		the string which might contain tabs
	 * @return
	 * 		the number of tabs where the given line contains.
	 */
	private static int numberOfTabsIn(String line) {
		int count = 0;
		while (line.contains("\t")) {
			count += 1;
			line = line.replaceFirst("\t", "");
		}
		return count;
	}
}
