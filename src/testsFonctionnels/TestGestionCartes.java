package testsFonctionnels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import cartes.Carte;
import cartes.JeuDeCartes;
import utils.GestionCartes;

public class TestGestionCartes {
	public static void main(String[] args) {
		JeuDeCartes jeu = new JeuDeCartes();
		List<Carte> listeCarteNonMelangee = new LinkedList<>();
		for (Carte carte : jeu.donnerCartes()) {
			listeCarteNonMelangee.add(carte);
		}
		
		List<Carte> listeCartes = new ArrayList<>(listeCarteNonMelangee);
		System.out.println(listeCartes);
		listeCartes = GestionCartes.melanger(listeCartes);
		System.out.println(listeCartes);
		System.out.println("Mélange de liste valide : "
		+ GestionCartes.verifierMelange(listeCarteNonMelangee, listeCartes));
		listeCartes = GestionCartes.rassembler(listeCartes);
		System.out.println(listeCartes);
		System.out.println("lMélange de liste valide : " + GestionCartes.verifierRassemblement(listeCartes));
		
		testerListe(new ArrayList<>(Arrays.asList()));
		testerListe(new ArrayList<>(Arrays.asList(1, 1, 2, 1, 3)));
		testerListe(new ArrayList<>(Arrays.asList(1, 4, 3, 2)));
		testerListe(new ArrayList<>(Arrays.asList(1, 1, 2, 3)));
        
	}
    public static void testerListe(List<Integer> liste) {
        System.out.println("Avant rassemblement : " + liste);
        System.out.println("Rassemblement valide : " + GestionCartes.verifierRassemblement(liste));
        List<Integer> resultat = GestionCartes.rassembler(liste);
        System.out.println("Après rassemblement : " + resultat);
        System.out.println("Rassemblement valide : " + GestionCartes.verifierRassemblement(resultat) + "\n");
    }
}
