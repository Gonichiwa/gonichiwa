package com.gonichiwa.util;

/**
 * MindMapVector 
 * 
 * utility class for calculating initial position of the nodeView
 * 
 * @author YONG_JOON_KIM
 *
 */
public class MindMapVector {
	private double x, y;
	
	public MindMapVector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Modifier & Accessor method
	 * 
	 * multiply vector by the given value
	 * 
	 * @param value
	 * 		the amount to be multiplied.
	 * @return
	 * 		calculated vector
	 */
	public MindMapVector mult(double value) {
		x *= value;
		y *= value;
		return this;
	}
	
	/**
	 * Accessor method
	 * 
	 * @return
	 * 		magnitude of current vector
	 */
	public double mag() {
		return Math.sqrt(x*x + y*y);
	}
	
	/**
	 * Modifier & Accessor method
	 * 
	 * divide vector by the given value
	 * 
	 * @param value
	 * 		the amount to be divided.
	 * @return
	 * 		calculated vector
	 */
	public MindMapVector div(double value) {
		x /= value;
		y /= value;
		return this;
	}
	
	/**
	 * Modifier & Accessor method
	 * 
	 * make current vector to unit vector
	 * 
	 * @return
	 * 		unit vector of previous vector
	 */
	public MindMapVector normalize() {
		double m = mag();
		if(m != 0 && m != 1) {
			div(m);
		}
		return this;
	}
	
	/**
	 * Modifier & Accessor method
	 * 
	 * rotate current vector by the given angle
	 * 
	 * @param angle
	 * 		radian value to be rotated
	 * @return
	 * 		calculated vector
	 */
	public MindMapVector rotate(double angle) {
		double prevX = x;
		double prevY = y;
		x = Math.cos(angle) * prevX - Math.sin(angle) * prevY;
		y = Math.sin(angle) * prevX + Math.cos(angle) * prevY;
		return this;
	}
	
	/**
	 * Accessor method
	 * 
	 * copy current vector
	 * 
	 * @return
	 * 		copied vector
	 */
	public MindMapVector copy() {
		return new MindMapVector(x, y);
	}
	
	/**
	 * Accessor method
	 * 
	 * @return
	 * 		x point
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Accessor method
	 * 
	 * @return
	 * 		y point
	 */
	public double getY() {
		return y;
	}
}
