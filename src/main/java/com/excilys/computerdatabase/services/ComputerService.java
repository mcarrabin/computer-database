package com.excilys.computerdatabase.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;
import com.excilys.computerdatabase.exceptions.ServiceException;

@Service("computerService")
public class ComputerService implements IService<Computer> {
    @Autowired
    @Qualifier("computerDao")
    private ComputerDao computerDao;

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
    public boolean update(Computer computer) {
        Company company = null;
        if (computer.getCompany() != null && computer.getCompany().getId() == -1) {
            computer.setCompany(company);
        }
        try {
            return computerDao.updateComputer(computer);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(long id) {
        try {
            Computer computer = computerDao.getById(id);
            return computerDao.delete(computer.getId());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean create(Computer computer) {
        Company company = null;
        if (computer.getCompany() != null && computer.getCompany().getId() == -1) {
            computer.setCompany(company);
        }
        try {
            return computerDao.createComputer(computer);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}
