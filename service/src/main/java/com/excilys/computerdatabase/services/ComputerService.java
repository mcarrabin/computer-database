package com.excilys.computerdatabase.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.dao.AbstractDao;
import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;
import com.excilys.computerdatabase.exceptions.ServiceException;

@Service("computerService")
public class ComputerService implements IService<Computer> {
    @Autowired
    public AbstractDao<Computer> computerDao;

    @Override
    public List<Computer> getAll() {
        try {
            return computerDao.getAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Computer getById(long id) {
        try {
            return computerDao.getById(id);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
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
        try {
            Page<Computer> page = computerDao.getByPage(nbreLine, numPage, name, orderBy);
            return page;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Computer computer) {
        Company company = null;
        if (computer.getCompany() != null && computer.getCompany().getId() == -1) {
            computer.setCompany(company);
        }
        try {
            computerDao.update(computer);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(long id) {
        try {
            Computer computer = computerDao.getById(id);
            computerDao.delete(computer);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void create(Computer computer) {
        Company company = null;
        if (computer.getCompany() != null && computer.getCompany().getId() == -1) {
            computer.setCompany(company);
        }
        try {
            computerDao.create(computer);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
