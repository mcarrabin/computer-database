package com.excilys.computerdatabase.services;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.entities.Page;
import com.excilys.computerdatabase.exceptions.ConnexionException;
import com.excilys.computerdatabase.exceptions.DaoException;

public class PageService {
	private static PageService instance = null;
	
	public static PageService getInstance(){
		if(instance == null){
			synchronized(PageService.class){
				if(instance == null)
					instance = new PageService();
			}
		}
		return instance;
	}
	
	/**
	 * Méthode qui va calculer le nombre total de page affichable en se basant sur le nombre de ligne par page reçu en paramètre
	 * @param nbreLine: nbre de ligne par page voulu
	 * @param numPage: numéro de la page voulue
	 * @return la page construite
	 */
	public Page getPage(int nbreLine, int numPage) throws DaoException, ConnexionException {
		Page page = new Page();
		ComputerDao computerDao = ComputerDao.getInstance(); 
		int numMaxPage = computerDao.getAll().size() / nbreLine + 1;
		if(numPage <= numMaxPage)
			page = ComputerService.getInstance().getComputerByPage(nbreLine, numPage);
		
		return page;
	}
}
