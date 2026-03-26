package jeu;

import cartes.Carte;

public class Joueur {
	private String nom;
	private MainJoueur main;

	public Joueur(String nom) {
		this.nom = nom;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Joueur joueur) {
			return joueur.getNom().equals(nom);
		}
		return false;
	}
	
	
	
	public String getNom() {
		return nom;
	}

	@Override
	public String toString() {
		return nom;
	}
	
	public void donner(Carte carte) {
		main.prendre(carte);
	}
	
	public Carte prendreCarte(Sabot sabot)  {
		if (sabot == null) {
			return null;
		}
		Carte carte = sabot.piocher();
		donner(carte);
		return carte;
	}
}
