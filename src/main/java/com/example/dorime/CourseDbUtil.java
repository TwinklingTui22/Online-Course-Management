package com.example.dorime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

public class CourseDbUtil {

	private DataSource datasource;
	
	public CourseDbUtil(DataSource datasource) {
		this.datasource = datasource;
	}
	
	public ArrayList<Course> getAllCourses() throws Exception{
		ArrayList<Course> courses = new ArrayList<Course>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			// get a connection
			conn = datasource.getConnection();
			
			// create sql statement
			String sql = "select * from courses";
			
			stmt = conn.createStatement();
			
			// execute statement
			rs = stmt.executeQuery(sql);
			
			// process result
			while(rs.next()) {
				// retrieve data from result row
				int courseId = rs.getInt("course_id");
				String title = rs.getString("title");
				String imagePath = rs.getString("image_path");
				String details = rs.getString("details");
				float price = rs.getFloat("price");
				int courseTeacherId = rs.getInt("course_teacher_id");
				String courseTeacherName = rs.getString("course_teacher_name");
				
				Course course = new Course(courseId, title, imagePath, price, details, courseTeacherId, courseTeacherName);
				courses.add(course);
				
			}
			
			return courses;
			
		}finally {
			// close jdbc connection
			close(conn, stmt, rs);
		}
		
	}
	
	public ArrayList<Course> getStudentCourses(int studentId) throws Exception{
		ArrayList<Course> courses = new ArrayList<Course>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		
		try {
			// get a connection
			conn = datasource.getConnection();
			
			// create sql statement
			String sql = "select * from courses inner join (select * from registrations where user_id = ?) reg on courses.course_id = reg.course_id";
			
			stmt = conn.prepareStatement(sql);
			
			// set param values
			stmt.setInt(1, studentId);
			
			// execute statement
			rs = stmt.executeQuery();
			
			// process result
			while(rs.next()) {
				// retrieve data from result row
				int courseId = rs.getInt("course_id");
				String title = rs.getString("title");
				String imagePath = rs.getString("image_path");
				String details = rs.getString("details");
				float price = rs.getFloat("price");
				int courseTeacherId = rs.getInt("course_teacher_id");
				String courseTeacherName = rs.getString("course_teacher_name");
				
				Course course = new Course(courseId, title, imagePath, price, details, courseTeacherId, courseTeacherName);
				courses.add(course);
				
			}
			
			return courses;
			
		}finally {
			// close jdbc connection
			close(conn, stmt, rs);
		}
		
	}
	
	
	public ArrayList<Course> getTeacherCourses(int teacherId) throws Exception{
		ArrayList<Course> courses = new ArrayList<Course>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		
		try {
			// get a connection
			conn = datasource.getConnection();
			
			// create sql statement
			String sql = "select * from courses where course_teacher_id=?";
			
			stmt = conn.prepareStatement(sql);
			
			// set param values
			stmt.setInt(1, teacherId);
			
			// execute statement
			rs = stmt.executeQuery();
			
			// process result
			while(rs.next()) {
				// retrieve data from result row
				int courseId = rs.getInt("course_id");
				String title = rs.getString("title");
				String imagePath = rs.getString("image_path");
				String details = rs.getString("details");
				float price = rs.getFloat("price");
				int courseTeacherId = rs.getInt("course_teacher_id");
				String courseTeacherName = rs.getString("course_teacher_name");
				
				Course course = new Course(courseId, title, imagePath, price, details, courseTeacherId, courseTeacherName);
				courses.add(course);
				
			}
			
			return courses;
			
		}finally {
			// close jdbc connection
			close(conn, stmt, rs);
		}
		
	}
	
	
	
	public ArrayList<Course> getAvailableCourses(int studentId) throws Exception{
		ArrayList<Course> courses = new ArrayList<Course>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "select * from courses where course_id not in (select course_id from registrations where user_id=?)";
			
			stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, studentId);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				int courseId = rs.getInt("course_id");
				String title = rs.getString("title");
				String imagePath = rs.getString("image_path");
				String details = rs.getString("details");
				float price = rs.getFloat("price");
				int courseTeacherId = rs.getInt("course_teacher_id");
				String courseTeacherName = rs.getString("course_teacher_name");
				
				Course course = new Course(courseId, title, imagePath, price, details, courseTeacherId, courseTeacherName);
				courses.add(course);
				
			}
			
			return courses;
			
		}finally {
			close(conn, stmt, rs);
		}
		
	}
	
	
	public void addCourse(Course course) throws Exception{
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "insert into courses (title, image_path, details, price, course_teacher_id, course_teacher_name) values (?,?,?,?,?,?)";
			
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, course.getTitle());
			stmt.setString(2, course.getImage_path());
			stmt.setString(3, course.getDetails());
			stmt.setFloat(4, course.getPrice());
			stmt.setInt(5, course.getCourse_teacher_id());
			stmt.setString(6, course.getCourse_teacher_name());
			
			stmt.execute();
			
			
		} finally {
			
			close(conn, stmt, null);
		}
		
	}
	
	
	
	public void addRegistration(String courseId, int userId, String username, String email) throws Exception{
		
		Connection conn = null;
		PreparedStatement stmt = null;
		int parsedCourseId = Integer.parseInt(courseId);
		
		try {
			conn = datasource.getConnection();
			
			String sql = "insert into registrations (user_id, course_id, user_name, user_email) values (?, ?, ?, ?)";
			
			stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, userId);
			stmt.setInt(2, parsedCourseId);
			stmt.setString(3, username);
			stmt.setString(4, email);
			
			stmt.execute();
			
		} finally {
			
			close(conn, stmt, null);
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
