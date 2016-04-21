package com.excilys.computerdatabase.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.excilys.computerdatabase.entities.CompanyEntity;
import com.excilys.computerdatabase.entities.ComputerEntity;
import com.excilys.computerdatabase.services.ComputerService;

public class Main {
	public static void main(String... args) {
		int actionAFaire = 0;
		ComputerService computerService = new ComputerService();
		do {
			actionAFaire = displayMenu();
			doAction(actionAFaire, computerService);
		} while (continuer());
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
			if ((i < min || min == -1) && (i > max || max == -1)){
				throw new Exception();
			}
			return i;
		} catch (Exception e) {
			return -1;
		}
	}

	public static boolean continuer() {
		String reponse = null;
		Scanner sc = new Scanner(System.in);
		boolean isReponseCorrecte = false;
		while (!isReponseCorrecte) {
			System.out.println("Voulez-vous continuer? [O/n]");
			reponse = sc.nextLine();
			if (reponse.equalsIgnoreCase("o") || reponse.equalsIgnoreCase("n"))
				isReponseCorrecte = true;
		}

		if (reponse.equalsIgnoreCase("o"))
			return true;

		return false;
	}

	public static void doAction(int action, ComputerService computerService) {
		ComputerEntity computer = new ComputerEntity();
		List<ComputerEntity> computers = new ArrayList<ComputerEntity>();
		switch (action) {
		case 1:
			ArrayList<CompanyEntity> companies = new ArrayList<CompanyEntity>();
			companies = computerService.getCompanies();
			for (CompanyEntity company : companies) {
				System.out.println(company.toString());
			}
			break;

		case 2:
			computers = computerService.getComputers();
			for (ComputerEntity c : computers) {
				System.out.println(c.toString());
			}
			break;

		case 3:
			Scanner sc = new Scanner(System.in);
			System.out.println("Quel ordinateur voulez-vous afficher? ");
//			sc.nextLine();
			Long id = new Long(newEntry(sc, 0, -1));
			computer = computerService.getComputerById(id);
			System.out.println(computer.toString());
			break;

		case 4:
			computerService.createComputer();
			break;

		case 5:
			computerService.updateComputer();
			break;

		case 6:
			computerService.deleteComputer();
			break;
			
		case 7:
			computerService.displayPagination();
		}
	}
}
