package strategies;

import java.util.Comparator;
import java.util.NavigableSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import cartes.Attaque;
import cartes.Botte;
import cartes.Carte;
import cartes.Type;
import jeu.Coup;
import jeu.Joueur;

public interface Presse extends Strategie, Priorite {
	static final Random RANDOM = new Random(0);

	@Override
	public default NavigableSet<Coup> trierCoups(Set<Coup> coups) {
		NavigableSet<Coup> coupsTries = new TreeSet<>(
			new Comparator<Coup>() {
				@Override
				public int compare(Coup coup1, Coup coup2) {
					int ordreNaturel = coup1.compareTo(coup2);
					if (ordreNaturel == 0) {
						return comparerCartes(coup1.getJoueurCourant(),coup1.getCarte(),coup2.getCarte());
					} else {
						return ordreNaturel;
					}
				}
			}
		);
		coupsTries.addAll(coups);
		return coupsTries;
	}

	private int comparerCartes(Joueur joueur, Carte carte1, Carte carte2) {			
		Integer comparaison = null;
		comparaison = donnerPrioriteLimites(carte1, carte2);
		if(comparaison != null) {
			return comparaison;
		}
		comparaison = donnerPrioriteBornes(carte1, carte2);
		if(comparaison != null) {
			return comparaison;
		}
		Carte carteSommet = joueur.donnerSommetPile();
		if(carteSommet instanceof Attaque attaque) {
			Type typeProbleme = attaque.getType();
			if(joueur.donnerBottes().contains(new Botte(typeProbleme))) {
				typeProbleme = Type.FEU;
			}
			comparaison = donnerPrioriteBottes(typeProbleme, carte1, carte2);
			if(comparaison != null) {
				return comparaison;
			}
		}
		comparaison = donnerPrioriteParades(carte1, carte2);
		if(comparaison != null) {
			return comparaison;
		}
		if (RANDOM.nextBoolean()) {
			return 1;
		} else {
			return -1;
		}
	}
}
