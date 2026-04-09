package jeu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cartes.Carte;

public class MainJoueur {
	private List<Carte> listeCartes = new ArrayList<>();
	
	public void prendre(Carte carte) {
		listeCartes.add(carte);
	}

	public List<Carte> getCartes() {
		return listeCartes;
	}
	
	public void jouer(Carte carte) {
		assert listeCartes.contains(carte);
		listeCartes.remove(carte);
	}
	
	@Override
	public String toString() {
		StringBuilder chaine = new StringBuilder();
		for (Iterator<Carte> iterator = listeCartes.iterator(); iterator.hasNext();) {
			chaine.append(iterator.next().toString());
			chaine.append("\n");
		}
		return chaine.toString();
	}

}
