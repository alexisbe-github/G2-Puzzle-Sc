package main.java.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Utils {

	static public BufferedImage createTransparentBufferedImage(int width, int height) {
	     BufferedImage bufferedImage = new BufferedImage(width, height, 
	                        BufferedImage.TYPE_INT_ARGB);
	     Graphics2D graphics = bufferedImage.createGraphics();

	     graphics.setBackground(new Color(0, true));
	     graphics.clearRect(0, 0, width, height);
	     graphics.dispose();

	     return bufferedImage;
	  }
	
}
