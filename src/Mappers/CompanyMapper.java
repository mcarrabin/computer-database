package Mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerdatabase.entities.CompanyEntity;

public class CompanyMapper implements Mapper<CompanyEntity> {
	private static CompanyMapper _instance = null;
	
	synchronized public static CompanyMapper getInstance(){
		if(_instance == null) 
			_instance = new CompanyMapper();
		return _instance;
	}

	/**
	 * Méthode qui va créer et retourner un objet CompanyEntity complété avec le contenu du resultSet
	 * @param result
	 * @return l'objet CompanyEntity créé et complété
	 */
	public CompanyEntity map(ResultSet result){
		String name;
		CompanyEntity company = null;
		try {
			name = result.getString("Name");
			Long id = result.getLong("id");
			company = new CompanyEntity(id, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return company;
	}
}
