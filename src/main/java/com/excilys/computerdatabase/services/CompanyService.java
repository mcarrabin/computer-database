package com.excilys.computerdatabase.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.DBConnection;
import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.exceptions.ServiceException;

public enum CompanyService {
    INSTANCE;
    private static final CompanyDao COMPANY_DAO = CompanyDao.INSTANCE;
    private static final ComputerDao COMPUTER_DAO = ComputerDao.INSTANCE;

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

    /**
     * Method that will call ComputerDao instance and CompanyDao instance to
     * delete every computer linked on the company received as a parameter in a
     * first place. The company will be deleted in a second place.
     *
     * @param company
     *            is the object to delete.
     */
    public void deleteCompany(Company company) {
        Connection con = DBConnection.INSTANCE.getConnection();
        try {
            con.setAutoCommit(false);
            COMPUTER_DAO.deleteByCompany(company.getId(), con);
            COMPANY_DAO.delete(company, con);
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                throw new ServiceException(e1);
            }
            throw new ServiceException(e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                throw new ServiceException(e);
            }
        }
    }
}
