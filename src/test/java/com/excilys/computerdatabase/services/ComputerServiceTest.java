package com.excilys.computerdatabase.services;

import java.time.LocalDate;
import java.time.Month;

import org.junit.BeforeClass;
import org.junit.Test;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.exceptions.ConnectionException;
import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.exceptions.ServiceException;

public class ComputerServiceTest extends junit.framework.TestCase {
    private static final ComputerService COMPUTER_SERVICE = ComputerService.INSTANCE;

    private static Computer computer1;
    private static Computer computer2;
    private static Company company1;
    private static Company company2;

    /**
     * Instantiation of test objects such as Computers, Companies and
     * ComputerService.
     *
     * @throws ServiceException
     *             if something went wrong during creation or connection
     *             handling.
     */
    @Override
    @BeforeClass
    public void setUp() throws DaoException, ConnectionException {
        LocalDate dateTime = LocalDate.of(1991, Month.JANUARY, 01);
        LocalDate dateTime2 = LocalDate.of(2006, Month.JANUARY, 10);

        // Mockito: inutilisable avec les singletons enum
        // computerServ = mock(ComputerService.class);
        company1 = new Company().getBuilder().name("Apple Inc.").id(1).build();
        company2 = new Company().getBuilder().name("Thinking Machines").id(2).build();

        computer1 = new Computer().getBuilder().name("CM-5").company(company1).id(1).introduced(dateTime)
                .discontinued(dateTime2).build();
        computer2 = new Computer().getBuilder().name("MacBook Pro").company(company2).id(2).build();

        // mockito tests definition
        // when(computerServ.getAll()).thenReturn(Arrays.asList(computer1,
        // computer2));
        // when(computerServ.getById(1)).thenReturn(computer1);
        // when(computerServ.create(computer1)).thenReturn(true);
        // when(computerServ.update(computer1)).thenReturn(true);
        // when(computerServ.delete(1)).thenReturn(true);
    }

    /**
     * Test method to get all computers.
     *
     * @throws ServiceException
     *             if something went wrong during get action or connection
     *             handling.
     */
    // @Test
    // public void testGetComputers() throws ServiceException {
    // String name = "computer 1", companyName = "company1";
    // LocalDateTime dateTime = LocalDateTime.of(1980, Month.JANUARY, 01, 0, 0,
    // 0);
    // LocalDateTime dateTime2 = LocalDateTime.of(2010, Month.JANUARY, 01, 0, 0,
    // 0);
    // long id = 1;
    //
    // try {
    // List<Computer> computers = computerServ.getAll();
    // Computer computer = computers.get(0);
    // assertNotNull(computers);
    // assertEquals(2, computers.size());
    // assertEquals(id, computer.getId());
    // assertEquals(name, computer.getName());
    // assertEquals(companyName, computer.getCompany().getName());
    // assertEquals(dateTime, computer.getIntroduced());
    // assertEquals(dateTime2, computer.getDiscontinued());
    // assertEquals(id, computer.getCompany().getId());
    // } catch (Exception e) {
    //
    // }
    // }

    /**
     * Test method to get a computer based on the id value.
     *
     * @throws ServiceException
     *             if something went wrong during get request or connection
     *             handling.
     */
    @Test
    public void testGetComputerById() throws ServiceException {
        String name = "CM-5";
        String companyName = "Thinking Machines";
        LocalDate dateTime = LocalDate.of(1991, Month.JANUARY, 01);
        long idComputer = 5;
        long idCompany = 2;
        Computer computer = COMPUTER_SERVICE.getById(5);

        assertEquals(idComputer, computer.getId());
        assertEquals(name, computer.getName());
        assertEquals(companyName, computer.getCompany().getName());
        assertEquals(dateTime, computer.getIntroduced());
        assertEquals(idCompany, computer.getCompany().getId());

    }

    /**
     * Test method to update a computer.
     *
     * @throws ServiceException
     *             if something went wrong during udpate or connection handling.
     */
    // @Test
    // public void testUpdateComputer() {

    // try {
    // boolean response = computerServ.create(computer1);
    // assertTrue(true);
    // } catch (Exception e) {
    //
    // }
    // }

    /**
     * Test method to delete a computer.
     *
     * @throws ServiceException
     *             if something went wrong during delete or connection handling.
     */
    // @Test
    // public void delete() throws ServiceException {
    // try {
    // boolean response = computerServ.delete(1);
    // assertTrue(response);
    // } catch (Exception e) {
    //
    // }
    // }

    /**
     * Test method to create a computer.
     *
     * @throws ServiceException
     *             if something went wrong during creation or connection
     *             handling.
     */
    // @Test
    // public void create() throws ServiceException {
    // try {
    // boolean response = computerServ.create(computer1);
    // assertTrue(response);
    // } catch (Exception e) {
    //
    // }
    // }
}
