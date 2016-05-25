package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.mappers.CompanyMapper;
import com.zaxxer.hikari.HikariDataSource;

@Repository("companyDao")
public class CompanyDao implements AbstractDao<Company> {

    @Autowired
    @Qualifier("companyMapper")
    private CompanyMapper companyMapper;

    @Autowired
    @Qualifier("dbManager")
    private DBManager dbManager;

    private HikariDataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private static final String GET_ALL_REQUEST = "select * from company order by name";
    private static final String GET_BY_ID_REQUEST = "select * from company where id = ?";
    private static final String DELETE_COMPANY_REQUEST = "delete from company where id = ?";

    public void setDataSource(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Method that will return a connection from the dbManager instance.
     *
     * @return a connection to the database.
     */
    public Connection connect() {
        return dbManager.getConnection();
    }

    @Override
    public List<Company> getAll() throws DaoException {
        jdbcTemplate = new JdbcTemplate(dataSource);
        List<Company> companies = jdbcTemplate.queryForList(GET_ALL_REQUEST, Company.class);

        return companies;
    }

    @Override
    public Company getById(long id) throws DaoException {
        if (id == -1) {
            return new Company();
        } else {
            jdbcTemplate = new JdbcTemplate(dataSource);
            Company company = jdbcTemplate.queryForObject(GET_BY_ID_REQUEST, new Object[] { id }, new CompanyMapper());
            return company;
        }
    }

    @Override
    public boolean delete(long id) throws DaoException {
        jdbcTemplate = new JdbcTemplate(dataSource);
        boolean isDeleteOk = jdbcTemplate.update(DELETE_COMPANY_REQUEST, id) > 0 ? true : false;
        return isDeleteOk;
    }
}
