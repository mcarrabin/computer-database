package com.excilys.computerdatabase.services;

import java.util.Scanner;

public class PageService {
	private int start;
	private int end;
	private int numPage;
	
	public PageService(){
		this.start = 0;
		this.end = 10000;
		this.numPage = 1;
	}
	
	public int getStart(){
		return start;
	}
	public int getEnd(){
		return end;
	}
	public int getNumPage(){
		return numPage;
	}
	public void setStart(int pStart){
		this.start = pStart;
	}
	public void setEnd(int pEnd){
		this.end = pEnd;
	}
	public void setNumPage(int pNumPage){
		this.numPage = pNumPage;
	}
	
	public void startPagination(){
		
	}
	
	public int typeNumPage(){
		Scanner sc = new Scanner(System.in);
		int start = -1;
		System.out.println("numéro de page: ");
		start = intEntry(sc, 0, -1);
		
		return start;
	}

	/**
	 * Méthode qui va vérifier que la saisie d'un entier est OK
	 * 
	 * @param sc
	 *            scanner
	 * @param min
	 *            limite minimum possible de la valeur. -1 si il n'y a pas de
	 *            minimum
	 * @param max
	 *            limite maximum possible de la valeur. -1 si il n'y a pas de
	 *            maximum
	 * @return renvoi l'entier saisi si celui-ci est bon, -1 sinon
	 */
	public static int intEntry(Scanner sc, int min, int max) {
		String temp = sc.nextLine();
		if (temp != null && temp.length() > 0) {
			try {
				int i = Integer.parseInt(temp);
				if ((i < min && min != -1) || (i > max && max != -1))
					throw new Exception();
				return i;
			} catch (Exception e) {
				System.out.println("La valeur saisie est incorrecte");
				return -1;
			}
		}
		return 0;
	}
}
