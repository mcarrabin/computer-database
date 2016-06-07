package com.excilys.computerdatabase.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.excilys.computerdatabase.entities.User;

public interface IUserService {

    /**
     * Method that will call the getByLogin method from the UserDao.
     *
     * @param login
     *            is the login of the user to get.
     * @return the user if found.
     */
    User getByLogin(String login);

    /**
     * Method to delete a user.
     *
     * @param user
     *            is the user to delete.
     */
    void delete(User user);

    /**
     * Method to create a user.
     *
     * @param user
     *            is the user to create.
     */
    void create(User user);

    /**
     * getting list of GrantedAuthority from UserRole.
     *
     * @param userRole
     *            userRole to convert
     * @return List of GrantedAuthority
     */
    UserDetails loadUserByUsername(String login) throws UsernameNotFoundException;

}
