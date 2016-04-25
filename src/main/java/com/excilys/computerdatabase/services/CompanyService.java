package com.excilys.computerdatabase.services;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.exceptions.ConnexionException;
import com.excilys.computerdatabase.exceptions.DaoException;

public class CompanyService {
    private static final CompanyDao COMPANY_DAO = CompanyDao.getInstance();
    private static CompanyService instance = null;

    /**
     * Constructor of the CompanyService class.
     */
    public CompanyService() {
    }

    /**
     * Method which create an instance of CompanyService if there is no current
     * one and return it.
     *
     * @return the current or created instance of CompanyService.
     */
    public static CompanyService getInstance() {
        if (instance == null) {
            synchronized (CompanyService.class) {
                if (instance == null) {
                    instance = new CompanyService();
                }
            }
        }
        return instance;
    }

    /**
     * Method which calls the CompanyDao to get all the companies from the
     * database.
     *
     * @return a list of all the companies.
     * @throws DaoException
     *             sent by the CompanyDao.
     * @throws ConnexionException
     *             sent by the CompanyDao.
     */
    public List<Company> getCompanies() throws DaoException, ConnexionException {
        List<Company> companies = new ArrayList<Company>();
        companies = COMPANY_DAO.getAll();
        return companies;
    }
}
