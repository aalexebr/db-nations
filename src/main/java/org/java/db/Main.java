package org.java.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	private static final String url = "jdbc:mysql://localhost:3306/db_nations";
	private static final String user = "root";
	private static final String pws = "root";
	
	public static void main(String[] args) {
		
//		milestone2();
		
		milestone3();
	}
	
	private static void milestone2() {
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
				    		
				    		System.out.println("country: "+country+" | "
				    				+"country id: "+ countryId+ " | "
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
	
	private static void milestone3() {
		
		Scanner in = new Scanner(System.in);
		
		System.out.print("what country do you want to search?");
		String userQuery = in.nextLine();
		
//		in.close();
		
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
					+ " WHERE countries.name LIKE ? "
					+ " GROUP BY countries.country_id; ";
			
				try (PreparedStatement ps = con.prepareStatement(sqlQuery)) {
				
						ps.setString(1, "%"+userQuery+"%");
						
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
							    	

							    }
						 }
							
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.print("give me an id of the selected country: ");
		String userId = in.nextLine();
		int id = Integer.valueOf(userId);
		
		in.close();
		
		bonus(id);
	}
	
	private static void bonus(int id) {
		String countrySelected = "";
		List <String> languages = new ArrayList<>();
		
		try (Connection con = DriverManager.getConnection(url, user, pws)){
			
			String sqlQuery = " SELECT languages.language, countries.name "
					+ " FROM countries\r\n"
					+ " JOIN country_languages "
					+ " ON country_languages.country_id = countries.country_id "
					+ " JOIN languages  "
					+ " ON country_languages.language_id = languages.language_id "
					+ " WHERE countries.country_id = ?; ";
			
				try (PreparedStatement ps = con.prepareStatement(sqlQuery)) {
				
						ps.setInt(1, id);
						
							    try(ResultSet rs = ps.executeQuery()){
							    	
							    	int counter = 0;
							    	
							    	
							    	while(rs.next()) {
							    		
							    		String language = rs.getString(1);
							    		countrySelected = rs.getString(2);
							    		languages.add(language);
							    		
							    		counter ++;
							    		
							    	}
							    	

							    }
						 }
							
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("country selected: "+ countrySelected);
		System.out.println("languages:\n"+languages);
		
		try (Connection con = DriverManager.getConnection(url, user, pws)){
			
			String sqlQuery = " SELECT country_stats.population, country_stats.gdp, country_stats.year "
					+ " FROM countries "
					+ " JOIN country_stats "
					+ " ON country_stats.country_id = countries.country_id "
					+ " WHERE countries.country_id = ? "
					+ " ORDER BY country_stats.year DESC "
					+ " LIMIT 1;";
			
				try (PreparedStatement ps = con.prepareStatement(sqlQuery)) {
				
						ps.setInt(1, id);
						
							    try(ResultSet rs = ps.executeQuery()){
							    	
							    	
							    	while(rs.next()) {
							    		
							    		String population = rs.getString(1);
							    		int year = rs.getInt(3);
							    		String gdp = rs.getString(2);
							    		
							    		
							    		System.out.println("Most recent stats: \n"+
							    				"Year: "+year+"\n"+
							    				"Population: "+population+"\n"+
							    				"GDP: "+gdp);
							    		
							    		
							    	}
							    	

							    }
						 }
							
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
}
