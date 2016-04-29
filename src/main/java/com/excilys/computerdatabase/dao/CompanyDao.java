package com.excilys.computerdatabase.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.exceptions.ConnexionException;
import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.mappers.CompanyMapper;

public class CompanyDao extends AbstractDao<Company> {
    private static CompanyDao instance = null;

    private static final String GET_ALL_REQUEST = "select * from company";
    private static final String GET_BY_ID_REQUEST = "select * from company where id = ?";

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
    public List<Company> getAll() throws DaoException {
        ResultSet result;
        List<Company> companies = new ArrayList<Company>();

        try {
            PreparedStatement statement = this.connect().prepareStatement(GET_ALL_REQUEST);
            result = statement.executeQuery();
            CompanyMapper compMapper = CompanyMapper.getInstance();
            companies = compMapper.mapAll(result);
            statement.close();
            result.close();
        } catch (Exception e) {
            throw new DaoException(e);
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
    public Company getById(long id) throws DaoException {
        if (id == -1) {
            return new Company();
        } else {
            ResultSet result;
            Company company = null;

            try {
                PreparedStatement statement = this.connect().prepareStatement(GET_BY_ID_REQUEST);
                statement.setLong(1, id);
                result = statement.executeQuery();
                CompanyMapper compMapper = CompanyMapper.getInstance();
                company = compMapper.mapAll(result).get(0);
                statement.close();
                result.close();
            } catch (Exception e) {
                throw new DaoException(e);
            } finally {
                this.closeConnection();
            }

            return company;
        }
    }
}
