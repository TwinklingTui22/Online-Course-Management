package com.example.dorime;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class homepage
 */
@WebServlet("/homepage")
public class HomepageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	private CourseDbUtil courseDbUtil;
	
	@Resource(name="jdbc/dorime")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			courseDbUtil = new CourseDbUtil(dataSource);
		} catch(Exception ex) {
			throw new ServletException(ex);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession session = request.getSession();
		
		// checking if a user is logged in
		if(session.getAttribute("username") == null) {
			response.sendRedirect("login");
			return;
		}
		
		
		
		try {
			
			//getting user type from session
			int userType =  (int) session.getAttribute("userType");
			
			// loading homepages according to the user type
			switch (userType) {

				case 0:
					loadAdminHomepage(request, response);
					break;
					
				case 1:
					loadStudentHomepage(request, response);
					break;
					
				case 2:
					loadTeacherHomepage(request, response);
					break;
			}
			
			
		}catch(Exception exc) {
			throw new ServletException(exc);
		}
	

	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		
//		if(request.getParameter("post")!=null) {
//			doGet(request,response);
//		}
//		
		try {
			
			HttpSession session = request.getSession();
			
			//fetching request parameters
			String courseId = request.getParameter("course_id");
			int userId = (int) session.getAttribute("userId");
			String username = (String) session.getAttribute("username");
			String email = (String) session.getAttribute("email");
			
			//enrolling student to course in database
			courseDbUtil.addRegistration(courseId, userId, username, email);
			
			//redirecting to homepage
			response.sendRedirect("homepage");	
			
			
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}





	private void loadAdminHomepage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// getting all the course for admin page
		ArrayList<Course> allCourses = courseDbUtil.getAllCourses();
		
		// setting request attributes
		request.setAttribute("ALL_COURSE_LIST", allCourses);
		
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		request.setAttribute("USERNAME", username);
	
		// sending to admin-homepage.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin-homepage.jsp");
		dispatcher.forward(request, response);	
	}


	private void loadStudentHomepage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//fetching user infos from session
		HttpSession session = request.getSession();
		int studentId = (int) session.getAttribute("userId");
		String username = (String) session.getAttribute("username");
		String email = (String) session.getAttribute("email");
		
		// getting course data from database
		ArrayList<Course> enrolledCourses = courseDbUtil.getStudentCourses(studentId);
		ArrayList<Course> availableCourses = courseDbUtil.getAvailableCourses(studentId);
		
		// setting request attributes
		request.setAttribute("USERID", studentId);
		request.setAttribute("USERNAME", username);
		request.setAttribute("EMAIL", email);
		request.setAttribute("ENROLLED_COURSES", enrolledCourses);
		request.setAttribute("AVAILABLE_COURSES", availableCourses);

		// sending to student homepage
		RequestDispatcher dispatcher = request.getRequestDispatcher("/student-homepage.jsp");
		dispatcher.forward(request, response);	
		
	}
	
	private void loadTeacherHomepage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		// getting user infos from session
		HttpSession session = request.getSession();
		int teacherId = (int) session.getAttribute("userId");
		String username = (String) session.getAttribute("username");
		
		// getting assigned courses for the teacher
		ArrayList<Course> assignedCourses = courseDbUtil.getTeacherCourses(teacherId);
		
		// setting request attributes
		request.setAttribute("USERNAME", username);
		request.setAttribute("ASSIGNED_COURSES", assignedCourses);

		// sending to teacher homepage
		RequestDispatcher dispatcher = request.getRequestDispatcher("/teacher-homepage.jsp");
		dispatcher.forward(request, response);
		
	}

	

}
