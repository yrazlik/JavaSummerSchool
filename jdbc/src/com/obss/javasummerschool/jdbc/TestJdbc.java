package com.obss.javasummerschool.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestJdbc
 */
@WebServlet("/TestJdbc")
public class TestJdbc extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 1- Set up the print writer
		PrintWriter writer = response.getWriter();
		response.setContentType("text/plain");

		// 2- Get a connection to the database
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			conn = getDbConnection();

			// 3- Create SQL statements
			String sql = "select * from users";
			statement = conn.createStatement();

			// 4- Execute SQL query
			resultSet = statement.executeQuery(sql);

			// 5- Process the result set
			while (resultSet.next()) {
				String email = resultSet.getString("email");
				writer.println(email);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Connection getDbConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/db_users?allowPublicKeyRetrieval=true&useSSL=false",
					"admin", "admin");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
