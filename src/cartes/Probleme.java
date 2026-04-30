package cartes;

public abstract class Probleme extends Carte implements Comparable<Probleme> {
	private Type type;

	protected Probleme(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && ((Probleme)obj).getType().equals(type);
	}
	
	@Override
	public int hashCode() {
		return 31 * getType().hashCode();
	}
	
	@Override
	public int compareTo(Probleme probleme) {
		return type.compareTo(probleme.getType());
	}
}
