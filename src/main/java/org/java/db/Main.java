package org.java.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {
	
	private static final String url = "jdbc:mysql://localhost:3306/db_nations";
	private static final String user = "root";
	private static final String pws = "root";
	
	public static void main(String[] args) {
		
		milestone1();
	}
	
	private static void milestone1() {
		System.out.println("m1");
		
		try (Connection con = DriverManager.getConnection(url, user, pws)){
			
			String sqlQuery = " SELECT countries.name as country_name, countries.country_id as country_id, regions.name as regon_name, continents.name as continent_name "
					+ " FROM continents "
					+ " JOIN regions "
					+ " ON continents.continent_id = regions.continent_id "
					+ " JOIN countries "
					+ " ON regions.region_id = countries.region_id "
					+ " JOIN country_languages "
					+ " ON country_languages.country_id = countries.country_id "
					+ " JOIN languages "
					+ " ON languages.language_id = country_languages.language_id "
					+ " GROUP BY countries.country_id; ";
			
			 try(PreparedStatement ps = con.prepareStatement(sqlQuery)){
				    try(ResultSet rs = ps.executeQuery()){
				    	
				    	int counter = 0;
				    	
				    	while(rs.next()) {
				    		
				    		String country = rs.getString(1);
				    		int countryId = rs.getInt(2);
				    		String region = rs.getString(3);
				    		String continent = rs.getString(4);
				    		counter ++;
				    		
				    		System.out.println("country: "+country+" - "
				    				+"country id: "+ countryId+ " - "
				    				+"region: "+region+" - "
				    				+"continent: "+continent);
				    		
				    		
				    	}
				    	System.out.println("counter: "+ counter);
				    }
			 }
			
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
}
