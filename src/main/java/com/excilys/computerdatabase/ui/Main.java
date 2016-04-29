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
    /**
     * Start of the app.
     *
     * @param args
     *            not used here.
     */
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

    /**
     * Méthod that will ask the action to do to the user.
     *
     * @return the id of the action.
     */
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

            if (result != -1) {
                isReponseCorrecte = true;
            } else {
                System.out.println("Saisie incorrecte. Merci de rentrer le numéro de l'action à faire");
            }
        }
        return result;
    }

    /**
     * Method that will return the int typed by the user.
     *
     * @param sc
     *            scanner
     * @param min
     *            is the minimal value. -1 if there is no minimum limit.
     * @param max
     *            is the maximal value. -1 if there is no maximum limit.
     * @return the int value typed. -1 if the typed value is not an int.
     */
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

    /**
     * Method that will ask the user if other actions have to be done.
     *
     * @return true if the user type "o", false if he types "n". If any other
     *         response, the user will have to type another response.
     */
    public static boolean continuerMainMenu() {
        String reponse = null;
        Scanner sc = new Scanner(System.in);
        boolean isReponseCorrecte = false;
        while (!isReponseCorrecte) {
            System.out.println("Voulez-vous continuer? [O/n]");
            reponse = sc.nextLine();
            if (reponse.equalsIgnoreCase("o") || reponse.equalsIgnoreCase("n")) {
                isReponseCorrecte = true;
            }
        }
        // sc.close();

        if (reponse.equalsIgnoreCase("o")) {
            return true;
        }

        return false;
    }

    /**
     * Method that will execute the action received as a parameter.
     *
     * @param action
     *            is the id of the requested action.
     * @param computerService
     *            is the current instance of ComputerService
     * @param companyService
     *            is the current instance of CompanyService
     * @param pageService
     *            is the current instance of PageService
     */
    public static void doAction(int action, ComputerService computerService, CompanyService companyService,
            PageService pageService) {
        Computer computer = new Computer();
        Page<Computer> page = null;
        Scanner sc = new Scanner(System.in);
        boolean actionRealized = false;
        List<Computer> computers = new ArrayList<Computer>();
        long id;
        switch (action) {
        case 1:
            try {
                List<Company> companies = new ArrayList<Company>();
                companies = companyService.getCompanies();
                for (Company company : companies) {
                    System.out.println(company.toString());
                }
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }

        case 2:
            try {
                computers = computerService.getComputers();
                for (Computer c : computers) {
                    System.out.println(c.toString());
                }
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }

        case 3:
            try {
                System.out.println("Quel ordinateur voulez-vous afficher? ");
                id = newEntry(sc, 0, -1);
                computer = computerService.getComputerById(id);
                System.out.println(computer.toString());
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }

        case 4:
            try {
                computer = typeComputer();
                actionRealized = computerService.createComputer(computer);
                if (actionRealized) {
                    actionValid("création");
                }
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }

        case 5:
            try {
                System.out.println("Quel ordinateur voulez-vous modifier? ");
                id = newEntry(sc, 0, -1);
                computer = computerService.getComputerById(id);
                computer = typeComputerUpdates(computer);
                computer.setId(id);
                actionRealized = computerService.updateComputer(computer);
                if (actionRealized) {
                    actionValid("modification");
                }
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }

        case 6:
            try {
                System.out.println("Quel ordinateur voulez-vous supprimer? ");
                id = newEntry(sc, 0, -1);
                actionRealized = computerService.deleteComputer(id);
                if (actionRealized) {
                    actionValid("suppression");
                }
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }

        case 7:
            try {
                int nbreLine = 50, numPage = 1;
                System.out.println("Combien d'éléments par page voulez-vous afficher? (minimum 1)");
                nbreLine = intEntry(sc, 1, -1);
                while (numPage > 0) {
                    System.out.println("Quelle page voulez-vous afficher? (0 pour retourner au menu principal");
                    numPage = intEntry(sc, 0, -1);
                    if (numPage > 0) {
                        page = pageService.getPage(nbreLine, numPage, "Amiga");
                        if (page.getElements().size() > 0) {
                            System.out.println(page.toString());
                        } else {
                            System.out.println("Numéro de page trop grand. \n" + "Page [nbreLine="
                                    + page.getElements().size() + ", numPage=" + page.getNumPage() + "]");
                        }
                    }
                }
                System.out.println("affichage de la pagination terminée. ");
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    /**
     * Méthode pour la saisie utilisateur d'un ordinateur.
     *
     * @return the Computer filled with the values typed by the user.
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
        companyId = intEntry(sc, 0, -1);
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
     * @return la date saisie après vérification.
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
                if (jour == 0 && mois == 0 && annee == 0) {
                    res = null;
                } else {
                    res = LocalDateTime.of(annee, mois, jour, 0, 0, 0);
                }
                isDateOK = true;
            } catch (Exception e) {
                System.out.println("Merci de ressaisir la date");
            }
        }
        return res;
    }

    /**
     * This method will check that the user typed an int.
     *
     * @param sc
     *            scanner
     * @param min
     *            Minimal value the user can type. -1 if these test does not
     *            have to be done.
     * @param max
     *            Maximal value the user can type. -1 if these test does not
     *            have to be done.
     * @return the int typed by the user if it passes the tests, else -1.
     */
    public static int intEntry(Scanner sc, int min, int max) {
        String temp = sc.nextLine();
        if (temp != null && temp.length() > 0) {
            try {
                int i = Integer.parseInt(temp);
                if ((i < min && min != -1) || (i > max && max != -1)) {
                    throw new Exception();
                }
                return i;
            } catch (Exception e) {
                System.out.println("La valeur saisie est incorrecte");
                return -1;
            }
        }
        return 0;
    }

    /**
     * This method will ask the user to type the Computer object new values.
     *
     * @param computer
     *            initial object
     * @return Computer object updated
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
                companyId = intEntry(sc, 0, -1);
                company.setId(companyId);
                computer.setCompany(company);
                break;
            }
        } while (continuerUpdate());
        return computer;
    }

    /**
     * Méthode qui va gérer le menu de mise à jour d'un Computer (quel champ
     * mettre à jour).
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
     * String.
     *
     * @return the value typed by the user as a String.
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
     * Méthode qui demande à l'utilisateur si un autre champ est à modifier.
     *
     * @return true si oui, non sinon
     */
    public static boolean continuerUpdate() {
        Scanner sc = new Scanner(System.in);
        String continu = "";
        System.out.println("Voulez-vous mettre un autre champ à jour?[O/n]");
        continu = sc.nextLine();
        // sc.close();
        return continu.equalsIgnoreCase("o");

    }

    /**
     * Méthode appelée lorsque l'action menée en base a été exécutée avec
     * succès.
     *
     * @param action
     *            libellé de l'action menée
     */
    public static void actionValid(String action) {
        System.out.println("La " + action + " a été réalisée avec succès.");
    }
}
