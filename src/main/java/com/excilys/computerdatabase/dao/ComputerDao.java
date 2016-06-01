package com.excilys.computerdatabase.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Page;
import com.excilys.computerdatabase.exceptions.DaoException;
import com.excilys.computerdatabase.mappers.ComputerMapper;
import com.excilys.computerdatabase.mappers.DateMapper;

@Repository("computerDao")
@Transactional
@Scope("singleton")
public class ComputerDao implements AbstractDao<Computer> {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier("dateMapper")
    public DateMapper dateMapper;

    @Autowired
    @Qualifier("computerMapper")
    public ComputerMapper computerMapper;

    private static final String GET_BY_PAGE_REQUEST = "select c from Computer as c left join c.company as comp";
    private static final String GET_TOTAL_COUNT_REQUEST = "select count(*) from Computer as c left join c.company as comp";
    private static final String DELETE_BY_COMP_REQUEST = "delete from Computer where companyId = :COMPANY";

    private static final String LIKE_REQUEST = " where c.name like :SEARCH or comp.name like :SEARCH ";

    public ComputerDao() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Computer> getAll() {
        List<Computer> computers = getSessionFactory().createQuery("from Computer").list();
        return computers;
    }

    @Override
    public Computer getById(long id) throws DaoException {
        return (Computer) getSessionFactory().get(Computer.class, id);
    }

    /**
     * Method that will call the PageSGET_BY_PAGE_REQUESTervice and return the
     * built page.
     *
     * @param nbreLine
     *            (int) wanted in the page.
     * @param numPage
     *            (int) is the index of the page we want to build.
     * @param name
     *            is the name filter in case of a filter is applied.
     * @return the built page.
     * @throws DaoException
     *             which are the exceptions handledrome by the Dao classes.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Computer> getByPage(int nbreLine, int numPage, String search, String orderBy) throws DaoException {

        String sql = GET_BY_PAGE_REQUEST;
        Query query = null;

        // if there is a search required, add the condition.
        if (search != null && !search.trim().isEmpty()) {
            sql += LIKE_REQUEST;
        }

        // if there is a sorting required, add the order by condition.
        if (orderBy != null && !orderBy.trim().isEmpty()) {
            sql += " order by " + orderBy;
        }

        query = getSessionFactory().createQuery(sql);
        if (search != null && !search.trim().isEmpty()) {
            query.setParameter("SEARCH", "%" + search + "%");
        }

        query.setMaxResults(nbreLine);
        query.setFirstResult(nbreLine * (numPage - 1));
        List<Computer> computers = query.list();
        System.out.println(computers);
        Page<Computer> page = new Page<Computer>().getBuilder().elements(computers).currentPage(numPage).build();

        return page;
    }

    /**
     * Method that will ask the DB how many entries are in the Computer table
     * with eventually a filter on the name attribute.
     *
     * @param name
     *            is the filter on the name values.
     * @return the number of entries in the Computer table in the DB filtered on
     *         the name attribute.
     * @throws DaoException
     *             if anything goes wrong.
     * @throws ConnexionException
     *             if something went wrong for connection opening of closing.
     */
    @Override
    public long getNumTotalComputer(String search) throws DaoException {
        String sql = GET_TOTAL_COUNT_REQUEST;
        Query query = null;
        if (search != null && !search.trim().isEmpty()) {
            sql += LIKE_REQUEST;
            query = getSessionFactory().createQuery(sql);
            query.setParameter("SEARCH", search + "%");
        } else {
            query = getSessionFactory().createQuery(sql);
        }
        return (long) query.uniqueResult();
    }

    @Override
    public void delete(Computer computer) throws DaoException {
        Session s = getSessionFactory();
        s.delete(computer);
        s.flush();
    }

    @Override
    public void update(Computer computer) throws DaoException {
        Session s = getSessionFactory();
        s.update(computer);
        s.flush();
    }

    @Override
    public void create(Computer computer) throws DaoException {
        getSessionFactory().save(computer);
    }

    /**
     * Method that will delete every computers linked to the company identified
     * by the parameter.
     *
     * @param companyId
     *            is the id of the company which every computer to delete are
     *            linked on.
     * @return true if delete went well else false.
     * @throws DaoExecption
     *             delete went wrong or prepared statement failed.
     */

    @Override
    public void deleteByCompany(long companyId) throws DaoException {
        Session s = getSessionFactory();
        s.createQuery(DELETE_BY_COMP_REQUEST).setParameter("COMPANY", companyId).executeUpdate();
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
