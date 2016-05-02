package com.excilys.computerdatabase.services;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;

public enum PageService {
    INSTANCE;
    private static PageService instance = null;

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
    public Page<Computer> getPage(int nbreLine, int numPage, String name) {
        Page<Computer> page = new Page<Computer>();
        ComputerDao computerDao = ComputerDao.INSTANCE;
        long numTotComputer = 0;
        try {
            numTotComputer = computerDao.getNumTotalComputer(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int numMaxPage = (int) numTotComputer / nbreLine + 1;

        if (numPage <= numMaxPage) {
            page = ComputerService.INSTANCE.getComputerByPage(nbreLine, numPage, name);
            page.setNumElementTotal(numTotComputer);
            page.setNumPageMax(numMaxPage);
            page.setSearchFilter(name);
        }
        return page;
    }
}
