package main.java.model;

import java.beans.PropertyChangeSupport;

public class Chrono {

	int seconds = 0;
	boolean isRunning = false; //Booleen qui gère si le thread tourne ou pas.

	/*
	 * Lance le chronomètre. Incrémente la variable seconde toutes les 1000ms.
	 */
	public void lancerChrono() {
		isRunning = true;
		Thread th = new Thread() {
			@Override
			public void run() {
				while (isRunning) {
					try {
						Thread.sleep(1000);
						seconds++;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		th.run();
	}

	/*
	 * interrompt le chronomètre
	 */
	public void stopChrono() {
		isRunning = false;
	}

	/**
	 * 
	 * @return String qui contient le temps écoulé : m:s. exemple : 6:22.
	 */
	public String getMS() {
		int m = (int) (seconds / 60);
		int s = (int) (seconds % 60);
		String r = "";
		if (m > 0) {
			r += m + ":";
		}
		if (s > 0) {
			r += s;
		}
		if (m <= 0 && s <= 0) {
			r = "0";
		}
		return r;
	}

}
