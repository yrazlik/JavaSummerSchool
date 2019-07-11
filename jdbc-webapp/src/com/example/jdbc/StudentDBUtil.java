package com.example.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDBUtil {
	
	private DataSource dataSource;
	
	public StudentDBUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<Student> getStudents() throws SQLException {
		List<Student> students = new ArrayList<>();
		
		Connection conn = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			conn = getDbConnection();//dataSource.getConnection();

			// 3- Create SQL statements
			String sql = "select * from student order by last_name";
			statement = conn.createStatement();

			// 4- Execute SQL query
			resultSet = statement.executeQuery(sql);

			// 5- Process the result set
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String email = resultSet.getString("email");
				students.add(new Student(id, firstName, lastName, email));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, statement, resultSet);
		}
		return students;
	}
	
	private void close(Connection conn, Statement statement, ResultSet resultSet) throws SQLException {
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

	public void addStudent(Student student) throws SQLException {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = getDbConnection();//dataSource.getConnection();

			// 3- Create SQL statements
			String sql = "insert into student(first_name, last_name, email) values(?, ?, ?)";
			statement = conn.prepareStatement(sql);

			// 4- Execute SQL query
			statement.setString(1, student.getFirstName());
			statement.setString(2, student.getLastName());
			statement.setString(3, student.getEmail());
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, statement, null);
		}
	}

	public void updateStudent(Student student) throws SQLException {
		Connection conn = null;
		PreparedStatement statement = null;

		try {
			conn = getDbConnection();//dataSource.getConnection();

			// 3- Create SQL statements
			String sql = "update student set first_name=?, last_name=?, email=? where id=?";
			statement = conn.prepareStatement(sql);

			// 4- Execute SQL query
			statement.setString(1, student.getFirstName());
			statement.setString(2, student.getLastName());
			statement.setString(3, student.getEmail());
			statement.setInt(4, student.getId());
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, statement, null);
		}
	}
	
	private Connection getDbConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_student_tracker?allowPublicKeyRetrieval=true&useSSL=false",
					"webstudent", "webstudent");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
