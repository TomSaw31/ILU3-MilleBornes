package jeu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import cartes.Botte;
import cartes.Carte;

public class Joueur {
	private String nom;
	private MainJoueur main;
	private ZoneDeJeu zoneDeJeu;
	private Random random = new Random(0);

	public Joueur(String nom, ZoneDeJeu zoneDeJeu) {
		this.nom = nom;
		this.zoneDeJeu = zoneDeJeu;
		this.main = new MainJoueur();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Joueur joueur) {
			return joueur.getNom().equals(nom);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return 31 * nom.hashCode();
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
	
	public ZoneDeJeu getZoneDeJeu() {
		return zoneDeJeu;
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
	
	public Set<Coup> coupsPossibles(Set<Joueur> participants) {
		Set<Coup> coups = new HashSet<>();
		for(Joueur cible : participants) {
			for(Carte carte : main.getCartes()) {
				Coup coup = new Coup(this, carte, cible);
				if (coup.estValide())
					coups.add(coup);
			}
		}
		return coups;
	}
	
	public Set<Coup> coupsDefausse() {
		Set<Coup> coups = new HashSet<>();
		
		for(Carte carte : main.getCartes()) {
			coups.add(new Coup(this, carte, null));
		}
		
		return coups;
	}
	
	public void retirerDeLaMain(Carte carte) {
		main.jouer(carte);
	}
	
	public Coup choisirCoup(Set<Joueur> participants) {
		Set<Coup> coups = coupsPossibles(participants);
		if (coups.isEmpty()) {
			return choisirCoupAleatoire(coupsDefausse());
		}
		return choisirCoupAleatoire(coups);
	}
	
	private Coup choisirCoupAleatoire(Set<Coup> coups) {
		Coup[] coupsTab = coups.toArray(new Coup[coups.size()]);
		int index = random.nextInt(coups.size());
		return coupsTab[index];
	}
	
	public String afficherEtatJoueur() {
		StringBuilder chaine = new StringBuilder("Joueur : " + nom);
		chaine.append("\nBottes : ");
		for (Botte botte : zoneDeJeu.getBottes()) {
			chaine.append(botte.toString()).append(" ");
		}
		
		chaine.append("\nLimitation de vitesse : " + (zoneDeJeu.donnerLimitationVitesse() != 200));
		if (zoneDeJeu.getPileBataille().isEmpty()) {
			chaine.append("\nSommet de la pile de bataille : null");
		}
		else {
			chaine.append("\nSommet de la pile de bataille : " + zoneDeJeu.getPileBataille().getFirst());
		}
		
		chaine.append("\nContenu de la main : ");
		for (Iterator<Carte> iter = main.iterator(); iter.hasNext();) {
			Carte carte = iter.next();
			chaine.append(carte.toString());
			if (iter.hasNext()) {
				chaine.append(", ");
			}
		}
		chaine.append("\n");
		
		return chaine.toString();
	}
}
