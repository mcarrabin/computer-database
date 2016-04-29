package com.excilys.computerdatabase.services;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;

public class PageService {
    private static PageService instance = null;

    /**
     * Method which create an instance of PageService if there is none current
     * and return it.
     *
     * @return the current or created instance of PageService.
     */
    public static PageService getInstance() {
        if (instance == null) {
            synchronized (PageService.class) {
                if (instance == null) {
                    instance = new PageService();
                }
            }
        }
        return instance;
    }

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
        ComputerDao computerDao = ComputerDao.getInstance();
        long numTotComputer = 0;
        try {
            numTotComputer = computerDao.getNumTotalComputer(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int numMaxPage = (int) numTotComputer / nbreLine + 1;

        if (numPage <= numMaxPage) {
            page = ComputerService.getInstance().getComputerByPage(nbreLine, numPage, name);
            page.setNumElementTotal(numTotComputer);
            page.setNumPageMax(numMaxPage);
            page.setSearchFilter(name);
        }
        return page;
    }
}
