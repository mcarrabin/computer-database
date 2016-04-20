package com.excilys.computerdatabase.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.excilys.computerdatabase.entities.CompanyEntity;
import com.excilys.computerdatabase.entities.ComputerEntity;

public class ComputerDAO {
	
	/**
	 * Méthode qui va construire une liste de toutes les entrées computer contenues en BDD.
	 * @return
	 * 		la liste de toutes ces
		ArrayList<ComputerEntity> computers = new ArrayList<ComputerEntity>(); entrées
	 */
	public ArrayList<ComputerEntity> getComputers() {
		ArrayList<ComputerEntity> computers = new ArrayList<ComputerEntity>();
		DBConnection dbConn = DBConnection.getInstance();
		Connection conn = dbConn.getConnection();
		ResultSet results = null;
		String query = "select * from computer c left join company comp on comp.id = c.company_id order by c.name";

		try {
			Statement statement = conn.createStatement();
			results = statement.executeQuery(query);
			while (results.next()) {
				String name = results.getString("c.name");
				LocalDateTime introduced = results.getTimestamp("c.introduced") == null ? null : results.getTimestamp("introduced").toLocalDateTime();
				LocalDateTime discontinued = results.getTimestamp("c.discontinued") == null ? null : results.getTimestamp("discontinued").toLocalDateTime();
				int companyId = results.getInt("comp.id");
				String companyName = results.getString("comp.name");
				int id = results.getInt("id");

				CompanyEntity company = new CompanyEntity(companyId, companyName);
				ComputerEntity computer = new ComputerEntity(id, name, introduced, discontinued, company);
				computers.add(computer);
			}
			return computers;

		} catch (Exception e) {
			System.out.println("Erreur dans l'exécution de la requête: " + e.getMessage());
		} finally {
			dbConn.closeConnection();
		}

		return null;
	}
	/**
	 * Méthode qui va retourner un ensemble d'ordinateurs en se basant sur le
	 * nom passé en paramètre
	 * 
	 * @param name:
	 *            nom de l'ordinateur à afficher
	 * @return le ou les ordinateurs dont le nom correspond au paramètre
	 */
	public ArrayList<ComputerEntity> getComputerByName(String name) {
		DBConnection dbConn = DBConnection.getInstance();
		Connection conn = dbConn.getConnection();
		String query = "select * from computer c left join company comp on comp.id = c.company_id where c.name like ? ";
		ResultSet results = null;
		ArrayList<ComputerEntity> computers = new ArrayList<ComputerEntity>();

		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, "%" + name + "%");
			results = stmt.executeQuery();
			while (results.next()) {
				int id = results.getInt("c.id");
				LocalDateTime introduced = results.getTimestamp("c.introduced") == null ? null
						: results.getTimestamp("c.introduced").toLocalDateTime();
				LocalDateTime discontinued = results.getTimestamp("c.discontinued") == null ? null
						: results.getTimestamp("c.discontinued").toLocalDateTime();
				int companyId = results.getInt("c.company_id");
				String companyName = results.getString("comp.name");
				
				CompanyEntity company = new CompanyEntity(companyId, companyName);
				ComputerEntity computer = new ComputerEntity(id, name, introduced, discontinued, company);
				computers.add(computer);
			}
		} catch (Exception e) {
			System.out.println("Erreur lors de l'exécution de la requête: \n" + e.getMessage());
		} finally {
			dbConn.closeConnection();
		}
		return computers;
	}
	
	public ArrayList<ComputerEntity> getComputerPage(int start, int end){
		DBConnection dbConn = DBConnection.getInstance();
		Connection conn = dbConn.getConnection();
		String query = "SELECT * FROM computer c LEFT JOIN company comp ON comp.id = c.company_id LIMIT ? OFFSET ? ";
		ResultSet results = null;
		ArrayList<ComputerEntity> computers = new ArrayList<ComputerEntity>();

		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, start);
			stmt.setInt(2, end);
			results = stmt.executeQuery();
			while (results.next()) {
				int id = results.getInt("c.id");
				LocalDateTime introduced = results.getTimestamp("c.introduced") == null ? null
						: results.getTimestamp("c.introduced").toLocalDateTime();
				LocalDateTime discontinued = results.getTimestamp("c.discontinued") == null ? null
						: results.getTimestamp("c.discontinued").toLocalDateTime();
				String name = results.getString("c.name");
				int companyId = results.getInt("c.company_id");
				String companyName = results.getString("comp.name");
				
				CompanyEntity company = new CompanyEntity(companyId, companyName);
				ComputerEntity computer = new ComputerEntity(id, name, introduced, discontinued, company);
				computers.add(computer);
			}
		} catch (Exception e) {
			System.out.println("Erreur lors de l'exécution de la requête: \n" + e.getMessage());
		} finally {
			dbConn.closeConnection();
		}
		return computers;
	}
	
	/**
	 * Méthode qui va récupérer une entree de la table computer en se basant sur l'id passé en paramètre
	 * @param id: id de l'ordinateur à récupérer
	 * @return l'ordinateur récupéré en BDD
	 */
	public ComputerEntity getComputerById(int id){
		DBConnection dbConn = DBConnection.getInstance();
		String query = "select * from computer c left join company comp on comp.id = c.company_id where c.id = ? ";
		ResultSet results = null;
		ComputerEntity computer = new ComputerEntity();

		try (Connection conn = dbConn.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			results = stmt.executeQuery();
			while (results.next()) {
				LocalDateTime introduced = results.getTimestamp("c.introduced") == null ? null
						: results.getTimestamp("c.introduced").toLocalDateTime();
				LocalDateTime discontinued = results.getTimestamp("c.discontinued") == null ? null
						: results.getTimestamp("c.discontinued").toLocalDateTime();
				String name = results.getString("c.name");
				int companyId = results.getInt("c.company_id");
				String companyName = results.getString("comp.name");
				
				CompanyEntity company = new CompanyEntity(companyId, companyName);
				computer = new ComputerEntity(id, name, introduced, discontinued, company);
			}
		} catch (Exception e) {
			System.out.println("Erreur lors de l'exécution de la requête: \n" + e.getMessage());
		} finally {
			dbConn.closeConnection();
		}
		return computer;
	}
	
	public ArrayList<ComputerEntity> getComputersByPage(int start, int end){
		DBConnection dbConn = DBConnection.getInstance();
		Connection conn = dbConn.getConnection();
		ArrayList<ComputerEntity> computers = new ArrayList<ComputerEntity>();
		
		return computers;
	}

	/**
	 * Méthode qui va supprimer l'entree dont l'id correspond avec l'id de l'objet passé en paramètre
	 * @param computer
	 * 				objet à supprimer dans la bdd. 
	 */
	public void deleteComputer(ComputerEntity computer) {
		DBConnection dbConn = DBConnection.getInstance();
		String query = "delete from computer where id = ? ;";
		Connection conn = dbConn.getConnection();

		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, computer.getId());
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("Erreur dans l'exécution de la requête: "+e.getMessage());
		} finally {
			dbConn.closeConnection();
		}
	}

	/**
	 * Méthode qui va lancer la re
		ArrayList<ComputerEntity> computers = new ArrayList<ComputerEntity>();quête de mise à jour dans la BDD en se basant sur l'id pour retrouver l'entrée à mettre à jour
	 * @param computer
	 * 				contient l'ordinnateur avec les nouvelles valeurs.
	 */
	public void updateComputer(ComputerEntity computer) {
		DBConnection dbConn = DBConnection.getInstance();
		Connection conn = dbConn.getConnection();
		String query = "update computer set name = ?, introduced = ?, discontinued = ?, company_id = ? where id = ?";

		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, computer.getName());
			if(computer.getIntroduced() != null ) 
				stmt.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced()));
			else
				stmt.setNull(2, java.sql.Types.TIMESTAMP);
			if(computer.getDiscontinued() != null) {
				stmt.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued()));
			}
			else {
				ArrayList<ComputerEntity> computers = new ArrayList<ComputerEntity>();
				stmt.setNull(3, java.sql.Types.TIMESTAMP);
			}
			stmt.setInt(4, computer.getCompany().getId());
			stmt.setInt(5, computer.getId());
			stmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("Erreur dans l'exécution de la requête: " + e.getMessage());
		} finally {
			dbConn.closeConnection();
		}
	}

	/**
	 * Méthode appelée pour créer un computer et l'insérer en bdd.
	 */
	public void createComputer(ArrayList<CompanyEntity> companies){
		ComputerEntity computer = new ComputerEntity();
		DBConnection dbConn = DBConnection.getInstance();
		Connection conn = dbConn.getConnection();
		String query = "insert into computer (name, introduced, discontinued, company_id) values (?, ?, ?, ?)";
		
		computer.createComputer(companies);
		
		try {
			PreparedStatement stm = conn.prepareStatement(query);
			stm.setString(1, computer.getName());
			if(computer.getIntroduced() != null ) 
				stm.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced()));
			else
				stm.setNull(2, java.sql.Types.TIMESTAMP);
			if(computer.getDiscontinued() != null) {
				stm.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued()));
			}
			else {
				stm.setNull(3, java.sql.Types.TIMESTAMP);
			}
			
			stm.setInt(4, computer.getCompany().getId());
			stm.executeUpdate();
			System.out.println("l'ordinateur suivant a été inséré: \n" + computer.toString());
		} catch (Exception e) {
			System.out.println("La création a échoué: " + e.getMessage());
		} finally {
			dbConn.closeConnection();
		}
	}

	/**
	 * Méthode qui va interroger la bdd pour connaître l'existance ou pas de l'id passer en paramètre
	 * @param id: id à chercher en base
	 * @return true si l'id est en bdd, false sinon
	 */
	public boolean checkComputerExists(int id) {
		DBConnection dbConn = DBConnection.getInstance();
		Connection conn = dbConn.getConnection();
		String query = "select 1 from computer where id = ?";
		ResultSet res = null;
		boolean ok = false;

		try {
			PreparedStatement stm = conn.prepareStatement(query);
			stm.setInt(1, id);
			res = stm.executeQuery();
			if (res.next())
				ok = true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			dbConn.closeConnection();
		}

		return ok;
	}
}
