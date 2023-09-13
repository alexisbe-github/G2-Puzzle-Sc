package main.java.model;

import java.awt.image.BufferedImage;

public class Case {

	private int index;
	private BufferedImage image;
	
	/**
	 * 
	 * @param index : index de la case
	 * @param image : image de la case
	 */
	public Case(int index, BufferedImage image) {
		this.index=index;
		this.image=image;
	}
	
	/**
	 * 
	 * @param index : index de la case
	 */
	public Case(int index) {
		this.index=index;
	}
	
	
	public int getIndex() {
		return this.index;
	}
	

	public BufferedImage getImage() {
		return this.image;
	}
	
	@Override
	public String toString() {
		return "["+this.index+"]";
	}
	
}
