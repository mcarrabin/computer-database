package com.excilys.computerdatabase.services;

import java.sql.Connection;
import java.util.List;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.DBManager;
import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.exceptions.ServiceException;

public enum CompanyService implements Service<Company> {
    INSTANCE;
    private static final CompanyDao COMPANY_DAO = CompanyDao.INSTANCE;
    private static final ComputerDao COMPUTER_DAO = ComputerDao.INSTANCE;
    private static final DBManager DB_MANAGER = DBManager.INSTANCE;

    @Override
    public List<Company> getAll() {
        List<Company> companies = null;
        try {
            companies = COMPANY_DAO.getAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return companies;
    }

    @Override
    public Company getById(long id) {
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
    @Override
    public boolean delete(long id) {
        Company company = COMPANY_DAO.getById(id);

        Connection con = DB_MANAGER.getConnection();
        try {
            DB_MANAGER.autoCommit(false);
            COMPUTER_DAO.deleteByCompany(company.getId());
            COMPANY_DAO.delete(company.getId());
            DB_MANAGER.callCommit();
        } catch (DaoException e) {
            DB_MANAGER.callRollback();
            throw new ServiceException(e);
        } finally {
            DB_MANAGER.closeConnection();
        }
        return true;
    }
}
