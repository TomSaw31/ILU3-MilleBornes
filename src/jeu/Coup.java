package jeu;

import cartes.Attaque;
import cartes.Carte;
import cartes.DebutLimite;

public class Coup {
	private Joueur joueurCourant;
	private Carte carte;
	private Joueur joueurCible;
	
	public Coup(Joueur joueurCourant, Carte carte, Joueur joueurCible) {
		this.joueurCourant = joueurCourant;
		this.carte = carte;
		this.joueurCible = joueurCible;
	}

	public Joueur getJoueurCourant() {
		return joueurCourant;
	}

	public Carte getCarte() {
		return carte;
	}

	public Joueur getJoueurCible() {
		return joueurCible;
	}
	
	public boolean estValide() {
		if (!joueurCible.getZoneDeJeu().estDepotAutorise(carte)) {
			return false;
		}
		
		if (carte instanceof Attaque || carte instanceof DebutLimite) {
			return !joueurCourant.equals(joueurCible);
		}
		
		return joueurCourant.equals(joueurCible);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Coup coup && coup.joueurCourant.equals(joueurCourant) && coup.carte.equals(carte)) {
			if (joueurCible == null) {
				return coup.joueurCible == null;
			}
			return coup.joueurCible.equals(joueurCible);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int hashCible = 0;
		if (joueurCible != null) {
			hashCible = joueurCible.hashCode();
		}
		return 31 * (joueurCourant.hashCode() + carte.hashCode() + hashCible);
		
	}
	
	@Override
	public String toString() {
		if (joueurCible == null) {
			return "defausse la carte " + carte.toString();
		}
		if (joueurCible.equals(joueurCourant)) {
			return "depose la carte " + carte.toString() + " dans sa zone de jeu.";
		}
		return "depose la carte " + carte.toString() + " dans la zone de jeu de " + joueurCible.getNom() + ".";

	}
}
