package jeu;

import cartes.Carte;

public class Joueur {
	private String nom;
	private MainJoueur main;
	private ZoneDeJeu zoneDeJeu;

	public Joueur(String nom, ZoneDeJeu zoneDeJeu) {
		this.nom = nom;
		this.zoneDeJeu = zoneDeJeu;
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
	
	public MainJoueur getMainJoueur() {
		return main;
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
	
	public int donnerKmParcourus() {
		return zoneDeJeu.donnerKmParcourus();
	}
	
	public void deposer(Carte c) {
		zoneDeJeu.deposer(c);
	}
	
	public boolean estDepotAutorise(Carte c) {
		return zoneDeJeu.estDepotAutorise(c);
	}
}
