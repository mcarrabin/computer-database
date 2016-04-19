package com.excilys.computerdatabase.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.excilys.computerdatabase.DAO.DBConnection;
import com.excilys.computerdatabase.entities.ComputerEntity;

public class ComputerService {

	/**
	 * Méthode qui va retourner un ensemble d'ordinateurs en se basant sur le nom passé en paramètre
	 * 
	 * @param name:
	 *            nom de l'ordinateur à afficher
	 * @return le ou les ordinateurs dont le nom correspond au paramètre
	 */
	public ArrayList<ComputerEntity> getComputerByName(String name) {
		DBConnection dbConn = DBConnection.getInstance();
		String query = "select * from computer where name like ? ;";
		ResultSet results = null;
		ArrayList<ComputerEntity> computers = new ArrayList<ComputerEntity>();

		try (Connection conn = dbConn.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, "%"+name);
			results = stmt.executeQuery();
			while (results.next()) {
				int id = results.getInt("Id");
				LocalDateTime introduced = results.getTimestamp("introduced") == null ? null : results.getTimestamp("introduced").toLocalDateTime();
				LocalDateTime discontinued = results.getTimestamp("discontinued") == null ? null : results.getTimestamp("discontinued").toLocalDateTime();
				int company_id = results.getInt("company_id");
				ComputerEntity computer = new ComputerEntity(id, name, introduced, discontinued, company_id);
				computers.add(computer);
			}
		} catch (Exception e) {
			System.out.println("Erreur lors de l'exécution de la requête: \n" + e.getMessage());
		}
		return computers;
	}
}
