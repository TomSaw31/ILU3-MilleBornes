package cartes;

import java.util.HashMap;
import java.util.Map;

public class JeuDeCartes {
	private Map<Carte, Integer> typesDeCartes = new HashMap<>();
	public JeuDeCartes() {
		typesDeCartes.put(new Borne(25), 10);
		typesDeCartes.put(new Borne(50), 10);
		typesDeCartes.put(new Borne(75), 10);
		typesDeCartes.put(new Borne(100), 12);
		typesDeCartes.put(new Borne(200), 4);
		
		typesDeCartes.put(new Parade(Type.FEU), 14);
		typesDeCartes.put(new Parade(Type.ESSENCE), 6);
		typesDeCartes.put(new Parade(Type.CREVAISON), 6);
		typesDeCartes.put(new Parade(Type.ACCIDENT), 6);
		
		typesDeCartes.put(new Attaque(Type.FEU), 5);
		typesDeCartes.put(new Attaque(Type.ESSENCE), 3);
		typesDeCartes.put(new Attaque(Type.CREVAISON), 3);
		typesDeCartes.put(new Attaque(Type.ACCIDENT), 3);
		
		typesDeCartes.put(new Botte(Type.FEU), 1);
		typesDeCartes.put(new Botte(Type.ESSENCE), 1);
		typesDeCartes.put(new Botte(Type.CREVAISON), 1);
		typesDeCartes.put(new Botte(Type.ACCIDENT), 1);
		
		typesDeCartes.put(new DebutLimite(), 4);
		typesDeCartes.put(new FinLimite(), 6);
	}	
	
	
	public Carte[] donnerCartes() {
		Carte[] cartes = new Carte[106];
		int carteIndex = 0;
		for (Map.Entry<Carte, Integer> typeDeCarte : typesDeCartes.entrySet()) {
			Carte key = typeDeCarte.getKey();
			Integer valeur = typeDeCarte.getValue();
			
			for (int i = 0; i < valeur; i++) {
				cartes[carteIndex++] = key;
			}
		}
		
		return cartes;
	}
	
	
	public String affichageJeuDeCartes() {
		StringBuilder chaine = new StringBuilder();
		for (Map.Entry<Carte, Integer> typeDeCarte : typesDeCartes.entrySet()) {
			chaine.append(typeDeCarte.getValue());
			chaine.append(" ");
			chaine.append(typeDeCarte.getKey());
			chaine.append("\n");
		}
		return chaine.toString();
	}
	
	public boolean checkCount() {
        Carte[] cartes = donnerCartes();
        
        for (Map.Entry<Carte,Integer> typeDeCarte : typesDeCartes.entrySet()) {
            if (!checkConfiguration(typeDeCarte, cartes)){
                return false;
            }
        }
    
        return true;
    }
    
    private boolean checkConfiguration(Map.Entry<Carte,Integer> typeDeCarte, Carte[] cartes) {
        int nbTrouvees = 0;
        
        for (int i = 0; i < cartes.length; i++) {
            if (cartes[i].equals(typeDeCarte.getKey())) {
                nbTrouvees++;
            }
        }
        return nbTrouvees == typeDeCarte.getValue();
    }
	
//	private static class Configuration {
//		private int nbExemplaires;
//		private Carte carte;
//		
//		private Configuration(Carte carte, int nbExemplaires) {
//			this.carte = carte;
//			this.nbExemplaires = nbExemplaires;
//		}
//		
//		public Carte getCarte() {
//			return carte;
//		}
//		
//		public int getNbExemplaires() {
//			return nbExemplaires;
//		}
//	}
}
