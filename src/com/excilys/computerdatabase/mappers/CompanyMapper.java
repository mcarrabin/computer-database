package com.excilys.computerdatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Company.CompanyBuilder;

public class CompanyMapper implements Mapper<Company> {
	private static CompanyMapper _instance = null;
	
	synchronized public static CompanyMapper getInstance(){
		if(_instance == null) 
			_instance = new CompanyMapper();
		return _instance;
	}

	/**
	 * Méthode qui va créer et retourner un objet CompanyEntity complété avec le contenu du resultSet
	 * @param result
	 * @return l'objet CompanyEntity créé et complété
	 */
	public Company mapUnique(ResultSet result){
		String name;
		Company company = null;
		try {
			name = result.getString("Name");
			Long id = result.getLong("id");
			company = new CompanyBuilder()
					.id(id)
					.name(name)
					.build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return company;
	}
	
	/**
	 * Méthode qui va boucler sur le result set et créer une liste d'objet Company 
	 * @param result
	 * @return la liste de Company créé 
	 */
	public List<Company> mapAll(ResultSet result){
		List<Company> companies = new ArrayList<Company>();
		try {
			while (result.next()){
				companies.add(mapUnique(result));
			}
		} catch (Exception e){
			
		}
		return companies;
	}
}
