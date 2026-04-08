package jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import cartes.Attaque;
import cartes.Bataille;
import cartes.Borne;
import cartes.Carte;
import cartes.DebutLimite;
import cartes.FinLimite;
import cartes.Limite;
import cartes.Parade;

public class ZoneDeJeu {
	List<Limite> pileLimites = new ArrayList<>();
	List<Bataille> pileBatailles = new ArrayList<>();
	List<Borne> pileBornes = new ArrayList<>();

	public int donnerLimitationVitesse() {
		if (pileLimites.isEmpty() || pileLimites.getLast() instanceof FinLimite) {
			return 200;
		}
		return 50;
	}

	public int donnerKmParcourus() {
		int km = 0;
		for (ListIterator<Borne> iter = pileBornes.listIterator(); iter.hasNext();) {
			Borne borne = iter.next();
			km += borne.getKm();
		}
		return km;
	}

	public void deposer(Carte c) {
		if (c instanceof Borne borne) {
			pileBornes.add(borne);
		} else if (c instanceof Limite limite) {
			pileLimites.add(limite);
		} else if (c instanceof Bataille bataille) {
			pileBatailles.add(bataille);
		}
	}

	public boolean peutAvancer() {
		if (pileBatailles.isEmpty()) {
			return false;
		}
		Bataille sommet = pileBatailles.getLast();
		if (sommet instanceof Parade parade) {
			return parade.equals(Cartes.FEU_VERT);
		}
		return false;
	}
	
	private boolean estDepotFeuVertAutorise() {
		if (pileBatailles.isEmpty()) {
			return true;
		} else {
			Bataille sommet = pileBatailles.getLast();
			if (sommet instanceof Attaque attaque) {
				return attaque.equals(Cartes.FEU_ROUGE);
			}
			if (sommet instanceof Parade parade) {
				return !parade.equals(Cartes.FEU_VERT);
			}
			return false;
		}
	}
	
	private boolean estDepotBorneAutorise(Borne borne) {
		boolean condition = peutAvancer();
		condition &= (donnerKmParcourus() <= 1000);
		condition &= borne.getKm() <= donnerLimitationVitesse();
		return condition;
	}
	
	private boolean estDepotLimiteAutorise(Limite limite) {
		if (limite instanceof DebutLimite debutLimite) {
			return pileLimites.isEmpty() || pileLimites.getLast() instanceof FinLimite;
		} else {
			return !pileLimites.isEmpty() && pileLimites.getLast() instanceof DebutLimite;
		}
	}
	
	private boolean estDepotBatailleAutorise(Bataille bataille) {
		if (bataille instanceof Attaque) {
			return peutAvancer();
		}
		if (bataille instanceof Parade parade) {
			if (parade.equals(Cartes.FEU_VERT)) {
				boolean res = pileBatailles.isEmpty();
				Carte sommet = pileBatailles.getLast();
				res |= sommet.equals(Cartes.FEU_ROUGE);
				res |= sommet instanceof Parade sommetParade && !sommetParade.equals(Cartes.FEU_VERT);
				return res;
			}
			return !pileBatailles.isEmpty() && pileBatailles.getLast().getType().equals(bataille.getType());
		}
		return false;
	}
	
	public boolean estDepotAutorise(Carte c) {
		if (c instanceof Borne borne) {
			return estDepotBorneAutorise(borne);
		}
		if (c instanceof Limite limite) {
			return estDepotLimiteAutorise(limite);
		}
		if (c instanceof Bataille bataille) {
			return estDepotBatailleAutorise(bataille);
		}
		return false;
	}
}
