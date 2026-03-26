package jeu;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import cartes.Bataille;
import cartes.Borne;
import cartes.FinLimite;
import cartes.Limite;

public class ZoneDeJeu {
	List<Limite> pileLimites = new ArrayList<>();
	List<Bataille> pileBatailles = new ArrayList<>();
	List<Borne> pileBornes = new ArrayList<>();
	
	public int DonnerLimitationVitesse() {
		if(pileLimites.isEmpty() || pileLimites.getLast() instanceof FinLimite) {
			return 200;
		}
		return 50;
	}
	
	public int donnerKmParcourus() {
		int km = 0;
		for(ListIterator<Borne> iter = new ; iter.hasNext();) {
			
		}
	}
}
