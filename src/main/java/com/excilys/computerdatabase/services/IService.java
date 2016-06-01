package com.excilys.computerdatabase.services;

import java.util.List;

import com.excilys.computerdatabase.exceptions.ServiceException;

public interface IService<T> {

    /**
     * Method that will ask every object of type T to the corresponding Dao.
     *
     * @return un ArrayList<T> containing every objects T from the Database.
     */
    List<T> getAll();

    /**
     * Method that will get the object T which id match with the one received as
     * a parameter.
     *
     * @param id
     *            is the id of the object wanted.
     * @return the object if found.
     */
    T getById(long id);

    /**
     * Method that will update an object T calling the Dao.
     *
     * @param t
     *            is the updated object to update in database.
     * @return true if update went well, else false.
     */
    default void update(T t) {
        throw new ServiceException("This methot is not implemented for this kind of Object");
    }

    /**
     * Method that will create an object t of type T in the database.
     *
     * @param t
     *            is the object to create.
     * @return true if create went well, else false.
     */
    default void create(T t) {
        throw new ServiceException("This methot is not implemented for this kind of Object");
    }

    /**
     * Method that will delete an object in the database based on his id.
     *
     * @param id
     *            is the id of the object we want to delete.
     * @return true if delete went well, else false.
     */
    void delete(long id);

}
