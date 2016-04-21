package com.excilys.computerdatabase.services;

import java.util.ArrayList;
import java.util.Scanner;

import com.excilys.computerdatabase.DAO.CompanyDAO;
import com.excilys.computerdatabase.DAO.ComputerDAO;
import com.excilys.computerdatabase.entities.CompanyEntity;
import com.excilys.computerdatabase.entities.ComputerEntity;

public class ComputerService {
	private ComputerDAO computerDao;
	private CompanyDAO companyDao;

	public ComputerService() {
		computerDao = new ComputerDAO();
		companyDao = new CompanyDAO();
	}

	public ComputerDAO getComputerDao() {
		return this.computerDao;
	}
	public CompanyDAO getCompanyDao(){
		return this.companyDao;
	}
	public void setCompanyDao(CompanyDAO pCompanyDao){
		this.companyDao = pCompanyDao;
	}
	public void setComputerDao(ComputerDAO pComputerDao) {
		this.computerDao = pComputerDao;
	}
	
	public ArrayList<CompanyEntity> getCompanies(){
		ArrayList<CompanyEntity> companies = new ArrayList<CompanyEntity>();
		companies = companyDao.getCompanies();
		return companies;
	}

	/**
	 * Méthode qui va demander la liste complète des ordinnateurs au DAO.
	 * 
	 * @return un ArrayList<ComputerEntity> contenant tous les ordinateurs de la
	 *         base
	 */
	public ArrayList<ComputerEntity> getComputers() {
		return computerDao.getComputers();
	}

	/**
	 * Méthode qui va retourner la liste des ordinateur dont le nom commence par
	 * le paramètre name
	 * 
	 * @param name:
	 *            début ou nom complet de(s) l'ordinateur(s) voulu(s)
	 * @return la liste des ordinnateurs dont le nom commence par "name"
	 */
	public ComputerEntity getComputerById(Long id) {
		ComputerEntity computer = new ComputerEntity();
		computer = computerDao.getById(id);

		return computer;
	}

	/**
	 * Méthode qui va demander à l'utilisateur de mettre à jour un ordinateur
	 */
	public void updateComputer() {
		Long id = getIdByUser();
		ComputerEntity computer = computerDao.getById(id);
		computer.updateComputer(companyDao.getCompanies());
		computerDao.updateComputer(computer);
	}

	/**
	 * Méthode qui va récupérer un computer grâce à un id saisi par
	 * l'utilisateur puis appeler la méthode de suppression du DAO
	 */
	public void deleteComputer() {
		Long id = getIdByUser();
		ComputerEntity computer = computerDao.getById(id);
		computerDao.deleteComputer(computer);
	}

	/**
	 * Méthode qui va demander à l'utilisateur de saisir un id d'ordinateur à
	 * mettre à jour en vérifiant son existance. Tant que l'id est inexistant,
	 * l'utilisateur devra recommencer sa saisie.
	 * 
	 * @return l'id de l'ordinateur à mettre à jour
	 */
	public Long getIdByUser() {
		Scanner sc = new Scanner(System.in);
		Long id;
		while (true) {
			System.out.println("quel est l'id de l'ordinateur à changer?");
			id = new Long(ComputerEntity.intEntry(sc, 0, -1));
			if (computerDao.checkComputerExists(id))
				break;
			else
				System.out.println("L'id saisi n'existe pas");
		}
		return id;
	}
	
	/**
	 * Méthode qui va appeler la méthode createComputer du DAO computer en passant en paramètre la liste des company
	 */
	public void createComputer(){
		computerDao.createComputer(companyDao.getCompanies());
	}
	
	public void displayPagination(){
		
	}
}
