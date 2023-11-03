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
 * Servlet implementation class NewCourseServlet
 */
@WebServlet("/create-course")
public class NewCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CourseDbUtil courseDbUtil;
	private UserDbUtil userDbUtil;
	
	@Resource(name="jdbc/dorime")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		try {
			courseDbUtil = new CourseDbUtil(dataSource);
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
		
		// checking if there is a user logged in
		if(session.getAttribute("username")==null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login");
			dispatcher.forward(request, response);
		}
				
		// checking if the user is admin
		if((int) session.getAttribute("userType")!=0) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/homepage");
			dispatcher.forward(request, response);
		}
		
		try {
			// get all teachers list
			ArrayList<User> teachersList = userDbUtil.getTeachers();
			
			// setting teachers list as request attribute
			request.setAttribute("TEACHERS_LIST", teachersList);
			
			// sending to new-course.jsp
			RequestDispatcher dispatcher = request.getRequestDispatcher("/new-course.jsp");
			dispatcher.forward(request, response);
			
		}catch(Exception exc) {
			throw new ServletException(exc);
		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			// fetching request parameters
			String title = request.getParameter("title");
			String imagePath = request.getParameter("image-path");
			String details = request.getParameter("details");
			String price = request.getParameter("price");
			String courseTeacherId = request.getParameter("course-teacher-id");
			
//			
//			System.out.println(title);
//			System.out.println(details);
//			System.out.println(price);
//			System.out.println(courseTeacherId);
//			System.out.println(imagePath);
			
			// fetching teacher from user id
			User teacher = userDbUtil.getTeacher(courseTeacherId);
			
			
			
			float parsedPrice = Float.parseFloat(price);
			int parsedCourseTeacherId = Integer.parseInt(courseTeacherId);
			
			Course course = new Course(title, imagePath, parsedPrice, details, parsedCourseTeacherId, teacher.getUsername());
			
			// adding course to database
			courseDbUtil.addCourse(course);
			

//			request.setAttribute("post", true);
			
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/homepage");
//			dispatcher.forward(request, response);
			
			// redirecting to homepage
			response.sendRedirect("homepage");
			
			
		}catch(Exception exc) {
			throw new ServletException(exc);
			
		}
		
		
	}

}
