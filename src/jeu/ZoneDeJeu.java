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
	
	public boolean estDepotFeuVertAutorise() {
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
	
	public boolean estDepotBorneAutorise(Borne borne) {
		boolean condition = peutAvancer();
		condition &= (donnerKmParcourus() <= 1000);
		condition &= borne.getKm() <= donnerLimitationVitesse();
		return condition;
	}
	
	public boolean estDepotLimitAutorise(Limite limite) {
		if (limite instanceof DebutLimite debutLimite) {
			return pileLimites.isEmpty() || pileLimites.getLast() instanceof FinLimite;
		} else {
			return !pileLimites.isEmpty() && pileLimites.getLast() instanceof DebutLimite;
		}
	}
}
