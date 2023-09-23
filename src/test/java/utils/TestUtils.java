package test.java.utils;

import static org.junit.jupiter.api.Assertions.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import main.java.utils.Utils;

class TestUtils {
	
	@Test
	public void testComparerImage() {
		BufferedImage imgTest;
		try {
			imgTest = ImageIO.read(new File("src/main/resources/testimg.jpg"));
			Assertions.assertTrue(Utils.comparerImages(imgTest, imgTest),"Deux images similaires devraient retourner true, mais elles retournent false.");	
			Assertions.assertTrue(!Utils.comparerImages(imgTest, imgTest.getSubimage(0, 0, 5, 5)),"Deux images différentes devraient retourner false, mais elles retournent true");
		} catch (IOException e) {
			fail("Erreur lors du chargement de l'image de test");
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreateTransparentBufferedImage() {
		BufferedImage imgTest;
		try {
			imgTest = ImageIO.read(new File("src/main/resources/test/image-1.png"));
			Assertions.assertTrue(
					Utils.comparerImages(imgTest, Utils.createTransparentBufferedImage(imgTest.getWidth(), imgTest.getHeight())),
					"l'image transparente crée ne correspond pas à l'image attendue"
					);
		}catch(IOException e){
			fail("Erreur lors du chargement de l'image de test");
			e.printStackTrace();
		}
	}

}
