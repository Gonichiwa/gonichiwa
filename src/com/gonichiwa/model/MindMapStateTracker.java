package com.gonichiwa.model;

import java.util.ArrayList;
import java.util.Stack;

//TODO: not be tested yet
class MindMapStateTracker {
	private ArrayList<MindMapTree> stateStack;
	private Stack<MindMapTree> stack;
	private int current;
	
	MindMapStateTracker() {
//		stateStack = new ArrayList<MindMapTree>();
		stack = new Stack<MindMapTree>();
		current = -1;
	}
	
	public void pushNewState(MindMapTree newState) {
		stack.push(newState);
		for(MindMapTree str: stack) {
			System.out.println(str);
		}
//		if(current < stateStack.size()-1) {
//			for(int i = current+1; i < stateStack.size(); i++) {
//				stateStack.remove(i);
//			}
//			stateStack.trimToSize();
//		}
//		stateStack.add(newState);
//		current++;
	}
	
	public MindMapTree getBackwardState() {
//		if(stateStack.size() <= 0)
//			throw new IllegalArgumentException("empty stack");
//		if(--current < 0) 
//			current = 0;
//		return stateStack.get(current);
		return stack.pop();
	}
	
	public MindMapTree getForwardState() {
		if(++current > stateStack.size() && stateStack.size() > 0) 
			current = stateStack.size()-1;
		return stateStack.get(current);
	}
	
	public void setStateStack(ArrayList<MindMapTree> newStateStack) {
		stateStack = newStateStack;
	}
}
