package com.excilys.computerdatabase.mappers;

import java.util.List;

public interface MapperDto<T, D> {
    /**
     * Method that will map from an object to a Dto.
     *
     * @param t
     *            is the object to convert to Dto.
     * @return the Dto object.
     */
    D toDto(T t);

    /**
     * Method that will map from a Dto to an object.
     *
     * @param d
     *            is the Dto to convert.
     * @return the object.
     */
    T fromDto(D d);

    /**
     * Method that will convert a list of object to a list of Dto.
     *
     * @param t
     *            is the object list.
     * @return the list of Dto.
     */
    List<D> toDtoList(List<T> t);

    /**
     * Methot that will convert a list of Dto to a list of objects.
     *
     * @param d
     *            is the list of Dto.
     * @return the list of objects.
     */
    List<T> fromDtoList(List<D> d);
}
