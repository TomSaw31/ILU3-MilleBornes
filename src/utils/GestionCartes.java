package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class GestionCartes {
	 private GestionCartes() {}

	private static Random random = new Random();
	
//	public static <T> T extraire(List<T> liste) {
//        int index = random.nextInt(liste.size());
//        return liste.remove(index);
//    }
	
	public static <T> T extraire(List<T> liste) {
		int index = random.nextInt(liste.size());
		ListIterator<T> iter = liste.listIterator(index);
		
		T element = iter.next();
		iter.remove();
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
		
		T carteRef = liste.get(0);
		for(ListIterator<T> iter = liste.listIterator(); iter.hasNext();) {
			T carte = iter.next();
			if (!carteRef.equals(carte) && autreElemDansListe(liste, carteRef, iter.nextIndex())) {
				return false;
			}
			carteRef = carte;
		}
		return true;
	}
	
	private static <T> boolean autreElemDansListe(List<T> liste, T carteRef, int indexDebut) {
		if (indexDebut >= liste.size()) {
			return false;
		}
		
		for(ListIterator<T> iterator = liste.listIterator(indexDebut); iterator.hasNext();) {
			T carte = iterator.next();
			if (carte.equals(carteRef)) {
				return true;
			}
		}
		return false;
	}
	
	
}
