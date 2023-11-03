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
 * Servlet implementation class EnrolledStudentsServlet
 */
@WebServlet("/enrolled-students")
public class EnrolledStudentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDbUtil userDbUtil;
	
	@Resource(name="jdbc/dorime")
	private DataSource dataSource;
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			userDbUtil = new UserDbUtil(dataSource);
		} catch(Exception ex) {
			throw new ServletException(ex);
		}
		
	}
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession session = request.getSession();
		
		//checking if a user is logged in
		if(session.getAttribute("username")==null) {
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
//			dispatcher.forward(request, response);
			response.sendRedirect("login");
			return;
		}
		
		// checking if the user is a teacher. if not redirecting to homepage
		if((int) session.getAttribute("userType")!=2) {
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/homepage");
//			dispatcher.forward(request, response);
			response.sendRedirect("homepage");
			return;
		}
		
		// fetching request parameters
		String courseId = request.getParameter("course_id");
		String courseTitle = request.getParameter("title");
		String username = (String) session.getAttribute("username");
		
		try {
			
			// get enrolled students of the course from database
			ArrayList<User> enrolledStudents = userDbUtil.getEnrolledStudents(courseId);
			
			// setting request attributes
			request.setAttribute("ENROLLED_STUDENTS", enrolledStudents);
			request.setAttribute("TITLE", courseTitle);
			request.setAttribute("USERNAME", username);
			
			// sending to enrolled-students.jsp
			RequestDispatcher dispatcher = request.getRequestDispatcher("/enrolled-students.jsp");
			dispatcher.forward(request, response);
			
		}catch(Exception e) {
			throw new ServletException(e);
		}

		
	}



}
