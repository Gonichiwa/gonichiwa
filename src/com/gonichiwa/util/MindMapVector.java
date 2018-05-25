package com.gonichiwa.util;

public class MindMapVector {
	private double x, y;
	
	public MindMapVector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void mult(double value) {
		x *= value;
		y *= value;
	}
	
	public double mag() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public MindMapVector normalize() {
		x /= mag();
		y /= mag();
		return this;
	}
	
	public MindMapVector rotate(double angle) {
		double prevX = x;
		double prevY = y;
		x = Math.cos(angle) * prevX - Math.sin(angle) * prevY;
		y = Math.sin(angle) * prevX + Math.cos(angle) * prevY;
		return this;
	}
	
	public MindMapVector copy() {
		return new MindMapVector(x, y);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
}
