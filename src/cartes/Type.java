package cartes;

public enum Type {
	FEU("Feu Rouge", "Feu Vert", "Prioritaire"),
	ESSENCE("Panne d'essence", "Bidon d'essence", "Citerne"),
	CREVAISON("Crevaison", "Roue de secours", "Increvable"),
	ACCIDENT("Accident", "Réparation", "As du volant");
	
	private String affichageAttaque;
	private String affichageParade;
	private String affichageBotte;
	
	private Type(String affichageAttaque, String affichageParade, String affichageBotte) {
		this.affichageAttaque = affichageAttaque;
		this.affichageParade = affichageParade;
		this.affichageBotte = affichageBotte;
	}

	public String getAffichageAttaque() {
		return affichageAttaque;
	}

	public String getAffichageParade() {
		return affichageParade;
	}

	public String getAffichageBotte() {
		return affichageBotte;
	}
}
