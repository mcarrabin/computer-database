package com.excilys.computerdatabase.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Company.CompanyBuilder;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Computer.ComputerBuilder;
import com.excilys.computerdatabase.exceptions.ConnexionException;
import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.exceptions.ServiceException;

public class ComputerServiceTest extends junit.framework.TestCase {
    private static ComputerService computerServ;
    private static Computer computer1;
    private static Computer computer2;
    private static Company company1;
    private static Company company2;

    @Override
    @BeforeClass
    public void setUp() throws DaoException, ConnexionException {
        LocalDateTime dateTime = LocalDateTime.of(1980, Month.JANUARY, 01, 0, 0, 0);
        LocalDateTime dateTime2 = LocalDateTime.of(2010, Month.JANUARY, 01, 0, 0, 0);

        computerServ = mock(ComputerService.class);
        company1 = new CompanyBuilder().name("company1").id(1).build();
        company2 = new CompanyBuilder().name("company2").id(2).build();

        computer1 = new ComputerBuilder().name("computer 1").company(company1).id(1).introduced(dateTime)
                .discontinued(dateTime2).build();
        computer2 = new ComputerBuilder().name("computer 2").company(company2).id(2).build();

        when(computerServ.getComputers()).thenReturn(Arrays.asList(computer1, computer2));
        when(computerServ.getComputerById((long) 1)).thenReturn(computer1);
        when(computerServ.createComputer(computer1)).thenReturn(true);
        when(computerServ.updateComputer(computer1)).thenReturn(true);
        when(computerServ.deleteComputer(1)).thenReturn(true);
    }

    @Test
    public void testGetComputers() throws ServiceException {
        String name = "computer 1", companyName = "company1";
        LocalDateTime dateTime = LocalDateTime.of(1980, Month.JANUARY, 01, 0, 0, 0);
        LocalDateTime dateTime2 = LocalDateTime.of(2010, Month.JANUARY, 01, 0, 0, 0);
        long id = 1;

        try {
            List<Computer> computers = computerServ.getComputers();
            Computer computer = computers.get(0);
            assertNotNull(computers);
            assertEquals(2, computers.size());
            assertEquals(id, computer.getId());
            assertEquals(name, computer.getName());
            assertEquals(companyName, computer.getCompany().getName());
            assertEquals(dateTime, computer.getIntroduced());
            assertEquals(dateTime2, computer.getDiscontinued());
            assertEquals(id, computer.getCompany().getId());
        } catch (Exception e) {

        }
    }

    @Test
    public void testGetComputerById() throws ServiceException {
        String name = "computer 1", companyName = "company1";
        LocalDateTime dateTime = LocalDateTime.of(1980, Month.JANUARY, 01, 0, 0, 0);
        LocalDateTime dateTime2 = LocalDateTime.of(2010, Month.JANUARY, 01, 0, 0, 0);
        long id = 1;
        try {
            Computer computer = computerServ.getComputerById((long) 1);
            assertEquals(id, computer.getId());
            assertEquals(name, computer.getName());
            assertEquals(companyName, computer.getCompany().getName());
            assertEquals(dateTime, computer.getIntroduced());
            assertEquals(dateTime2, computer.getDiscontinued());
            assertEquals(id, computer.getCompany().getId());
        } catch (Exception e) {

        }
    }

    @Test
    public void updateComputer() throws ServiceException {

        try {
            boolean response = computerServ.createComputer(computer1);
            assertTrue(response);
        } catch (Exception e) {

        }
    }

    @Test
    public void deleteComputer() throws ServiceException {
        try {
            boolean response = computerServ.deleteComputer(1);
            assertTrue(response);
        } catch (Exception e) {

        }
    }

    @Test
    public void createComputer() throws ServiceException {
        try {
            boolean response = computerServ.createComputer(computer1);
            assertTrue(response);
        } catch (Exception e) {

        }
    }
}
