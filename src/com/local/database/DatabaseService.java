package com.local.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;

public class DatabaseService {
	Connection c = null;
	Statement stmt = null;

	// creating tables, insert, delete
	public String executeQuery(String query) {

		try {
			Class.forName("org.sqlite.JDBC");
//			Properties properties = new Properties();
//			properties.setProperty("PRAGMA foreign_keys", "ON");
//			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			c = DriverManager.getConnection("jdbc:sqlite:test.db;foreign keys=true;");

			stmt = c.createStatement();
			stmt.execute("PRAGMA foreign_keys = ON");
			stmt.executeUpdate(query);
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return e.getMessage();
		}
		System.out.println("Operation done successfully");
		return "success";
	}

	public JSONArray getObjects(String query) {
		JSONArray array = new JSONArray();
		try {
			Class.forName("org.sqlite.JDBC");
//			Properties properties = new Properties();
//			properties.setProperty("PRAGMA foreign_keys", "ON");
//			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			c = DriverManager.getConnection("jdbc:sqlite:test.db;foreign keys=true;");

			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			stmt = c.createStatement();
			stmt.execute("PRAGMA foreign_keys = ON");

			ResultSet resultSet = stmt.executeQuery(query);
			array = convertResultSetToJSONArray(resultSet);
			stmt.close();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return array;
	}

	private JSONArray convertResultSetToJSONArray(ResultSet resultSet) {
		int mdCount;
		String name;
		Object value;
		ResultSetMetaData rsmd;
		JSONArray array = new JSONArray();
		try {
			rsmd = resultSet.getMetaData();
			mdCount = rsmd.getColumnCount();
			while (resultSet.next()) {
				JSONObject object = new JSONObject();
				for (int i = 1; i <= mdCount; i++) {
					name = rsmd.getColumnName(i);
					value = resultSet.getObject(i);
					if (value != null) {
						String str = value.toString();
						object.put(name, str);
					}
				}
				array.put(object);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error in the ResultSet");
		}
		return array;
	}
}
