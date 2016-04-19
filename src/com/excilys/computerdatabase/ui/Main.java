package com.excilys.computerdatabase.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.excilys.computerdatabase.DAO.CompanyDAO;
import com.excilys.computerdatabase.DAO.ComputerDAO;
import com.excilys.computerdatabase.entities.CompanyEntity;
import com.excilys.computerdatabase.entities.ComputerEntity;
import com.excilys.computerdatabase.services.ComputerService;

public class Main {
	public static void main(String... args) {
		int actionAFaire = 0;
		actionAFaire = displayMenu();
		doAction(actionAFaire);
		while(continuer()){
			actionAFaire = displayMenu();
			doAction(actionAFaire);
		}
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
			result = newEntry(sc);

			if (result != -1)
				isReponseCorrecte = true;
			else
				System.out.println("Saisie incorrecte. Merci de rentrer le numéro de l'action à faire");
		}
		return result;
	}

	public static int newEntry(Scanner sc) {
		String result = sc.nextLine();
		try {
			int i = Integer.parseInt(result);
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
			if(reponse.equalsIgnoreCase("o") || reponse.equalsIgnoreCase("n"))
				isReponseCorrecte = true;
		}

		if (reponse.equalsIgnoreCase("o"))
			return true;
		
		return false;
	}
	
	public static void doAction(int action){
		switch (action){
			case 1: 
				CompanyDAO companyDao = new CompanyDAO();
				ArrayList<CompanyEntity> companies = new ArrayList<CompanyEntity>();
				companies = companyDao.getCompanies();
				for(CompanyEntity company: companies){
					System.out.println(company.toString());
				}
				break;
				
			case 2: 
				ComputerDAO computerDao = new ComputerDAO();
				ArrayList<ComputerEntity> computers = new ArrayList<ComputerEntity>();
				computers = computerDao.getComputers();
				for(ComputerEntity computer: computers){
					System.out.println(computer.toString());
				}
				break;
				
			case 3:
				Scanner sc = new Scanner(System.in);
				System.out.println("Quel ordinateur voulez-vous afficher? ");
				String name = sc.nextLine();
				ComputerService computerService = new ComputerService();
				computers = computerService.getComputerByName(name);
				for(ComputerEntity c: computers){
					System.out.println(c.toString());
				}
				break;
				
			case 4:
				
			case 5:
				
			case 6:
		}
	}
}
