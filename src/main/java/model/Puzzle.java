package main.java.model;

import java.util.Random;

public class Puzzle {
	
	public static final int TAILLE_MINI= 3;
	private final int TAILLE;
	private Case[][] grille;
	
	/**
	 * Définit la taille du puzzle : si inferieur à 3, remise automatiquement à 3.
	 * @param taille du Puzzle (si 4 -> 4x4).
	 */
	public Puzzle(int taille){
		this.TAILLE = (taille>TAILLE_MINI ? taille : TAILLE_MINI);
		this.grille=new Case[this.TAILLE][this.TAILLE];
		this.initGrille();
	}
	
	/**
	 * Initialise la grille avec des cases de valeurs allant de 0 à 
	 */
	public void initGrille() {
		int compteur = 0;
		for(int i = 0; i<this.TAILLE;i++) {
			for(int j = 0; j<this.TAILLE;j++) {
				this.grille[j][i] = new Case(compteur);
				compteur++;
			}
		}
		this.grille[0][0] = new Case(-1);
	}
	
	/**
	 * Mélange la grille (place chaque case à une place aléatoire).
	 */
	public void melanger() {
		Random rd = new Random();
		int tempi;
		int tempj;
		Case tempCase;
		
		for(int i=0;i<this.TAILLE;i++) {
			for(int j=0;j<this.TAILLE;j++) {
				tempi = rd.nextInt(this.TAILLE);
				tempj = rd.nextInt(this.TAILLE);
				this.echangerCase(i, j, tempi, tempj);
			}
		}
	}
	
	/**
	 * déplace la case vide dans une direction.
	 * @param Enumeration de direction (EDeplacement) : HAUT -> y-1, BAS -> y+1, GAUCHE -> x-1, DROITE -> x+1.
	 */
	public void deplacerCase(EDeplacement dp) {
		int oldCoordX = this.getXCaseVide();
		int oldCoordY = this.getYCaseVide();
		int newCoordX = oldCoordX;
		int newCoordY = oldCoordY;
		switch(dp) {
		case HAUT :
			newCoordY += 1;
			break;
		case BAS :
			newCoordY += -1;
			break;
		case GAUCHE :
			newCoordX += 1;
			break;
		case DROITE :
			newCoordX += -1;
			break;
		}
		if(newCoordX < this.TAILLE && newCoordX >= 0 && newCoordY < this.TAILLE && newCoordY >= 0) {
			this.echangerCase(oldCoordX, oldCoordY, newCoordX, newCoordY);
		}
	}
	
	/**
	 * Permet d'échanger deux cases.
	 * 
	 * @param x1 Coordonnée X de la première case
	 * @param y1 Coordonnée Y de la première case
	 * @param x2 Coordonnée X de la deuxieme case
	 * @param y2 Coordonnée Y de la deuxieme case
	 */
	private void echangerCase(int x1, int y1, int x2, int y2){
		try {
			Case tempCase = grille[x1][y1];
			this.grille[x1][y1] = this.grille[x2][y2];
			this.grille[x2][y2] = tempCase;
		}catch(ArrayIndexOutOfBoundsException e) {
			//Ne pas déplacer les cases si les coordonnées sont éronnées
		}
		
	}
	
	
	/**
	 * 
	 * @return TRUE si la grille est terminée, FALSE sinon
	 */
	public boolean verifierGrille() {
		boolean res = true;
		int last = this.grille[0][0].getIndex();
		
		for(int i=0;i<this.TAILLE && res==true;i++) {
			for(int j=0;j<this.TAILLE && res==true;j++) {
				if(this.grille[j][i].getIndex()>=last) {
					last = this.grille[j][i].getIndex();
				}else res=false;
			}
		}
		
		return res;
	}
	
	
	
	public Case[][] getGrille(){
		return this.grille;
	}
		
	public void setGrille(Case[][] grille) {
			this.grille=grille;
		}
	
	
	
	/**
	 * Getter coordonnée X Case vide
	 * @return coordonnée X de la case vide (0 à gauche -> taille-1 à droite)
	 */
	public int getXCaseVide() {
		int res = -1;
		for(int i=0;i<this.grille.length;i++) {
			for(int j=0;j<this.TAILLE;j++) {
				if(this.grille[i][j].getIndex()==-1) {
					res=i;
				}
			}
		}
		return res;
	}
	
	/**
	 * Getter coordonnée Y Case vide
	 * @return coordonnée Y de la case vide (0 en haut -> taille-1 à en bas)
	 */
	public int getYCaseVide() {
		int res = -1;
		for(int i=0;i<this.grille.length;i++) {
			for(int j=0;j<this.TAILLE;j++) {
				if(this.grille[i][j].getIndex()==-1) {
					res=j;
				}
			}
		}
		return res;
	}
	
	
	/**
	 * Getter Case a une coordonnée
	 * @param x : Coordonnée x de la case (de gauche à droite)
	 * @param y : Coordonnée y de la case (de haut en bas)
	 * @return La case aux coordonnées demandées.
	 */
	public Case getCase(int x, int y) throws ArrayIndexOutOfBoundsException{
		return grille[x][y];
	}
	

	
	public int getTaille() {
		return this.TAILLE;
	}
	
	@Override
	public String toString() {
		String res = "";
		for(int i=0;i<this.TAILLE;i++) {
			for(int j=0;j<this.TAILLE;j++) {
				res+=this.grille[j][i];
				if(j==this.TAILLE-1) res+="\n";
					else res+=" / ";
			}
		}
		return res;
	}

}
