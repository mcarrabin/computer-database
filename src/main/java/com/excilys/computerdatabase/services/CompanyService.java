package com.excilys.computerdatabase.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.DBManager;
import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.exceptions.ServiceException;

@Service("companyService")
public class CompanyService implements IService<Company> {
    @Autowired
    @Qualifier("companyDao")
    private CompanyDao companyDao;

    @Autowired
    @Qualifier("computerDao")
    private ComputerDao computerDao;

    @Autowired
    @Qualifier("dbManager")
    private DBManager dbManager;

    @Override
    public List<Company> getAll() {
        List<Company> companies = null;
        try {
            companies = companyDao.getAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return companies;
    }

    @Override
    public Company getById(long id) {
        Company company = null;
        try {
            company = companyDao.getById(id);
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
    @Transactional
    @Override
    public boolean delete(long id) {
        Company company = companyDao.getById(id);
        try {
            computerDao.deleteByCompany(company.getId());
            companyDao.delete(company.getId());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return true;
    }
}
