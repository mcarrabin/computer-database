package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.mappers.CompanyMapper;

@Repository("companyDao")
public class CompanyDao implements AbstractDao<Company> {

    @Autowired
    @Qualifier("companyMapper")
    private CompanyMapper companyMapper;

    @Autowired
    @Qualifier("dbManager")
    private DBManager dbManager;

    private static final String GET_ALL_REQUEST = "select * from company order by name";
    private static final String GET_BY_ID_REQUEST = "select * from company where id = ?";
    private static final String DELETE_COMPANY_REQUEST = "delete from company where id = ?";

    /**
     * Method that will return a connection from the dbManager instance.
     *
     * @return a connection to the database.
     */
    public Connection connect() {
        return dbManager.getConnection();
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
        Connection con = connect();

        try {
            PreparedStatement statement = con.prepareStatement(GET_ALL_REQUEST);
            result = statement.executeQuery();
            companies = companyMapper.mapAll(result);
            statement.close();
            result.close();
        } catch (Exception e) {
            throw new DaoException(e);
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
            Connection con = connect();

            try {
                PreparedStatement statement = con.prepareStatement(GET_BY_ID_REQUEST);
                statement.setLong(1, id);
                result = statement.executeQuery();
                company = companyMapper.mapAll(result).get(0);
                statement.close();
                result.close();
            } catch (Exception e) {
                throw new DaoException(e);
            }

            return company;
        }
    }

    /**
     * Method that will delete a company and every computers linked to this
     * company. Use of a transaction to make the deletion safe.
     *
     * @param company
     *            is the object to delete.
     * @return true if deletion succeed, else false.
     * @throws DaoException
     *             if something went wrong.
     */
    @Override
    public boolean delete(long id) throws DaoException {
        Connection con = connect();
        try {

            PreparedStatement stmt = con.prepareStatement(DELETE_COMPANY_REQUEST);
            stmt.setLong(1, id);
            stmt.executeUpdate();

            stmt.close();
            return true;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}
