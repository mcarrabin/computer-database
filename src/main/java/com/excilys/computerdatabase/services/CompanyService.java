package com.excilys.computerdatabase.services;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.exceptions.ServiceException;

public enum CompanyService {
    INSTANCE;
    private static final CompanyDao COMPANY_DAO = CompanyDao.INSTANCE;

    /**
     * Method which calls the CompanyDao to get all the companies from the
     * database.
     *
     * @return a list of all the companies.
     */
    public List<Company> getCompanies() {
        List<Company> companies = new ArrayList<Company>();
        try {
            companies = COMPANY_DAO.getAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return companies;
    }

    /**
     * Method which calls the CompanyDao to get the company having the id
     * received as a parameter.
     *
     * @param id
     *            is the id of the company to look for.
     * @return the company found.
     */
    public Company getCompanyById(long id) {
        Company company = null;
        try {
            company = COMPANY_DAO.getById(id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return company;
    }
}
