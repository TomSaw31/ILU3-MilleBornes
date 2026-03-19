package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class GestionCartes {
	private static Random random = new Random();
	
//	public static <T> T extraire(List<T> liste) {
//        int index = random.nextInt(liste.size());
//        return liste.remove(index);
//    }
	
	public static <T> T extraire(List<T> liste) {
		int index = random.nextInt(liste.size());

		ListIterator<T> it = liste.listIterator();
		
		T element = null;
		for (int i = 0; i <= index; i++) {
			element = it.next();
		}
		
		it.remove();
		return element;
	}
	
	public static <T> List<T> melanger(List<T> liste) {
		List<T> listeMelangee = new ArrayList<>();
		while(!liste.isEmpty()) {
			T carte = extraire(liste);
			listeMelangee.add(carte);
		}
		return listeMelangee;
	}
	
	
	public static <T> boolean verifierMelange(List<T> liste1, List<T> liste2) {
		if (liste1.size() != liste2.size()) {
			return false;
		}
		
		for(T element : liste1) {
			if (Collections.frequency(liste1, element) != Collections.frequency(liste2, element)) {
				return false;
			}
		}
		return true;
	}
	
	public static <T> List<T> rassembler(List<T> liste) {
		if (liste.isEmpty()) {
			return liste;
		}
		
		List<T> listeRassemblee = new ArrayList<>();
		
		while (!liste.isEmpty()) {
			T carteRef = liste.remove(0);
			listeRassemblee.add(carteRef);
			
			for (ListIterator<T> iterator = liste.listIterator(); iterator.hasNext();) {
				T carte = iterator.next();
				if (carteRef.equals(carte)) {
					iterator.remove();
					listeRassemblee.add(carte);
				}
			}
		}
		return listeRassemblee;	
	}
	
	public static <T> boolean verifierRassemblement(List<T> liste) {
		if (liste.isEmpty()) {
			return true;
		}
		ListIterator<T> iterator = liste.listIterator();
		T carteRef = iterator.next();
		while (iterator.hasNext()) {
			T carte = iterator.next();
			
			if (!carteRef.equals(carte) && autreElemDansListe(liste, carteRef, iterator.nextIndex())) {
				return false;
			}
			
			carteRef = carte;
		}
		
		return true;
	}
	
	private static <T> boolean autreElemDansListe(List<T> liste, T carteRef, int indexDebut) {
		ListIterator<T> iterator = liste.listIterator();
		if (indexDebut >= liste.size()) {
			return false;
		}
		
		while (iterator.nextIndex() < indexDebut) {
			iterator.next();
		}
		
		while (iterator.hasNext()) {
			T carte = iterator.next();
			if (carte.equals(carteRef)) {
				return true;
			}
		}
		return false;
	}
}
