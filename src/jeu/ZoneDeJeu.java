package jeu;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import cartes.Attaque;
import cartes.Bataille;
import cartes.Borne;
import cartes.Botte;
import cartes.Carte;
import cartes.DebutLimite;
import cartes.FinLimite;
import cartes.Limite;
import cartes.Parade;
import cartes.Type;

public class ZoneDeJeu {
	List<Limite> limites = new LinkedList<>();
	List<Bataille> batailles = new LinkedList<>();
	List<Borne> bornes = new LinkedList<>();
	Set<Botte> bottes = new HashSet<>();

	public int donnerLimitationVitesse() {
		if (estPrioritaire() || limites.isEmpty() || limites.getFirst() instanceof FinLimite) {
			return 200;
		}
		return 50;
	}

	public int donnerKmParcourus() {
		int km = 0;
		for (ListIterator<Borne> iter = bornes.listIterator(); iter.hasNext();) {
			Borne borne = iter.next();
			km += borne.getKm();
		}
		return km;
	}

	public void deposer(Carte c) {
		if (c instanceof Borne borne) {
			bornes.add(0, borne);
		} else if (c instanceof Limite limite) {
			limites.add(0,limite);
		} else if (c instanceof Bataille bataille) {
			batailles.add(0,bataille);
		} else if (c instanceof Botte botte) {
			bottes.add(botte);
		}
	}

	public boolean peutAvancer() {
		if (batailles.isEmpty()) {
			return estPrioritaire();
		}
		Bataille sommet = batailles.getFirst();
		if (sommet instanceof Parade parade) {
			return parade.equals(Cartes.FEU_VERT) || estPrioritaire();
		}
		if (sommet instanceof Attaque attaque) {
			if (attaque.getType().equals(Type.FEU)) {
				return estPrioritaire();
			}
			return estPrioritaire() && bottes.contains(new Botte(sommet.getType()));
		}
		return false;
	}
	
	private boolean estDepotFeuVertAutorise() {
		if (estPrioritaire()) {
			return false;
		}
		if (batailles.isEmpty()) {
			return true;
		}
		
		Bataille sommet = batailles.getFirst();
		if (sommet instanceof Parade parade && !parade.equals(Cartes.FEU_VERT)) {
			return true;
		} else if (sommet instanceof Attaque attaque) {
			if (sommet.equals(Cartes.FEU_ROUGE)) {
				return true;
			}
			Botte botteCorrespondante = new Botte(attaque.getType());
			return bottes.contains(botteCorrespondante);
		}
		return false;
	}
	
	private boolean estDepotBorneAutorise(Borne borne) {
		boolean condition = peutAvancer();
		condition &= (donnerKmParcourus() <= 1000);
		condition &= borne.getKm() <= donnerLimitationVitesse();
		return condition;
	}
	
	private boolean estDepotLimiteAutorise(Limite limite) {
		if (estPrioritaire()) {
			return false;
		}
		if (limite instanceof DebutLimite) {
			return limites.isEmpty() || limites.getFirst() instanceof FinLimite;
		} else {
			return !limites.isEmpty() && limites.getFirst() instanceof DebutLimite;
		}
	}
	
	private boolean estDepotBatailleAutorise(Bataille bataille) {
		Botte botteCorrespondante = new Botte(bataille.getType());
		if(bottes.contains(botteCorrespondante)) {
			return false;
		}
		if (bataille instanceof Attaque) {
			return peutAvancer();
		}
		if (bataille instanceof Parade parade) {
			if (parade.equals(Cartes.FEU_VERT)) {
				return estDepotFeuVertAutorise();
			}
			return !batailles.isEmpty() && batailles.getFirst().getType().equals(bataille.getType());
		}
		return false;
	}
	
	public boolean estDepotAutorise(Carte carte) {
		if (carte instanceof Borne borne) {
			return estDepotBorneAutorise(borne);
		}
		if (carte instanceof Limite limite) {
			return estDepotLimiteAutorise(limite);
		}
		if (carte instanceof Bataille bataille) {
			return estDepotBatailleAutorise(bataille);
		}
		return carte instanceof Botte;
	}
	
	public boolean estPrioritaire() {
		return bottes.contains(Cartes.PRIORITAIRE);
	}
	
	public Set<Botte> getBottes() {
		return bottes;
	}
	
	public List<Bataille> getPileBataille() {
		return batailles;
	}
}
