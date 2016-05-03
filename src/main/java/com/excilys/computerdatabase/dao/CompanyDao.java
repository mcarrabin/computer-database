package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.mappers.CompanyMapper;

public enum CompanyDao implements AbstractDao<Company> {
    INSTANCE;

    private static final String GET_ALL_REQUEST = "select * from company order by name";
    private static final String GET_BY_ID_REQUEST = "select * from company where id = ?";

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
        Connection con = INSTANCE.connect();

        try {
            PreparedStatement statement = con.prepareStatement(GET_ALL_REQUEST);
            result = statement.executeQuery();
            CompanyMapper compMapper = CompanyMapper.INSTANCE;
            companies = compMapper.mapAll(result);
            statement.close();
            result.close();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            INSTANCE.closeConnection(con);
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
            Connection con = INSTANCE.connect();

            try {
                PreparedStatement statement = con.prepareStatement(GET_BY_ID_REQUEST);
                statement.setLong(1, id);
                result = statement.executeQuery();
                CompanyMapper compMapper = CompanyMapper.INSTANCE;
                company = compMapper.mapAll(result).get(0);
                statement.close();
                result.close();
            } catch (Exception e) {
                throw new DaoException(e);
            } finally {
                INSTANCE.closeConnection(con);
            }

            return company;
        }
    }
}
