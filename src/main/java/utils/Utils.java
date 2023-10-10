package main.java.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Random;

public class Utils {

	public static BufferedImage createTransparentBufferedImage(int width, int height) {
	     BufferedImage bufferedImage = new BufferedImage(width, height, 
	                        BufferedImage.TYPE_INT_ARGB);
	     Graphics2D graphics = bufferedImage.createGraphics();

	     graphics.setBackground(new Color(0, true));
	     graphics.clearRect(0, 0, width, height);
	     graphics.dispose();

	     return bufferedImage;
	  }
	
	/**
	 * Génére un int entre min et max inclus
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("Max doit être supérieur à min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
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
