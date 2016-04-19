package com.excilys.computerdatabase.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.excilys.computerdatabase.entities.ComputerEntity;

public class ComputerDAO {

	/**
	 * Méthode qui va construire une liste de toutes les entrées computer contenues en BDD.
	 * @return
	 * 		la liste de toutes ces entrées
	 */
	public ArrayList<ComputerEntity> getComputers() {
		ArrayList<ComputerEntity> computers = new ArrayList<ComputerEntity>();
		DBConnection dbConn = DBConnection.getInstance();
		ResultSet results = null;
		String query = "select * from computer order by name;";

		try (Connection conn = dbConn.getConnection()) {
			Statement statement = conn.createStatement();
			results = statement.executeQuery(query);
			while (results.next()) {
				String name = results.getString("Name");
				LocalDateTime introduced = results.getTimestamp("introduced") == null ? null : results.getTimestamp("introduced").toLocalDateTime();
				LocalDateTime discontinued = results.getTimestamp("discontinued") == null ? null : results.getTimestamp("discontinued").toLocalDateTime();
				int company_id = results.getInt("company_id");
				int id = results.getInt("id");

				ComputerEntity computer = new ComputerEntity(id, name, introduced, discontinued, company_id);
				computers.add(computer);
			}
			return computers;

		} catch (Exception e) {
			System.out.println("Erreur dans l'exécution de la requête: " + e.getMessage());
		}

		return null;
	}

	/**
	 * Méthode qui va supprimer l'entree dont l'id correspond avec l'id de l'objet passé en paramètre
	 * @param computer
	 * 				objet à supprimer dans la bdd. 
	 */
	public void deleteComputer(ComputerEntity computer) {
		DBConnection dbConn = DBConnection.getInstance();
		String query = "delete from computer where id = ? ;";

		try (Connection conn = dbConn.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, computer.getId());
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.out.println("Erreur dans l'exécution de la requête");
		}
	}

	/**
	 * Méthode qui va lancer la requête de mise à jour dans la BDD en se basant sur l'id pour retrouver l'entrée à mettre à jour
	 * @param computer
	 * 				contient l'ordinnateur avec les nouvelles valeurs.
	 */
	public void updateComputer(ComputerEntity computer) {
		DBConnection dbConn = DBConnection.getInstance();
		String query = "update computer set name = ?, introduced = ?, discontinued = ?, company_id = ? where id = ? ;";

		try (Connection conn = dbConn.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, computer.getName());
			stmt.setInt(5, computer.getId());
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.out.println("Erreur dans l'exécution de la requête");
		}
	}
	
	public void createComputer(){
		ComputerEntity computer = new ComputerEntity();
		DBConnection dbConn = DBConnection.getInstance();
		String query = "insert into computer (name, introduced, discontinued, company_id) values (?, ?, ?, ?);";
		computer.createComputer();
		
		try (Connection conn = dbConn.getConnection()){
			PreparedStatement stm = conn.prepareStatement(query);
			stm.setString(1, computer.getName());
			stm.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced()));
			stm.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued()));
			stm.setInt(4, computer.getCompanyId());
			stm.executeUpdate();
		} catch (Exception e) {
			System.out.println("La création a échoué: " + e.getMessage());
		}
	}
}
