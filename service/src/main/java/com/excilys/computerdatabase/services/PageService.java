package com.excilys.computerdatabase.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.dao.AbstractDao;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;
import com.excilys.computerdatabase.mappers.SqlSort;

@Service("pageService")
public class PageService implements IPageService {
    @Autowired
    @Qualifier("computerService")
    public ComputerService computerService;

    @Autowired
    public AbstractDao<Computer> computerDao;

    @Override
    public Page<Computer> getPage(int itemsPerPage, int numPage, String search, String orderBy, String sorting) {
        Page<Computer> page = new Page<Computer>();
        long computersTotalCount = 0;
        String sqlSort = SqlSort.getSortColumn(orderBy);
        try {
            computersTotalCount = computerDao.getNumTotalComputer(search);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int maxPage = (int) computersTotalCount / itemsPerPage + 1;

        if (numPage <= maxPage) {
            page = computerService.getComputerByPage(itemsPerPage, numPage, search,
                    sqlSort.trim().isEmpty() ? "" : (sqlSort + " " + sorting));
            page.setItemsTotalCount(computersTotalCount);
            page.setMaxPage(maxPage);
            page.setSearch(search);
            page.setOrderBy(orderBy);
            page.setSorting(sorting);
        }
        return page;
    }
}
