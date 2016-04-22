package com.excilys.computerdatabase.mappers;

import java.sql.ResultSet;
import java.util.List;

public interface Mapper<T> {
	
	/**
	 * Méthode qui va créer et retourner un objet complété avec le contenu du resultSet
	 * @param result
	 * @return l'objet créé et complété
	 */
	public T mapUnique(ResultSet result);
	
	/**
	 * Méthode qui va boucler sur le ResultSet pour construire une liste d'objets
	 * @param result
	 * @return une liste d'objets
	 */
	public List<T> mapAll(ResultSet result);
}
