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

    /**
     * Constructor of the CompanyDao class.
     */
    public CompanyDao() {
    };

    /**
     * Method that will check if an instance of CompanyDao is currently active.
     * If yes, this method will return it. If not, the method will create one
     * and return it.
     *
     * @return the CompanyDao instance created or existing.
     */
    public static CompanyDao getInstance() {
        if (instance == null) {
            synchronized (CompanyDao.class) {
                if (instance == null) {
                    instance = new CompanyDao();
                }
            }
        }
        return instance;
    }

    /**
     * Method that will get every companies stored in the database.
     *
     * @return the list of the companies stored in the database.
     * @throws DaoException
     *             which are the exceptions handled by the Dao.
     * @throws ConnexionException
     *             which are the exceptions due to connexion issues.
     */
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

    /**
     * Method that will return the company matching with the id param.
     *
     * @param id
     *            is the id of the company searched.
     * @return Company is the company found in the database.
     * @throws DaoException
     *             which are the exceptions handled by Dao.
     * @throws ConnexionException
     *             which are the exceptions due to connection issues.
     */
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
