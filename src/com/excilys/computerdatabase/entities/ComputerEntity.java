package com.excilys.computerdatabase.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ComputerEntity {
	private int id;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private int company_id;
	private String name;

	public ComputerEntity(int pId, String pName, LocalDateTime pIntroduced, LocalDateTime pDiscontinued,
			int pCompany_id) {
		this.id = pId;
		this.name = pName;
		this.introduced = pIntroduced;
		this.discontinued = pDiscontinued;
		this.company_id = pCompany_id;
	}

	public ComputerEntity() {
		this.id = -1;
		this.name = "";
		this.introduced = null;
		this.discontinued = null;
		this.company_id = -1;
	}

	public int getId() {
		return this.id;
	}

	public LocalDateTime getIntroduced() {
		return this.introduced;
	}

	public LocalDateTime getDiscontinued() {
		return this.discontinued;
	}

	public int getCompanyId() {
		return this.company_id;
	}

	public String getName() {
		return this.name;
	}

	public void setId(int pId) {
		this.id = pId;
	}

	public void setIntroduced(LocalDateTime pIntroduced) {
		this.introduced = pIntroduced;
	}

	public void setDiscontinued(LocalDateTime pDiscontinued) {
		this.discontinued = pDiscontinued;
	}

	public void setCompanyId(int pCompanyId) {
		this.company_id = pCompanyId;
	}

	public void setName(String pName) {
		this.name = pName;
	}

	public ComputerEntity createComputer() {
		ComputerEntity computer = new ComputerEntity();
		String name;
		LocalDateTime introduced, discontinued;
		Scanner sc = new Scanner(System.in);
		int company_id;

		System.out.println("Saisissez un nom: ");
		name = sc.nextLine();
		System.out.println("Saisie de la date de début: ");
		introduced = typeDate();
		System.out.println("Saisie de la date de fin: ");
		discontinued = typeDate();
		System.out.println("Saisissez un id de société: ");
		company_id = intEntry(sc, -1, -1);

		return computer;
	}

	/**
	 * la méthode toString() d'un ComputerEntity va afficher les valeurs de tous
	 * ses champs les unes en dessous des autres
	 */
	public String toString() {
		String res = "";
		res += "Id: " + this.id + "\n";
		res += "Name: " + this.name + "\n";
		res += "Introduced: " + (this.introduced == null ? " " : this.introduced.toString()) + "\n";
		res += "Discontinued: " + (this.discontinued == null ? " " : this.discontinued.toString()) + "\n";
		res += "Company id: " + String.valueOf(this.company_id) + "\n";

		return res;
	}

	/**
	 * Méthode qui va gérer la saisie d'une date par l'utilisateur.
	 * 
	 * @return: la date saisie après vérification.
	 */
	public LocalDateTime typeDate() {
		LocalDateTime res = null;
		Scanner sc = new Scanner(System.in);
		int jour, mois, annee;
		boolean isDateOK = false;
		while (!isDateOK) {
			try {
				System.out.println("Saisissez un jour: ");
				jour = intEntry(sc, 1, 31);
				System.out.println("Saisissez un mois: ");
				mois = intEntry(sc, 1, 12);
				System.out.println("Saisissez une année: ");
				annee = intEntry(sc, 1, -1);
				res = LocalDateTime.of(annee, mois, jour, 0, 0, 0);
				isDateOK = true;
			} catch (Exception e) {
				System.out.println("Merci de ressaisir de la date");
			}
		}
		return res;
	}

	/**
	 * Méthode qui va vérifier que la saisie d'un entier est OK
	 * 
	 * @param sc
	 *            scanner
	 * @param min
	 *            palier minimum possible de la valeur. -1 si il n'y a pas de
	 *            minimum
	 * @param max
	 *            palier maximum possible de la valeur. -1 si il n'y a pas de
	 *            maximum
	 * @return renvoi l'entier saisi si celui-ci est bon, -1 sinon
	 */
	public int intEntry(Scanner sc, int min, int max) {
		String temp = sc.nextLine();
		try {
			int i = Integer.parseInt(temp);
			if ((i < min && min != -1) || (i > max && max != -1))
				throw new Exception();
			return i;
		} catch (Exception e) {
			System.out.println("La valeur saisie est incorrecte");
		}
		return -1;
	}
}
