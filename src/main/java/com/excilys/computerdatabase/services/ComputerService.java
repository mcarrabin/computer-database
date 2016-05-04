package com.excilys.computerdatabase.services;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;
import com.excilys.computerdatabase.exceptions.ServiceException;

public enum ComputerService {
    INSTANCE;
    private static final ComputerDao COMPUTER_DAO = ComputerDao.INSTANCE;

    /**
     * Méthode qui va demander la liste complète des ordinnateurs au DAO.
     *
     * @return un ArrayList<ComputerEntity> contenant tous les ordinateurs de la
     *         base.
     */
    public List<Computer> getComputers() {
        List<Computer> computers = new ArrayList<>();
        try {
            computers = COMPUTER_DAO.getAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return computers;
    }

    /**
     * Méthode qui va retourner la liste des ordinateur dont le nom commence par
     * le paramètre name.
     *
     * @param id
     *            id de l'ordinateur voulu
     * @return l'ordinnateur dont l'id est envoyé en paramètre
     */
    public Computer getComputerById(Long id) {
        Computer computer = null;
        try {
            computer = COMPUTER_DAO.getById(id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }

        return computer;
    }

    /**
     * Method which will call the ComputerDao to get the computers from the
     * database. Eventually, the name parameter can be filled. In that case, the
     * request will take every computer which names contain the name parameter.
     *
     * @param nbreLine
     *            is the number of elements expected.
     * @param numPage
     *            is the page number.
     * @param name
     *            is the name search parameter.
     * @return a page object.
     */
    public Page<Computer> getComputerByPage(int nbreLine, int numPage, String name, String orderBy) {
        Page<Computer> page = null;
        try {
            page = COMPUTER_DAO.getByPage(nbreLine, numPage, name, orderBy);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return page;
    }

    /**
     * Méthod wich will call the update method of the ComputerDao.
     *
     * @param computer
     *            is the object to update.
     * @return true if everything went well, else false.
     */
    public boolean updateComputer(Computer computer) {
        boolean result = false;
        try {
            result = COMPUTER_DAO.updateComputer(computer);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return result;
    }

    /**
     * Méthod wich will call the delete method of the ComputerDao.
     *
     * @param id
     *            is the object to delete.
     * @return true if everything went well, else false.
     */
    public boolean deleteComputer(long id) {
        boolean result = false;
        try {
            Computer computer = COMPUTER_DAO.getById(id);
            result = COMPUTER_DAO.delete(computer);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return result;
    }

    /**
     * Méthod wich will call the create method of the ComputerDao.
     *
     * @param computer
     *            is the object to create.
     * @return true if everything went well, else false.
     */
    public boolean createComputer(Computer computer) {
        boolean result = false;
        try {
            result = COMPUTER_DAO.createComputer(computer);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return result;
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
