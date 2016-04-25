package com.excilys.computerdatabase.services;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.exceptions.ConnexionException;
import com.excilys.computerdatabase.exceptions.DaoException;

public class CompanyService {
	public static final CompanyDao COMPANY_DAO = CompanyDao.getInstance();
	private static CompanyService instance = null;
	
	public CompanyService(){}
	
	synchronized public static CompanyService getInstance(){
		if(instance == null) {
			instance = new CompanyService();
		}
		return instance;
	}
	
	public List<Company> getCompanies() throws DaoException, ConnexionException {
		List<Company> companies = new ArrayList<Company>();
		companies = COMPANY_DAO.getAll();
		return companies;
	}
}
