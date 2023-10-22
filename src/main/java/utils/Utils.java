package main.java.utils;

import javafx.scene.paint.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Utils {
	
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
	public static boolean comparerImages(Image imga, Image imgb) {
	  if (imga.getWidth() != imgb.getWidth() || imga.getHeight() != imgb.getHeight()) {
	    return false;
	  }

	  PixelReader imgar = imga.getPixelReader(); 
	  PixelReader imgbr = imgb.getPixelReader();
	  
	  int width  = (int) imga.getWidth();
	  int height = (int) imga.getHeight();

	  for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {

	      if (!imgar.getColor(x, y).equals(imgbr.getColor(x, y))) { //vérifie l'identité de chaque pixels
	        return false;
	      }
	      
	    }
	  }
	  return true;
	}
	
	
	public static Image getSubImage(int x, int y, int w, int h, Image stripImg)
    {
        PixelReader pr = stripImg.getPixelReader();
        WritableImage wImg = new WritableImage(w, h);
        PixelWriter pw = wImg.getPixelWriter();
        
        for( int readY = y ; readY < y + h; readY++ ) {
        	for( int readX = x; readX < x + w; readX++ ) {
                //Obtenir le pixels aux coordonnées X et Y
                Color color = pr.getColor( readX, readY );
                //Appliquer le pixel à la WritableImage à l'aide du Pixel Writer
                pw.setColor(readX-x, readY-y, color);
            }//X
        }//Y
        return wImg;
    }//
	
	
}
