package com.excilys.computerdatabase.services;

import java.util.List;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;
import com.excilys.computerdatabase.exceptions.ConnexionException;
import com.excilys.computerdatabase.exceptions.DaoException;

public class ComputerService {
    private static final ComputerDao COMPUTER_DAO = ComputerDao.getInstance();
    private static ComputerService instance = null;

    /**
     * Constructor of the ComupterService class.
     */
    public ComputerService() {
    }

    /**
     * Method which create an instance of ComputerService if it does not exist
     * and return it.
     *
     * @return the instance of ComputerService created.
     */
    public static ComputerService getInstance() {
        if (instance == null) {
            synchronized (ComputerService.class) {
                if (instance == null) {
                    instance = new ComputerService();
                }
            }
        }
        return instance;
    }

    /**
     * Méthode qui va demander la liste complète des ordinnateurs au DAO.
     *
     * @return un ArrayList<ComputerEntity> contenant tous les ordinateurs de la
     *         base.
     * @throws DaoException
     *             from the ComputerDao method
     * @throws ConnexionException
     *             from the computerDao method connexion
     */
    public List<Computer> getComputers() throws DaoException, ConnexionException {
        return COMPUTER_DAO.getAll();
    }

    /**
     * Méthode qui va retourner la liste des ordinateur dont le nom commence par
     * le paramètre name.
     *
     * @param id
     *            id de l'ordinateur voulu
     * @return l'ordinnateur dont l'id est envoyé en paramètre
     * @throws DaoException
     *             from the ComputerDao method
     * @throws ConnexionException
     *             from the computerDao method connexion
     */
    public Computer getComputerById(Long id) throws DaoException, ConnexionException {
        Computer computer = new Computer();
        computer = COMPUTER_DAO.getById(id);

        return computer;
    }

    /**
     * Method which will call the ComputerDao to get the computers from the
     * database.
     *
     * @param nbreLine
     *            is the numer of elements expected.
     * @param numPage
     *            is the page number.
     * @return a page object.
     * @throws DaoException
     *             if something went wrong during the Computer getting.
     * @throws ConnexionException
     *             if something went wrong with the connexion (creation and
     *             closure).
     */
    public Page getComputerByPage(int nbreLine, int numPage) throws DaoException, ConnexionException {
        return COMPUTER_DAO.getByPage(nbreLine, numPage);
    }

    /**
     * Méthod wich will call the update method of the ComputerDao.
     *
     * @param computer
     *            is the object to update.
     * @return true if everything went well, else false.
     * @throws DaoException
     *             if the update failed
     * @throws ConnexionException
     *             if something was wrong with the connexion
     */
    public boolean updateComputer(Computer computer) throws DaoException, ConnexionException {
        return COMPUTER_DAO.updateComputer(computer);
    }

    /**
     * Méthod wich will call the delete method of the ComputerDao.
     *
     * @param id
     *            is the object to delete.
     * @return true if everything went well, else false.
     * @throws DaoException
     *             if the deletion failed
     * @throws ConnexionException
     *             if something was wrong with the connexion.
     */
    public boolean deleteComputer(long id) throws DaoException, ConnexionException {
        Computer computer = COMPUTER_DAO.getById(id);
        return COMPUTER_DAO.deleteComputer(computer);
    }

    /**
     * Méthod wich will call the create method of the ComputerDao.
     *
     * @param computer
     *            is the object to create.
     * @return true if everything went well, else false.
     * @throws DaoException
     *             if the creation failed
     * @throws ConnexionException
     *             if something was wrong with the connexion
     */
    public boolean createComputer(Computer computer) throws DaoException, ConnexionException {
        return COMPUTER_DAO.createComputer(computer);
    }

    /**
     * Method which verifies that the discontinued date is after the introduced
     * date.
     *
     * @param computer
     *            is the Object the dates which need to be verify are linked to.
     * @return true if the discontinued date is after the introduced one, else
     *         false.
     */
    public boolean isObjectValid(Computer computer) {
        return computer.getDiscontinued().isAfter(computer.getIntroduced());
    }
}
