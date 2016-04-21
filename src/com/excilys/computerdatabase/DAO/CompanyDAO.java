package com.excilys.computerdatabase.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.excilys.computerdatabase.entities.CompanyEntity;

import Mappers.CompanyMapper;

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
				CompanyMapper cm = CompanyMapper.getInstance();
				CompanyEntity company = cm.map(rsts);
				companies.add(company);
			}
		} catch (Exception e) {
			System.out.println("Erreur lors de l'extraction des company: " + e.getMessage());
		} finally {
			dbConn.closeConnection();
		}

		return companies;
	}

	public CompanyEntity getById(Long id) {
		DBConnection dbConn = DBConnection.getInstance();
		Connection conn = dbConn.getConnection();
		String query = "select * from company where id = ?";
		ResultSet rsts;
		CompanyEntity company = null;

		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setLong(1, id);
			rsts = stmt.executeQuery();
			String name = rsts.getString("Name");
			company = new CompanyEntity(id, name);
		} catch (Exception e) {
			System.out.println("Erreur lors de l'extraction des company: " + e.getMessage());
		} finally {
			dbConn.closeConnection();
		}

		return company;
	}
}
