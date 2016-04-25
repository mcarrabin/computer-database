package com.excilys.computerdatabase.services;

import java.util.List;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;
import com.excilys.computerdatabase.exceptions.ConnexionException;
import com.excilys.computerdatabase.exceptions.DaoException;

public class ComputerService {
	public static final ComputerDao COMPUTER_DAO = ComputerDao.getInstance();
	private static ComputerService instance = null;
	
	public ComputerService(){}
	
	synchronized public static ComputerService getInstance(){
		if(instance == null) {
			instance = new ComputerService();
		}
		return instance;
	}

	/**
	 * Méthode qui va demander la liste complète des ordinnateurs au DAO.
	 * 
	 * @return un ArrayList<ComputerEntity> contenant tous les ordinateurs de la
	 *         base
	 */
	public List<Computer> getComputers() throws DaoException, ConnexionException {
		return COMPUTER_DAO.getAll();
	}

	/**
	 * Méthode qui va retourner la liste des ordinateur dont le nom commence par
	 * le paramètre name
	 * 
	 * @param id:
	 *            id de l'ordinateur voulu
	 * @return l'ordinnateur dont l'id est envoyé en paramètre
	 */
	public Computer getComputerById(Long id) throws DaoException, ConnexionException {
		Computer computer = new Computer();
		computer = COMPUTER_DAO.getById(id);

		return computer;
	}
	
	/**
	 * Méthode qui va demander au Dao de construire une page et de la retourner
	 * @param nbreLine: nbre de ligne par page
	 * @param numPage: numéro de la page voulue
	 * @return la page construite
	 */
	public Page getComputerByPage(int nbreLine, int numPage) throws DaoException, ConnexionException {
		return COMPUTER_DAO.getByPage(nbreLine, numPage);
	}

	/**
	 * Méthode qui va demander à l'utilisateur de mettre à jour un ordinateur
	 */
	public boolean updateComputer(Computer computer) throws DaoException, ConnexionException {
		return COMPUTER_DAO.updateComputer(computer);
	}

	/**
	 * Méthode qui va récupérer un computer grâce à un id saisi par
	 * l'utilisateur puis appeler la méthode de suppression du DAO
	 */
	public boolean deleteComputer(long id) throws DaoException, ConnexionException {
		Computer computer = COMPUTER_DAO.getById(id);
		return COMPUTER_DAO.deleteComputer(computer);
	}
	
	/**
	 * Méthode qui va appeler la méthode createComputer du DAO computer en passant en paramètre la liste des company
	 */
	public boolean createComputer(Computer computer) throws DaoException, ConnexionException {
		return COMPUTER_DAO.createComputer(computer);
	}
	
	public boolean isObjectValid(Computer computer){
		return computer.getDiscontinued().isAfter(computer.getIntroduced());
	}
}
