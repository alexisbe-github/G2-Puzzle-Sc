package main.java.model;

import java.awt.image.BufferedImage;
import java.util.Objects;

import javafx.scene.image.Image;

public class Case {

	public final static int INDEX_CASE_VIDE = -1;
	private int index;
	private Image image;

	/**
	 * 
	 * @param index : index de la case
	 * @param image : image de la case
	 */
	public Case(int index, Image image) {
		this.index = index;
		this.image = image;
	}

	/**
	 * 
	 * @param index : index de la case
	 */
	public Case(int index) {
		this.index = index;
	}

	public void setImage(Image img) {
		this.image = img;
	}

	public int getIndex() {
		return this.index;
	}

	public Image getImage() {
		return this.image;
	}

	@Override
	public String toString() {
		return "[" + this.index + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(index);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Case other = (Case) obj;
		return Objects.equals(image, other.image) && index == other.index;
	}

}
