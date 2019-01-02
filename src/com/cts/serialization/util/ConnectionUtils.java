package com.cts.serialization.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtils {

	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";  
	static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	static final String USER = "system";
	static final String PASS = "manager";

	private static Connection connection;

	public static Connection getConnection() {
		if(connection == null) {
			try {
				Class.forName(JDBC_DRIVER);
				connection = DriverManager.getConnection(DB_URL,USER,PASS);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return connection;
	}
	public static void closeConnection() {
		try {
			if(connection != null) {
				connection.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
