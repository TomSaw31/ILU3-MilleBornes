package strategies;

import java.util.Comparator;
import java.util.NavigableSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import jeu.Coup;

public interface Strategie {
	public default NavigableSet<Coup> trierCoups(Set<Coup> coups) {
		NavigableSet<Coup> coupsTries = new TreeSet<>(
			new Comparator<Coup>() {
				private Random random = new Random();
				@Override
				public int compare(Coup coup1, Coup coup2) {
					if (coup1 != null && coup1.equals(coup2)) {
						return 0;
					} else {
						return random.nextBoolean() ? 1 : -1;
					}
				}
			}
		);
		coupsTries.addAll(coups);
		return coupsTries;
	}
	
	public default Coup selectionnerCoup(Set<Coup> coups) {
		NavigableSet<Coup> coupsTries = trierCoups(coups);
		return coupsTries.getFirst();
	}
	
	public default Coup selectionnerDefausse(Set<Coup> coups) {
		NavigableSet<Coup> coupsTries = trierCoups(coups);
		return coupsTries.getLast();
	}
}
