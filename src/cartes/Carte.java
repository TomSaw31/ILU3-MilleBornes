package cartes;

public abstract class Carte {
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Carte carte) {
			return toString().equals(carte.toString());
		}
		return false;
	}
}
