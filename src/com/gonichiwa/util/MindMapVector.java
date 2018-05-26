package com.gonichiwa.util;

public class MindMapVector {
	private double x, y;
	
	public MindMapVector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public MindMapVector mult(double value) {
		x *= value;
		y *= value;
		return this;
	}
	
	public double mag() {
		return Math.sqrt(x*x + y*y);
	}
	
	public MindMapVector div(double value) {
		x /= value;
		y /= value;
		return this;
	}
	
	public MindMapVector normalize() {
		double m = mag();
		if(m != 0 && m != 1) {
			div(m);
		}
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
