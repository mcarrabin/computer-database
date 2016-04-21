package Mappers;

import java.sql.ResultSet;

public interface Mapper<T> {
	
	/**
	 * Méthode qui va créer et retourner un objet complété avec le contenu du resultSet
	 * @param result
	 * @return l'objet créé et complété
	 */
	public T map(ResultSet result);
}
