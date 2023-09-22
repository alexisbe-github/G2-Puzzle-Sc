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
	
	/**
	 * Compare deux images pixel par pixel
	 *
	 * @param imga premiere image
	 * @param imgb deconde image.
	 * @return true si les deux images correspondent.
	 */
	public static boolean comparerImages(BufferedImage imga, BufferedImage imgb) {
	  if (imga.getWidth() != imgb.getWidth() || imga.getHeight() != imgb.getHeight()) {
	    return false;
	  }

	  int width  = imga.getWidth();
	  int height = imga.getHeight();

	  for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {

	      if (imga.getRGB(x, y) != imgb.getRGB(x, y)) { //vérifie l'identité de chaque pixels
	        return false;
	      }
	      
	    }
	  }
	  return true;
	}
	
}
