package com.excilys.computerdatabase.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.entities.Company;
import com.excilys.computerdatabase.entities.Company.CompanyBuilder;
import com.excilys.computerdatabase.entities.Computer;
import com.excilys.computerdatabase.entities.Computer.ComputerBuilder;

public class ComputerMapper implements Mapper<Computer> {
	private static ComputerMapper _instance = null;

	synchronized public static ComputerMapper getInstance() {
		if (_instance == null) {
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
	public Computer mapUnique(ResultSet result) {
		LocalDateTime introduced, discontinued;
		Long companyId, id;
		Computer computer = null;
		try {
			String name = result.getString("c.name");
			introduced = result.getTimestamp("c.introduced") == null ? null
					: result.getTimestamp("introduced").toLocalDateTime();
			discontinued = result.getTimestamp("c.discontinued") == null ? null
					: result.getTimestamp("discontinued").toLocalDateTime();
			companyId = result.getLong("comp.id");
			String companyName = result.getString("comp.name");
			id = result.getLong("id");

			Company company = new CompanyBuilder()
					.id(companyId)
					.name(companyName)
					.build();
			computer = new ComputerBuilder().id(id)
					.name(name)
					.introduced(introduced)
					.discontinued(discontinued)
					.company(company)
					.build();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return computer;
	}

	public List<Computer> mapAll(ResultSet result) {
		List<Computer> computers = new ArrayList<Computer>();

		try {
			while (result.next()) {
				computers.add(mapUnique(result));
			}
		} catch (SQLException e) {
			
		}
		return computers;
	}
}
