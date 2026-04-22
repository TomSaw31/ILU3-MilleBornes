package jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import cartes.Carte;
import cartes.JeuDeCartes;
import utils.GestionCartes;

public class Jeu {
	private Sabot sabot;
	private LinkedHashSet<Joueur> joueurs = new LinkedHashSet<>();
	private static final int NBCARTES = 6;
	private Iterator<Joueur> iter;
	
	public Jeu() {
		JeuDeCartes jeuDeCartes = new JeuDeCartes();
		List<Carte> listeCartes = new ArrayList<>();
		Collections.addAll(listeCartes, jeuDeCartes.donnerCartes());
		listeCartes = GestionCartes.melanger(listeCartes);
		sabot = new Sabot(listeCartes.toArray(new Carte[0]));
	}
	
	public void inscrire(Joueur ... joueursInscrits) {
		for(Joueur joueur : joueursInscrits) {
			joueurs.addLast(joueur);
		}
	}
	
	public void distribuerCartes() {
		for(int i = 0; i < NBCARTES; i++) {
			for(Joueur joueur : joueurs) {
				joueur.donner(sabot.piocher());
			}
		}
	}
	
	public String jouerTour(Joueur joueur) {
		StringBuilder chaine = new StringBuilder();
		
		Carte cartePioche = sabot.piocher();
		joueur.donner(cartePioche);
		chaine.append("Le joueur " + joueur.getNom() + " a pioche " + cartePioche.toString() + "\n");
		
		chaine.append("Il a dans sa main : [");
		for (Iterator<Carte> iterCartes = joueur.getMainJoueur().iterator(); iterCartes.hasNext();) {
			
			Carte carte = iterCartes.next();
			chaine.append(carte.toString());
			
			if (iterCartes.hasNext()) {
				chaine.append(", ");
			}
		}
		
		chaine.append("]\n");
		
		Coup coup = joueur.choisirCoup(joueurs);
		
		Carte carteJouee = coup.getCarte();
		joueur.retirerDeLaMain(carteJouee);
		
		if (coup.getJoueurCible() == null) {
			sabot.ajouterCarte(carteJouee);
		} else {
			coup.getJoueurCible().deposer(carteJouee);
		}
		chaine.append(joueur.getNom() + " " + coup.toString() + "\n\n");
		
		return chaine.toString();
	}
	

	public String lancer() {
		StringBuilder chaine = new StringBuilder("Debut : \n\n");
		
		while (!sabot.estVide()) {
			Joueur joueurCourant = donnerJoueurSuivant();
			chaine.append(jouerTour(joueurCourant));
			
			int km = joueurCourant.donnerKmParcourus();
			if (km >= 1000) {
				chaine.append(joueurCourant.getNom());
				chaine.append(" a parcouru ");
				chaine.append(Integer.toString(km));
				chaine.append(" km.\n");
				chaine.append(joueurCourant.toString());
				chaine.append(" remporte la partie.\n");
				return chaine.toString();
			}
		}
		
		chaine.append("Le sabot est épuisé, la partie est terminée.\n");
		return chaine.toString();
	}
	
	public Joueur donnerJoueurSuivant() {
		if (joueurs.isEmpty()) {
			throw new IllegalStateException();
		}
		if (iter == null || !iter.hasNext()) {
			iter = joueurs.iterator();
		}
		return iter.next();
	}
	
}
