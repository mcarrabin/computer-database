package com.excilys.computerdatabase.services;

import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;

public interface IPageService {

    /**
     * Method that will call the ComputerDao to get Computer objects of the
     * page.
     *
     * @param nbreLine
     *            is the number of items per page.
     * @param numPage
     *            is the desired page number.
     * @param name
     *            is the search parameter on Computer and Company name field.
     * @return the built page.
     */
    Page<Computer> getPage(int itemsPerPage, int numPage, String search, String orderBy, String sorting);
}
