package main.java.model;

public class Puzzle {
	
	private int taille;
	private Case[][] grille;
	
	/**
	 * 
	 * @param taille du Puzzle (si 4 -> 4x4).
	 */
	Puzzle(int taille){
		this.taille=taille;
		this.grille=new Case[4][4];
	}
	
	/**
	 * Mélange la grille (place chaque case à une place aléatoire).
	 */
	private void melanger() {
		//TODO
	}
	
	/**
	 * 
	 * @param nouvelle grille.
	 */
	private void setGrille(Case[][] grille) {
		this.grille=grille;
	}
	
	/**
	 * 
	 * @return coordonnée X de la case vide (0 à gauche -> taille-1 à droite)
	 */
	public int getXCaseVide() {
		int res = -1;
		for(int i=0;i<this.grille.length;i++) {
			for(int j=0;j<this.grille[i].length;j++) {
				if(grille[i][j].getIndex()==-1) {
					res=i;
				}
			}
		}
		return res;
	}
	
	/**
	 * 
	 * @return coordonnée Y de la case vide (0 en haut -> taille-1 à en bas)
	 */
	public int getYCaseVide() {
		int res = -1;
		for(int i=0;i<this.grille.length;i++) {
			for(int j=0;j<this.grille[i].length;j++) {
				if(grille[i][j].getIndex()==-1) {
					res=j;
				}
			}
		}
		return res;
	}
	
	
	/**
	 * 
	 * @param x : Coordonnée x de la case (de gauche à droite)
	 * @param y : Coordonnée y de la case (de haut en bas)
	 * @return La case aux coordonnées demandées.
	 */
	public Case getCase(int x, int y) throws Exception{
		return grille[x][y];
	}
	
	/**
	 * 
	 * @return taille du puzzle (Puzzle de 4x4 -> retourne 4).
	 */
	public int getTaille() {
		return this.taille;
	}
	
	/**
	 * déplace la case vide dans une direction.
	 * @param Enumeration de direction (EDeplacement) : HAUT -> y-1, BAS -> y+1, GAUCHE -> x-1, DROITE -> x+1.
	 */
	private void deplacerCase(EDeplacement dp) {
		//TODO
		switch(dp) {
		case HAUT :
			break;
		case BAS :
			break;
		case GAUCHE :
			break;
		case DROITE :
			break;
		}
	}

}
