package test.java.utils;

import static org.junit.jupiter.api.Assertions.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javafx.scene.image.Image;
import main.java.utils.Utils;

public class TestUtils {
	
	@Test
	public void testComparerImage() {
		Image imgTest;
		try {
			imgTest = new Image(new File("src/test/resources/testimg.jpg").toURI().toURL().toString());
			Assertions.assertTrue(Utils.comparerImages(imgTest, imgTest),"Deux images similaires devraient retourner true, mais elles retournent false.");	
			Assertions.assertTrue(!Utils.comparerImages(imgTest, Utils.getSubImage(0, 0, 5, 5, imgTest)),"Deux images différentes devraient retourner false, mais elles retournent true");
		} catch (IOException e) {
			fail("Erreur lors du chargement de l'image de test");
			e.printStackTrace();
		}
	}

}
