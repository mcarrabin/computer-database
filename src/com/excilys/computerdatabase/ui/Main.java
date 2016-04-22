package com.excilys.computerdatabase.ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;
import com.excilys.computerdatabase.services.CompanyService;
import com.excilys.computerdatabase.services.ComputerService;
import com.excilys.computerdatabase.services.PageService;

public class Main {
	public static void main(String... args) {
		int actionAFaire = 0;
		ComputerService computerService = ComputerService.getInstance();
		CompanyService companyService = CompanyService.getInstance();
		PageService pageService = PageService.getInstance();
		do {
			actionAFaire = displayMenu();
			doAction(actionAFaire, computerService, companyService, pageService);
		} while (continuerMainMenu());
	}

	public static int displayMenu() {
		int result = -1;
		Scanner sc = new Scanner(System.in);
		boolean isReponseCorrecte = false;
		while (!isReponseCorrecte) {
			System.out.println("Que voulez-vous faire ?");
			System.out.println(" 1. Afficher toutes les compagnies");
			System.out.println(" 2. Afficher tous les ordinateurs");
			System.out.println(" 3. Afficher toutes les informations d'un seul ordinateur");
			System.out.println(" 4. Créer un ordinateur");
			System.out.println(" 5. Modifier un ordinateur");
			System.out.println(" 6. Supprimer un ordinateur");
			System.out.println(" 7. Afficher tous les ordinateurs avec pagination");
			result = newEntry(sc, 1, 7);

			if (result != -1)
				isReponseCorrecte = true;
			else
				System.out.println("Saisie incorrecte. Merci de rentrer le numéro de l'action à faire");
		}
		return result;
	}

	public static int newEntry(Scanner sc, int min, int max) {
		String result = sc.nextLine();
		try {
			int i = Integer.parseInt(result);
			if ((i < min || min == -1) && (i > max || max == -1)) {
				throw new Exception();
			}
			return i;
		} catch (Exception e) {
			return -1;
		}
	}

	public static boolean continuerMainMenu() {
		String reponse = null;
		Scanner sc = new Scanner(System.in);
		boolean isReponseCorrecte = false;
		while (!isReponseCorrecte) {
			System.out.println("Voulez-vous continuer? [O/n]");
			reponse = sc.nextLine();
			if (reponse.equalsIgnoreCase("o") || reponse.equalsIgnoreCase("n"))
				isReponseCorrecte = true;
		}
		// sc.close();

		if (reponse.equalsIgnoreCase("o"))
			return true;

		return false;
	}

	public static void doAction(int action, ComputerService computerService, CompanyService companyService,
			PageService pageService) {
		Computer computer = new Computer();
		Page page = null;
		Scanner sc = new Scanner(System.in);
		boolean actionRealized = false;
		List<Computer> computers = new ArrayList<Computer>();
		switch (action) {
		case 1:
			List<Company> companies = new ArrayList<Company>();
			companies = companyService.getCompanies();
			for (Company company : companies) {
				System.out.println(company.toString());
			}
			break;

		case 2:
			computers = computerService.getComputers();
			for (Computer c : computers) {
				System.out.println(c.toString());
			}
			break;

		case 3:
			System.out.println("Quel ordinateur voulez-vous afficher? ");
			long id = (long) newEntry(sc, 0, -1);
			computer = computerService.getComputerById(id);
			System.out.println(computer.toString());
			break;

		case 4:
			computer = typeComputer();
			actionRealized = computerService.createComputer(computer);
			if (actionRealized)
				actionValid("création");
			break;

		case 5:
			System.out.println("Quel ordinateur voulez-vous modifier? ");
			id = (long) newEntry(sc, 0, -1);
			computer = computerService.getComputerById(id);
			computer = typeComputerUpdates(computer);
			computer.setId(id);
			actionRealized = computerService.updateComputer(computer);
			if (actionRealized)
				actionValid("modification");
			break;

		case 6:
			System.out.println("Quel ordinateur voulez-vous supprimer? ");
			id = (long) newEntry(sc, 0, -1);
			actionRealized = computerService.deleteComputer(id);
			if (actionRealized)
				actionValid("suppression");
			break;

		case 7:
			int nbreLine = 50, numPage = 1;
			System.out.println("Combien d'éléments par page voulez-vous afficher? (minimum 1)");
			nbreLine = intEntry(sc, 1, -1);
			while (numPage > 0) {
				System.out.println("Quelle page voulez-vous afficher? (0 pour retourner au menu principal");
				numPage = intEntry(sc, 0, -1);
				if (numPage > 0) {
					page = pageService.getPage(nbreLine, numPage);
					if (page.getComputers().size() > 0) {
						System.out.println(page.toString());
					} else {
						System.out.println("Numéro de page trop grand. \n" + "Page [nbreLine=" + page.getNbreLine()
								+ ", numPage=" + page.getNumPage() + "]");
					}
				}
			}
			System.out.println("affichage de la pagination terminée. ");
			break;
		}
	}

