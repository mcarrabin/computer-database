package com.excilys.computerdatabase.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.dao.ComputerDao;
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
        List<Computer> computers = new ArrayList<>();
        try {
            computers = computerDao.getAll();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return computers;
    }

    @Override
    public Computer getById(long id) {
        Computer computer = null;
        try {
            computer = computerDao.getById(id);
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
            page = computerDao.getByPage(nbreLine, numPage, name, orderBy);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return page;
    }

    @Override
    public boolean update(Computer computer) {
        boolean result = false;
        try {
            result = computerDao.updateComputer(computer);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean delete(long id) {
        boolean result = false;
        try {
            Computer computer = computerDao.getById(id);
            result = computerDao.delete(computer.getId());
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean create(Computer computer) {
        boolean result = false;
        try {
            result = computerDao.createComputer(computer);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return result;
    }
}
