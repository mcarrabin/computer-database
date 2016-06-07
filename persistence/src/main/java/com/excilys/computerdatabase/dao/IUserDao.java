package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.entities.User;

public interface IUserDao {
    /**
     * Method that will get a User from the DB based on the login.
     *
     * @param login
     *            is the login of the user desired.
     * @return the user if found in the DB.
     */
    User getByLogin(String login);

    /**
     * Method to delete a User.
     *
     * @param user
     *            is the User to delete.
     */
    void delete(User user);

    /**
     * Method to create a user in the DB.
     *
     * @param user
     *            is the user to create.
     */
    void create(User user);

}
