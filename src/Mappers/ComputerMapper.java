package Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.excilys.computerdatabase.entities.CompanyEntity;
import com.excilys.computerdatabase.entities.ComputerEntity;

public class ComputerMapper implements Mapper<ComputerEntity> {
	private static ComputerMapper _instance = null;
	
	synchronized public static ComputerMapper getInstance(){
		if(_instance == null) {
			_instance = new ComputerMapper();
		}
		return _instance;
	}
	/**
	 * Méthode qui va créer et retourner un objet complété avec le contenu du
	 * resultSet
	 * 
	 * @param result
	 * @return l'objet créé et complété
	 */
	public ComputerEntity map(ResultSet result) {
		LocalDateTime introduced, discontinued;
		Long companyId, id;
		ComputerEntity computer = null;
		try {
			String name = result.getString("c.name");
			introduced = result.getTimestamp("c.introduced") == null ? null
					: result.getTimestamp("introduced").toLocalDateTime();
			discontinued = result.getTimestamp("c.discontinued") == null ? null
					: result.getTimestamp("discontinued").toLocalDateTime();
			companyId = result.getLong("comp.id");
			String companyName = result.getString("comp.name");
			id = result.getLong("id");

			CompanyEntity company = new CompanyEntity(companyId, companyName);
			computer = new ComputerEntity(id, name, introduced, discontinued, company);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return computer;
	}
}
