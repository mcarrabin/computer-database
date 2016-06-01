package com.excilys.computerdatabase.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.dao.AbstractDao;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;
import com.excilys.computerdatabase.mappers.SqlSort;

@Service("pageService")
public class PageService {
    @Autowired
    @Qualifier("computerService")
    public ComputerService computerService;

    @Autowired
    public AbstractDao<Computer> computerDao;

    /**
     * Methode qui va calculer le nombre total de page affichable en se basant
     * sur le nombre de ligne par page reçu en parametre.
     *
     * @param nbreLine
     *            nbre de ligne par page voulu
     * @param numPage
     *            numero de la page voulue
     * @param name
     *            filtre sur les noms de Computer.
     * @return la page construite
     */
    public Page<Computer> getPage(int nbreLine, int numPage, String name, String orderBy, String sorting) {
        Page<Computer> page = new Page<Computer>();
        long computersTotalCount = 0;
        String sqlSort = SqlSort.getSortColumn(orderBy);
        try {
            computersTotalCount = computerDao.getNumTotalComputer(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int maxPage = (int) computersTotalCount / nbreLine + 1;

        if (numPage <= maxPage) {
            page = computerService.getComputerByPage(nbreLine, numPage, name,
                    sqlSort.trim().isEmpty() ? "" : (sqlSort + " " + sorting));
            page.setItemsTotalCount(computersTotalCount);
            page.setMaxPage(maxPage);
            page.setSearch(name);
            page.setOrderBy(orderBy);
            page.setSorting(sorting);
        }
        return page;
    }
}
