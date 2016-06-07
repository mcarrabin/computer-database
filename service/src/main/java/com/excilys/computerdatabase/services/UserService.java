package com.excilys.computerdatabase.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.dao.UserDao;
import com.excilys.computerdatabase.entities.User;
import com.excilys.computerdatabase.entities.UserRole;

@Service("userService")
public class UserService implements UserDetailsService, IUserService {

    @Autowired
    public UserDao userDao;

    @Override
    public User getByLogin(String login) {
        return userDao.getByLogin(login);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public void create(User user) {
        userDao.create(user);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userDao.getByLogin(login);
        return buildUserForAuthentication(user, setUserAuthority(user.getRole()));
    }

    /**
     * getting UserDetails from User object.
     *
     * @param user
     *            user to convert
     * @param authorities
     *            user authorities
     * @return UserDetail use by Spring Security
     */
    private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user,
            List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }

    private List<GrantedAuthority> setUserAuthority(UserRole userRole) {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority(userRole.toString()));
        return grantedAuthorityList;
    }
}
