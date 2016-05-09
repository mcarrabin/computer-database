package com.excilys.computerdatabase.services;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.DBManager;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;
import com.excilys.computerdatabase.mappers.SqlSort;

public enum PageService {
    INSTANCE;
    private static final DBManager DB_MANAGER = DBManager.INSTANCE;

    /**
     * Méthode qui va calculer le nombre total de page affichable en se basant
     * sur le nombre de ligne par page reçu en paramètre.
     *
     * @param nbreLine
     *            nbre de ligne par page voulu
     * @param numPage
     *            numéro de la page voulue
     * @param name
     *            filtre sur les noms de Computer.
     * @return la page construite
     */
    public Page<Computer> getPage(int nbreLine, int numPage, String name, String orderBy, String sorting) {
        Page<Computer> page = new Page<Computer>();
        ComputerDao computerDao = ComputerDao.INSTANCE;
        long computersTotalCount = 0;
        String sqlSort = SqlSort.getSortColumn(orderBy);
        try {
            computersTotalCount = computerDao.getNumTotalComputer(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int maxPage = (int) computersTotalCount / nbreLine + 1;

        if (numPage <= maxPage) {
            page = ComputerService.INSTANCE.getComputerByPage(nbreLine, numPage, name, sqlSort + " " + sorting);
            page.setItemsTotalCount(computersTotalCount);
            page.setMaxPage(maxPage);
            page.setSearch(name);
            page.setOrderBy(orderBy);
            page.setSorting(sorting);
        }
        return page;
    }
}
