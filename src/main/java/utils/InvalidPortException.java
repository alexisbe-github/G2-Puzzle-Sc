package main.java.utils;

public class InvalidPortException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static int PORT_MIN = 1024;
	private final static int PORT_MAX = 65535;

	public InvalidPortException(int portInvalide) {
		super("Veuillez entrer un port entre " + PORT_MIN + " et " + PORT_MAX + ", le port entré:" + portInvalide
				+ " est invalide.");
	}
}
