package main.java;

import main.java.vue.MainApplication;

public class Main {

	public static void main(String[] args) {
		if (args.length > 0 && args[0].equals("-c"))
			new JeuConsole();
		else
			MainApplication.open();
	}

}
