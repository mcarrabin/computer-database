package com.excilys.computerdatabase.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.mappers.CompanyMapper;

public class CompanyDao extends AbstractDao<Company> {
	private static CompanyDao instance = null;
	
	public CompanyDao(){};
	
	synchronized public static CompanyDao getInstance(){
		if(instance == null){
			instance = new CompanyDao();
		}
		return instance;
	}
	
	@Override
	public List<Company> getAll() {
		String query = "select * from company";
		ResultSet result;
		List<Company> companies = new ArrayList<Company>();

		try {
			PreparedStatement statement = this.connect().prepareStatement(query);
			result = statement.executeQuery();
			CompanyMapper compMapper = CompanyMapper.getInstance();
			companies = compMapper.mapAll(result);
			statement.close();
			result.close();
		} catch (Exception e) {
			System.out.println("Erreur lors de l'extraction des company: " + e.getMessage());
		} finally {
			this.closeConnection();
		}

		return companies;
	}

	@Override
	public Company getById(long id) {
		String query = "select * from company where id = ?";
		ResultSet result;
		Company company = null;

		try {
			PreparedStatement statement = this.connect().prepareStatement(query);
			statement.setLong(1, id);
			result = statement.executeQuery();
			CompanyMapper compMapper = CompanyMapper.getInstance();
			company = compMapper.mapUnique(result);
			statement.close();
			result.close();
		} catch (Exception e) {
			System.out.println("Erreur lors de l'extraction des company: " + e.getMessage());
		} finally {
			this.closeConnection();
		}

		return company;
	}
}
