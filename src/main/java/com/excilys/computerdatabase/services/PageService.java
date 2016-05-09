package com.excilys.computerdatabase.services;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;
import com.excilys.computerdatabase.mappers.SqlSort;

public enum PageService {
    INSTANCE;
    private static final ComputerService COMPUTER_SERVICE = ComputerService.INSTANCE;
    private static final ComputerDao COMPUTER_DAO = ComputerDao.INSTANCE;

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
        long computersTotalCount = 0;
        String sqlSort = SqlSort.getSortColumn(orderBy);
        try {
            computersTotalCount = COMPUTER_DAO.getNumTotalComputer(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int maxPage = (int) computersTotalCount / nbreLine + 1;

        if (numPage <= maxPage) {
            page = COMPUTER_SERVICE.getComputerByPage(nbreLine, numPage, name, sqlSort + " " + sorting);
            page.setItemsTotalCount(computersTotalCount);
            page.setMaxPage(maxPage);
            page.setSearch(name);
            page.setOrderBy(orderBy);
            page.setSorting(sorting);
        }
        return page;
    }
}
