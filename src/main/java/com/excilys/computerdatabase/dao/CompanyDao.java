package com.excilys.computerdatabase.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.mappers.CompanyMapper;
import com.zaxxer.hikari.HikariDataSource;

@Repository("companyDao")
@Transactional
@Scope("singleton")
public class CompanyDao implements AbstractDao<Company> {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("companyMapper")
    private CompanyMapper companyMapper;

    @Autowired
    @Qualifier("dbManager")
    private DBManager dbManager;

    @Autowired
    @Qualifier("dataSource")
    private HikariDataSource dataSource;

    private static final String GET_ALL_REQUEST = "select id, name from company order by name";
    private static final String GET_BY_ID_REQUEST = "select id, name from company where id = ?";
    private static final String DELETE_COMPANY_REQUEST = "delete from company where id = ?";

    public void setDataSource(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Company> getAll() throws DaoException {
        List<Company> companies = getSessionFactory().createQuery("from Company").list();
        return companies;
    }

    @Override
    public Company getById(long id) throws DaoException {
        return (Company) getSessionFactory().get(Company.class, id);
    }

    @Override
    public void delete(Company company) throws DaoException {
        // jdbcTemplate = new JdbcTemplate(dataSource);
        // boolean isDeleteOk = jdbcTemplate.update(DELETE_COMPANY_REQUEST, id)
        // > 0 ? true : false;
        // return isDeleteOk;
    }

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    protected final Session getSessionFactory() {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        return session;
    }
}
