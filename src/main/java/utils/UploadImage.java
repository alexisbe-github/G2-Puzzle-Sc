package main.java.utils;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import javafx.scene.image.Image;

public class UploadImage {

	private static final String API_CLE = "6d207e02198a847aa98d0a2a901485a5";
	private static final String API_URL = "https://freeimage.host/api/1/upload?key=" + API_CLE;

	public static String uploadImage(String cheminImage) {
		try {
			// Charger l'image depuis le chemin spécifié
			byte[] imageBytes = Utils.imageToByteArray(new Image(cheminImage), "png"); // Files.readAllBytes(Paths.get(cheminImage));
			String base64Image = Base64.getEncoder().encodeToString(imageBytes);

			try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
				HttpPost httpPost = new HttpPost(API_URL);

				// Add headers to the request
				httpPost.setHeader("Content-type", "application/json");

				// Set the request body
				String request = "{\"key\":\"%s\", \"action\":\"upload\", \"source\": \"%s\", \"format\": \"json\"}";
				request = String.format(request, API_CLE, base64Image);
				System.out.println(request);
				StringEntity entity = new StringEntity(request);
				httpPost.setEntity(entity);
				
				// Execute the request and obtain the response
				HttpResponse httpResponse = httpClient.execute(httpPost);

				// Extract the response's content
				HttpEntity responseEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(responseEntity);

				// Print the response
				return(response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String parseImageUrl(String reponse) {
		System.out.println(reponse);
		return "URL_de_l_image";
	}

	public static void main(String[] args) throws IOException {
		// Exemple d'utilisation
		String imagePath = "https://upload.wikimedia.org/wikipedia/commons/4/47/PNG_transparency_demonstration_1.png";// "src/main/resources/images/defaulticon.png";
		System.err.println(Utils.imageToByteArray(new Image(imagePath), "png") != null);
		String uploadedImageUrl = uploadImage(imagePath);

		if (uploadedImageUrl != null) {
			System.out.println("L'image a été téléchargée avec succès. URL : " + uploadedImageUrl);
		} else {
			System.out.println("Une erreur s'est produite lors du téléchargement de l'image.");
		}
	}
}
