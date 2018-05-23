package com.gonichiwa.util;

public class MindMapVector {
	double x, y;
	
	public MindMapVector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void mult(int value) {
		x *= value;
		y *= value;
	}
	
	public double mag() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public void normalize() {
		x /= mag();
		y /= mag();
	}
	
	public void rotate(double angle) {
		x = Math.cos(angle) * x - Math.sin(angle) * y;
		y = Math.sin(angle) * x + Math.cos(angle) * y;
	}
	
	public MindMapVector copy() {
		return new MindMapVector(x, y);
	}
	
}
