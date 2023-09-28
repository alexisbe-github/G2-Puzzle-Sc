package main.java.model.api;

import java.util.ArrayList;
import java.util.List;

import main.java.model.bdd.dao.DAOJoueur;
import main.java.model.bdd.dao.DAOPartie;
import main.java.model.bdd.dao.DAOPartieCompetitive;
import main.java.model.bdd.dao.DAOPartieCooperative;
import main.java.model.bdd.dao.beans.JoueurSQL;
import main.java.model.bdd.dao.beans.PartieCompetitiveSQL;
import main.java.model.bdd.dao.beans.PartieCooperativeSQL;
import main.java.model.bdd.dao.beans.PartieSQL;

/**
 * Classe contenant localement les données devant transiter par l'API REST
 */
public class DonneesLocales {

	/**
	 * Le singleton représentant l'instance de la classe
	 */
	private static DonneesLocales instance;

	private List<JoueurSQL> listeJoueurs = new ArrayList<>();
	private List<PartieSQL> listeParties = new ArrayList<>();
	private List<PartieCompetitiveSQL> listePartiesCompetitives = new ArrayList<>();
	private List<PartieCooperativeSQL> listePartiesCooperatives = new ArrayList<>();

	/**
	 * @return L'instance de la classe
	 */
	public static DonneesLocales getInstance() {
		if (instance == null) {
			instance = new DonneesLocales();
		}
		return instance;
	}

	private DonneesLocales() {
		listeJoueurs = new DAOJoueur().trouverTout();
		listeParties = new DAOPartie().trouverTout();
		listePartiesCompetitives = new DAOPartieCompetitive().trouverTout();
		listePartiesCooperatives = new DAOPartieCooperative().trouverTout();
	}

}
