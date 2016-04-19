package com.excilys.computerdatabase.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.entities.CompanyEntity;

public class CompanyDAO {

	public ArrayList<CompanyEntity> getCompanies(){
		DBConnection dbConn = DBConnection.getInstance();
//		Connection conn = null;
		String query = "select * from company";
		ResultSet rsts;
		ArrayList<CompanyEntity> companies = new ArrayList<CompanyEntity>();
		
//		conn = dbConn.getConnection();
		
		try (Connection conn = dbConn.getConnection()){
			Statement statement = conn.createStatement();
			rsts = statement.executeQuery(query);
			while(rsts.next()){
				String name = rsts.getString("Name");
				int id = rsts.getInt("id");
				CompanyEntity company = new CompanyEntity(id, name);
				companies.add(company);
			}
		} catch (Exception e) {
			System.out.println("Erreur dans l'exécution de la requête");
		}
		
		return companies;
	}
}
