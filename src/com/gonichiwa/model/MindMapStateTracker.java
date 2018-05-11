package com.gonichiwa.model;

import java.util.ArrayList;

//TODO: not be tested yet
class MindMapStateTracker {
	private ArrayList<MindMapTree> stateStack;
	private int current;
	
	MindMapStateTracker() {
		stateStack = new ArrayList<MindMapTree>();
		current = -1;
	}
	
	public void pushNewState(MindMapTree newState) {
		if(current < stateStack.size()-1) {
			for(int i = current+1; i < stateStack.size(); i++) {
				stateStack.remove(i);
			}
			stateStack.trimToSize();
		}
		stateStack.add(newState);
		current++;
	}
	
	public MindMapTree getBackwardState() {
		if(--current < 0) 
			current = 0;
		return stateStack.get(current);
	}
	
	public MindMapTree getForwardState() {
		if(++current > stateStack.size()) 
			current = stateStack.size()-1;
		return stateStack.get(current);
	}
	
	public void setStateStack(ArrayList<MindMapTree> newStateStack) {
		stateStack = newStateStack;
	}
}
