package com.example.dorime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;


public class UserDbUtil {
	
	private DataSource datasource;
	
	public UserDbUtil(DataSource datasource) {
		this.datasource = datasource;
	}
	
	public User getUser(String inputEmail, String inputPassword) throws Exception {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			//get a connection
			conn = datasource.getConnection();
			
			// create sql statement
			String sql = "select * from users where email=? and password=?";
			
			stmt = conn.prepareStatement(sql);
			
			// set params
			stmt.setString(1, inputEmail);
			stmt.setString(2, inputPassword);

			// execute statement
			rs = stmt.executeQuery();
			
			// process result
			if(rs.next()) {
				// retrieve data from result row
				int userId = rs.getInt("user_id");
				String username = rs.getString("username");
				String email = rs.getString("email");
				int userType = rs.getInt("user_type");
				
				
				User user = new User(userId, username, email, userType);
				return user;
			} else {
				return null;
			}
		} finally {
			// close jdbc connection
			close(conn, stmt, rs);
		}
	}
	
	public ArrayList<User> getTeachers() throws Exception{
		ArrayList<User> teachersList = new ArrayList<User>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			//get a connection
			conn = datasource.getConnection();
			
			// create sql statement
			String sql = "select * from users where user_type=2";
			
			// set params
			stmt = conn.createStatement();
			
			// execute statement
			rs = stmt.executeQuery(sql);
			
			// process result
			while(rs.next()) {
				// retrieve data from result row
				int userId = rs.getInt("user_id");
				String username = rs.getString("username");
				String email = rs.getString("email");
				int userType = rs.getInt("user_type");
				
				User user = new User(userId, username, email, userType);
				teachersList.add(user);
			}
			
			return teachersList;
			
		}finally {
			// close jdbc connection
			close(conn, stmt, rs);
		}
		
		
	}
	
	
	public User getTeacher(String teacherId) throws Exception{
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int parsedTeacherId = Integer.parseInt(teacherId);
		
		try {
			conn = datasource.getConnection();
			
			String sql = "select * from users where user_id=?";
			
			stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, parsedTeacherId);
			
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				int userId = rs.getInt("user_id");
				String username = rs.getString("username");
				String email = rs.getString("email");
				int userType = rs.getInt("user_type");
				
				User user = new User(userId, username, email, userType);
				
				return user;
			}else {
				return null;
			}
			
			
		}finally {
			close(conn, stmt, rs);
		}
		
		
	}
	
	
	public ArrayList<User> getEnrolledStudents(String courseId) throws Exception{
		ArrayList<User> enrolledStudents = new ArrayList<User>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		int parsedCourseId = Integer.parseInt(courseId);
		
		try {
			conn = datasource.getConnection();
			
			String sql = "select * from registrations where course_id=?";
			
			stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, parsedCourseId);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				int userId = rs.getInt("user_id");
				String username = rs.getString("user_name");
				String email = rs.getString("user_email");
				
				User user = new User(userId, username, email, 1);
				enrolledStudents.add(user);
			}
			
			return enrolledStudents;
			
		}finally {
			close(conn, stmt, rs);
		}
		
		
	}
	
	
	
	
	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		try {
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				myConn.close();   // doesn't really close it ... just puts back in connection pool
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}

}