	/**
	 * Méthode pour la saisie utilisateur d'un ordinateur
	 * 
	 * @return
	 */
	public static Computer typeComputer() {
		Computer computer = new Computer();
		String name;
		LocalDateTime introduced, discontinued;
		Scanner sc = new Scanner(System.in);
		long companyId;
		Company company = new Company();

		System.out.println("Saisissez un nom: ");
		name = sc.nextLine();
		System.out.println("Saisie de la date de début: ");
		introduced = typeDate();
		System.out.println("Saisie de la date de fin: ");
		discontinued = typeDate();
		System.out.println("Saisie de l'id d'une company: ");
		companyId = (long) intEntry(sc, 0, -1);
		company.setId(companyId);

		computer.setName(name);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		computer.setCompany(company);

		return computer;
	}

	/**
	 * Méthode qui va gérer la saisie d'une date par l'utilisateur.
	 * 
	 * @return: la date saisie après vérification.
	 */
	public static LocalDateTime typeDate() {
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
	 * Méthode qui va demander à l'utilisateur de rentrer les nouvelles valeurs
	 * 
	 * @param computer:
	 *            Objet à modifier
	 * @return le Computer mis à jour
	 */
	public static Computer typeComputerUpdates(Computer computer) {
		Company company = computer.getCompany();
		int field;
		Scanner sc = new Scanner(System.in);
		do {
			field = updateMenu();
			switch (field) {
			case 1:
				computer.setName(newName());
				break;
			case 2:
				computer.setIntroduced(typeDate());
				break;
			case 3:
				computer.setDiscontinued(typeDate());
				break;
			case 4:
				long companyId;
				System.out.println("Saisissez un nouveau company_id:");
				companyId = (long) intEntry(sc, 0, -1);
				company.setId(companyId);
				computer.setCompany(company);
				break;
			}
		} while (continuerUpdate());
		return computer;
	}

	/**
	 * Méthode qui va gérer le menu de mise à jour d'un Computer (quel champ
	 * mettre à jour)
	 * 
	 * @return return l'indice du champ à modifier
	 */
	public static int updateMenu() {
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

	/**
	 * Méthode qui va lire la saisie clavier et la retourner sous forme de
	 * String
	 * 
	 * @return
	 */
	public static String newName() {
		String result;
		Scanner sc = new Scanner(System.in);
		System.out.println("renseignez la nouvelle valeur du champ name:");
		result = sc.nextLine();
		// sc.close();
		return result;
	}

	/**
	 * Méthode qui demande à l'utilisateur si un autre champ est à modifier
	 * 
	 * @return true si oui, non sinon
	 */
	public static boolean continuerUpdate() {
		Scanner sc = new Scanner(System.in);
		String continu = "";
		System.out.println("Voulez-vous mettre un autre champ à jour?[O/n]");
		continu = sc.nextLine();
		// sc.close();
		if (continu.equalsIgnoreCase("o"))
			return true;
		else
			return false;

	}

	/**
	 * Méthode appelée lorsque l'action menée en base a été exécutée avec succès
	 * 
	 * @param action:
	 *            libellé de l'action menée
	 */
	public static void actionValid(String action) {
		System.out.println("La " + action + " a été réalisée avec succès.");
	}
}
