package com.obss.javasummerschool.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	
	public void saveUser(User user) throws SQLException {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = getDbConnection();

			// 3- Create SQL statements
			String sql = "insert into users(first_name, last_name, email) values(?, ?, ?)";
			statement = conn.prepareStatement(sql);

			// 4- Execute SQL query
			statement.setString(1, user.getName());
			statement.setString(2, user.getSurname());
			statement.setString(3, user.getEmail());
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, statement, null);
		}
	}
	
	private Connection getDbConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_users?allowPublicKeyRetrieval=true&useSSL=false",
					"admin", "admin");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	private void closeConnection(Connection conn, Statement statement, ResultSet resultSet) throws SQLException {
		if(resultSet != null) {
			resultSet.close();
		}
		
		if(statement != null) {
			statement.close();
		}
		
		if(conn != null) {
			conn.close();
		}
	}

}
