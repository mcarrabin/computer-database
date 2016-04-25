package com.excilys.computerdatabase.services;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.entities.Computer;

public class ComputerServiceTest extends junit.framework.TestCase {
    private static ComputerDao computerDao;

    private static

    @BeforeClass protected void testBefore() {
        ComputerDao computerDao = ComputerDao.getInstance();
    }

    @Test
    public void getAll() {
        try {
            List<Computer> computers = ComputerDao.getInstance().getAll();
        } catch (Exception e) {
            System.out.println("bla bla");
        }
    }

    @AfterClass
    protected void messageAdieu() {

    }

}
