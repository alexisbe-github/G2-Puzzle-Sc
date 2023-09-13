package main.java.model;

import java.util.Random;

public class Puzzle {
	
	public static final int TAILLEMINI= 3;
	private final int TAILLE;
	private Case[][] grille;
	
	/**
	 * Définit la taille du puzzle : si inferieur à 3, remise automatiquement à 3.
	 * @param taille du Puzzle (si 4 -> 4x4).
	 */
	public Puzzle(int taille){
		this.TAILLE = (taille>TAILLEMINI ? taille : TAILLEMINI);
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
		//TODO
		Random rd = new Random();
		int tempi;
		int tempj;
		Case tempCase;
		
		for(int i=0;i<this.TAILLE;i++) {
			for(int j=0;j<this.TAILLE;j++) {
				tempi = rd.nextInt(this.TAILLE);
				tempj = rd.nextInt(this.TAILLE);
				tempCase = this.grille[tempi][tempj];
				this.grille[tempi][tempj] = grille[i][j];
				this.grille[i][j] = tempCase;
			}
		}
	}
	
	/**
	 * déplace la case vide dans une direction.
	 * @param Enumeration de direction (EDeplacement) : HAUT -> y-1, BAS -> y+1, GAUCHE -> x-1, DROITE -> x+1.
	 */
	public void deplacerCase(EDeplacement dp) {
		//TODO
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
		System.out.println("direction attendue : "+dp+"\nPuzzle Avant : \n"+this); //DEBUG
		if(newCoordX < this.TAILLE && newCoordX >= 0 && newCoordY < this.TAILLE && newCoordY >= 0) {
			Case tempCase = grille[newCoordX][newCoordY];
			this.grille[newCoordX][newCoordY] = this.grille[oldCoordX][oldCoordY];
			this.grille[oldCoordX][oldCoordY] = tempCase;
		}
		System.out.println("Puzzle Après : \n"+this); //DEBUG
	}
	
	
	/**
	 * 
	 * @return TRUE si la grille est terminée, FALSE sinon
	 */
	public boolean verifierGrille() {
		//TODO
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
	public Case getCase(int x, int y) throws Exception{
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
