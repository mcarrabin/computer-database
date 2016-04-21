package com.excilys.computerdatabase.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class ComputerEntity {
	private Long id;
	private LocalDateTime introduced;
	private LocalDateTime discontinued;
	private CompanyEntity company;
	private String name;

	public ComputerEntity(Long pId, String pName, LocalDateTime pIntroduced, LocalDateTime pDiscontinued,
			CompanyEntity company) {
		this.id = pId;
		this.name = pName;
		this.introduced = pIntroduced;
		this.discontinued = pDiscontinued;
		this.company = company;
	}

	public ComputerEntity() {
		this.id = -1L;
		this.name = "";
		this.introduced = null;
		this.discontinued = null;
		this.company = new CompanyEntity();
	}

	public Long getId() {
		return this.id;
	}

	public LocalDateTime getIntroduced() {
		return this.introduced;
	}

	public LocalDateTime getDiscontinued() {
		return this.discontinued;
	}

	public CompanyEntity getCompany() {
		return this.company;
	}

	public String getName() {
		return this.name;
	}

	public void setId(Long pId) {
		this.id = pId;
	}

	public void setIntroduced(LocalDateTime pIntroduced) {
		this.introduced = pIntroduced;
	}

	public void setDiscontinued(LocalDateTime pDiscontinued) {
		this.discontinued = pDiscontinued;
	}

	public void setCompany(CompanyEntity pCompany) {
		this.company = pCompany;
	}

	public void setName(String pName) {
		this.name = pName;
	}

	public void updateComputer(ArrayList<CompanyEntity> companies) {
		int field;
		Scanner sc = new Scanner(System.in);
		do {
			field = updateMenu();
			switch (field) {
			case 1:
				this.name = newName();
				break;
			case 2:
				this.introduced = typeDate();
				break;
			case 3:
				this.discontinued = typeDate();
				break;
			case 4:
				System.out.println("Saisissez un nouveau company_id:");
				this.company = chooseCompany(companies);
				break;
			}
		} while (continuer());
	}

	public int updateMenu() {
		int field;
		Scanner sc = new Scanner(System.in);
		System.out.println("Quel champ voulez-vous mettre à jour?");
		System.out.println(" 1. Name");
		System.out.println(" 2. Introduced");
		System.out.println(" 3. Discontinued");
		System.out.println(" 4. company_id");
		field = intEntry(sc, 1, 4);
		return field;
	}

	public String newName() {
		String result;
		Scanner sc = new Scanner(System.in);
		System.out.println("renseignez la nouvelle valeur du champ name:");
		result = sc.nextLine();
		return result;
	}

	public boolean continuer() {
		Scanner sc = new Scanner(System.in);
		String continu = "";
		System.out.println("Voulez-vous mettre un autre champ à jour?[O/n]");
		continu = sc.nextLine();
		if (continu.equalsIgnoreCase("o"))
			return true;
		else
			return false;

	}

	public void createComputer(ArrayList<CompanyEntity> companies) {
		String name;
		LocalDateTime introduced, discontinued;
		Scanner sc = new Scanner(System.in);
		int company_id;
		CompanyEntity company = new CompanyEntity();

		System.out.println("Saisissez un nom: ");
		name = sc.nextLine();
		System.out.println("Saisie de la date de début: ");
		introduced = typeDate();
		System.out.println("Saisie de la date de fin: ");
		discontinued = typeDate();

		company = chooseCompany(companies);

		this.setName(name);
		this.setIntroduced(introduced);
		this.setDiscontinued(discontinued);
		this.setCompany(company);
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
		res += "Company: \n";
		res += "	Name: " + this.company.getId() + "\n";
		res += "	Id: " + this.company.getName() + "\n";

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
				if (jour == 0 && mois == 0 && annee == 0)
					res = null;
				else
					res = LocalDateTime.of(annee, mois, jour, 0, 0, 0);
				isDateOK = true;
			} catch (Exception e) {
				System.out.println("Merci de ressaisir la date");
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

	/**
	 * Méthode qui va gérer le choix d'une société par l'utilisateur
	 * 
	 * @param companies:
	 *            liste des Company présentes dans la BDD
	 * @return l'objet CompanyEntity correspondant au choix de l'utilisateur
	 */
	public CompanyEntity chooseCompany(ArrayList<CompanyEntity> companies) {
		Scanner sc = new Scanner(System.in);
		Long idCompany = -1L;
		for (CompanyEntity c : companies) {
			System.out.println(c.toString());
		}
		while (true) {
			System.out.println("Quelle compagnie voulez-vous? (tapez l'id)");
			id = new Long(intEntry(sc, -1, -1));
			if (isCompanyOk(id, companies))
				break;
			System.out.println("L'id saisi ne correspond à aucune société, veuillez recommencer");
		}

		for (CompanyEntity c : companies) {
			if (c.getId() == id)
				return c;
		}
		return null;
	}

	/**
	 * Méthode qui vérifie que l'id de la société choisi est correcte
	 * 
	 * @param id:
	 *            id de la société à vérifier
	 * @param companies:
	 *            liste des company présentes dans la BDD
	 * @return true si l'id existe, false sinon
	 */
	public boolean isCompanyOk(Long id, ArrayList<CompanyEntity> companies) {
		for (CompanyEntity c : companies) {
			if (c.getId() == id)
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComputerEntity other = (ComputerEntity) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
