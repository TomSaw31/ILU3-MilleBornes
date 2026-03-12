package cartes;

public class JeuDeCartes {
	private Configuration[] typesDeCartes = new Configuration[] {
			 // Bornes
            new Configuration(new Borne(25), 10),
            new Configuration(new Borne(50), 10),
            new Configuration(new Borne(75), 10),
            new Configuration(new Borne(100), 12),
            new Configuration(new Borne(200), 4),

            // Parades
            new Configuration(new Parade(Type.FEU), 14),
            new Configuration(new FinLimite(), 6),
            new Configuration(new Parade(Type.ESSENCE), 6),
            new Configuration(new Parade(Type.CREVAISON), 6),
            new Configuration(new Parade(Type.ACCIDENT), 6),

            // Attaques
            new Configuration(new Attaque(Type.FEU), 5),
            new Configuration(new DebutLimite(), 4),
            new Configuration(new Attaque(Type.ESSENCE), 3),
            new Configuration(new Attaque(Type.CREVAISON), 3),
            new Configuration(new Attaque(Type.ACCIDENT), 3),

            // Bottes
            new Configuration(new Botte(Type.FEU), 1),
            new Configuration(new Botte(Type.ESSENCE), 1),
            new Configuration(new Botte(Type.CREVAISON), 1),
            new Configuration(new Botte(Type.ACCIDENT), 1)
    };
	
	public Carte[] donnerCartes() {
		int taille = 0;
		for (Configuration typeDeCarte : typesDeCartes) {
			taille += typeDeCarte.getNbExemplaires();
		}
		
		Carte[] cartes = new Carte[taille];
		
		int index = 0;
		for (Configuration typeDeCarte : typesDeCartes) {
			int tailleType = typeDeCarte.getNbExemplaires();
			for(int i = 0; i < tailleType; i++) {
				cartes[index] = typeDeCarte.getCarte();
				index++;
			}
		}
		return cartes;
	}
	
	
	public String affichageJeuDeCartes() {
		StringBuilder chaine = new StringBuilder();
		for (Configuration typeDeCarte : typesDeCartes) {
			chaine.append(typeDeCarte.getNbExemplaires());
			chaine.append(" ");
			chaine.append(typeDeCarte.getCarte());
			chaine.append("\n");
		}
		return chaine.toString();
	}
	
	public boolean checkCount() {
        Carte[] cartes = donnerCartes();
        
        for (Configuration configuration : typesDeCartes) {
            if (!checkConfiguration(configuration, cartes)){
                return false;
            }
        }
    
        return true;
    }
    
    private boolean checkConfiguration(Configuration configuration, Carte[] cartes) {
        int nbTrouvees = 0;
        
        for (int i = 0; i < cartes.length; i++) {
            if (cartes[i].equals(configuration.getCarte())) {
                nbTrouvees++;
            }
        }
        return nbTrouvees == configuration.getNbExemplaires();
    }
	
	private static class Configuration {
		private int nbExemplaires;
		private Carte carte;
		
		private Configuration(Carte carte, int nbExemplaires) {
			this.carte = carte;
			this.nbExemplaires = nbExemplaires;
		}
		
		public Carte getCarte() {
			return carte;
		}
		
		public int getNbExemplaires() {
			return nbExemplaires;
		}
	}
}
