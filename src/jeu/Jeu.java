package jeu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

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
		
		chaine.append(afficherMain(joueur));
		
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
	
	private String afficherMain(Joueur joueur) {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Il a dans sa main : [");
		for (Iterator<Carte> iterCartes = joueur.getMainJoueur().iterator(); iterCartes.hasNext();) {
			Carte carte = iterCartes.next();
			chaine.append(carte.toString());
			
			if (iterCartes.hasNext()) {
				chaine.append(", ");
			}
		}
		chaine.append("]\n");
		return chaine.toString();
	}
	

	public String lancer() {
		StringBuilder chaine = new StringBuilder("Debut : \n\n");
		
		while (!sabot.estVide()) {
			Joueur joueurCourant = donnerJoueurSuivant();
			chaine.append(jouerTour(joueurCourant));
			
			int km = joueurCourant.donnerKmParcourus();
			if (km >= 1000) {
				chaine.append(joueurCourant.toString());
				chaine.append(" remporte la partie.\n\n");
				chaine.append("Classement : \n");
				chaine.append(afficherClassement());
				return chaine.toString();
			}
		}
		
		chaine.append("Le sabot est épuisé, la partie est terminée.\n");
		return chaine.toString();
	}
	
	private String afficherClassement() {
		StringBuilder chaine = new StringBuilder();
		for(Joueur joueur : classement()) {
			chaine.append(joueur.toString());
			chaine.append(" : ");
			chaine.append(Integer.toString(joueur.donnerKmParcourus()));
			chaine.append("km\n");
		}
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
	
	public List<Joueur> classement() {
		NavigableSet<Joueur> setJoueurs = new TreeSet<>(
			new Comparator<Joueur>() {
				@Override
				public int compare(Joueur joueur1, Joueur joueur2) {
					int joueur1km = joueur1.donnerKmParcourus();
					int joueur2km = joueur2.donnerKmParcourus();
					if(joueur1km == joueur2km) {
						return 1;
					}
					return joueur2km - joueur1km;
				}
			}
		);
		setJoueurs.addAll(joueurs);
		List<Joueur> listeJoueurs = new LinkedList<>();
		for(Joueur joueur : setJoueurs) {
			listeJoueurs.add(joueur);
		}
		return listeJoueurs;
	}	
}
