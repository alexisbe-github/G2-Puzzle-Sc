package main.java.model;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

import main.java.utils.Utils;

public class Puzzle {

	public static final int TAILLE_MINI = 3;
	private final int TAILLE;
	private Case[][] grille;
	private BufferedImage image;

	/**
	 * Définit la taille du puzzle : si inferieur à 3, remise automatiquement à 3.
	 * 
	 * @param taille du Puzzle (si 4 -> 4x4).
	 */
	public Puzzle(int taille) {
		this.TAILLE = (taille > TAILLE_MINI ? taille : TAILLE_MINI);
		this.grille = new Case[this.TAILLE][this.TAILLE];
		this.initGrille();
	}

	/**
	 * Définit l'image et la taille du puzzle : si inferieur à 3, remise
	 * automatiquement à 3.
	 * 
	 * @param imgSrc : image du puzzle
	 * @param taille du Puzzle (si 4 -> 4x4).
	 */
	public Puzzle(int taille, BufferedImage image) {
		this(taille);
		this.image = image;
		this.initGrille();
		this.decoupageImage();
	}

	/**
	 * Initialise la grille avec des cases de valeurs allant de 0 à
	 */
	private void initGrille() {
		int compteur = 1;
		for (int i = 0; i < this.TAILLE; i++) {
			for (int j = 0; j < this.TAILLE; j++) {
				this.grille[j][i] = new Case(compteur);
				compteur++;
			}
		}

		this.grille[this.TAILLE - 1][this.TAILLE - 1] = new Case(Case.INDEX_CASE_VIDE);
		this.melanger();
	}

	/**
	 * Mélange la grille (déplace la case vide de TAILLE^4 dans une direction
	 * aléatoire pour laisser le taquin soluble).
	 */
	public void melanger() {
		Random rd = new Random();
		do {
			for (int i = 0; i < Math.pow(this.TAILLE, 4); i++) {
				int x = Utils.getRandomNumberInRange(0, 3);
				switch (x) {
				case 0:
					this.deplacerCase(EDeplacement.HAUT);
					break;
				case 1:
					this.deplacerCase(EDeplacement.BAS);
					break;
				case 2:
					this.deplacerCase(EDeplacement.GAUCHE);
					break;
				case 3:
					this.deplacerCase(EDeplacement.DROITE);
					break;
				}
			}
		} while (this.verifierGrille()); // Permet d'éviter de se retrouver avec une grille ordonnée malgré le mélange
	}

	/**
	 * déplace la case vide dans une direction.
	 * 
	 * @param Enumeration de direction (EDeplacement) : HAUT -> y-1, BAS -> y+1,
	 *                    GAUCHE -> x-1, DROITE -> x+1.
	 */
	public void deplacerCase(EDeplacement dp) {
		int oldCoordX = this.getXCaseVide();
		int oldCoordY = this.getYCaseVide();
		int newCoordX = oldCoordX;
		int newCoordY = oldCoordY;
		switch (dp) {
		case HAUT:
			newCoordY += 1;
			break;
		case BAS:
			newCoordY += -1;
			break;
		case GAUCHE:
			newCoordX += 1;
			break;
		case DROITE:
			newCoordX += -1;
			break;
		}
		if (newCoordX < this.TAILLE && newCoordX >= 0 && newCoordY < this.TAILLE && newCoordY >= 0) {
			this.echangerCase(new Point(oldCoordX, oldCoordY), new Point(newCoordX, newCoordY));
		}
	}

	/**
	 * Permet d'échanger l'emplacement des deux cases dans la grille.
	 * 
	 * @param p1 Point de la première case
	 * @param p2 Point de la deuxième case
	 */
	private void echangerCase(Point p1, Point p2) {
		try {
			Case tempCase = grille[p1.x][p1.y];
			this.grille[p1.x][p1.y] = this.grille[p2.x][p2.y];
			this.grille[p2.x][p2.y] = tempCase;
		} catch (ArrayIndexOutOfBoundsException e) {
			// Ne pas déplacer les cases si les coordonnées sont éronnées
		}
	}

	/**
	 * Vérifie si la grille est dans son état final.
	 * 
	 * @return TRUE si la grille est terminée, FALSE sinon
	 */
	public boolean verifierGrille() {
		int last = -1;
		for (int i = 0; i < this.TAILLE; i++) {
			for (int j = 0; j < this.TAILLE; j++) {
				if (this.grille[j][i].getIndex() <= last && !(i == TAILLE - 1 && j == TAILLE - 1))
					return false;
				last = this.grille[j][i].getIndex();
			}
		}
		if (this.grille[TAILLE - 1][TAILLE - 1].getIndex() != -1)
			return false;
		return true;
	}

	public Case[][] getGrille() {
		return this.grille;
	}

	public void setGrille(Case[][] grille) {
		this.grille = grille;
	}

	/**
	 * Getter coordonnée X Case vide
	 * 
	 * @return coordonnée X de la case vide (0 à gauche -> taille-1 à droite)
	 */
	public int getXCaseVide() {
		int res = -1;
		for (int i = 0; i < this.grille.length; i++) {
			for (int j = 0; j < this.TAILLE; j++) {
				if (this.grille[i][j].getIndex() == Case.INDEX_CASE_VIDE) {
					res = i;
				}
			}
		}
		return res;
	}

	/**
	 * Getter coordonnée Y Case vide
	 * 
	 * @return coordonnée Y de la case vide (0 en haut -> taille-1 à en bas)
	 */
	public int getYCaseVide() {
		int res = -1;
		for (int i = 0; i < this.grille.length; i++) {
			for (int j = 0; j < this.TAILLE; j++) {
				if (this.grille[i][j].getIndex() == Case.INDEX_CASE_VIDE) {
					res = j;
				}
			}
		}
		return res;
	}

	/**
	 * Permet de découper l'image en images de tailles égales correspondant a
	 * l'index de chaque cases.
	 * 
	 */
	public void decoupageImage() {
		// Largeur et hauteur des sous-images
		int height = this.image.getHeight() / this.TAILLE;
		int width = this.image.getWidth() / this.TAILLE;
		// Parcours de la grille
		for (int i = 0; i < this.TAILLE; i++) {
			for (int j = 0; j < this.TAILLE; j++) {
				// Initialisation de la sous image
				BufferedImage subImg;
				if (!(j == this.getYCaseVide() && i == this.getXCaseVide())) { // Si la case n'est pas la case vide
					subImg = this.image.getSubimage(width * j, height * i, width, height); // "Découpe" de l'image
				} else {
					subImg = Utils.createTransparentBufferedImage(width, height); // Sinon image transparent de la même
																					// taille
				}
				this.grille[j][i].setImage(subImg);
			}
		}
	}

	/**
	 * Récupération de la Case a une coordonnée
	 * 
	 * @param x : Coordonnée x de la case (de gauche à droite)
	 * @param y : Coordonnée y de la case (de haut en bas)
	 * @return La case aux coordonnées demandées.
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public Case getCase(int x, int y) throws ArrayIndexOutOfBoundsException {
		return grille[x][y];
	}

	public int getTaille() {
		return this.TAILLE;
	}

	@Override
	public String toString() {
		String res = "";
		for (int i = 0; i < this.TAILLE; i++) {
			for (int j = 0; j < this.TAILLE; j++) {
				res += this.grille[j][i];
				if (j == this.TAILLE - 1)
					res += "\n";
				else
					res += " / ";
			}
		}
		return res;
	}

}
