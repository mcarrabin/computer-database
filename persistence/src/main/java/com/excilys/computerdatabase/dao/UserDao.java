package com.excilys.computerdatabase.dao;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.entities.User;

@Repository("userDao")
@Transactional
@Scope("singleton")
public class UserDao implements IUserDao {

    private final static String GET_BY_LOGIN_REQUEST = "select u from User as u where login = :LOGIN";

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getByLogin(String login) {
        String sql = GET_BY_LOGIN_REQUEST;
        Query query = getSession().createQuery(sql);
        query.setParameter("LOGIN", login);
        return (User) query.uniqueResult();
    }

    @Override
    public void delete(User user) {
        Session s = getSession();
        s.delete(user);
        s.flush();
    }

    @Override
    public void create(User user) {
        getSession().save(user);
    }

    /**
     * Setter of the sessionFactory attribute.
     *
     * @param session
     *            is the session to set.
     */
    public void setSessionFactory(SessionFactory session) {
        this.sessionFactory = session;
    }

    /**
     * Getter of the sessionFactory attribute.
     *
     * @return the sessionFactory attribute.
     */
    protected final Session getSession() {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }
        return session;
    }
}
