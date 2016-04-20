package com.excilys.computerdatabase.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.entities.CompanyEntity;

public class CompanyDAO {

	public ArrayList<CompanyEntity> getCompanies() {
		DBConnection dbConn = DBConnection.getInstance();
		String query = "select * from company";
		ResultSet rsts;
		ArrayList<CompanyEntity> companies = new ArrayList<CompanyEntity>();

		try {
			Statement statement = dbConn.getConnection().createStatement();
			rsts = statement.executeQuery(query);
			while (rsts.next()) {
				String name = rsts.getString("Name");
				int id = rsts.getInt("id");
				CompanyEntity company = new CompanyEntity(id, name);
				companies.add(company);
			}
		} catch (Exception e) {
			System.out.println("Erreur lors de l'extraction des company: " + e.getMessage());
		} finally {
			dbConn.closeConnection();
		}

		return companies;
	}

	public CompanyEntity getCompanyById(int id) {
		DBConnection dbConn = DBConnection.getInstance();
		Connection conn = dbConn.getConnection();
		String query = "select * from company where id = ?";
		ResultSet rsts;
		CompanyEntity companie = new CompanyEntity();

		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, id);
			rsts = stmt.executeQuery();
			String name = rsts.getString("Name");
			CompanyEntity company = new CompanyEntity(id, name);
			return company;
		} catch (Exception e) {
			System.out.println("Erreur lors de l'extraction des company: " + e.getMessage());
		} finally {
			dbConn.closeConnection();
		}

		return null;
	}
}
