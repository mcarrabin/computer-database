package com.excilys.computerdatabase.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.exceptions.ConnexionException;
import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.mappers.CompanyMapper;

public class CompanyDao extends AbstractDao<Company> {
	private static CompanyDao instance = null;
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);

	public CompanyDao() {
	};

	synchronized public static CompanyDao getInstance() {
		if (instance == null) {
			instance = new CompanyDao();
		}
		return instance;
	}

	@Override
	public List<Company> getAll() throws DaoException, ConnexionException {
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
			LOGGER.error(e.getMessage());
			throw new DaoException("Erreur lors de l'extraction des objets company");
		} finally {
			this.closeConnection();
		}

		return companies;
	}

	@Override
	public Company getById(long id) throws DaoException, ConnexionException {
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
			LOGGER.error(e.getMessage());
			throw new DaoException("Erreur lors de l'extraction des objets company");
		} finally {
			this.closeConnection();
		}

		return company;
	}
}
